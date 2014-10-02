package todo.console;

public class TodoTask{
	
	/*
	 * Member Variables:
	 */
	public String title;
	public String content;
	public String project;
	public TaskPriority priority;
	public TaskStatus status;
	
	/*
	 * Getters and Setters:
	 */

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public TaskPriority getPriority() {
		return priority;
	}
	public void setPriority(TaskPriority priority) {
		this.priority = priority;
	}
	public TaskStatus getStatus() {
		return status;
	}
	public void setStatus(TaskStatus status) {
		this.status = status;
	}
	
	public String toString(){
		String nl = "\n";
		String output = "Title: " + this.title + nl;
		output += "Project: " + this.project + nl;
		output += "Status: " + this.status.toString() + nl;
		output += "Priority: " + this.priority.toString() + nl;
		output += "Content: " + this.content + nl;
		
		return output;
	}
	
	public boolean canPassFilter(String fTitle, String fContent, String fProject, TaskPriority fPriority, TaskStatus fStatus){
		if(fTitle != null && !title.toLowerCase().contains(fTitle.toLowerCase())){
			return false;
		}
		if(fContent != null && !content.toLowerCase().contains(fContent.toLowerCase())){
			return false;
		}
		if(fProject != null && !project.toLowerCase().contains(fProject.toLowerCase())){
			return false;
		}
		if(fPriority != null && !priority.equals(fPriority)){
			return false;
		}
		if(fStatus != null && !status.equals(fStatus)){
			return false;
		}
		
		return true;
	}
}
