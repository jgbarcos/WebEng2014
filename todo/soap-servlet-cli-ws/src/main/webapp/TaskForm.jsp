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
		String htmlTitle = (String)request.getAttribute("htmlTitle");
		String htmlButtonAction = (String)request.getAttribute("htmlButtonAction");
	
		String taskID = (String)request.getAttribute("taskID");
		String title = (String)request.getAttribute("title");
		String project = (String)request.getAttribute("project");
		String endDate = (String)request.getAttribute("endDate");
		String content = (String)request.getAttribute("content");
		String priority = (String)request.getAttribute("priority");
		String status = (String)request.getAttribute("status");
		
		if(title == null) title = "";
		if(project == null) project = "";
		if(content == null) content = "";
		if(endDate == null) endDate = "";
		
		String[] priorityArray = JSPHelper.getPrioritySelectedArray(priority);
		String[] statusArray = JSPHelper.getStatusSelectedArray(status);
	%>

	<!-- HTML // Show Task Form -->
	<h1><%= htmlTitle %></h1>
	<div style="margin-left: 1em;">
		<form method="post" action="<%= htmlButtonAction %>">
			<row>
				<cell>Title:</cell>
				<cell><input type="text" name="title" value="<%= title %>"></cell>
			</row>
			<row>
				<cell>Project:</cell>
				<cell><input type="text" name="project" value="<%= project %>" ></cell>
			</row>
			<row>
				<cell>End Date:</cell>
				<cell><input type="text" name="endDate" value="<%= endDate %>" ></cell>
			</row>
			<row>
				<cell>Content:</cell>
				<cell>
					<textarea name="content" rows="4" cols="50"><%= content %></textarea>
				</cell>
			</row>
			<row>
				<cell>Priority:</cell>
				<cell><select name="priority">
					<option value="" <%= priorityArray[0] %> >Default</option>
					<option value="low" <%= priorityArray[1] %> >Low</option>
					<option value="normal" <%= priorityArray[2] %> >Normal</option>
					<option value="important" <%= priorityArray[3] %> >Important</option>
					<option value="critical" <%= priorityArray[4] %> >Critical</option>
				</select></cell>
			</row>
			<row>
				<cell>Status:</cell>
				<cell><select name="status">
					<option value="" <%= statusArray[0] %> >Default</option>
					<option value="pending" <%= statusArray[1] %> >Pending</option>
					<option value="completed" <%= statusArray[2] %> >Completed</option>
				</select></cell>
			</row>
			 
			<input type="hidden" name="taskID" value="<%= taskID %>">
			<input type="submit" value="Save">
		</form>
	</div>
</body>
</html>