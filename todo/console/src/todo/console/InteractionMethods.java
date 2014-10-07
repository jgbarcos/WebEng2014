package todo.console;

import java.io.PrintStream;
import java.util.Scanner;

import todo.actions.Action;

/**
 * Provide IO methods to show info or to request info to user
 * @author Javier García Barcos
 */
public class InteractionMethods {

	public static void printMenu(PrintStream output, String title, String[] optionsMenu) {
		output.println(title + ":");
		String separationLine = "";
		for (int i = 0; i < title.length() + 1; i++) {
			separationLine += "=";
		}
		output.println(separationLine);
		for (int i = 0; i < optionsMenu.length; i++) {
			output.println(i + ". " + optionsMenu[i]);
		}
		output.println("Select a option [0-" + (int) (optionsMenu.length - 1) + "]: ");
	}

	public static void printMenu(PrintStream output, String title, Action[] actionsMenu) {
		String[] optionTitles = new String[actionsMenu.length];
		for (int i = 0; i < actionsMenu.length; i++) {
			optionTitles[i] = actionsMenu[i].getTitle();
		}
		printMenu(output, title, optionTitles);
	}

	public static int readOption(Scanner input, PrintStream output) {
		output.print(">>");
		int option = -1;
		try {
			option = Integer.parseInt(input.nextLine());
		} catch (NumberFormatException e) {
			option = -1;
		}
		return option;
	}

	public static int askNumber(Scanner input, PrintStream output, String question, int maxNumber) {
		if (question != null)
			output.println(question);
		int option = readOption(input, output);
		while (option < 0 || option > maxNumber) {
			output.println("--ERROR: please provide a valid number [0-" + maxNumber + "]");
			if (question != null)
				output.println(question);
			option = readOption(input, output);
		}
		return option;
	}

	public static boolean askYesNo(Scanner input, PrintStream output, String question) {

		String response = "";
		while (!response.toLowerCase().equals("y") && !response.toLowerCase().equals("n")) {
			output.print(question);
			response = input.nextLine();

			if (response.toLowerCase().equals("y")) {
				return true;
			} else if (response.toLowerCase().equals("n")) {
				return false;
			}
			output.println("--ERROR: please provide a valid response");
		}

		System.err.println("--FATAL ERROR: askYesNo() returned undefined answer");
		return false;
	}
}
