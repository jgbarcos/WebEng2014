package todo.servlet;

public abstract class JSPHelper {
	
	private static String SELECTED_ATTR = "selected=\"selected\"";
	
	public static String[] getPrioritySelectedArray(String priority){
		int priorityIndex = 0;
		if(priority != null){
			priority = priority.toLowerCase().trim();
			if(priority.equals("low")){
				priorityIndex = 1;
			}
			else if(priority.equals("normal")){
				priorityIndex = 2;
			}
			else if(priority.equals("important")){
				priorityIndex = 3;
			}
			else if(priority.equals("critical")){
				priorityIndex = 4;
			}
		}
		
		String[] priorityArray = {"","","","",""};
		priorityArray[priorityIndex] = SELECTED_ATTR;
		return priorityArray;
	}
	public static String[] getStatusSelectedArray(String status){
		int statusIndex = 0;
		if(status != null){
			status = status.toLowerCase().trim();
			if(status.equals("pending")){
				statusIndex = 1;
			}
			else if (status.equals("completed")){
				statusIndex = 2;
			}
		}
		
		String[] statusArray = {"","",""};
		statusArray[statusIndex] = SELECTED_ATTR;
		return statusArray;
	}
}
