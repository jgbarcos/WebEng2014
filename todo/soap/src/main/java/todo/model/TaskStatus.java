package todo.model;

public enum TaskStatus {
	COMPLETED, PENDING;
	
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
