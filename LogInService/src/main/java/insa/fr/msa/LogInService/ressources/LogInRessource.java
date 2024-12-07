package insa.fr.msa.LogInService.ressources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import insa.fr.msa.LogInService.model.User;

@RestController
public class LogInRessource {

	private static final Logger logger = LoggerFactory.getLogger(LogInRessource.class);

	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/logInUser/{id}/{pwd}")
	public Boolean logInUser(@PathVariable("id") int id, @PathVariable("pwd") String pwd) {
		
		String url = "http://UserManagement-Service/users/" + id;

	    try {
	        User user = restTemplate.getForObject(url, User.class);
	        
	        if (pwd.equals(user.getMdp())) {
	        	return true;
	        } else {
	        	return false;
	        }
	        
	    } catch (RestClientException e) {
	        throw new RuntimeException("Failed to call UserManagement API", e);
	    }
	    		
	}

}
