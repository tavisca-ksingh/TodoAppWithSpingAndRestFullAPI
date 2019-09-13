package com.tavisca.workshops.todolist.com.tavisca.workshops.todolist;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ToDoListRepositoryTest {
	@Autowired
	private TodoItemRepository repository;
	
	@Test
	public void testFindAll() {
		List<ToDoList> items = repository.findAll();
		assertEquals(3, items.size());
	}
	
	
}
