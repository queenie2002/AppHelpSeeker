package fr.insa.mas.studentManagementMS.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fr.insa.mas.studentManagementMS.model.Student;

@RestController
public class StudentResource {
	
    private Map<Integer, Student> studentList = new HashMap<>();
    
	@GetMapping("/students")
	public int studentNumber() {
		return 200;
	}
	
	@GetMapping("/students/{id}")
	public Student infosStudent(@PathVariable int id) {
		return studentList.get(id);
	}
	
	@GetMapping(value="/students/xml/{id}", produces=MediaType.APPLICATION_XML_VALUE)
	public Student xmlInfosStudent(@PathVariable int id) {
		return studentList.get(id);
	}
	
	@PostMapping(value="/students/insert/{id}/{firstName}/{lastName}")
	public int createStudent(@PathVariable int id, @PathVariable String firstName, @PathVariable String lastName) {
		studentList.put((Integer) id, new Student(id,firstName,lastName));
        studentList.values().forEach(student -> System.out.println(student.getId() + " " + student.getFirstName() + " " + student.getLastName()));
		return 200;
	}
	
	@PutMapping(value="/students/update/{id}/{firstName}/{lastName}")
    public int updateStudent(@PathVariable int id, @PathVariable String firstName, @PathVariable String lastName) {
        if (!studentList.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }
        studentList.put((Integer) id, new Student(id, firstName, lastName));
        studentList.values().forEach(student -> System.out.println(student.getId() + " " + student.getFirstName() + " " + student.getLastName()));
        return 200;
    }
	
	@DeleteMapping(value="/students/delete/{id}")
    public int deleteStudent(@PathVariable int id, @PathVariable String firstName, @PathVariable String lastName) {
        if (!studentList.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }
        studentList.remove(id);
        studentList.values().forEach(student -> System.out.println(student.getId() + " " + student.getFirstName() + " " + student.getLastName()));
        return 200;
    }
	
}
