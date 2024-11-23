package fr.insa.mas.StudentListService.model;

import java.util.ArrayList;
import java.util.List;

public class StudentIDList {

	List <Integer> studentList;

	public StudentIDList(List<Integer> studentList) {
		super();
		this.studentList = new ArrayList<Integer>();
	}
	
	public StudentIDList() {
		this.studentList = new ArrayList<Integer>();
	}

	public List<Integer> getStudentList() {
		return studentList;
	}

	public void saddStudentToList(Integer studentId) {
		this.studentList.add(studentId);
	}
	
	
}
