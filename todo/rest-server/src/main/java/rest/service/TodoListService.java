package rest.service;

import java.net.URI;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import rest.model.GsonIO;
import rest.model.HelperMethods;
import rest.model.TodoList;
import rest.model.TodoTask;

/**
 * A service tha manipulates tasks in a todoList
 */
@Path("/todoList")
public class TodoListService{
	/**
	 * The (shared) todoList object
	 */
	@Inject
	TodoList todoList;
	@Inject
	GsonIO gsonIO;
	
	/**
	 * A GET /todoList request should return the todoList in JSON
	 * @return a JSON representation of the todoList
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public TodoList getTodoList(){
		//TODO DEBUG : Debug for knowing if method is being called
		System.out.println("[TODO DEBUG] GET \"/todoList\" worked!");
		return todoList;
	}
	
	/**
	 * A POST /todoList request should add a new task to the todoList.
	 * @param task TodoTask
	 * @return a JSON representation of the created Task
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addTask(@Context UriInfo info, TodoTask task){
		HelperMethods.addTask(todoList, task);
		if(gsonIO != null){
			gsonIO.saveFile(todoList);
		}
		return Response.created(URI.create(""+task.getId())).entity(task).build();
	}
	
	/**
	 * A GET /todoList/{id} request should return the task with the associated id
	 * @param id the unique identifier (inside a TodoList) of a TodoTask
	 * @return a JSON representation of the new entry or 404
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTask(@PathParam("id") int id){
		TodoTask task = HelperMethods.getTask(todoList, id);
		
		if(task != null){
			return Response.ok(task).build();
		}else{
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	/**
	 * A PUT /todoList/{id} should update a entry if exists
	 * @param info the URI information of the request
	 * @param task the TodoTask posted entity
	 * @param id the unique identifier of a task
	 * @return a JSON representation of the new updated entry or 400 if the id is not a key
	 */
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateTask(@Context UriInfo info, @PathParam("id") int id, TodoTask task) {
		if(task.getId() == id && HelperMethods.containsTask(todoList,  id)){
			HelperMethods.removeTask(todoList, id);
			HelperMethods.addTask(todoList, task);
			if(gsonIO != null){
				gsonIO.saveFile(todoList);
			}
			return Response.ok(task).build();
		}
		else{
			return Response.status(Status.BAD_REQUEST).build();
		}
	}
	
	/**
	 * A DELETE /todoList/{id} should delete a entry if exists
	 * @param id the unique identifier of a person
	 * @return 204 if the request is succesful, 404 if the id is not a key
	 */
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteTask(@PathParam("id") int id){
		if(HelperMethods.removeTask(todoList, id)){
			if(gsonIO != null){
				gsonIO.saveFile(todoList);
			}
			return Response.noContent().build();
		}
		else{
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
}
