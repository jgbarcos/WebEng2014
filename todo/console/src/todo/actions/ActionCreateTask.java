package todo.actions;

import java.io.PrintStream;
import java.util.Scanner;

import todo.model.TaskPriority;
import todo.model.TaskStatus;
import todo.model.TodoList;
import todo.model.TodoTask;


public class ActionCreateTask extends Action {
	private final static String TITLE = "Create Task";

	@Override
	public String getTitle() {
		return TITLE;
	}
	
	private TaskPriority defaultPriority;
	private TaskStatus defaultStatus;
	
	public ActionCreateTask(TaskPriority defaultPriority, TaskStatus defaultStatus){
		this.defaultPriority = defaultPriority;
		this.defaultStatus = defaultStatus;
	}

	@Override
	public void perform(Scanner input, PrintStream output, TodoList todoList, TodoTask task) {
		enterTitle(input, output, task);
		enterEndDate(input, output, task);
		enterProject(input, output, task);
		enterContent(input, output, task);
		enterPriority(input, output, task, defaultPriority);
		enterStatus(input, output, task, defaultStatus);

		todoList.addTask(task);
	}
}
