package todo.console;

import java.util.ArrayList;
import java.util.List;

public class TodoList {
	private List<TodoTask> taskList = new ArrayList<TodoTask>();
	
	public void addTask(TodoTask task){
		taskList.add(task);
	}
	
	public void removeTask(TodoTask task){
		taskList.remove(task);
	}
	
	public void setTaskList(List<TodoTask> tasks){
		taskList = tasks;
	}
	
	public List<TodoTask> getTaskList(){
		return taskList;
	}
}
