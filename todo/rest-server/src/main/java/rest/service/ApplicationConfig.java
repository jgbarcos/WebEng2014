package rest.service;

import org.eclipse.persistence.jaxb.rs.MOXyJsonProvider;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import rest.model.GsonIO;
import rest.model.TodoList;

public class ApplicationConfig extends ResourceConfig {
	/**
     * Default constructor
     */
    public ApplicationConfig() {
    	// This is will be not saved on exit
    	this(new TodoList(), null);
    }


    /**
     * Main constructor
     * @param addressBook a provided address book
     */
    public ApplicationConfig(final TodoList todoList, final GsonIO gsonIO) {
    	register(TodoList.class);
    	register(GsonIO.class);
    	register(MOXyJsonProvider.class);
    	register(new AbstractBinder() {
			@Override
			protected void configure() {
				bind(todoList).to(TodoList.class);
				bind(gsonIO).to(GsonIO.class);
			}});
	}	
}
