package fr.insa.mas.StudentListService.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import fr.insa.mas.StudentListService.model.Evaluation;
import fr.insa.mas.StudentListService.model.Student;
import fr.insa.mas.StudentListService.model.StudentIDList;
import fr.insa.mas.StudentListService.model.StudentInfos;

@RestController
@RequestMapping("/students")
public class StudentListResource {
	
	@GetMapping("/ids/{idSpeciality}")
	public StudentIDList getIDStudents(@PathVariable("idSpeciality") String speciality ) {
		StudentIDList studentsId = new StudentIDList();
		studentsId.saddStudentToList(0);
		studentsId.saddStudentToList(1);
		studentsId.saddStudentToList(2);
		studentsId.saddStudentToList(3);
		return studentsId;
	}
	
	@GetMapping("/all/{idSpeciality}")
	public List<Student> getStudents(@PathVariable("idSpeciality") String speciality ) {
		StudentIDList students = new StudentIDList();
		students.saddStudentToList(0);
		students.saddStudentToList(1);
		students.saddStudentToList(2);
		
		RestTemplate restTemplate = new RestTemplate();
		int i=0;
		List<Student> listStudents = new ArrayList<Student>();
		
		while (i<students.getStudentList().size()) {
			StudentInfos etudInfos=restTemplate.getForObject("http://localhost:8081/student/"+i, StudentInfos.class);
			Evaluation eval=restTemplate.getForObject("http://localhost:8082/evaluation/"+i, Evaluation.class);
			listStudents.add(new Student(i,etudInfos.getFirstName(),etudInfos.getLastName(),eval.getAverage()));
			i++;
		}
		return listStudents;
	}
	
}
