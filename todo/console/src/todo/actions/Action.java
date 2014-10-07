package todo.actions;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import todo.model.TaskPriority;
import todo.model.TaskStatus;
import todo.model.TodoList;
import todo.model.TodoTask;

public abstract class Action {	
	public abstract String getTitle();
	
	public abstract void perform(Scanner input, PrintStream output, TodoList todoList, TodoTask task);
	
	/*
	 * Field Input Methods
	 */
	protected static void enterTitle(Scanner input, PrintStream output, TodoTask task) {
		// Enter Title:
		output.print("Enter task " + TodoTask.TASK_TITLE + ": ");
		task.setTitle(input.nextLine());
	}

	protected static void enterEndDate(Scanner input, PrintStream output, TodoTask task) {
		// Enter End Date
		output.print("Enter end " + TodoTask.TASK_END_DATE + ": ");
		task.setEndDate(input.nextLine());
	}

	protected static void enterProject(Scanner input, PrintStream output, TodoTask task) {
		// Enter Project_
		output.print("Enter task " + TodoTask.TASK_PROJECT + ": ");
		task.setProject(input.nextLine());
	}

	protected static void enterContent(Scanner input, PrintStream output, TodoTask task) {
		// Enter Content:
		output.print("Enter task " + TodoTask.TASK_CONTENT + ": ");
		task.setContent(input.nextLine());
	}

	protected static void enterPriority(Scanner input, PrintStream output, TodoTask task, TaskPriority defaultPriority) {
		// Enter Priority:
		output.print("Enter task " + TodoTask.TASK_PRIORITY + " " + TaskPriority.enumString() + ": ");
		task.setPriority(TaskPriority.findPriority(input.nextLine()));
		if (task.getPriority() == null) {
			output.println("Unknown priority.  Using default: " + defaultPriority.toString());
			task.setPriority(defaultPriority);
		}
	}

	protected static void enterStatus(Scanner input, PrintStream output, TodoTask task, TaskStatus defaultStatus) {
		// Enter Status:
		output.print("Enter task " + TodoTask.TASK_STATUS + " " + TaskStatus.enumString() + ": ");
		task.setStatus(TaskStatus.findPriority(input.nextLine()));
		if (task.getStatus() == null) {
			output.println("Unknown status.  Using default: " + defaultStatus.toString());
			task.setStatus(defaultStatus);
		}
	}
	
	/*
	 * Helper Methods
	 */
	public static void listTasks(List<TodoTask> taskList, String emptyListMsg, boolean displayIndex) {
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

	public static List<TodoTask> filterTasks(TodoList todoList, Scanner sc) {
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
