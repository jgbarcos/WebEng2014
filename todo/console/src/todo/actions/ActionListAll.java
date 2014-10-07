package todo.actions;

import java.io.PrintStream;
import java.util.Scanner;

import todo.model.TodoList;
import todo.model.TodoTask;

public class ActionListAll extends Action {
	private final static String TITLE = "List All";
	
	private final static String EMPTY_DESCRIPTION = "Task list is Empty";

	@Override
	public String getTitle() {
		return TITLE;
	}
	@Override
	public void perform(Scanner input, PrintStream output, TodoList todoList, TodoTask task) {
		// Show Results
		listTasks(todoList.getTaskList(), EMPTY_DESCRIPTION, false);
	}
	
}
