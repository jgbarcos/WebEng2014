package todo.model;

import java.util.Scanner;

public class TodoTask {

	/*
	 * Member Variables:
	 */
	public String title;
	public String endDate;
	public String project;
	public String content;
	public TaskPriority priority;
	public TaskStatus status;

	public final static String TASK_TITLE = "Title";
	public final static String TASK_END_DATE = "End Date";
	public final static String TASK_PROJECT = "Project";
	public final static String TASK_CONTENT = "Content";
	public final static String TASK_PRIORITY = "Priority";
	public final static String TASK_STATUS = "Status";
	
	private final static int DEFAULT_LINE_LENGTH = -1;

	/*
	 * Getters and Setters:
	 */

	public String getTitle() {
		return title;
	}

	public void setTitle(String value) {
		this.title = value != null && !value.equals("")? value : null;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String value) {
		this.endDate = value != null && !value.equals("")? value : null;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String value) {
		this.project = value != null && !value.equals("")? value : null;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String value) {
		this.content = value != null && !value.equals("")? value : null;
	}

	public TaskPriority getPriority() {
		return priority;
	}

	public void setPriority(TaskPriority value) {
		this.priority = value;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus value) {
		this.status = value;
	}

	/**
	 * Default Constructor
	 */
	public TodoTask() {
	}

	/**
	 * Copy Constructor
	 */
	public TodoTask(TodoTask copy) {
		if (copy.title != null)
			title = new String(copy.title);
		if (copy.endDate != null)
			endDate = new String(copy.endDate);
		if (copy.project != null)
			project = new String(copy.project);
		if (copy.content != null)
			content = new String(copy.content);
		if (copy.priority != null)
			priority = copy.priority;
		if (copy.status != null)
			status = copy.status;
	}
	
	public String toString(){
		return toString("", "\n");
	}
	
	public String toString(String prefix, String lineBreak){
		return toString(prefix, lineBreak, DEFAULT_LINE_LENGTH);
	}

	public String toString(String prefix, String lineBreak, int lineLength) {
		String output = "";

		output += emptySafeToString(prefix, lineBreak, TASK_TITLE, nullSafeToString(this.title), lineLength);
		output += emptySafeToString(prefix, lineBreak, TASK_END_DATE, nullSafeToString(this.endDate), lineLength);
		output += emptySafeToString(prefix, lineBreak, TASK_PROJECT, nullSafeToString(this.project), lineLength);
		output += emptySafeToString(prefix, lineBreak, TASK_CONTENT, nullSafeToString(this.content), lineLength);
		output += emptySafeToString(prefix, lineBreak, TASK_PRIORITY, nullSafeToString(this.priority), lineLength);
		output += emptySafeToString(prefix, lineBreak, TASK_STATUS, nullSafeToString(this.status), lineLength);

		return output;
	}

	private String nullSafeToString(Object obj) {
		if (obj == null)
			return "";
		else
			return obj.toString();
	}

	private String emptySafeToString(String prefix, String lineBreak, String field, String value, int lineLength) {
		if (value.equals(""))
			return "";
		else
			return prefix + stringLengthDelimiter(field + ": " + value, lineBreak+prefix+prefix, lineLength) + lineBreak;
	}
	
	private String stringLengthDelimiter(String str, String delimiter, int length){
		if(length > 0){
			String output = "";
			Scanner sc = new Scanner(str);
			
			int i=0;
			int lastDivision = 0;
			while(sc.hasNext()){
				String word = sc.next();
				output += word;
				
				if(sc.hasNext()){
					output += " ";
					i += word.length();
					if(i / length > lastDivision){
						output += delimiter;
						lastDivision = i/length;
					}
				}
			}
			
			sc.close();
			
			
			
			return output;
		}
		else {
			return str;
		}
	}

	public boolean canPassFilter(String fTitle, String fContent, String fProject, TaskPriority fPriority,
			TaskStatus fStatus) {
		if (fTitle != null && !fTitle.equals("") && !title.toLowerCase().contains(fTitle.toLowerCase())) {
			return false;
		}
		if (fContent != null && !fContent.equals("") && !content.toLowerCase().contains(fContent.toLowerCase())) {
			return false;
		}
		if (fProject != null && !fProject.equals("") && !project.toLowerCase().contains(fProject.toLowerCase())) {
			return false;
		}
		if (fPriority != null && !priority.equals(fPriority)) {
			return false;
		}
		if (fStatus != null && !status.equals(fStatus)) {
			return false;
		}

		return true;
	}
}
