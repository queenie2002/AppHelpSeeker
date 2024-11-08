package fr.insa.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName="logger")
public class CheckLoginWS {
	
	private boolean validateCredentials(String username, String password) {
        return "user".equals(username) && "pwd".equals(password);
    }
	
	
    @WebMethod(operationName="login")
    public Boolean checkLogin(
            @WebParam(name="username") String username,
            @WebParam(name="password") String password) {

        if (validateCredentials(username, password)) {
            return true;
        } else {
            return false;
        }
    }
}