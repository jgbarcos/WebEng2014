package todo.actions;

import java.io.PrintStream;
import java.util.Scanner;

import todo.console.InteractionMethods;
import todo.model.TodoList;
import todo.model.TodoTask;

public class ActionRemoveTask extends Action {
	private final static String TITLE = "Remove Task";

	@Override
	public String getTitle() {
		return TITLE;
	}

	@Override
	public void perform(Scanner input, PrintStream output, TodoList todoList, TodoTask task) {
		
		String question = "Do you want to remove task \"" + task.getTitle() + "\"?(y/n):";
		
		if(InteractionMethods.askYesNo(input,  output, question) == true){
			todoList.removeTask(task);
			output.println("Task \"" + task.getTitle() + "\" removed.");
		}	
	}

}
