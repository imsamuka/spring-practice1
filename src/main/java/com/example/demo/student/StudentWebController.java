package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
public class StudentWebController {

	private final StudentService studentService;

	@Autowired
	public StudentWebController(StudentService studentService) {
		this.studentService = studentService;
	}

	@GetMapping("list")
	@PreAuthorize("hasAuthority('student:read')")
	public String list(Model model) {
		model.addAttribute("students", studentService.getAllStudents());
		return "student-list";
	}

	@GetMapping("add")
	@PreAuthorize("hasAuthority('course:write')")
	public String add(Model model) {
		model.addAttribute("student", new Student());
		return "student-add";
	}

	@PostMapping("add")
	@PreAuthorize("hasAuthority('course:write')")
	public String addStudent(@Validated Student student, BindingResult result) {
		if (result.hasErrors())
			return "student-add";

		studentService.addNewStudent(student);

		return "redirect:list";
	}

	@DeleteMapping("delete/{studentId}")
	@PreAuthorize("hasAuthority('course:write')")
	public String deleteStudent(@PathVariable("studentId") Long studentId) {
		studentService.removeStudent(studentId);

		return "redirect:list";
	}
}
