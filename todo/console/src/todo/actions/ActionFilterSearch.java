package todo.actions;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import todo.console.InteractionMethods;
import todo.model.TodoList;
import todo.model.TodoTask;

public class ActionFilterSearch extends Action{
	private final static String TITLE = "Filter Search";

	@Override
	public String getTitle() {
		return TITLE;
	}
	
	private Action selectedItemAction;
	
	public ActionFilterSearch(Action selectedItemAction){
		this.selectedItemAction = selectedItemAction;
	}

	@Override
	public void perform(Scanner input, PrintStream output, TodoList todoList, TodoTask task) {
		
		// Filter Search
		List<TodoTask> results = filterTasks(todoList, input);

		// Show Results
		listTasks(results, "No results from provided Filters", true);
		
		int option = -1;

		if (results.size() > 0) {			
			String question = "Enter the result number of the task to perform an action (cancel with 0): ";
			option = InteractionMethods.askNumber(input, output, question, results.size());

			if (option > 0) {
				TodoTask selectedTask = results.get(option - 1);
				output.println("- Selected Task: ");
				output.print(selectedTask.toString("\t"));
				output.println("--------------------------");

				selectedItemAction.perform(input, output, todoList, selectedTask);
			}
		}
	}
}
