package com.tavisca.workshops.todolist.com.tavisca.workshops.todolist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class TodoDao {
	
	
	ToDoList toDoList;
	private int todoCount=0;
	private static List<ToDoList> items=new ArrayList<ToDoList>(){
		{add(new ToDoList(1,"call"));}
	};
	
	@Autowired
	private TodoItemRepository repository;
	
	public List<ToDoList> reteriveAllItems(){
		return repository.findAll();
	} 
	
	
	public ToDoList showItems() {
		return new ToDoList(1,"call");
	}
	
	
	public List<ToDoList> listOfItems() {
		return items; 
	}
	
	public ToDoList save(ToDoList item){
		 if(item.getId()==null)
		 {
			 item.setId(++todoCount);
		}
		 items.add(item);
		 return item;
	}
	
	public ToDoList findAnItem(int itemId) {
		for (ToDoList item : items) {
			if(item.getId()==itemId)
				return item;	
		}		
		return null;		
	}
	
	public boolean updateAnItem(int itemId, ToDoList data) {
		for (ToDoList item : items) {
		
			if(item.getId()==itemId)
			{	
				item.setName(data.getName());
				items.set(itemId,item);
				return true;
			}	
		}
		return false;
	}
	
	public ToDoList deleteById(int itemId) {
		Iterator<ToDoList> iterator=items.iterator();
		while(iterator.hasNext()) {
			ToDoList item=iterator.next();
			if(item.getId()==itemId) {
				iterator.remove();
				return item;
			}
		}
		return null;
	}

}
