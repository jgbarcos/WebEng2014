package todo.actions;

import java.io.PrintStream;
import java.util.Scanner;

import todo.model.TodoList;
import todo.model.TodoTask;

public interface Menu {
	public int performMenuSelection(Scanner input, PrintStream output, TodoList todoList, TodoTask task);
}
