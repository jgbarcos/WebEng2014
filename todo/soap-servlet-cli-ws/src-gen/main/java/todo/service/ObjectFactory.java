
package todo.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the todo.service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _EditTaskResponse_QNAME = new QName("http://service.todo/", "editTaskResponse");
    private final static QName _FilterTasksResponse_QNAME = new QName("http://service.todo/", "filterTasksResponse");
    private final static QName _RemoveTask_QNAME = new QName("http://service.todo/", "removeTask");
    private final static QName _CreateTaskResponse_QNAME = new QName("http://service.todo/", "createTaskResponse");
    private final static QName _CreateTask_QNAME = new QName("http://service.todo/", "createTask");
    private final static QName _EditTask_QNAME = new QName("http://service.todo/", "editTask");
    private final static QName _FilterTasks_QNAME = new QName("http://service.todo/", "filterTasks");
    private final static QName _RemoveTaskResponse_QNAME = new QName("http://service.todo/", "removeTaskResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: todo.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EditTask }
     * 
     */
    public EditTask createEditTask() {
        return new EditTask();
    }

    /**
     * Create an instance of {@link RemoveTaskResponse }
     * 
     */
    public RemoveTaskResponse createRemoveTaskResponse() {
        return new RemoveTaskResponse();
    }

    /**
     * Create an instance of {@link FilterTasks }
     * 
     */
    public FilterTasks createFilterTasks() {
        return new FilterTasks();
    }

    /**
     * Create an instance of {@link CreateTaskResponse }
     * 
     */
    public CreateTaskResponse createCreateTaskResponse() {
        return new CreateTaskResponse();
    }

    /**
     * Create an instance of {@link CreateTask }
     * 
     */
    public CreateTask createCreateTask() {
        return new CreateTask();
    }

    /**
     * Create an instance of {@link RemoveTask }
     * 
     */
    public RemoveTask createRemoveTask() {
        return new RemoveTask();
    }

    /**
     * Create an instance of {@link FilterTasksResponse }
     * 
     */
    public FilterTasksResponse createFilterTasksResponse() {
        return new FilterTasksResponse();
    }

    /**
     * Create an instance of {@link EditTaskResponse }
     * 
     */
    public EditTaskResponse createEditTaskResponse() {
        return new EditTaskResponse();
    }

    /**
     * Create an instance of {@link TransferTask }
     * 
     */
    public TransferTask createTransferTask() {
        return new TransferTask();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EditTaskResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.todo/", name = "editTaskResponse")
    public JAXBElement<EditTaskResponse> createEditTaskResponse(EditTaskResponse value) {
        return new JAXBElement<EditTaskResponse>(_EditTaskResponse_QNAME, EditTaskResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FilterTasksResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.todo/", name = "filterTasksResponse")
    public JAXBElement<FilterTasksResponse> createFilterTasksResponse(FilterTasksResponse value) {
        return new JAXBElement<FilterTasksResponse>(_FilterTasksResponse_QNAME, FilterTasksResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveTask }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.todo/", name = "removeTask")
    public JAXBElement<RemoveTask> createRemoveTask(RemoveTask value) {
        return new JAXBElement<RemoveTask>(_RemoveTask_QNAME, RemoveTask.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateTaskResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.todo/", name = "createTaskResponse")
    public JAXBElement<CreateTaskResponse> createCreateTaskResponse(CreateTaskResponse value) {
        return new JAXBElement<CreateTaskResponse>(_CreateTaskResponse_QNAME, CreateTaskResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateTask }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.todo/", name = "createTask")
    public JAXBElement<CreateTask> createCreateTask(CreateTask value) {
        return new JAXBElement<CreateTask>(_CreateTask_QNAME, CreateTask.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EditTask }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.todo/", name = "editTask")
    public JAXBElement<EditTask> createEditTask(EditTask value) {
        return new JAXBElement<EditTask>(_EditTask_QNAME, EditTask.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FilterTasks }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.todo/", name = "filterTasks")
    public JAXBElement<FilterTasks> createFilterTasks(FilterTasks value) {
        return new JAXBElement<FilterTasks>(_FilterTasks_QNAME, FilterTasks.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveTaskResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.todo/", name = "removeTaskResponse")
    public JAXBElement<RemoveTaskResponse> createRemoveTaskResponse(RemoveTaskResponse value) {
        return new JAXBElement<RemoveTaskResponse>(_RemoveTaskResponse_QNAME, RemoveTaskResponse.class, null, value);
    }

}
