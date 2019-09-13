package com.tavisca.workshops.todolist.com.tavisca.workshops.todolist;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoItemRepository extends JpaRepository<ToDoList,Integer>{
	
}
