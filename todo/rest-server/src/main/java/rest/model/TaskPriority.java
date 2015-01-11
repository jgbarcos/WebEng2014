package rest.model;

public enum TaskPriority {
	LOW, NORMAL, IMPORTANT, CRITICAL;
	
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
