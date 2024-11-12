package fr.insa.soap.SignUpProject;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;

public class RestClient {
	public static void main(String[] args) {
		Client client=ClientBuilder.newClient();
		Response response=client.target("http://localhost:8080/SignUpProject/webapi/comparator/longueurDouble?chaine=aeg").request().get();
		System.out.println(response.readEntity(String.class));
	}
}