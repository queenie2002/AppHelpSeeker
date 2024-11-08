package fr.insa.soap.SignUpProject;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("signUp")
public class SignUp {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String saySigningUp() {
        return "signing up";
    }
    
    @POST
    @Path("user")
    @Produces(MediaType.TEXT_PLAIN)
    public String addUser(@QueryParam("name") String name, @QueryParam("pwd") String pwd, @QueryParam("role") String role) throws Exception {
    	switch(role.toLowerCase()) {
    	  case "bénévole":
    	    // code block
    		  
    		  
    	    break;
    	  case "helpSeeker":
    	    // code block
    	    break;
    	  case "valideur":
    		//code block
    		break;
    	  default:
    		  throw new Exception("rôle invalide de l'utilisateur : " + name);
    	}

    	
    	
        return "signed up: " + name + " with pwd: " + pwd + " as : " + role;
    }
    
    @PUT
    @Path("/{idEtudiant}")
    @Produces(MediaType.TEXT_PLAIN)
    public int updateEtudiant(@PathParam("idEtudiant") int id) {
        System.out.println("mise à jour réussie!!!" + id);
        return id;
    }
}
