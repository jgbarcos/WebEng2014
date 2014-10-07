package todo.actions;

import java.io.PrintStream;
import java.util.Scanner;

import todo.model.TodoList;
import todo.model.TodoTask;

public class ActionEmpty extends Action {
	private String title;

	@Override
	public String getTitle() {
		return title;
	}
	
	public ActionEmpty(String title){
		this.title = title;
	}

	@Override
	public void perform(Scanner input, PrintStream output, TodoList todoList, TodoTask task) {
		// Do Nothing
	}
}
