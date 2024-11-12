package fr.insa.soap.wsdltojava;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.4.2
 * 2024-11-06T09:46:39.684+01:00
 * Generated source version: 3.4.2
 *
 */
@WebService(targetNamespace = "http://soap.insa.fr/", name = "CheckLoginWS")
@XmlSeeAlso({ObjectFactory.class})
public interface CheckLoginWS {

    @WebMethod
    @Action(input = "http://soap.insa.fr/CheckLoginWS/loginRequest", output = "http://soap.insa.fr/CheckLoginWS/loginResponse")
    @RequestWrapper(localName = "login", targetNamespace = "http://soap.insa.fr/", className = "fr.insa.soap.wsdltojava.Login")
    @ResponseWrapper(localName = "loginResponse", targetNamespace = "http://soap.insa.fr/", className = "fr.insa.soap.wsdltojava.LoginResponse")
    @WebResult(name = "return", targetNamespace = "")
    public java.lang.Boolean login(

        @WebParam(name = "username", targetNamespace = "")
        java.lang.String username,
        @WebParam(name = "password", targetNamespace = "")
        java.lang.String password
    );
}