package fr.insa.mas.StudentEvalService.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.insa.mas.StudentEvalService.model.Evaluation;

@RestController
@RequestMapping("/evaluation")
public class StudentEvalResource {
	    
	@GetMapping("/{idStudent}")
	public Evaluation getStudentEval(@PathVariable("idStudent") int id ) {
		List<Evaluation> evalList=Arrays.asList(
				new Evaluation(0,13F),
				new Evaluation(1,10.65F),
				new Evaluation(2,18F),
				new Evaluation(3,18F)
				);
				
		return evalList.get(id);
	}
}
