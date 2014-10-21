<%@ page import="java.util.List" %>
<%@ page import="todo.model.TodoTask" %>

<html>
    <head>
    	<% String title = (String)request.getAttribute("title"); %>
        <title><%= title %></title>
    </head>
    <body>
    	<h1><%= title %></h1>
        
		<%
			List<TodoTask> results = (List<TodoTask>)request.getAttribute("results");
			
			if(results == null || results.size() == 0){
		%>
		
		No results from provided filters
		
		<%
			} else {
			
				for(TodoTask task : results){
		%>
		
		<%= task.toString("","<br />") %>
		<br/>
		
		<%
				}
			}
		%>

    </body>
</html>