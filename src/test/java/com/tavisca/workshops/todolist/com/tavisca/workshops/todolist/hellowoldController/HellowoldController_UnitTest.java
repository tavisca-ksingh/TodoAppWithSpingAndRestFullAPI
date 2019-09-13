package com.tavisca.workshops.todolist.com.tavisca.workshops.todolist.hellowoldController;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.tavisca.workshops.todolist.com.tavisca.workshops.todolist.TodoDao;
import com.tavisca.workshops.todolist.com.tavisca.workshops.todolist.hello.HelloWorldController;

@RunWith(SpringRunner.class)
@WebMvcTest(HelloWorldController.class)
public class HellowoldController_UnitTest {
	
	@Autowired
	private MockMvc mockmvc;
	

	@Test
	public void helloWorld_basic() {
		
	}
	
}
