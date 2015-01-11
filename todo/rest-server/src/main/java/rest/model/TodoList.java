package rest.model;

import java.util.ArrayList;
import java.util.List;

public class TodoList {
	private int currentId = 0;

	private List<TodoTask> taskList = new ArrayList<TodoTask>();

	public int getCurrentId() {
		return currentId;
	}

	public void setCurrentId(int currentId) {
		this.currentId = currentId;
	}

	public List<TodoTask> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<TodoTask> taskList) {
		this.taskList = taskList;
	}
}
