package fr.insa.soap.SignUpProject;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("comparator")
public class Comparator {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello() {
        return "Bonjour!";
    }
    
    @GET
    @Path("longueur/{chaine}")
    @Produces(MediaType.TEXT_PLAIN)
    public int getLongueur(@PathParam("chaine") String chaine) {
        return chaine.length();
    }
    
    @GET
    @Path("longueurDouble")
    @Produces(MediaType.TEXT_PLAIN)
    public int getLongueurDouble(@QueryParam("chaine") String chaine) {
        return chaine.length()*2;
    }
    
    @PUT
    @Path("/{idEtudiant}")
    @Produces(MediaType.TEXT_PLAIN)
    public int updateEtudiant(@PathParam("idEtudiant") int id) {
        System.out.println("mise à jour réussie!!!" + id);
        return id;
    }
}
