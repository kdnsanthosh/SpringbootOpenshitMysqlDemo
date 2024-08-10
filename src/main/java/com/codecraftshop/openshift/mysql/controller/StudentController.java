package com.codecraftshop.openshift.mysql.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.codecraftshop.openshift.mysql.model.Student;
import com.codecraftshop.openshift.mysql.repository.StudentRepository;

@RestController //controller
@RequestMapping("/students")
public class StudentController {

	@Autowired
	private StudentRepository studentRepository;

	// Create a new student
	@PostMapping
	public Student createStudent(@RequestBody Student student) {
		return studentRepository.save(student);
	}

	// Get all students
	@GetMapping
	public List<Student> getAllStudents() {
		return (List<Student>) studentRepository.findAll();
	}

	// Get a single student by ID
	@GetMapping("/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable(value = "id") Long studentId) {
		Optional<Student> student = studentRepository.findById(studentId);
		if(student.isPresent()) {
			return ResponseEntity.ok().body(student.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Update a student by ID
	@PutMapping("/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable(value = "id") Long studentId, @RequestBody Student studentDetails) {
		Optional<Student> student = studentRepository.findById(studentId);
		if(student.isPresent()) {
			Student updatedStudent = student.get();
			updatedStudent.setName(studentDetails.getName());
			// Update other fields as necessary
			final Student savedStudent = studentRepository.save(updatedStudent);
			return ResponseEntity.ok(savedStudent);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Delete a student by ID
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteStudent(@PathVariable(value = "id") Long studentId) {
		Optional<Student> student = studentRepository.findById(studentId);
		if(student.isPresent()) {
			studentRepository.delete(student.get());
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
