package todo.console;

public enum TaskPriority {
	LOW, NORMAL, IMPORTANT, CRITICAL;
	
	public static TaskPriority findPriority(String key){
		for(int i=0; i<TaskPriority.values().length; i++){
			TaskPriority priority = TaskPriority.values()[i];
			if(key.toLowerCase().equals(priority.toString().toLowerCase())){
				return priority;
			}
		}
		return null;
	}
	
	public static String enumString(){
		String result = "{";
		int numPriorities = TaskPriority.values().length;
		for(int i=0; i<numPriorities; i++){
			result += TaskPriority.values()[i].toString();
			if(i != numPriorities - 1){
				result += ",";
			}
		}
		result += "}";
		return result;
	}
}
