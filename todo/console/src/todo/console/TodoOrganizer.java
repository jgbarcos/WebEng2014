package todo.console;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class TodoOrganizer {

	/*
	 * DEFAULT Constants:
	 */
	public final static String DEFAULT_FILE_NAME = "todoList.json";

	private final static TaskPriority DEFAULT_PRIORITY = TaskPriority.NORMAL;
	private final static TaskStatus DEFAULT_STATUS = TaskStatus.PENDING;
	
	private final static String PROGRAM_BANNER = 
		" ______        ____              \n"+
		"/\\__  _\\      /\\  _`\\            \n"+
		"\\/_/\\ \\/   ___\\ \\ \\/\\ \\    ___   \n"+
		"   \\ \\ \\  / __`\\ \\ \\ \\ \\  / __`\\ \n"+
		"    \\ \\ \\/\\ \\L\\ \\ \\ \\_\\ \\/\\ \\L\\ \\\n"+
		"     \\ \\_\\ \\____/\\ \\____/\\ \\____/\n"+
		"      \\/_/\\/___/  \\/___/  \\/___/ \n"+                             
		" _____                                                            \n"+
		"/\\  __`\\                                __                        \n"+
		"\\ \\ \\/\\ \\  _ __    __      __      ___ /\\_\\  ____      __   _ __  \n"+
		" \\ \\ \\ \\ \\/\\`'__\\/'_ `\\  /'__`\\  /' _ `\\/\\ \\/\\_ ,`\\  /'__`\\/\\`'__\\\n"+
		"  \\ \\ \\_\\ \\ \\ \\//\\ \\L\\ \\/\\ \\L\\.\\_/\\ \\/\\ \\ \\ \\/_/  /_/\\  __/\\ \\ \\/ \n"+
		"   \\ \\_____\\ \\_\\\\ \\____ \\ \\__/.\\_\\ \\_\\ \\_\\ \\_\\/\\____\\ \\____\\\\ \\_\\ \n"+
		"    \\/_____/\\/_/ \\/___L\\ \\/__/\\/_/\\/_/\\/_/\\/_/\\/____/\\/____/ \\/_/ \n"+
		"                   /\\____/                                        \n"+
		"                   \\_/__/                                         \n";

	/*
	 * Menu Options Constants:
	 */
	private static final String SAVE_EXIT = "Save & Exit";
	private static final String CREATE_TASK = "Create Task";
	private static final String REMOVE_TASK = "Remove Task";
	private static final String FILTER_SEARCH = "Filter Search";

	public static void main(String[] args) {

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String filename = DEFAULT_FILE_NAME;
		
		boolean flag_interactive = false;
		String commandOption = null;
		
		if (args.length > 0) {
			filename = args[0];
			
			if(args.length > 1){
				String argument = args[1];
				if(argument.equals("--interactive") || argument.equals("-i")){
					flag_interactive = true;
				}
				else if(argument.equals("--add") || argument.equals("-a")){
					commandOption = CREATE_TASK;
				}
				else if(argument.equals("--remove") || argument.equals("-r")){
					commandOption = REMOVE_TASK;
				}
				else if(argument.equals("--search") || argument.equals("-s")){
					commandOption = FILTER_SEARCH;
				}
			}
		}

		try {
			// Load TodoList from JSON file:
			TodoList todoList = null;
			try {
				todoList = gson.fromJson(new FileReader(filename), TodoList.class);
			} catch (FileNotFoundException e) {
				System.out.println("Filename \"" + filename + "\" not found, a new one will be created");
				todoList = new TodoList();
			}
			
			if(flag_interactive){
				// Print Banner 
				System.out.println(PROGRAM_BANNER);
	
				// Start Interactive Console:
				interactiveConsole(todoList);
			}
			else if(commandOption != null){
				Scanner sc = new Scanner(System.in);
				if (commandOption.equals(CREATE_TASK)) {
					createTask(todoList, sc);
				} else if (commandOption.equals(REMOVE_TASK)) {
					removeTask(todoList, sc);
				} else if (commandOption.equals(FILTER_SEARCH)) {
					listTasks(filterTasks(todoList, sc), "No results from provided Filters", false);
				}
			}
			else{
				printUsage();
			}

			// Save TodoList into JSON file:
			FileWriter output = new FileWriter(filename);
			output.write(gson.toJson(todoList));
			output.close();

		} catch (JsonSyntaxException | JsonIOException e) {
			System.out.println("--ERROR: cannot read from file \"" + filename + "\"");
		} catch (IOException e) {
			System.out.println("--ERROR: cannot save in file \"" + filename + "\"");
		}
	}

	private static void printUsage() {
		System.out.println("USAGE: filename option");
		System.out.println("Options:");
		System.out.println("\t-h | --help: Show this usage text");
		System.out.println("\t-i | --interactive: Start program in interactive mode");
		System.out.println("\t-a | --add: Create a new Task.");
		System.out.println("\t-r | --remove: Remove a Task.");
		System.out.println("\t-s | --search: Perform a filtered search.");
	}

	private static void interactiveConsole(TodoList todoList) {
		Scanner sc = new Scanner(System.in);

		int option = -1;

		String[] optionsMenu = { SAVE_EXIT, CREATE_TASK, REMOVE_TASK, FILTER_SEARCH };

		while (option != 0) {
			printMenu(optionsMenu);
			option = readOption(sc);

			if (option > 0 && option < optionsMenu.length) {
				if (optionsMenu[option].equals(CREATE_TASK)) {
					createTask(todoList, sc);
				} else if (optionsMenu[option].equals(REMOVE_TASK)) {
					removeTask(todoList, sc);
				} else if (optionsMenu[option].equals(FILTER_SEARCH)) {
					listTasks(filterTasks(todoList, sc), "No results from provided Filters", false);
				} else {
					System.out.println("Method not implemented");
				}
				System.out.println();
			} else if (option != 0) {
				System.out.println("--ERROR: please provide a valid number");
			}
		}

		sc.close();
	}

	private static void printMenu(String[] optionsMenu) {
		System.out.println("Options:");
		System.out.println("========");
		for (int i = 0; i < optionsMenu.length; i++) {
			System.out.println(i + ". " + optionsMenu[i]);
		}
		System.out.print("Select a option [0-" + (int) (optionsMenu.length - 1) + "]: ");
	}

	private static int readOption(Scanner sc) {
		int option = -1;
		try {
			option = Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e) {
			option = -1;
		}
		return option;
	}

	private static void createTask(TodoList todoList, Scanner input) {
		TodoTask task = new TodoTask();

		// Enter Title:
		System.out.print("Enter task title: ");
		task.setTitle(input.nextLine());

		// Enter Project_
		System.out.print("Enter task project: ");
		task.setProject(input.nextLine());

		// Enter Content:
		System.out.print("Enter task content: ");
		task.setContent(input.nextLine());

		// Enter Priority:
		System.out.print("Enter task priority " + TaskPriority.enumString() + ": ");
		task.setPriority(TaskPriority.findPriority(input.nextLine()));
		if (task.getPriority() == null) {
			System.out.println("Unknown priority.  Using default: " + DEFAULT_PRIORITY.toString());
			task.setPriority(DEFAULT_PRIORITY);
		}

		// Enter Status:
		System.out.print("Enter task status " + TaskStatus.enumString() + ": ");
		task.setStatus(TaskStatus.findPriority(input.nextLine()));
		if (task.getPriority() == null) {
			System.out.println("Unknown status.  Using default: " + DEFAULT_STATUS.toString());
			task.setStatus(DEFAULT_STATUS);
		}

		todoList.addTask(task);
	}

	private static void removeTask(TodoList todoList, Scanner sc) {		
		// Filter Search
		List<TodoTask> results = filterTasks(todoList, sc);
		
		// Show Results
		listTasks(results, "No results from provided Filters", true);

		// Ask user to select a task to delete
		if (results.size() > 0) {
			System.out.println("Enter the result number of the task to remove (cancel with 0)");
			int option = readOption(sc);
			while (option < 0 || option > results.size()) {
				System.out.println("--ERROR: please provide a valid number [0-" + results.size() + "]");
				System.out.println("Enter the result number of the task to remove (cancel with 0)");
				option = readOption(sc);
			}
			if (option != 0) {
				option -= 1;
				TodoTask task = results.get(option);
				todoList.removeTask(task);
				System.out.println("Task \"" + task.getTitle() + "\" removed.");
			}
		}
	}

	private static void listTasks(List<TodoTask> taskList, String emptyListMsg, boolean displayIndex) {
		if (taskList.size() > 0) {
			System.out.println("\nShowing " + taskList.size() + " tasks: ");
			System.out.println("==========================");

			int index = 1;
			for (TodoTask task : taskList) {
				if (displayIndex) {
					System.out.println("--- Result number " + index + " ---");
					index++;
				}
				System.out.print(task.toString());
				System.out.println("--------------------------");
			}
		} else {
			System.out.println(emptyListMsg);
		}
	}

	private static List<TodoTask> filterTasks(TodoList todoList, Scanner sc) {
		System.out.println("\nSelect the Filter for each Field");
		System.out.println("================================");
		System.out.println("(Note: Provide nothing for not filter a field)");

		// Filter Title
		System.out.print("Enter Title Filter: ");
		String fTitle = sc.nextLine();

		// Filter Project
		System.out.print("Enter Project Filter: ");
		String fProject = sc.nextLine();

		// Filter Content
		System.out.print("Enter Content Filter: ");
		String fContent = sc.nextLine();

		// Filter Priority
		System.out.print("Enter Priority Filter " + TaskPriority.enumString() + ": ");
		String line = sc.nextLine();
		TaskPriority fPriority = TaskPriority.findPriority(line);
		if (fPriority == null && !line.equals("")) {
			System.out.println("Unknown priority.  Priority not filered");
		}

		// Filter Status
		System.out.print("Enter Status Filter " + TaskStatus.enumString() + ": ");
		line = sc.nextLine();
		TaskStatus fStatus = TaskStatus.findPriority(line);
		if (fStatus == null && !line.equals("")) {
			System.out.println("Unknown status.  Status not filtered");
		}

		List<TodoTask> results = new ArrayList<TodoTask>();
		for (TodoTask task : todoList.getTaskList()) {
			if (task.canPassFilter(fTitle, fContent, fProject, fPriority, fStatus)) {
				results.add(task);
			}
		}
		return results;
	}
}
