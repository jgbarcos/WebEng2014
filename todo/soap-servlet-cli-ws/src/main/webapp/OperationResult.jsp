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
		String operationResult = (String)request.getAttribute("operationResult");
	%>

	<!-- HTML // Show Search Form -->
	<h1>Filter Search</h1>
	<div style="margin-left: 1em;">
		(Note: Provide nothing for not filter a text field)
		<form method="get" action="query">
			<row>
				<cell>Title:</cell>
				<cell><input type="text" name="title"></cell>
			</row>
			<row>
				<cell>Project:</cell>
				<cell><input type="text" name="project"></cell>
			</row>
			<row>
				<cell>Content:</cell>
				<cell><input type="text" name="content"></cell>
			</row>
			<row>
				<cell>Priority:</cell>
				<cell><select name="priority">
					<option value="">All</option>
					<option value="low">Low</option>
					<option value="normal">Normal</option>
					<option value="important">Important</option>
					<option value="critical">Critical</option>
				</select></cell>
			</row>
			<row>
				<cell>Status:</cell>
				<cell><select name="status">
					<option value="">All</option>
					<option value="pending">Pending</option>
					<option value="completed">Completed</option>
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
	<h1>Operation Result</h1>
    	<div style="margin-left: 1em;">
			<%= operationResult %>
		</div>
	<br/>
	<a href="#top">Go to top</a>
</body>
</html>