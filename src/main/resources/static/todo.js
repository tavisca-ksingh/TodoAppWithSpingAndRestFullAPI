var taskInput=document.getElementById("add-new-task");
var addButton=document.getElementsByTagName("button")[0];
var incompleteTask=document.getElementById("incomplete-tasks");
var completedTasks=document.getElementById("completed-tasks");


	var baseurl = "http://localhost:8080/todo/";
	var idValue=0;
	function getTodo(){
	  var xmlhttp = new XMLHttpRequest();
	  xmlhttp.open("GET",baseurl ,true);
	  xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState ===4 && xmlhttp.status ===200){
		  var todo = JSON.parse(xmlhttp.responseText);
		  for (i = 0; i < todo.length; i++){
			var name= todo[i].name;
			var id= todo[i].id;
			idValue=id;
			addTaskFromServer(id,name);
		  }
		}
	  };
	  xmlhttp.send();
	}
	
	window.onload = function(){
	  getTodo();
	}

	
	function postTodo(inputValue){
		var newElement = inputValue;
        console.log(newElement);
        var xmlhttp = new XMLHttpRequest();   // new HttpRequest instance 
        xmlhttp.open("POST", baseurl);
    
        xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
        xmlhttp.send(JSON.stringify({ "id":idValue, "name": newElement, "details" : newElement } ));
    
    }
	
	function deleteValueTodo(value){
		var xhr = new XMLHttpRequest(); 
		xhr.open("DELETE", baseurl+ value, true);
		 xhr.onload = function () 
		 { var users = JSON.parse(xhr.responseText); 
			if (xhr.readyState == 4 && xhr.status == "200") {
				 console.table(users); 
				} 
				else { 
					console.error(users);
				 } }
				  xhr.send(null);
	}
	
	function updateValueTodo(id,name){
		var data = {};
		data.name = name;
		var json = JSON.stringify(data);
		var xhr = new XMLHttpRequest();
		xhr.open("PUT", baseurl + id, true);
		xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
		xhr.onload = function () {
			var users = JSON.parse(xhr.responseText);
			if (xhr.readyState == 4 && xhr.status == "200") {
				console.table(users);
			} else {
				console.error(users);
			}
		}
		xhr.send(json);
	}


addTaskFromServer= function(id,name){
	var taskInputValue=name;
		let option=document.createElement('option');
		option.value=taskInputValue;
		document.getElementById('list').appendChild(option);
	var listItem=createNewTaskElement(taskInputValue);
	listItem.id=id;
	incompleteTask.appendChild(listItem);
	bindTaskEvents(listItem, taskCompleted);
	
}



var createNewTaskElement=function(taskString){
	var { label, checkBox, editInput, editButton, deleteButton, listItem } = createElements();

	label.innerText=taskString;

	checkBox.type="checkbox";
	editInput.type="text";
	editButton.innerText="Edit";
	editButton.className="edit";
	deleteButton.innerText="Delete";
	deleteButton.className="delete";


	listItem.appendChild(checkBox);
	listItem.appendChild(label);
	listItem.appendChild(editInput);
	listItem.appendChild(editButton);
	listItem.appendChild(deleteButton);

	return listItem;
}

function createElements() {
	
	var listItem = document.createElement("li");
    var checkBox = document.createElement("input");
    var label = document.createElement("label");
	var editInput = document.createElement("input");
	editInput.disabled=false;
	var editButton = document.createElement("button");
	var deleteButton = document.createElement("button");
	
    return { label, checkBox, editInput, editButton, deleteButton, listItem };
}


var addTask=function(){
	if(taskInput.value!='')
	{
		var taskInputValue=taskInput.value;
		let option=document.createElement('option');
		option.value=taskInputValue;
		document.getElementById('list').appendChild(option);
	var listItem=createNewTaskElement(taskInputValue);
	listItem.id=++idValue;
	incompleteTask.appendChild(listItem);
	bindTaskEvents(listItem, taskCompleted);
		postTodo(taskInputValue);
	}
	taskInput.value="";
}


var editTask=function(){
	var listItem=this.parentNode;
	var editInput=listItem.querySelector('input[type=text]');
	var label=listItem.querySelector("label");
	var containsClass=listItem.classList.contains("editMode");
	var editButton = listItem.querySelector(".edit");

	console.log(editInput.value);
	console.log(listItem.id);

		if(containsClass){
			label.innerText=editInput.value;
			editButton.innerText="Edit";
			updateValueTodo(listItem.id,editInput.value);
		}else{
			editButton.innerText="Save";
			editInput.value=label.innerText;
		
		}
		listItem.classList.toggle("editMode");
}

var deleteTask=function(){
		var listItem=this.parentNode;
		var ul=listItem.parentNode;
		var deleteId=listItem.id;
		console.log(deleteId);
		deleteValueTodo(deleteId);
		ul.removeChild(listItem);
		
}

var taskCompleted=function(){
	var listItem=this.parentNode;
	completedTasks.appendChild(listItem);
	bindTaskEvents(listItem, taskIncomplete);

}


var taskIncomplete=function(){
	var listItem=this.parentNode;
	incompleteTask.appendChild(listItem);
			bindTaskEvents(listItem,taskCompleted);
}

addButton.onclick=addTask;
addButton.addEventListener("click",addTask);


var bindTaskEvents=function(taskListItem,checkBoxEventHandler){
	var checkBox=taskListItem.querySelector("input[type=checkbox]");
	var editButton=taskListItem.querySelector("button.edit");
	var deleteButton=taskListItem.querySelector("button.delete");
			editButton.onclick=editTask;
			deleteButton.onclick=deleteTask;
			checkBox.onchange=checkBoxEventHandler;
}

	for (var i=0; i<incompleteTask.children.length;i++){
		bindTaskEvents(incompleteTask.children[i],taskCompleted);
	}

	for (var i=0; i<completedTasks.children.length;i++){
		bindTaskEvents(completedTasks.children[i],taskIncomplete);
	}  


