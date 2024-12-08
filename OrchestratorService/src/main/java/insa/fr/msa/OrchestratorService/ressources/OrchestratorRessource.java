package insa.fr.msa.OrchestratorService.ressources;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import insa.fr.msa.OrchestratorService.model.Request;
import insa.fr.msa.OrchestratorService.model.User;

@RestController
public class OrchestratorRessource {

	private static final Logger logger = LoggerFactory.getLogger(OrchestratorRessource.class);

	@Autowired
	private RestTemplate restTemplate;
	
	/* SIGN UP and LOGIN -------------------------------------------------------------------------------------------------------*/
	//Signs up a user and returns his ID that will be needed to login again
	@PostMapping("/signUpUser")
	public Integer signUpUser(@RequestBody User newUser) {
	    String url = "http://SignUpService/signUpUser";

	    try {
	        ResponseEntity<Integer> response = restTemplate.exchange(
	            url,
	            HttpMethod.POST,  
	            new HttpEntity<>(newUser),  
	            Integer.class  
	        );
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
	        ResponseEntity<Boolean> response = restTemplate.exchange(
	            url,
	            HttpMethod.GET,  
	            null,  
	            Boolean.class 
	        );
	        return response.getBody();
	    } catch (RestClientException e) {
	        throw new RuntimeException("Failed to call LogIn API", e);
	    }
	}
	
	/* USER MANAGEMENT -------------------------------------------------------------------------------------------------------*/

	//Gets all users
	@GetMapping("/users")
	public List<User> getAllUsers() {
	    String url = "http://UserManagement-Service/users";

	    try {
	        List<User> listUsers = restTemplate.exchange(
	                url,
	                HttpMethod.GET,
	                null,
	                new ParameterizedTypeReference<List<User>>() {}
	        ).getBody();
	        
	        return listUsers;
	    } catch (RestClientException e) {
	        throw new RuntimeException("Failed to call SignUp API", e);
	    }    
	}
	
	//Gets a user from ID
	@GetMapping("/users/{id}")
	public User getUser(@PathVariable("id") int id) {
	    String url = "http://UserManagement-Service/users/" + id;

	    try {
	    	User user = restTemplate.exchange(
	                url,
	                HttpMethod.GET,
	                null,
	                new ParameterizedTypeReference<User>() {}
	        ).getBody();
	        
	        return user;
	    } catch (RestClientException e) {
	        throw new RuntimeException("Failed to call UserManagement API", e);
	    }    
	}
	
	//Deletes the user of ID
	@DeleteMapping("/deleteUser/{id}")
	public Boolean delUser(@PathVariable("id") int id) {
		String url = "http://UserManagement-Service/deleteUser/" + id;

		try {
	        ResponseEntity<Boolean> response = restTemplate.exchange(
	            url,
	            HttpMethod.DELETE,  
	            null,  
	            Boolean.class 
	        );
	        return response.getBody();
	    } catch (RestClientException e) {
	        throw new RuntimeException("Failed to call UserManagement API", e);
	    }   
	}

	/* REQUEST MANAGEMENT -------------------------------------------------------------------------------------------------------*/

	//Get all requests
	@GetMapping("/requests")
	public List<Request> getAllRequests() {
		 String url = "http://RequestManagement-Service/requests";

		    try {
		        List<Request> listRequests = restTemplate.exchange(
		                url,
		                HttpMethod.GET,
		                null,
		                new ParameterizedTypeReference<List<Request>>() {}
		        ).getBody();
		        
		        return listRequests;
		    } catch (RestClientException e) {
		        throw new RuntimeException("Failed to call RequestManagement API", e);
		    }  
	}

	//Get requests of user of ID
	@GetMapping("/requests/user/{id}")
	public List<Request> getUserRequests(@PathVariable("id") int id){
		String url = "http://RequestManagement-Service/requests/user/" + id;

	    try {
	        List<Request> listRequests = restTemplate.exchange(
	                url,
	                HttpMethod.GET,
	                null,
	                new ParameterizedTypeReference<List<Request>>() {}
	        ).getBody();
	        
	        return listRequests;
	    } catch (RestClientException e) {
	        throw new RuntimeException("Failed to call RequestManagement API", e);
	    }  
	}
	
	//Get request, by the ID
	@GetMapping("/requests/{id}")
	public Request getRequest(@PathVariable("id") int id) {
		String url = "http://RequestManagement-Service/requests/" + id;

	    try {
	    	Request requete = restTemplate.exchange(
	                url,
	                HttpMethod.GET,
	                null,
	                new ParameterizedTypeReference<Request>() {}
	        ).getBody();
	        
	        return requete;
	    } catch (RestClientException e) {
	        throw new RuntimeException("Failed to call RequestManagement API", e);
	    }    
	}
	
	//Get all requests not verified yet
	@GetMapping("/requests/unverified")
	public List<Request> unverifiedRequests() {
		String url = "http://RequestManagement-Service/requests/unverified";

	    try {
	        List<Request> listRequests = restTemplate.exchange(
	                url,
	                HttpMethod.GET,
	                null,
	                new ParameterizedTypeReference<List<Request>>() {}
	        ).getBody();
	        
	        return listRequests;
	    } catch (RestClientException e) {
	        throw new RuntimeException("Failed to call RequestManagement API", e);
	    }  
	}
	
	//Get all requests requested by people in need of help that have been verified by admin and not taken by volunteers yet
	@GetMapping("/requests/verified/needy")
	public List<Request> verifiedRequestsNeedy() {
		String url = "http://RequestManagement-Service/requests/verified/needy";

	    try {
	        List<Request> listRequests = restTemplate.exchange(
	                url,
	                HttpMethod.GET,
	                null,
	                new ParameterizedTypeReference<List<Request>>() {}
	        ).getBody();
	        
	        return listRequests;
	    } catch (RestClientException e) {
	        throw new RuntimeException("Failed to call RequestManagement API", e);
	    } 
	}
	
	//Get all requests requested by volunteers that have been verified by admin and not taken by people in need of help yet
	@GetMapping("/requests/verified/volunteer")
	public List<Request> verifiedRequestsVolunteer() {
		String url = "http://RequestManagement-Service/requests/verified/volunteer";

	    try {
	        List<Request> listRequests = restTemplate.exchange(
	                url,
	                HttpMethod.GET,
	                null,
	                new ParameterizedTypeReference<List<Request>>() {}
	        ).getBody();
	        
	        return listRequests;
	    } catch (RestClientException e) {
	        throw new RuntimeException("Failed to call RequestManagement API", e);
	    } 
	}
	
	//Add request for person in need of help
	@PostMapping("/addRequest/needy")
	public Boolean addRequestNeedy(@RequestBody Request newRequest) {
		String url = "http://RequestManagement-Service/addRequest/needy";

		try {
	        ResponseEntity<Boolean> response = restTemplate.exchange(
	            url,
	            HttpMethod.POST,  
	            new HttpEntity<>(newRequest),  
	            Boolean.class 
	        );
	        return response.getBody();
	    } catch (RestClientException e) {
	        throw new RuntimeException("Failed to call RequestManagement API", e);
	    }   
	}
	
	//Add request for person offering help
	@PostMapping("/addRequest/volunteer")
	public Boolean addRequestVolunteer(@RequestBody Request newRequest) {
		String url = "http://RequestManagement-Service/addRequest/volunteer";

		try {
	        ResponseEntity<Boolean> response = restTemplate.exchange(
	            url,
	            HttpMethod.POST,  
	            new HttpEntity<>(newRequest),  
	            Boolean.class 
	        );
	        return response.getBody();
	    } catch (RestClientException e) {
	        throw new RuntimeException("Failed to call RequestManagement API", e);
	    }  
	}
	
	//Admin refuses request of ID and adds a commentary
	@PutMapping("/adminRefuseRequest/{idRq}")
	public Boolean adminRefuseRequest(@PathVariable("idRq") int idRq, @RequestParam("commentaire") String commentaire) {
		String url = "http://RequestManagement-Service/adminRefuseRequest/" + idRq + "?commentaire="+commentaire;

		try {
	        ResponseEntity<Boolean> response = restTemplate.exchange(
	            url,
	            HttpMethod.PUT,  
	            null,  
	            Boolean.class 
	        );
	        return response.getBody();
	    } catch (RestClientException e) {
	        throw new RuntimeException("Failed to call RequestManagement API", e);
	    }  
	}
	
	//Admin verifies request of ID
	@PutMapping("/adminVerifyRequest/{idRq}")
	public Boolean adminVerifyRequest(@PathVariable("idRq") int idRq) {
		String url = "http://RequestManagement-Service/adminVerifyRequest/" + idRq;

		try {
	        ResponseEntity<Boolean> response = restTemplate.exchange(
	            url,
	            HttpMethod.PUT,  
	            null,  
	            Boolean.class 
	        );
	        return response.getBody();
	    } catch (RestClientException e) {
	        throw new RuntimeException("Failed to call RequestManagement API", e);
	    }      
	}
	
	//Needy/Volunteer accepts a request rqID 
	@PutMapping("/acceptRequest/{idPerson}/{rqId}")
	public Boolean  acceptRequest(@PathVariable("idPerson") int idPerson , @PathVariable("rqId") int rqId) {
		String url = "http://RequestManagement-Service/acceptRequest/" + idPerson + "/" + rqId;

		try {
	        ResponseEntity<Boolean> response = restTemplate.exchange(
	            url,
	            HttpMethod.PUT,  
	            null,  
	            Boolean.class 
	        );
	        return response.getBody();
	    } catch (RestClientException e) {
	        throw new RuntimeException("Failed to call RequestManagement API", e);
	    }  
	}
	
	//Add a commentary
	@PutMapping("/addCommentary/{idRq}")
	public Boolean addCommentary(@PathVariable("idRq") int idRq, @RequestParam("commentaire") String commentaire) {
		String url = "http://RequestManagement-Service/addCommentary/" + idRq + "?commentaire=" + commentaire;

		try {
	        ResponseEntity<Boolean> response = restTemplate.exchange(
	            url,
	            HttpMethod.PUT,  
	            null,  
	            Boolean.class 
	        );
	        return response.getBody();
	    } catch (RestClientException e) {
	        throw new RuntimeException("Failed to call RequestManagement API", e);
	    }      
	}

	//Delete a request of ID
	@DeleteMapping("/deleteRequest/{id}")
	public Boolean deleteRequest(@PathVariable("id") int id) {
		String url = "http://RequestManagement-Service/deleteRequest/" + id;

		try {
	        ResponseEntity<Boolean> response = restTemplate.exchange(
	            url,
	            HttpMethod.DELETE,  
	            null,  
	            Boolean.class 
	        );
	        return response.getBody();
	    } catch (RestClientException e) {
	        throw new RuntimeException("Failed to call RequestManagement API", e);
	    }   
	}

	//Delete all requests
	@DeleteMapping("/deleteAllRequests")
	public Boolean deleteAllRequests() {
		String url = "http://RequestManagement-Service/deleteAllRequests";

		try {
	        ResponseEntity<Boolean> response = restTemplate.exchange(
	            url,
	            HttpMethod.DELETE,  
	            null,  
	            Boolean.class 
	        );
	        return response.getBody();
	    } catch (RestClientException e) {
	        throw new RuntimeException("Failed to call RequestManagement API", e);
	    }   
	}




}
