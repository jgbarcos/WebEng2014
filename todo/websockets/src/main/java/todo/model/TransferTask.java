package todo.model;

import com.google.gson.annotations.Expose;

public class TransferTask {

	/*
	 * Member Variables:
	 */
	@Expose
	private int id = -1;
	@Expose
	private String title;
	@Expose
	private String endDate;
	@Expose
	private String project;
	@Expose
	private String content;
	@Expose
	private String priority;
	@Expose
	private String status;

	/*
	 * Getters and Setters:
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/*
	 * Constructor :
	 */
	public TransferTask(){
		//Default Constructor
	}
	
	TransferTask(TodoTask task) {
		this.id = task.getId();
		this.title = task.getTitle();
		this.endDate = task.getEndDate();
		this.project = task.getProject();
		this.content = task.getContent();
		if (task.getPriority() != null) {
			this.priority = task.getPriority().toString();
		}
		if (task.getStatus() != null) {
			this.status = task.getStatus().toString();
		}
	}

	/*
	 * Create TodoTask from TransferTask
	 */
	public TodoTask retrieveTask(){
		return retrieveTask(null,null);
	}
	
	public TodoTask retrieveTask(TaskPriority defaultPriority, TaskStatus defaultStatus) {
		TodoTask task = new TodoTask();
		task.setId(this.id);
		task.setTitle(this.title);
		task.setEndDate(this.endDate);
		task.setProject(this.project);
		task.setContent(this.content);

		try {
			task.setPriority(TaskPriority.valueOf(this.priority.toUpperCase()));
		} catch (IllegalArgumentException | NullPointerException e) {
			if(defaultPriority != null){
				task.setPriority(defaultPriority);
			}
		}
		try {
			task.setStatus(TaskStatus.valueOf(this.status.toUpperCase()));
		} catch (IllegalArgumentException | NullPointerException e) {
			if(defaultStatus != null){
				task.setStatus(defaultStatus);
			}
		}

		return task;
	}
}
