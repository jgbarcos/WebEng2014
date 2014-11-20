
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

    private final static QName _ListTask_QNAME = new QName("http://service.todo/", "listTask");
    private final static QName _ListTaskResponse_QNAME = new QName("http://service.todo/", "listTaskResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: todo.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ListTaskResponse }
     * 
     */
    public ListTaskResponse createListTaskResponse() {
        return new ListTaskResponse();
    }

    /**
     * Create an instance of {@link ListTask }
     * 
     */
    public ListTask createListTask() {
        return new ListTask();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListTask }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.todo/", name = "listTask")
    public JAXBElement<ListTask> createListTask(ListTask value) {
        return new JAXBElement<ListTask>(_ListTask_QNAME, ListTask.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListTaskResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.todo/", name = "listTaskResponse")
    public JAXBElement<ListTaskResponse> createListTaskResponse(ListTaskResponse value) {
        return new JAXBElement<ListTaskResponse>(_ListTaskResponse_QNAME, ListTaskResponse.class, null, value);
    }

}
