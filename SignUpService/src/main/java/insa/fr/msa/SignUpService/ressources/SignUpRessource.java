package insa.fr.msa.SignUpService.ressources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import insa.fr.msa.SignUpService.model.User;

@RestController
public class SignUpRessource {

	private static final Logger logger = LoggerFactory.getLogger(SignUpRessource.class);

	@Autowired
	private RestTemplate restTemplate;
	
	@PostMapping("/signUpUser")
	public String signUpUser(@RequestBody User newUser) {
		
		String url = "http://UserManagement-Service/addUser";

	    try {
	        ResponseEntity<String> response = restTemplate.postForEntity(url, newUser, String.class);
	        return response.getBody();
	    } catch (RestClientException e) {
	        throw new RuntimeException("Failed to call UserManagement API", e);
	    }
	    		
	}

}
