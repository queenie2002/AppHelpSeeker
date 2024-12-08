package insa.fr.msa.OrchestratorService.ressources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import insa.fr.msa.OrchestratorService.model.User;

@RestController
public class OrchestratorRessource {

	private static final Logger logger = LoggerFactory.getLogger(OrchestratorRessource.class);

	@Autowired
	private RestTemplate restTemplate;
	
	//Signs up a user and returns his ID that will be needed to login again
	@PostMapping("/signUpUser")
	public Integer signUpUser(@RequestBody User newUser) {
		
		String url = "http://SignUpService/signUpUser";

	    try {
	        ResponseEntity<Integer> response = restTemplate.postForEntity(url, newUser, Integer.class);
	        return response.getBody();
	    } catch (RestClientException e) {
	        throw new RuntimeException("Failed to call SignUp API", e);
	    }
	    		
	}
	
	//Logs in a user and returns whether he is authorized to
	@GetMapping("/logInUser/{id}/{pwd}")
	public Boolean logInUser(@PathVariable("id") int id, @PathVariable("pwd") String pwd) {
		
		String url = "http://LogInService/logInUser/" + id + "/" + pwd;

	    try {
	        return restTemplate.getForObject(url, Boolean.class);
	        
	    } catch (RestClientException e) {
	        throw new RuntimeException("Failed to call LogIn API", e);
	    }
	    		
	}
	
	

	
	
}
