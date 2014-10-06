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

	private final static String PROGRAM_BANNER = " ______        ____              \n"
			+ "/\\__  _\\      /\\  _`\\            \n" + "\\/_/\\ \\/   ___\\ \\ \\/\\ \\    ___   \n"
			+ "   \\ \\ \\  / __`\\ \\ \\ \\ \\  / __`\\ \n" + "    \\ \\ \\/\\ \\L\\ \\ \\ \\_\\ \\/\\ \\L\\ \\\n"
			+ "     \\ \\_\\ \\____/\\ \\____/\\ \\____/\n" + "      \\/_/\\/___/  \\/___/  \\/___/ \n"
			+ " _____                                                            \n"
			+ "/\\  __`\\                                __                        \n"
			+ "\\ \\ \\/\\ \\  _ __    __      __      ___ /\\_\\  ____      __   _ __  \n"
			+ " \\ \\ \\ \\ \\/\\`'__\\/'_ `\\  /'__`\\  /' _ `\\/\\ \\/\\_ ,`\\  /'__`\\/\\`'__\\\n"
			+ "  \\ \\ \\_\\ \\ \\ \\//\\ \\L\\ \\/\\ \\L\\.\\_/\\ \\/\\ \\ \\ \\/_/  /_/\\  __/\\ \\ \\/ \n"
			+ "   \\ \\_____\\ \\_\\\\ \\____ \\ \\__/.\\_\\ \\_\\ \\_\\ \\_\\/\\____\\ \\____\\\\ \\_\\ \n"
			+ "    \\/_____/\\/_/ \\/___L\\ \\/__/\\/_/\\/_/\\/_/\\/_/\\/____/\\/____/ \\/_/ \n"
			+ "                   /\\____/                                        \n"
			+ "                   \\_/__/                                         \n";

	/*
	 * Menu Options Constants:
	 */
	private static final String EXIT = "Exit";
	private static final String CREATE_TASK = "Create Task";
	private static final String LIST_ALL = "List All";
	private static final String FILTER_SEARCH = "Filter Search";
	private static final String SAVE_DATA = "Save Changes";

	private static final String REMOVE_TASK = "Remove Task";
	private static final String EDIT_TASK = "Edit Task";

	public static void main(String[] args) {

		//Debug args
//		args = new String[2];
//		args[0] = DEFAULT_FILE_NAME;
//		args[1] = "-i";

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String filename = DEFAULT_FILE_NAME;

		boolean flag_interactive = false;
		String commandOption = null;

		if (args.length > 0) {
			filename = args[0];

			if (args.length > 1) {
				String argument = args[1];
				if (argument.equals("--interactive") || argument.equals("-i")) {
					flag_interactive = true;
				} else if (argument.equals("--add") || argument.equals("-a")) {
					commandOption = CREATE_TASK;
				} else if (argument.equals("--remove") || argument.equals("-r")) {
					commandOption = REMOVE_TASK;
				} else if (argument.equals("--list") || argument.equals("-l")) {
					commandOption = LIST_ALL;
				} else if (argument.equals("--search") || argument.equals("-s")) {
					commandOption = FILTER_SEARCH;
				} else if (argument.equals("--edit") || argument.equals("-e")) {
					commandOption = EDIT_TASK;
				}
			}
		}

		try {
			if (flag_interactive) {
				TodoList todoList = loadFile(gson, filename);

				// Print Banner
				System.out.println(PROGRAM_BANNER);

				// Start Interactive Console:
				interactiveConsole(gson, filename, todoList);
			
			} else if (commandOption != null) {
				TodoList todoList = loadFile(gson, filename);

				Scanner sc = new Scanner(System.in);
				if (commandOption.equals(CREATE_TASK)) {
					createTask(todoList, sc);
				} else if (commandOption.equals(REMOVE_TASK)) {
					removeTask(todoList, sc);
				} else if (commandOption.equals(EDIT_TASK)) {
					editTask(todoList, sc);
				} else if (commandOption.equals(LIST_ALL)) {
					listTasks(todoList.getTaskList(), "No results from provided file", false);
				} else if (commandOption.equals(FILTER_SEARCH)) {
					listTasks(filterTasks(todoList, sc), "No results from provided Filters", false);
				}
				saveFile(gson, filename, todoList);

			} else {
				printUsage();
			}

		} catch (JsonSyntaxException | JsonIOException e) {
			System.out.println("--ERROR: cannot read from file \"" + filename + "\"");
		}
	}

	private static TodoList loadFile(Gson gson, String filename) {
		// Load TodoList from JSON file:
		TodoList todoList = null;
		try {
			todoList = gson.fromJson(new FileReader(filename), TodoList.class);
			todoList.setSavedChanges();
		} catch (FileNotFoundException e) {
			System.out.println("Filename \"" + filename + "\" not found, a new one will be created");
			todoList = new TodoList();
			todoList.setUnsavedChanges();
		}
		return todoList;
	}

	private static boolean saveFile(Gson gson, String filename, TodoList todoList){
		// Save TodoList into JSON file:
		try {
			FileWriter output = new FileWriter(filename);
			output.write(gson.toJson(todoList));
			output.close();
			System.out.println("File \"" + filename + "\" has been saved.");
			todoList.setSavedChanges();
			return true;
		} catch (IOException e) {
			System.out.println("--ERROR: cannot save in file \"" + filename + "\"");
			return false;
		}
	}

	private static void printUsage() {
		System.out.println("USAGE: filename option");
		System.out.println("Options:");
		System.out.println("\t-h | --help: Show this usage text");
		System.out.println("\t-i | --interactive: Start program in interactive mode");
		System.out.println("\t-a | --add: Create a new Task.");
		System.out.println("\t-r | --remove: Remove a Task.");
		System.out.println("\t-e | --edit: Edit a existing Task");
		System.out.println("\t-l | --list: List all tasks");
		System.out.println("\t-s | --search: Perform a filtered search.");
	}

	private static void interactiveConsole(Gson gson, String filename, TodoList todoList) {
		Scanner sc = new Scanner(System.in);

		int option = -1;

		String[] optionsMenu = { EXIT, SAVE_DATA, CREATE_TASK, LIST_ALL, FILTER_SEARCH };

		while (option != 0) {
			if(todoList.hasUnsavedChanges()){
				System.out.println("(Note: Unsaved Changes. Remember to save changes before exit)");
			}
			printMenu("Main Menu", optionsMenu);
			option = readOption(sc);

			if (option > 0 && option < optionsMenu.length) {
				if (optionsMenu[option].equals(SAVE_DATA)) {
					saveFile(gson, filename, todoList);
				} else if (optionsMenu[option].equals(CREATE_TASK)) {
					createTask(todoList, sc);
				} else if (optionsMenu[option].equals(LIST_ALL)) {
					listTasks(todoList.getTaskList(), "No results from provided file", false);
				} else if (optionsMenu[option].equals(FILTER_SEARCH)) {
					searchAction(sc, todoList);
				} else {
					System.out.println("Method not implemented");
				}
			} else if (option != 0) {
				System.out.println("--ERROR: please provide a valid number");
			}
		}
		
		// Ask for save unsaved changes before closing:
		if(todoList.hasUnsavedChanges()){
			System.out.print("Do you want to save changes before closing?(y/n): ");
			String response = sc.nextLine();
			while(!response.equals("y") && !response.equals("n")){
				System.out.println("--ERROR: please provide a valid response");
				System.out.print("Do you want to save changes before closing?(y/n): ");
				response = sc.nextLine();
			}
			
			if(response.equals("y")){
				saveFile(gson, filename, todoList);
			}
		}

		sc.close();
	}

	private static void printMenu(String title, String[] optionsMenu) {
		System.out.println(title + ":");
		String separationLine = "";
		for (int i = 0; i < title.length() + 1; i++) {
			separationLine += "=";
		}
		System.out.println(separationLine);
		for (int i = 0; i < optionsMenu.length; i++) {
			System.out.println(i + ". " + optionsMenu[i]);
		}
		System.out.println("Select a option [0-" + (int) (optionsMenu.length - 1) + "]: ");
	}

	private static int readOption(Scanner sc) {
		System.out.print(">>");
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

		enterTitle(input, task);
		enterEndDate(input, task);
		enterProject(input, task);
		enterContent(input, task);
		enterPriority(input, task);
		enterStatus(input, task);

		todoList.addTask(task);
	}

	private static void searchAction(Scanner sc, TodoList todoList) {
		// Filter Search
		List<TodoTask> results = filterTasks(todoList, sc);

		// Show Results
		listTasks(results, "No results from provided Filters", true);

		int option = -1;

		if (results.size() > 0) {
			System.out.println("Enter the result number of the task to perform an action (cancel with 0): ");
			option = readOption(sc);
			while (option < 0 || option > results.size()) {
				System.out.println("--ERROR: please provide a valid number [0-" + results.size() + "]");
				System.out.println("Enter the result number of the task to perform an action (cancel with 0): ");
				option = readOption(sc);
			}

			if (option != 0) {
				TodoTask selectedTask = results.get(option - 1);
				System.out.println("- Selected Task: ");
				System.out.print(selectedTask.toString("\t"));
				System.out.println("--------------------------");

				selectAction(sc, todoList, selectedTask);
			}
		}
	}

	private static void selectAction(Scanner sc, TodoList todoList, TodoTask task) {
		String[] optionsMenu = { "Do Nothing", EDIT_TASK, REMOVE_TASK };

		int option = -1;

		while (option == -1) {
			printMenu("Actions to Perform", optionsMenu);
			option = readOption(sc);
			if (option > 0 && option < optionsMenu.length) {
				if (optionsMenu[option].equals(EDIT_TASK)) {
					editFields(sc, todoList, task);
				} else if (optionsMenu[option].equals(REMOVE_TASK)) {
					removeTask(todoList, task);
				} else {
					System.out.println("Method not implemented");
				}
				System.out.println();
			} else if (option != 0) {
				System.out.println("--ERROR: please provide a valid number");
				option = -1;
			}
		}
	}

	private static void removeTask(TodoList todoList, Scanner sc) {
		// Filter Search
		List<TodoTask> results = filterTasks(todoList, sc);

		// Show Results
		listTasks(results, "No results from provided Filters", true);

		// Ask user to select a task to delete
		if (results.size() > 0) {
			System.out.println("Enter the result number of the task to remove (cancel with 0): ");
			int option = readOption(sc);
			while (option < 0 || option > results.size()) {
				System.out.println("--ERROR: please provide a valid number [0-" + results.size() + "]");
				System.out.println("Enter the result number of the task to remove (cancel with 0): ");
				option = readOption(sc);
			}
			if (option != 0) {
				option -= 1;
				TodoTask task = results.get(option);
				removeTask(todoList, task);
			}
		}
	}

	private static void removeTask(TodoList todoList, TodoTask task) {
		todoList.removeTask(task);
		System.out.println("Task \"" + task.getTitle() + "\" removed.");
	}

	private static void editTask(TodoList todoList, Scanner sc) {
		// Filter Search
		List<TodoTask> results = filterTasks(todoList, sc);

		// Show Results
		listTasks(results, "No results from provided Filters", true);

		// Ask user to select a task to edit
		if (results.size() > 0) {
			System.out.println("Enter the result number of the task to edit (cancel with 0): ");
			int option = readOption(sc);
			while (option < 0 || option > results.size()) {
				System.out.println("--ERROR: please provide a valid number [0-" + results.size() + "]");
				System.out.println("Enter the result number of the task to edit (cancel with 0): ");
				option = readOption(sc);
			}
			if (option != 0) {
				option -= 1;
				TodoTask task = results.get(option);
				editFields(sc, todoList, task);
			}
		}
	}

	private static void editFields(Scanner sc, TodoList todoList, TodoTask task) {
		TodoTask editedTask = new TodoTask(task);

		String[] optionsMenu = { "Cancel Changes", "Apply Changes", TodoTask.TASK_TITLE, TodoTask.TASK_END_DATE,
				TodoTask.TASK_PROJECT, TodoTask.TASK_CONTENT, TodoTask.TASK_PRIORITY, TodoTask.TASK_STATUS };

		int option = -1;
		while (option != 0 && option != 1) {
			System.out.println("- Original Task: ");
			System.out.println(task.toString("\t"));
			System.out.println("--------------------------");
			System.out.println("- Currently Editing: ");
			System.out.println(editedTask.toString("\t"));
			System.out.println("--------------------------");

			printMenu("Edit Options", optionsMenu);
			option = readOption(sc);
			if (option > 1 && option < optionsMenu.length) {
				if (optionsMenu[option].equals(TodoTask.TASK_TITLE)) {
					enterTitle(sc, editedTask);
				} else if (optionsMenu[option].equals(TodoTask.TASK_END_DATE)) {
					enterEndDate(sc, editedTask);
				} else if (optionsMenu[option].equals(TodoTask.TASK_PROJECT)) {
					enterProject(sc, editedTask);
				} else if (optionsMenu[option].equals(TodoTask.TASK_CONTENT)) {
					enterContent(sc, editedTask);
				} else if (optionsMenu[option].equals(TodoTask.TASK_PRIORITY)) {
					enterPriority(sc, editedTask);
				} else if (optionsMenu[option].equals(TodoTask.TASK_STATUS)) {
					enterStatus(sc, editedTask);
				} else {
					System.out.println("Method not implemented");
				}
				System.out.println();
			} else if (option != 0 && option != 1) {
				System.out.println("--ERROR: please provide a valid number");
			}
		}

		// Save Changes
		if (option == 1) {
			todoList.removeTask(task);
			todoList.addTask(editedTask);
			System.out.println("Task \"" + task.getTitle() + "\" updated.");
		}

	}

	private static void listTasks(List<TodoTask> taskList, String emptyListMsg, boolean displayIndex) {
		if (taskList.size() > 0) {
			String text = "Showing " + taskList.size() + " tasks:";
			System.out.println("\n" + text);
			String separationLine = "";
			for (int i = 0; i < text.length(); i++) {
				separationLine += "=";
			}
			System.out.println(separationLine);

			int index = 1;
			for (TodoTask task : taskList) {
				if (displayIndex) {
					System.out.println("- Search Result " + index + ":");
					index++;
				}
				System.out.print(task.toString("\t"));
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

	/*
	 * Field Input Methods
	 */
	private static void enterTitle(Scanner sc, TodoTask task) {
		// Enter Title:
		System.out.print("Enter task " + TodoTask.TASK_TITLE + ": ");
		task.setTitle(sc.nextLine());
	}

	private static void enterEndDate(Scanner sc, TodoTask task) {
		// Enter End Date
		System.out.print("Enter end " + TodoTask.TASK_END_DATE + ": ");
		task.setEndDate(sc.nextLine());
	}

	private static void enterProject(Scanner sc, TodoTask task) {
		// Enter Project_
		System.out.print("Enter task " + TodoTask.TASK_PROJECT + ": ");
		task.setProject(sc.nextLine());
	}

	private static void enterContent(Scanner sc, TodoTask task) {
		// Enter Content:
		System.out.print("Enter task " + TodoTask.TASK_CONTENT + ": ");
		task.setContent(sc.nextLine());
	}

	private static void enterPriority(Scanner sc, TodoTask task) {
		// Enter Priority:
		System.out.print("Enter task " + TodoTask.TASK_PRIORITY + " " + TaskPriority.enumString() + ": ");
		task.setPriority(TaskPriority.findPriority(sc.nextLine()));
		if (task.getPriority() == null) {
			System.out.println("Unknown priority.  Using default: " + DEFAULT_PRIORITY.toString());
			task.setPriority(DEFAULT_PRIORITY);
		}
	}

	private static void enterStatus(Scanner sc, TodoTask task) {
		// Enter Status:
		System.out.print("Enter task " + TodoTask.TASK_STATUS + " " + TaskStatus.enumString() + ": ");
		task.setStatus(TaskStatus.findPriority(sc.nextLine()));
		if (task.getPriority() == null) {
			System.out.println("Unknown status.  Using default: " + DEFAULT_STATUS.toString());
			task.setStatus(DEFAULT_STATUS);
		}
	}
}
