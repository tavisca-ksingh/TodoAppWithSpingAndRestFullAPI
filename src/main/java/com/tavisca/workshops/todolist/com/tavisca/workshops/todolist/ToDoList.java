package com.tavisca.workshops.todolist.com.tavisca.workshops.todolist;

public class ToDoList {
	
	private Integer id;
	private String name;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "ToDoList [id=" + id + ", name=" + name + "]";
	}
	public ToDoList(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;

	}
	
	
	

}
