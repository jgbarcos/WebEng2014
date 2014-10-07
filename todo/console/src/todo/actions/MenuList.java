package todo.actions;

import java.io.PrintStream;
import java.util.Scanner;

import todo.console.InteractionMethods;
import todo.model.TodoList;
import todo.model.TodoTask;

public class MenuList extends Action implements Menu {

	private Action[] actionsMenu;
	private String menuTitle;
	private int option;

	@Override
	public String getTitle() {
		return null;
	}

	public MenuList(Action[] actionsMenu, String menuTitle) {
		this.actionsMenu = actionsMenu;
		this.menuTitle = menuTitle;
	}

	@Override
	public void perform(Scanner input, PrintStream output, TodoList todoList, TodoTask task) {
		option = -1;
		while (option == -1) {
			InteractionMethods.printMenu(output, menuTitle, actionsMenu);
			option = InteractionMethods.readOption(input, output);
			if (option >= 0 && option < actionsMenu.length) {
				output.println();
				actionsMenu[option].perform(input, output, todoList, task);
			} else {
				output.println("--ERROR: please provide a valid number");
				option = -1;
			}
		}
	}

	@Override
	public int performMenuSelection(Scanner input, PrintStream output, TodoList todoList, TodoTask task) {
		perform(input, output, todoList, task);
		return option;
	}

	public int getSelectedOption() {
		return option;
	}
}
