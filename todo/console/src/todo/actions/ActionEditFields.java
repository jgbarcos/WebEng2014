package todo.actions;

import java.io.PrintStream;
import java.util.Scanner;

import todo.console.InteractionMethods;
import todo.model.TaskPriority;
import todo.model.TaskStatus;
import todo.model.TodoList;
import todo.model.TodoTask;

public class ActionEditFields extends Action {
	private final static String TITLE = "Edit Task";
	
	private final static String MENU_TITLE = "Edit Options";

	@Override
	public String getTitle() {
		return TITLE;
	}
	
	private TaskPriority defaultPriority;
	private TaskStatus defaultStatus;
	
	public ActionEditFields(TaskPriority defaultPriority, TaskStatus defaultStatus){
		this.defaultPriority = defaultPriority;
		this.defaultStatus = defaultStatus;
	}

	@Override
	public void perform(Scanner input, PrintStream output, TodoList todoList, TodoTask task) {
		TodoTask editedTask = new TodoTask(task);

		String[] optionsMenu = { "Cancel Changes", "Apply Changes", TodoTask.TASK_TITLE, TodoTask.TASK_END_DATE,
				TodoTask.TASK_PROJECT, TodoTask.TASK_CONTENT, TodoTask.TASK_PRIORITY, TodoTask.TASK_STATUS };

		
		int option = -1;
		while (option != 0 && option != 1) {
			output.println("- Original Task: ");
			output.println(task.toString("\t"));
			output.println("--------------------------");
			output.println("- Currently Editing: ");
			output.println(editedTask.toString("\t"));
			output.println("--------------------------");

			InteractionMethods.printMenu(output, MENU_TITLE, optionsMenu);
			option = InteractionMethods.readOption(input, output);
			if (option > 1 && option < optionsMenu.length) {
				if (optionsMenu[option].equals(TodoTask.TASK_TITLE)) {
					enterTitle(input, output, editedTask);
				} else if (optionsMenu[option].equals(TodoTask.TASK_END_DATE)) {
					enterEndDate(input, output, editedTask);
				} else if (optionsMenu[option].equals(TodoTask.TASK_PROJECT)) {
					enterProject(input, output, editedTask);
				} else if (optionsMenu[option].equals(TodoTask.TASK_CONTENT)) {
					enterContent(input, output, editedTask);
				} else if (optionsMenu[option].equals(TodoTask.TASK_PRIORITY)) {
					enterPriority(input, output, editedTask, defaultPriority);
				} else if (optionsMenu[option].equals(TodoTask.TASK_STATUS)) {
					enterStatus(input, output, editedTask, defaultStatus);
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
}
