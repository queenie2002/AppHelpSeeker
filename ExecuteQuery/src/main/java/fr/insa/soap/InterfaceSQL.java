package fr.insa.soap; 


import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.sql.*;

@WebService(serviceName="InterfaceSQL")
public class InterfaceSQL {
    public static Connection Connect() throws SQLException {
        // Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/projet_gei_060", "projet_gei_060", "Yoor8Xei");
        System.out.println("connection successful");
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("show tables;");
        while(rs.next()) {
            System.out.print(rs.getString(1));
            System.out.println();
        }
        return conn;   
    }
    @WebMethod(operationName="ExecuteQuery")
    public void ExecuteQuery(@WebParam(name="query") String query) throws SQLException{
    	Connection db = Connect();
    	Statement stm = db.createStatement();
    	ResultSet rs = stm.executeQuery(query);
    }
    
    @WebMethod(operationName="ExecuteUpdate")
    public void ExecuteUpdate(@WebParam(name="update") String update) throws SQLException{
    	Connection db = Connect();
    	Statement stm = db.createStatement();
    	stm.executeUpdate(update);
    }
   
}
