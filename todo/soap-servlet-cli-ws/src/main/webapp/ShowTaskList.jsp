<%@ page import="java.util.List" %>
<%@ page import="todo.model.TodoTask" %>
<%@ page import="todo.servlet.JSPHelper" %>
<html>
<head>
	<title>ToDo Organizer</title>
	<style>
		form  { display: table;      }
		row   { display: table-row;  }
		cell  { display: table-cell; }
		div#pad { }
	</style>
</head>
<body>
	<!-- JAVA // Retrieve request parameters and attributes -->
	<%
		List<TodoTask> results = (List<TodoTask>)request.getAttribute("results");
	
		String searchTitle = request.getParameter("title");
		String searchProject = request.getParameter("project");
		String searchContent = request.getParameter("content");
		String searchPriority = request.getParameter("priority");
		String searchStatus = request.getParameter("status");
		
		if(searchTitle == null) searchTitle = "";
		if(searchProject == null) searchProject = "";
		if(searchContent == null) searchContent = "";
		
		String[] priorityArray = JSPHelper.getPrioritySelectedArray(searchPriority);
		String[] statusArray = JSPHelper.getStatusSelectedArray(searchStatus);
	%>

	<!-- HTML // Show Search Form -->
	<h1>Filter Search</h1>
	<div style="margin-left: 1em;">
		(Note: Provide nothing for not filter a text field)
		<form method="get" action="query">
			<row>
				<cell>Title:</cell>
				<cell><input type="text" name="title" value="<%= searchTitle %>"></cell>
			</row>
			<row>
				<cell>Project:</cell>
				<cell><input type="text" name="project" value="<%= searchProject %>" ></cell>
			</row>
			<row>
				<cell>Content:</cell>
				<cell><input type="text" name="content" value="<%= searchContent %>" ></cell>
			</row>
			<row>
				<cell>Priority:</cell>
				<cell><select name="priority">
					<option value="" <%= priorityArray[0] %> >All</option>
					<option value="low" <%= priorityArray[1] %> >Low</option>
					<option value="normal" <%= priorityArray[2] %> >Normal</option>
					<option value="important" <%= priorityArray[3] %> >Important</option>
					<option value="critical" <%= priorityArray[4] %> >Critical</option>
				</select></cell>
			</row>
			<row>
				<cell>Status:</cell>
				<cell><select name="status">
					<option value="" <%= statusArray[0] %> >All</option>
					<option value="pending" <%= statusArray[1] %> >Pending</option>
					<option value="completed" <%= statusArray[2] %> >Completed</option>
				</select></cell>
			</row>
			 
			<input type="submit" value="Search">
		</form>
	</div>
	
	<!-- HTML // Show Create Button -->
	<div style="margin-left: 1em;">
		Cannot find a task? Create your own task!: 
		<form method="get" action="create" style="display:inline-block">
			<input type="submit" value="Create">
		</form>
	</div>
	<h1>Search Results</h1>
    <div style="margin-left: 1em;">
		<%			
			if(results == null || results.size() == 0){
		%>

				No results from provided filters
				<br/>
		
		<%
			} else {
			
				for(TodoTask task : results){
		%>
					<!-- JAVA // Print task information -->
					<%= task.toString("","<br />") %>
				
					<!-- HTML // Show edit and remove buttons -->
					<form method="get" action="edit" style="display:inline-block">
						<input type="hidden" name="taskID" value="<%= task.getId() %>">
						<input type="submit" value="Edit">
					</form>
					<form method="post" action="remove" style="display:inline-block">
						<input type="hidden" name="taskID" value="<%= task.getId() %>">
						<input type="submit" value="Remove">
					</form>
					<br/>
		<%
				}
			}
		%>
	</div>
	<br/>
	<a href="#top">Go to top</a>
</body>
</html>