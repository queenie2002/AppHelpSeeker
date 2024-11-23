package fr.insa.mas.StudentInfoService.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.insa.mas.StudentInfoService.model.StudentInfos;

@RestController
@RequestMapping("/student")
public class StudentInfoResource {
	    
	@GetMapping("/{idStudent}")
	public StudentInfos getInfoStudents(@PathVariable("idStudent") int id ) {
		List<StudentInfos> etudInfos=Arrays.asList(
				new StudentInfos(0,"Godart","Noemie", "12/12/1992"),
				new StudentInfos(1,"Perrin","Ania", "10/02/1993"),
				new StudentInfos(2,"Azi","Sana", "22/05/1992"),
				new StudentInfos(3,"Yala","Nelia", "12/06/1994")
				);
				
		return etudInfos.get(id);
	}
	
	/*
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
	*/
}
