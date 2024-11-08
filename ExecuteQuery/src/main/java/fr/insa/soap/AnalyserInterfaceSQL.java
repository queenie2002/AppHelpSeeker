package fr.insa.soap;

import java.net.MalformedURLException;
import javax.xml.ws.Endpoint;

public class AnalyserInterfaceSQL {
	public static String  host= "localhost";
	public static short port = 8809 ;
	
	public void demarrerService() {
		String url = "http://"+host+":"+port+"/";
		System.out.println(url);
		Endpoint.publish(url, new InterfaceSQL());
	}
	
	public static void main(String [] args) throws MalformedURLException {
		new AnalyserInterfaceSQL().demarrerService();
		System.out.println("Service a Demarrer");
	}
}
