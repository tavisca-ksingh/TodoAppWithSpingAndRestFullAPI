package com.tavisca.workshops.todolist.com.tavisca.workshops.todolist;

import org.h2.command.dml.MergeUsing.When;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import ch.qos.logback.core.status.Status;

@RunWith(SpringRunner.class)
@WebMvcTest(TodoRestController.class)
public class TodoListRestController_Tests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TodoDao todoDao;

	@Test
	public void todoList_GetTest() throws Exception {
		List<ToDoList> items = new ArrayList<ToDoList>() {
			{
				add(new ToDoList(1, "call"));
			}
		};

		Mockito.when(todoDao.listOfItems()).thenReturn(items);
		RequestBuilder request = MockMvcRequestBuilders.get("/todo").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(request).andExpect(status().isOk())
				.andExpect(content().json("[{id:1,name:call}]")).andReturn();
	}

	@Test
	public void reteriveAllItemsFromdb() throws Exception {

		Mockito.when(todoDao.reteriveAllItems())
				.thenReturn(Arrays.asList(new ToDoList(1, "call"), new ToDoList(2, "Eat")));

		RequestBuilder request = MockMvcRequestBuilders.get("/todo-list-from-db").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(request).andExpect(status().isOk())
				.andExpect(content().json("[{id:1,name:call},{id:2,name:Eat}]")).andReturn();
	}

	@Test
	public void todoList_PostTest() throws Exception {

		ToDoList items = new ToDoList(10, "Chai pee lo Friends");
		String json = "{\"id\":2,\"name\":\"eat\"}";

		Mockito.when(todoDao.save(items)).thenReturn(items);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/todo").accept(MediaType.APPLICATION_JSON)
				.content("{\"id\":2,\"name\":\"eat\"}").contentType(MediaType.APPLICATION_JSON);

//	   MvcResult result = mockMvc.perform(requestBuilder) 
//						  .andExpect(status().isCreated())
//						  .andExpect(content().json("/todo-list/"))
//						  .andReturn();
//						  

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		System.out.println(response.getStatus());
		System.out.println(response.getHeader(HttpHeaders.LOCATION));
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
//	  assertEquals("http://localhost:8080/todo-list",
//	  response.getHeader(HttpHeaders.LOCATION));

	}

	@Test
	public void delete_todoList_Test() throws Exception {

		ToDoList item = new ToDoList(10, "Hello");

		Mockito.when(todoDao.deleteById(10)).thenReturn(item);

		this.mockMvc.perform(MockMvcRequestBuilders.delete("/todo/10").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	@Test
	public void updateTodoItem() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		ToDoList mockTodolist = new ToDoList(1, "do brush");
		Mockito.when(todoDao.updateAnItem(1, mockTodolist)).thenReturn(true);

		
		  RequestBuilder requestBuilder= MockMvcRequestBuilders.put("/todo/1")
				  						.accept(MediaType.APPLICATION_JSON)
									  .content(objectMapper.writeValueAsString(mockTodolist))
									  .contentType(MediaType.APPLICATION_JSON);
		  
		  MvcResult result=mockMvc.perform(requestBuilder).andReturn();
		  MockHttpServletResponse res=result.getResponse();
	        Assert.assertEquals(HttpStatus.OK.value(),res.getStatus());
		  
//		 mockMvc.perform(requestBuilder) 
//				  .andExpect(status().isOk())
//				  .andReturn();
	}
	
}
