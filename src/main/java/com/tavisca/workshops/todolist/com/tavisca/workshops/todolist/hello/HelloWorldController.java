package com.tavisca.workshops.todolist.com.tavisca.workshops.todolist.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

		@GetMapping("/hello-world")
		public String helloWOrld() {
			return "Hello World";
		}
}
