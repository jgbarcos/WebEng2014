package todo.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class TodoList {
	@Expose
	private int currentID = 0;
	@Expose
	private List<TodoTask> taskList = new ArrayList<TodoTask>();

	private boolean unsavedChanges = false;

	public void setID(TodoTask task) {
		task.setId(currentID);
		currentID += 1;
	}

	public void addTask(TodoTask task) {
		taskList.add(task);
		unsavedChanges = true;
	}

	public boolean removeTask(int id) {
		boolean removed = false;
		// Search all coincidences
		for (int i = 0; i < taskList.size(); i++) {
			if (taskList.get(i).getId() == id) {
				taskList.remove(i);
				removed = true;
			}
		}
		unsavedChanges = removed;
		return removed;
	}
	
	public boolean containsTask(int id){
		for(TodoTask task : taskList){
			if(task.getId() == id){
				return true;
			}
		}
		
		return false;
	}

	public void setTaskList(List<TodoTask> tasks) {
		taskList = tasks;
	}

	public List<TodoTask> getTaskList() {
		return taskList;
	}

	public boolean hasUnsavedChanges() {
		return unsavedChanges;
	}

	public void setSavedChanges() {
		unsavedChanges = false;
	}

	public void setUnsavedChanges() {
		unsavedChanges = true;
	}
}
