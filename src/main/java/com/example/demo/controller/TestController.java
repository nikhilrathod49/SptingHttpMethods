package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.components.Student;

@RestController
public class TestController {

	static List<Student> students = new ArrayList<>();

//	@RequestMapping(value = "/endpoint" , method = RequestMethod.GET)
	@GetMapping("/endpoint/{roll}/student")
	public void printmsg(@PathVariable String roll) {
		System.out.println("Endpoint called with value " + roll);
	}

	@GetMapping("/endpoint/student")
	public void printmsg1(@RequestParam String num) {
		System.out.println("printmsg1 called with value " + num);
	}

	@GetMapping("/getall")
	public ResponseEntity<List<Student>> getStudents() {
		return ResponseEntity.ok(students);

	}

	@DeleteMapping("/delete/{name}")
	public ResponseEntity<String> delete(@PathVariable String name) {
		List<Student> temp = students;
		for (int i = 0; i < students.size(); i++) {
			if (students.get(i).getName().equals(name)) {
				temp.remove(i);
			}
		}
		students = temp;
		return ResponseEntity.status(HttpStatus.OK).body("Student deleted Successfully... ");
	}

	@PostMapping("/create")
	public ResponseEntity<String> createStudent(@RequestBody Student student) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Token", "some-value");

		students.add(student);
		return ResponseEntity.status(HttpStatus.ACCEPTED).headers(headers)
				.body("Student creation done Successfully... ");
	}

	@PutMapping("/createUpdate")
	public ResponseEntity<String> createOrUpdateStudent(@RequestBody Student student) {
		boolean exist = false;
		for (int i = 0; i < students.size(); i++) {
			if (students.get(i).getName().equals(student.getName())) {
				exist = true;
			}
		}
		// update
		if (exist) {
			List<Student> temp = students;
			for (int i = 0; i < students.size(); i++) {
				if (students.get(i).getName().equals(student.getName())) {
					temp.set(i, student);
				}
			}
			students = temp;
		} else {
			// insert
			students.add(student);
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Student creation done Successfully... ");
	}

	@PatchMapping("/partialUpdate")
	public ResponseEntity<String> partialUpdate(@RequestBody Student student) {
		for (int i = 0; i < students.size(); i++) {
			if (students.get(i).getName().equals(student.getName())) {
				students.get(i).setCollege(student.getCollege());
			}
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("partial update done Successfully... ");
	}
}

//@PathVariable
//http://localhost:8080/endpoint/10/student

//@RequestParam
//http://localhost:8080//endpoint/student?num=10