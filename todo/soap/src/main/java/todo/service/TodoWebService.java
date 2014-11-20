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
	
	private static enum UpdateJob{
		CREATE_OR_EDIT, REMOVE;
	}

	private static Gson gson = null;

	@WebMethod()
	public TodoList filterTasks(String title, String content, String project, String priority, String status) {
		TodoList todoList = readTodoList();

		// Check if priority exists
		TaskPriority taskPriority = TaskPriority.findPriority(priority);

		// Check if status exists
		TaskStatus taskStatus = TaskStatus.findStatus(status);

		List<TodoTask> filterResults = new ArrayList<TodoTask>();
		for (TodoTask task : todoList.getTaskList()) {
			if (task.canPassFilter(title, content, project, taskPriority, taskStatus)) {
				filterResults.add(task);
			}
		}
		
		TodoList resultTodoList = new TodoList();
		resultTodoList.setTaskList(filterResults);
		return resultTodoList;
	}

	@WebMethod()
	public int createTask(int id, String title, String endDate, String project, String content, String priority,
			String status) {
		TodoTask task = new TodoTask();
		task.setId(id);
		task.setTitle(title);
		task.setEndDate(endDate);
		task.setContent(content);
		task.setProject(project);
		task.setPriority(TaskPriority.findPriority(priority));
		task.setStatus(TaskStatus.findStatus(status));

		return threadSafeUpdate(UpdateJob.CREATE_OR_EDIT, task);
	}

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
		if (job == UpdateJob.CREATE_OR_EDIT) {
			TodoList todoList = readTodoList();
			// Update task
			if (task.getId() >= 0) {
				if (todoList.removeTask(task.getId())) {
					todoList.addTask(task);
					saveTodoList(todoList);
					System.out.println("Task " + task.getId() + " Updated");
					return task.getId();
				}
			}
			// Create task
			else {
				todoList.setID(task);
				todoList.addTask(task);
				saveTodoList(todoList);
				System.out.println("Task " + task.getId() + " Created");
				return task.getId();
			}
		}
		// Remove a Task (only task.id is necessary)
		else if (job == UpdateJob.REMOVE) {
			TodoList todoList = readTodoList();
			if (task.getId() >= 0 && todoList.removeTask(task.getId())) {
				saveTodoList(todoList);
				System.out.println("Task " + task.getId() + " Removed");
				return task.getId();
			}
		}

		// Return -1 if action didnt provoke changes
		return -1;
	}
}
