package todo.actions;

import java.io.PrintStream;
import java.util.Scanner;

import todo.console.GsonIO;
import todo.model.TodoList;
import todo.model.TodoTask;

public class ActionSaveFile extends Action {
	private final static String TITLE = "Save Changes";

	@Override
	public String getTitle() {
		return TITLE;
	}
	
	private GsonIO gsonIO;

	@Override
	public void perform(Scanner input, PrintStream output, TodoList todoList, TodoTask task) {
		if(gsonIO != null){
			gsonIO.saveFile(todoList);
		}
		else{
			System.err.println("--ERROR: Action SaveFile GsonIO reference is null");
		}
	}
	
	public void setGsonIO(GsonIO gsonIO){
		this.gsonIO = gsonIO;
	}
}
