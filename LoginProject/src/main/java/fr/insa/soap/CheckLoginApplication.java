package fr.insa.soap;

import javax.xml.ws.Endpoint;
import java.net.MalformedURLException;

public class CheckLoginApplication {
    public static String host="localhost";
    public static short port=8089;

    public void demarrerService(){
        String url="http://"+host+":"+port+"/";
        Endpoint.publish(url,new CheckLoginWS());
    }

    public static void main(String[] args) throws MalformedURLException {
        new CheckLoginApplication().demarrerService();
        System.out.println("Service a démarré.");
    }

}