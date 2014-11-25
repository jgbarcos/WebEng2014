package todo.service;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import todo.model.*;

@WebService
public class TodoWebService {

	private final static String DEFAULT_FILE_NAME = "todoList.json";
	private final static TaskPriority DEFAULT_PRIORITY = TaskPriority.NORMAL;
	private final static TaskStatus DEFAULT_STATUS = TaskStatus.PENDING;

	private static enum UpdateJob {
		CREATE, EDIT, REMOVE;
	}

	private static Gson gson = null;

	@WebMethod()
	public TransferTask[] filterTasks(String title, String content, String project, String priority, String status) {
		TodoList todoList = readTodoList();

		// Check if priority exists
		TaskPriority taskPriority = null;
		try {
			taskPriority = TaskPriority.valueOf(priority.toUpperCase());
		} catch (IllegalArgumentException | NullPointerException doNothing) {
		}

		// Check if status exists
		TaskStatus taskStatus = null;
		try {
			taskStatus = TaskStatus.valueOf(status.toUpperCase());
		} catch (IllegalArgumentException | NullPointerException doNothing) {
		}

		List<TodoTask> filterResults = new ArrayList<TodoTask>();
		for (TodoTask task : todoList.getTaskList()) {
			if (task.canPassFilter(title, content, project, taskPriority, taskStatus)) {
				filterResults.add(task);
			}
		}

		TransferTask[] results = new TransferTask[filterResults.size()];
		for(int i=0; i<results.length; i++){
			results[i] = new TransferTask(filterResults.get(i));
		}
		return results;
	}

	@WebMethod()
	public int createTask(TransferTask transfer) {
		TodoTask task = transfer.retrieveTask(DEFAULT_PRIORITY, DEFAULT_STATUS);
		return threadSafeUpdate(UpdateJob.CREATE, task);
	}
	
	@WebMethod()
	public int editTask(TransferTask transfer){
		TodoTask task = transfer.retrieveTask(DEFAULT_PRIORITY, DEFAULT_STATUS);
		return threadSafeUpdate(UpdateJob.EDIT, task);
	}
	
	/*
	//@WebMethod()
	public int createTask(int id, String title, String endDate, String project, String content, String priority,
			String status) {
		TodoTask task = new TodoTask();
		task.setId(id);
		task.setTitle(title);
		task.setEndDate(endDate);
		task.setContent(content);
		task.setProject(project);

		try {
			task.setPriority(TaskPriority.valueOf(priority.toUpperCase()));
		} catch (IllegalArgumentException | NullPointerException e) {
			task.setPriority(DEFAULT_PRIORITY);
		}
		try {
			task.setStatus(TaskStatus.valueOf(status.toUpperCase()));
		} catch (IllegalArgumentException | NullPointerException e) {
			task.setStatus(DEFAULT_STATUS);
		}

		return threadSafeUpdate(UpdateJob.CREATE_OR_EDIT, task);
	}

	 */

	@WebMethod()
	public int removeTask(int id) {
		TodoTask sampleTask = new TodoTask();
		sampleTask.setId(id);

		return threadSafeUpdate(UpdateJob.REMOVE, sampleTask);
	}

	private TodoList readTodoList() {
		if (gson == null) {
			System.out.println("Creating GSON object");
			gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		}
		return GsonIO.loadFile(gson, DEFAULT_FILE_NAME);
	}

	private void saveTodoList(TodoList todoList) {
		if (gson == null) {
			gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		}
		GsonIO.saveFile(gson, DEFAULT_FILE_NAME, todoList);
	}

	private synchronized int threadSafeUpdate(UpdateJob job, TodoTask task) {
		// Create a new Task or edit an existing one
		if (job == UpdateJob.CREATE) {
			TodoList todoList = readTodoList();
			if(task.getId() < 0){
				todoList.setID(task);
			}
			else if(todoList.containsTask(task.getId())){
				return -1;
			}
			
			todoList.addTask(task);
			System.out.println("Task " + task.getId() + " Created");
			saveTodoList(todoList);
			return task.getId();
		}
		// Edit Existing Task
		else if(job == UpdateJob.EDIT){
			TodoList todoList = readTodoList();
			if (task.getId() >= 0 && todoList.removeTask(task.getId())) {
				todoList.addTask(task);
				System.out.println("Task " + task.getId() + " Updated");
				saveTodoList(todoList);
				return task.getId();
			}
		}
		// Remove a Task (only task.id is necessary)
		else if (job == UpdateJob.REMOVE) {
			TodoList todoList = readTodoList();
			if (task.getId() >= 0 && todoList.removeTask(task.getId())) {
				System.out.println("Task " + task.getId() + " Removed");
				saveTodoList(todoList);
				return task.getId();
			}
		}

		// Return -1 if action didnt provoke changes
		return -1;
	}
}
