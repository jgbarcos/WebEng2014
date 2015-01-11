package rest.model;

import java.util.ArrayList;
import java.util.List;

public class HelperMethods{
	public static List<TodoTask> filterSearch(TodoList todoList, String fTitle, String fProject, String fContent,
			TaskPriority fPriority, TaskStatus fStatus) {
		List<TodoTask> results = new ArrayList<TodoTask>();
		for (TodoTask task : todoList.getTaskList()) {
			if (task.canPassFilter(fTitle, fContent, fProject, fPriority, fStatus)) {
				results.add(task);
			}
		}
		return results;
	}
	
	public static void addTask(TodoList todoList, TodoTask task) {
		if(task.getId() < 0){
			task.setId(todoList.getCurrentId()+1);
			todoList.setCurrentId(todoList.getCurrentId()+1);
		}
		todoList.getTaskList().add(task);
	}

	public static boolean removeTask(TodoList todoList, int id) {
		boolean removed = false;
		
		List<TodoTask> taskList = todoList.getTaskList();
		// Search all coincidences
		for (int i = 0; i < taskList.size(); i++) {
			if (taskList.get(i).getId() == id) {
				taskList.remove(i);
				removed = true;
			}
		}
		return removed;
	}
	
	public static boolean containsTask(TodoList todoList, int id){
		for(TodoTask task : todoList.getTaskList()){
			if(task.getId() == id){
				return true;
			}
		}
		
		return false;
	}
	
	public static TodoTask getTask(TodoList todoList, int id){
		for(TodoTask task : todoList.getTaskList()){
			if(task.getId() == id){
				return task;
			}
		}
		
		return null;
	}
}
