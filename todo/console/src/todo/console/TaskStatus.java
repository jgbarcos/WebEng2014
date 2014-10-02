package todo.console;

public enum TaskStatus {
	COMPLETED, PENDING;
	
	public static TaskStatus findPriority(String key){
		for(int i=0; i<TaskStatus.values().length; i++){
			TaskStatus priority = TaskStatus.values()[i];
			if(key.toLowerCase().equals(priority.toString().toLowerCase())){
				return priority;
			}
		}
		return null;
	}
	
	public static String enumString(){
		String result = "{";
		int numPriorities = TaskStatus.values().length;
		for(int i=0; i<numPriorities; i++){
			result += TaskStatus.values()[i].toString();
			if(i != numPriorities - 1){
				result += ",";
			}
		}
		result += "}";
		return result;
	}
}
