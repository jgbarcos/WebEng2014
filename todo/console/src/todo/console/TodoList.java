package todo.console;

import java.util.ArrayList;
import java.util.List;

public class TodoList {
	private List<TodoTask> taskList = new ArrayList<TodoTask>();
	
	private boolean unsavedChanges = false;
	
	public void addTask(TodoTask task){
		taskList.add(task);
		unsavedChanges = true;
	}
	
	public void removeTask(TodoTask task){
		taskList.remove(task);
		unsavedChanges = true;
	}
	
	public void setTaskList(List<TodoTask> tasks){
		taskList = tasks;
	}
	
	public List<TodoTask> getTaskList(){
		return taskList;
	}

	public boolean hasUnsavedChanges() {
		return unsavedChanges;
	}

	public void setSavedChanges() {
		unsavedChanges = false;
	}
	
	public void setUnsavedChanges(){
		unsavedChanges = true;
	}
}
