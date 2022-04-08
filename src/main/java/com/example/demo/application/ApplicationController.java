package com.example.demo.application;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ApplicationController {

	@GetMapping
	public String index() {
		return "index";
	}

	@GetMapping("login")
	public String login() {
		return "login";
	}

	@GetMapping("students")
	public String students() {
		return "students";
	}

}
