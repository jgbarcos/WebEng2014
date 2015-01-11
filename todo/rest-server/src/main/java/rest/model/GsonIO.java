package rest.model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

public class GsonIO {
	
	private Gson gson;
	public Gson getGson() {return gson;}
	public void setGson(Gson gson) {this.gson = gson;}

	private String filename;
	
	public GsonIO (Gson gson, String filename){
		this.gson = gson;
		this.filename = filename;
	}
	
	public TodoList loadFile(){
		return loadFile(gson, filename);
	}
	
	public boolean saveFile(TodoList todoList){
		return saveFile(gson, filename, todoList);
	}
	
	public static TodoList loadFile(Gson gson, String filename) {
		// Load TodoList from JSON file:
		TodoList todoList = null;
		try {
			todoList = gson.fromJson(new FileReader(filename), TodoList.class);
		} catch (FileNotFoundException e) {
			System.out.println("Filename \"" + filename + "\" not found, a new one will be created");
			todoList = new TodoList();
		}
		return todoList;
	}

	public static boolean saveFile(Gson gson, String filename, TodoList todoList){
		// Save TodoList into JSON file:
		try {
			FileWriter output = new FileWriter(filename);
			output.write(gson.toJson(todoList));
			output.close();
			System.out.println("File \"" + filename + "\" has been saved.");
			return true;
		} catch (IOException e) {
			System.out.println("--ERROR: cannot save in file \"" + filename + "\"");
			return false;
		}
	}
}
