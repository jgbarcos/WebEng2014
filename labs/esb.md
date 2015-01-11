# Lab 5. Enterprise Service Bus
This laboratory will consist of a series of tasks designed to familiarize you with the concept 
[Enterprise Service Bus](http://en.wikipedia.org/wiki/Enterprise_service_bus). It is expected that 
students use the online documentation of the ESB [Apache ServiceMix](http://servicemix.apache.org/) 
during the laboratory.

## Introduction to `ServiceMix`

Download (or ask a copy to your teacher) a copy of [Apache ServiceMix 5.3.0](http://servicemix.apache.org/downloads/servicemix-5.3.0.html) for your OS. Next, decompress it in a folder. Open a terminal and go to the folder `apache-servicemix-5.0.0-SNAPSHOT\bin` and run `servicemix`. This will launch `ServiceMix` and the `SeviceMix console`. Hit `<tab>` to see the available commands.  

Explore the console and run the following commands:
* Run `help osgi:shutdown` and after understanding the command run `osgi:shutdown`.
* What does `list` show? And `features:list`? Do you recognize something? 
* Run `features:install webconsole` and open with a browser [http://localhost:8181/system/console]( http://localhost:8181/system/console) [user:smx, password:smx]. What do you see?
* Run `log:display`. Which command shows the last occurred exception recorded in the log?

## EIP: The magic `Camel` route

Let's copy files automatically between folders using `Camel`. Create and copy to the `deploy` folder the following file.
```xml
<?xml version="1.0" encoding="UTF-8"?>
<blueprint
    xmlns="http://www.osgi.org/xmlns/blueprint/v1.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="
	http://www.osgi.org/xmlns/blueprint/v1.0.0
	http://www.osgi.org/xmlns/blueprint/v1.0.0/blueprint.xsd">
    <camelContext xmlns="http://camel.apache.org/schema/blueprint">
		<route>
			<from uri="file:input"/>
			<log message="Moving ${file:name} to the output directory"/>
			<to uri="file:output"/>
		</route>
    </camelContext>
</blueprint>
```
Questions:
* What is [OSGI](http://www.osgi.org/Main/HomePage)? What is a [Bluepring](http://www.ibm.com/developerworks/opensource/library/os-osgiblueprint/)? Are you **already** using an OSGI platform? Which?
* What had happen? How `list` and `log:display` help?
* Could you list the available `Camel` routes? Hint, use first `help camel`.
* Test the route. Copy a file to `input` and see how it is moved to `output`.
* Can you uninstall the route?

## REST: `JAX-RS` customer

In order to understand this and following tasks you should see the source code of the examples. Import to your Eclipse the Maven projects found in the `examples` folder. Open the `README.txt` file of the `cxf-jaxrs-blueprint` example and follow the instructions to **fast** install and run the JAX-RS CXF examples.

Questions:
* From where the code was installed?
* What can be found at [http://localhost:8181/cxf/crm/customerservice?_wadl&_type=xml](http://localhost:8181/cxf/crm/customerservice?_wadl&_type=xml)?
* What is the advantage of using a `Blueprint` here?
* If you want to deploy a new version to `ServiceMix`, how you would proceed? What would be the role of the maven plugin `maven-bundle-plugin`?

## SOAP: `JAX-WS` Hello world

Read the `README.txt` file of the `cxf-jaxrws-blueprint` example and insall the JAX-WS CXF examples. Test it using the `client.html`.

Questions:
* How many CXF endpoints are running now? 
* Can you stop and enpoint? Why you could want to do it?
* Can you explain why the maven plugin `maven-bundle-plugin` is different from JAX-RS bundle?

## Events: pub-sub example

`ServiceMix` supports different event-based systems (e.g. `JMS`, `ActiveMQ`). We are going to explore the support of the OASIS standard [WS-Notification](https://www.oasis-open.org/committees/tc_home.php?wg_abbrev=wsn). Go to the project `cxf-wsn` and install in `ServiceMix` the receiver and notifier modules.

Questions:
* [Only if something goes wrong] How to uninstall/reinstall a bundle?
* Is it possible to generate a report that describes what's going on a route (e.g. number of emails sent)?
* Which component put the message in the queue? Who provides it? Locate its URL
* List the busses working in your instance? What do you think the term bus identifies? 

## Challenge: Use a BPMN solution in your code

The following code is the [BPMN](http://www.bpmn.org/) specification of a process (see more details [here](http://servicemix.apache.org/docs/5.x/activiti/activiti-camel-example.html)). This specification can be run by [Activity](http://www.activiti.org/), a BPMN process engine supported by `ServiceMix`. `ServiceMix` glues each `serviceTask` to routes, brokers, services, Java beans ... 
```xml
<definitions xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
	xmlns:activiti="http://activiti.org/bpmn" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" 
	xmlns:omgdc="http://www.omg.org/spec/DD/20100524/DC"
	xmlns:omgdi="http://www.omg.org/spec/DD/20100524/DI"
	typeLanguage="http://www.w3.org/2001/XMLSchema" 
	expressionLanguage="http://www.w3.org/1999/XPath" 
	targetNamespace="http://www.activiti.org/test">
    <process id="OrderProcess" isExecutable="true">
        <startEvent id="start"/>
        <sequenceFlow id="flow1" sourceRef="start" targetRef="processOrder"/>
        <serviceTask id="processOrder" activiti:delegateExpression="${camel}"/>
        <sequenceFlow id="flow2" sourceRef="processOrder" targetRef="receiveDelivery"/>
        <receiveTask id="receiveDelivery" name="Wait for Delivery" />
        <sequenceFlow id="flow3" sourceRef="receiveDelivery" targetRef="processDelivery"/>
        <serviceTask id="processDelivery" activiti:delegateExpression="${camel}"/>
        <sequenceFlow id="flow4" sourceRef="processDelivery" targetRef="end"/>
        <endEvent id="end"/>
    </process>
</definitions>
```
This process defines the BPMN definition and is automatically deployed as soon as bundle is started:
```
start --> processOrder --> waitForDelivery --> processDelivery --> end
```
Can you use `ServiceMix` (i.e JAX-RS + Camel routes + Java Beans + Activiti) in your project?


