package edu.poly.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.poly.entity.Student;
import edu.poly.service.StudentService;

@RestController
@RequestMapping("ImportExcel")
@CrossOrigin("*")

public class DemoAddProductExcelController {

	@Autowired
	StudentService service;
	
	@PostMapping("")
	public Student post(@RequestBody Student student) {
		service.save(student);
		return student;
	}
	
	@GetMapping("{email}")
	public Student get(@PathVariable("email")String email) {
		Student st = service.findById(email).get();
		return st;
	}
}
