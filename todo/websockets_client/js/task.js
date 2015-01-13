var ws_endpoint = "ws://localhost:8025/websockets/todo";
var ws;

$( document ).ready(function() {
	$("#create_button").click(function () {
		var task = readTaskProperties();
		if(task.length > 0){
			createTask(task);
		}
	});
	
	$("#edit_button").click(function () {
		var task = readTaskProperties();
		if(task.length > 0){
			editTask(task);
		}
	});
	prepareSocket();
});

function prepareSocket(){
	ws = new WebSocket(ws_endpoint);
	ws.onopen = function () {
	};

	ws.onmessage = function (response){
		var command = response.data.substring(0,response.data.indexOf("$"));
		var content = response.data.substr(response.data.indexOf("$")+1);
		
		console.log("COMMAND: " + command);
		console.log("CONTENT: " + content);
		switch (command) {
		case "CREATE":
			var tasks = content.split("&");
			for(var i in tasks){
				var task = tasks[i];
				if(task.length > 0){
					taskList_add(task);
				}
			}
			taskList_print();
			break;
		case "EDIT":
			var tasks = content.split("&");
			for(var i in tasks){
				var task = tasks[i];
				if(task.length > 0){
					taskList_edit(task);
				}
			}
			taskList_print();
			break;
		case "DELETE":
			var ids = content.split("&");
			for(var i in ids){
				var id = ids[i];
				if(id.length > 0){
					taskList_remove(id);
				}
			}
			taskList_print();
			break;
		case "LIST":
			var tasks = content.split("&");
			taskList_create(tasks);
			taskList_print();
			break;
		case "ERROR":
			console.log("Error ocurred: " + command);
			break;
		}
	};
	
	ws.onerror = function (){
		alert("Un error ha ocurrido con el servidor");
	};
}

/*
 * Simple TaskList implementation on javascript
 */
var taskList = [];

function taskList_create(tasks){
	taskList = [];
	for (var i in tasks){
		if(tasks[i] != ""){
			taskList[taskList.length] = tasks[i];
		}
	}
}

function taskList_add(task){
	if(task.length > 0){
		// Remove duplicates
		taskList_remove(taskList_getID(task));
		
		// Search an empty position
		var added = false;
		for(var i in taskList){
			if(taskList[i] == ""){
				taskList[i] = task;
				added = true;
				break;
			}
		}
		
		// Add at last if there isnt any empty positions
		if(added == false){
			taskList[taskList.length] = task;
		}
	}
}

function taskList_remove(id){
	if(!isNaN(id)){
		for(var i in taskList){
			if(!taskList_isEmptyAt(i)){
				// Mark task as empty position
				if(taskList_getID(taskList[i]) == id){
					taskList[i] = "";
				}
			}
		}
	}
}

function taskList_edit(task){
	if(task.length > 0){
		var id = taskList_getID(task);
		var changed = false;
		for(var i in taskList){
			if(!taskList_isEmptyAt(i)){
				// Remove duplicates
				if(changed = true){
					taskList[i] = "";
				}
				// Edit only once
				else if(id == taskList_getID(taskList[i])){
					taskList[i] = task;
					changed = true;
				}
			}
		}
	}
}

function taskList_getID(task){
	var props = task.split("|");
	if(props.length > 0){
		return props[0];
	}
	else{
		return "-1";
	}
}

function taskList_isEmptyAt(index){
	if(taskList[index].length > 0){
		return false;
	}
	else{
		return true;
	}
}

function taskList_print(){
	$('#TodoList').find('tbody').html("");
	var htmlList = "";
	for (var i in taskList){
		if(!taskList_isEmptyAt(i)){
			var props = taskList[i].split("|");
			htmlList += taskList_toHTML(props);
		}
	}
	$('#TodoList').find('tbody').append(htmlList);
}

function taskList_toHTML(props){
	var result = '<tr>';
	for(var i in props){
		result += '<td>'+props[i]+'</td>';
	}
	result += '<td>'+addRemoveHTMLButton(props[0])+'</td>';
	result += '</tr>';
	return result;
}

function addRemoveHTMLButton(id){
	return '<input type="button" value="Remove" onClick="deleteTask(\'' + id + '\')" />';
}

function readTaskProperties(){
	var task_id = $('#task_id').val();
	var task_title = $('#task_title').val();
	var task_project = $('#task_project').val();
	var task_end_date = $('#task_end_date').val();
	var task_content = $('#task_content').val();
	var task_priority = $('#task_priority').val();
	var task_status = $('#task_status').val();
	
	var task = "";
	
	if( task_title.length == 0 || task_project.length == 0 
	  || task_end_date.length == 0 || task_content.length == 0){
		return "";
		alert("Fill all the fields before creating a task");
	}
	else{
		if(task_id.isNaN){
			task_id = "-1";
		}
		task = task_id
			+"|"+ task_title
			+"|"+ task_end_date
			+"|"+ task_project
			+"|"+ task_content
			+"|"+ task_priority
			+"|"+ task_status;
	}
	
	return task;
}

/*
 * Actions of HTML Buttons
 */
function refreshTaskList(){
	ws.send("LIST$");
}

function createTask(task){
	ws.send("CREATE$"+task);
}

function editTask(task){
	ws.send("EDIT$"+task);
}
function deleteTask(id){
	ws.send("DELETE$"+id);
}