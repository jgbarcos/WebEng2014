package todo.model;

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
}
