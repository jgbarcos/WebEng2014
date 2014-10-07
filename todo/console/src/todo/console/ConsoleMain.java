package todo.console;

import java.io.PrintStream;
import java.util.Scanner;

import todo.actions.*;
import todo.model.TaskPriority;
import todo.model.TaskStatus;
import todo.model.TodoList;
import todo.model.TodoTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

/**
 * Class that contains the main method for running the console aplication.
 * @author Javier García Barcos
 */
public class ConsoleMain {

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
	 * Program Flags, for command identification purposes
	 */
	private enum programFlag {
		ERROR, INTERACTIVE, CREATE_TASK, LIST_ALL, FILTER_SEARCH, REMOVE_TASK, EDIT_TASK
	};

	/*
	 * User Interface Text Constants:
	 */
	private static final String MAIN_MENU = "Main Menu";
	private final static String ITEM_OPTION_MENU = "Actions to Perform";

	private static final String EXIT = "Exit";
	private static final String NOTHING_OPTION = "Nothing";

	/**
	 * Menu and Actions definition should be HERE:
	 * 
	 * To build a menu, create static instances of Actions and SubMenus,
	 * from bottom to root order
	 * .
	 * For more actions, create new Action classes and Specify a class Constructor
	 * to provide specific parameters to an Action.
	 * 
	 * All Actions and Menus are executed by using perform() method (although Menus
	 * also implements an own interface that provides performMenuSelection() method).
	 */
	// BASIC MENU HIERARCHY:
	// Menu(MainMenu)
	// --- Action(Exit)
	// --- Action(SaveFile)
	// --- Action(CreateTask)
	// --- Action(ListAll)
	// --- Action(FilterSearch)
	// --- --- Menu(ItemOptionMenu)
	// --- --- --- Action(Nothing)
	// --- --- --- Action(EditFields)
	// --- --- --- Action(RemoveTask)

	// ACTIONS OF: ItemOptionMenu:
	private static Action actionOptionNothing = new ActionEmpty(NOTHING_OPTION);
	private static Action actionEditFields = new ActionEditFields(DEFAULT_PRIORITY, DEFAULT_STATUS);
	private static Action actionRemoveTask = new ActionRemoveTask();

	// DEFINITION OF: ItemOptionMenu:
	private static Action[] itemOptionMenuActions = { actionOptionNothing, actionEditFields, actionRemoveTask };
	private static Action actionItemOptionMenu = new MenuList(itemOptionMenuActions, ITEM_OPTION_MENU);

	// ACTIONS OF: MainMenu:
	private static Action actionExit = new ActionEmpty(EXIT);
	private static Action actionSaveFile = new ActionSaveFile();
	private static Action actionCreateTask = new ActionCreateTask(DEFAULT_PRIORITY, DEFAULT_STATUS);
	private static Action actionListAll = new ActionListAll();
	private static Action actionFilterSearch = new ActionFilterSearch(actionItemOptionMenu);

	// DEFINITION OF: Main Menu (this menu is the root of the menu system):
	private static Action[] mainMenuActions = { actionExit, actionSaveFile, actionCreateTask, actionListAll,
			actionFilterSearch };
	private static Menu mainMenu = new MenuList(mainMenuActions, MAIN_MENU);

	/**
	 * Main method of ToDo Organizer program
	 * @param args
	 */
	public static void main(String[] args) {
		// Debug args
		args = new String[2];
		args[0] = "-i";
		args[1] = DEFAULT_FILE_NAME;

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String filename = DEFAULT_FILE_NAME;

		programFlag executionMode = programFlag.ERROR;

		if (args.length > 0) {
			String argument = args[0];
			if (argument.equals("--interactive") || argument.equals("-i")) {
				executionMode = programFlag.INTERACTIVE;
			} else if (argument.equals("--add") || argument.equals("-a")) {
				executionMode = programFlag.CREATE_TASK;
			} else if (argument.equals("--remove") || argument.equals("-r")) {
				executionMode = programFlag.REMOVE_TASK;
			} else if (argument.equals("--list") || argument.equals("-l")) {
				executionMode = programFlag.LIST_ALL;
			} else if (argument.equals("--search") || argument.equals("-s")) {
				executionMode = programFlag.FILTER_SEARCH;
			} else if (argument.equals("--edit") || argument.equals("-e")) {
				executionMode = programFlag.EDIT_TASK;
			}

			if (args.length == 2) {
				filename = args[1];
			}
		}

		try {
			// Activate interactive mode:
			if (executionMode == programFlag.INTERACTIVE) {
				TodoList todoList = GsonIO.loadFile(gson, filename);

				// Print Banner
				System.out.println(PROGRAM_BANNER);

				// Start Interactive Console:
				interactiveConsole(gson, filename, todoList);

			} else if (executionMode == programFlag.ERROR) {
				printUsage();
			} else {
				// Execute requested command:
				TodoList todoList = GsonIO.loadFile(gson, filename);

				Scanner input = new Scanner(System.in);
				PrintStream output = System.out;

				if (executionMode == programFlag.CREATE_TASK) {
					actionCreateTask.perform(input, System.out, todoList, new TodoTask());
				} else if (executionMode == programFlag.REMOVE_TASK) {
					(new ActionFilterSearch(actionRemoveTask)).perform(input, output, todoList, null);
				} else if (executionMode == programFlag.EDIT_TASK) {
					(new ActionFilterSearch(actionEditFields)).perform(input, output, todoList, null);
				} else if (executionMode == programFlag.LIST_ALL) {
					actionListAll.perform(input, output, todoList, null);
				} else if (executionMode == programFlag.FILTER_SEARCH) {
					actionFilterSearch.perform(input, output, todoList, null);
				}
				input.close();

				// Save on Exit
				GsonIO.saveFile(gson, filename, todoList);
			}

		} catch (JsonSyntaxException | JsonIOException e) {
			System.out.println("--ERROR: cannot read from file \"" + filename + "\"");
		}
	}

	private static void printUsage() {
		System.out.println("USAGE: Option filename");
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
		Scanner input = new Scanner(System.in);
		PrintStream output = System.out;

		int option = -1;
		while (option != 0) {
			if (todoList.hasUnsavedChanges()) {
				System.out.println("(Note: Unsaved Changes. Remember to save changes before exit)");
			}
			option = mainMenu.performMenuSelection(input, output, todoList, null);
		}

		// Ask for save unsaved changes before closing:
		if (todoList.hasUnsavedChanges()) {
			output.print("Do you want to save changes before closing?(y/n): ");
			String response = input.nextLine();
			while (!response.equals("y") && !response.equals("n")) {
				output.println("--ERROR: please provide a valid response");
				output.print("Do you want to save changes before closing?(y/n): ");
				response = input.nextLine();
			}

			if (response.equals("y")) {
				GsonIO.saveFile(gson, filename, todoList);
			}
		}

		input.close();
	}
}
