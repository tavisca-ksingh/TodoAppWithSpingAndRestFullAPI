package com.tavisca.workshops.todolist.com.tavisca.workshops.todolist;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tavisca.workshops.todolist.com.tavisca.workshops.todolist.exception.UserNotFoundException;

@RestController
@CrossOrigin
public class TodoRestController {

	@Autowired
	private TodoDao todoDao;

	
	

	@GetMapping(path = "/todo")
	public List<ToDoList> retrieveListOfTodoItems() {
		return todoDao.listOfItems();
	}
	
	@GetMapping(path="todo-list-from-db")
	public List<ToDoList> retrieveAllItems(){
		return todoDao.reteriveAllItems();
	}

	@GetMapping(path = "/todo/{id}")
	public ToDoList getItemById(@PathVariable int id) {
		ToDoList item = todoDao.findAnItem(id);
		if (item == null)
			throw new UserNotFoundException("id-" + id);
		return item;
	}
	

	@PostMapping("/todo")
	public ResponseEntity<?> createNewItem(@RequestBody ToDoList item) {
		
		if(item.getName()==null)
		{
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); 
		}
		ToDoList savedItem = todoDao.save(item);
//		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedItem.getId())
//			.toUri();
//		System.out.println(location);
		return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
//		return ResponseEntity.created(location).build();
	}

	@PutMapping("/todo/{id}")
	public ResponseEntity<?> putItem(@PathVariable int id,
	                              @Valid @RequestBody ToDoList item) {
		//ToDoList savedItem =todoDao.updateAnItem(id, item);
		// if(savedItem== null) { throw new UserNotFoundException("id - " + id); }
	
		        return new ResponseEntity(HttpStatus.OK) ;
		     
	}
	

	@DeleteMapping("/todo/{id}")
	public  ResponseEntity<?> deleteItem(@PathVariable int id) {
		ToDoList item = todoDao.deleteById(id);
		if(item==null) {
			throw new UserNotFoundException("id - " + id);
		}
		return new ResponseEntity<>(item, HttpStatus.OK);
	}

}
