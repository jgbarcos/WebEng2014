
package todo.service;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.8
 * Generated source version: 2.1
 * 
 */
@WebService(name = "TodoWebService", targetNamespace = "http://service.todo/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface TodoWebService {


    /**
     * 
     * @param arg0
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "editTask", targetNamespace = "http://service.todo/", className = "todo.service.EditTask")
    @ResponseWrapper(localName = "editTaskResponse", targetNamespace = "http://service.todo/", className = "todo.service.EditTaskResponse")
    public int editTask(
        @WebParam(name = "arg0", targetNamespace = "")
        TransferTask arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "removeTask", targetNamespace = "http://service.todo/", className = "todo.service.RemoveTask")
    @ResponseWrapper(localName = "removeTaskResponse", targetNamespace = "http://service.todo/", className = "todo.service.RemoveTaskResponse")
    public int removeTask(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "createTask", targetNamespace = "http://service.todo/", className = "todo.service.CreateTask")
    @ResponseWrapper(localName = "createTaskResponse", targetNamespace = "http://service.todo/", className = "todo.service.CreateTaskResponse")
    public int createTask(
        @WebParam(name = "arg0", targetNamespace = "")
        TransferTask arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns todo.service.TransferTask
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getTask", targetNamespace = "http://service.todo/", className = "todo.service.GetTask")
    @ResponseWrapper(localName = "getTaskResponse", targetNamespace = "http://service.todo/", className = "todo.service.GetTaskResponse")
    public TransferTask getTask(
        @WebParam(name = "arg0", targetNamespace = "")
        int arg0);

    /**
     * 
     * @param arg4
     * @param arg3
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns java.util.List<todo.service.TransferTask>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "filterTasks", targetNamespace = "http://service.todo/", className = "todo.service.FilterTasks")
    @ResponseWrapper(localName = "filterTasksResponse", targetNamespace = "http://service.todo/", className = "todo.service.FilterTasksResponse")
    public List<TransferTask> filterTasks(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        String arg3,
        @WebParam(name = "arg4", targetNamespace = "")
        String arg4);

}
