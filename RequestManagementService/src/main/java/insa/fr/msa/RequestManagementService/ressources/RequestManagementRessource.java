package insa.fr.msa.RequestManagementService.ressources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import insa.fr.msa.RequestManagementService.model.Request;
import insa.fr.msa.UserManagementService.ressources.UserManagementRessource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RequestManagementRessource {

	private static final Logger logger = LoggerFactory.getLogger(RequestManagementRessource.class);
	
	//Get database login information from the properties file		
	@Value("${db.uri}")
	private String dbUri;
	
	@Value("${db.login}")
	private String dbLogin;
	
	@Value("${db.pwd}")
	private String dbPwd;
	
	//Functions to connect and disconnect from database
	public Connection Connect() {
		Connection conn;
		try {
			conn = DriverManager.getConnection(dbUri, dbLogin, dbPwd);
            logger.info("Database connection opened successfully.");
			return conn;
		} catch (SQLException e) {
			throw new RuntimeException("Unable to connect to the database", e);
		}
	}
	
	public void closeConnection(Connection conn) {
	    if (conn != null) {
	        try {
	            conn.close();
	            logger.info("Database connection closed successfully.");
	        } catch (SQLException e) {
	            logger.error("Failed to close database connection: " + e.getMessage());
	        }
	    }
	}
	
	//Get all requests
	@GetMapping("/requests")
	public List<Request> GetAllRequests() {
		List<Request> requestList = new ArrayList<Request>();
		String query = "SELECT * FROM requete";
		Connection db;
		db = Connect();
		Statement stm = null;
		try {
			stm = db.createStatement();
			ResultSet rs = stm.executeQuery(query);
			while(rs.next()) {
				requestList.add(new Request(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4),rs.getString(5)));
			}
		} catch (SQLException e) {
			logger.error("Couldn't getAllRequests");
			e.printStackTrace();
		} finally {
	        if (stm != null) {
	            try {
	            	stm.close();
	                closeConnection(db);
	            } catch (SQLException ignored) {}
	        }
	    }
		return requestList;
	}
	
	//Get request, by the ID
	@GetMapping("/requests/{id}")
	public Request GetRequestInformationById(@PathVariable("id") int id) {
		String query = "SELECT * FROM requete WHERE id = " + id;
		Connection db = Connect();
		Statement stm = null;
		Request requete = null;
		
		try {
			stm = db.createStatement();
			ResultSet rs = stm.executeQuery(query);
			if(rs.next()) {
				requete = new Request(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4),rs.getString(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Couldn't getRequestInformationById");
		} finally {
	        if (stm != null) {
	            try {
	            	stm.close();
	                closeConnection(db);
	            } catch (SQLException ignored) {}
	        }
	    }	
		return requete;
	}
	
	//Change request of ID' status to status
	@PutMapping("/request/{id}/{status}")
	public Boolean changeRequestStatus(@PathVariable("id") int id , @PathVariable("status") String status) {
		String query = "UPDATE requete SET status = " + status + " WHERE id = " + id;
		Connection db = Connect();
		Statement stm = null;
		Boolean changed = false;
		try {
			stm = db.createStatement();
			stm.executeUpdate(query);
			changed = true;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Couldn't changeRequestStatus");
		} finally {
	        if (stm != null) {
	            try {
	            	stm.close();
	                closeConnection(db);
	            } catch (SQLException ignored) {}
	        }
	    }
		return changed;
	}
	
	//Accept request rqID of idHelper 
	@PutMapping("/acceptRequest/{idHelper}/{rqId}")
	public Boolean  acceptRequest(@PathVariable("idHelper") int idHelper , @PathVariable("rqId") int rqId) {
		Connection db = Connect();
		String query = "UPDATE requete SET volontaire = " + idHelper  + " WHERE id = " + rqId;
		Statement stm = null;
		Boolean accepted = false;
		try {
			stm = db.createStatement();
			stm.executeUpdate(query);
			accepted = true;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Couldn't acceptRequest");
		} finally {
	        if (stm != null) {
	            try {
	            	stm.close();
	                closeConnection(db);
	            } catch (SQLException ignored) {}
	        }
	    }
		return accepted;
	}
	
	//Add request
	@PostMapping("/addRequest")
	public Boolean addRequest(@RequestBody Request newRequest) {
		String query ="INSERT INTO requete(patient,status,description) VALUES (?,?,?)";
		Connection db = Connect();
		PreparedStatement pstm = null;
		Boolean added = false;
		try {
			pstm = db.prepareStatement(query);
			pstm.setInt(1, newRequest.getIdNeedy());
			pstm.setString(2, "WAITING FOR HELPER");
			pstm.setString(3, newRequest.getDescription());
			
			pstm.executeUpdate();
			added = true;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Couldn't addRequest");
		} finally {
	        if (pstm != null) {
	            try {
	            	pstm.close();
	                closeConnection(db);
	            } catch (SQLException ignored) {}
	        }
	    }
		return added;
	}
	
	//Delete request of ID
	@DeleteMapping("/delRequest/{id}")
	public Boolean delRequest(@PathVariable("id") int id) {
		String query = "DELETE FROM requete WHERE id = " + id;
		Connection db = Connect();
		Statement stm = null;
		Boolean deleted = false;
		try {
			stm = db.createStatement();
			stm.executeUpdate(query);
			deleted = true;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Couldn't delRequest");
		} finally {
	        if (stm != null) {
	            try {
	            	stm.close();
	                closeConnection(db);
	            } catch (SQLException ignored) {}
	        }
	    }
		return deleted;
	}
	
	//Get request of user of ID
	@GetMapping("/requests/user/{id}")
	public List<Request> getUserRequests(@PathVariable("id") int id){
		List<Request> listRequest = new ArrayList<Request>();
		String query = "SELECT * FROM requete WHERE volontaire = " + id + " OR patient = " + id;
		Connection db = Connect();
		Statement stm = null;
		try {
			stm = db.createStatement();
			ResultSet rs = stm.executeQuery(query);
			while(rs.next()) {
				listRequest.add(new Request(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4),rs.getString(5)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Couldn't getRequest");
		} finally {
	        if (stm != null) {
	            try {
	            	stm.close();
	                closeConnection(db);
	            } catch (SQLException ignored) {}
	        }
	    }
		return listRequest;
	}
	
	//Refuse request of ID
	@PutMapping("/refuseRequest/{idRq}")
	public Boolean refuseRequest(@PathVariable("idRq") int idRq, @RequestParam("commentaire") String commentaire) {
	    String query = "UPDATE requete SET description = ? WHERE id = ?";
		Connection db = Connect();
		PreparedStatement stmt = null;
		Boolean refused = false;
		try {
			stmt = db.prepareStatement(query);
			stmt.setString(1, "Refus : " + commentaire); 
	        stmt.setInt(2, idRq);
	        
	        int rowsUpdated = stmt.executeUpdate();
	        
	        if (rowsUpdated > 0) {
	        	refused = true;
	        }
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Couldn't refuseRequest");
		} finally {
	        if (stmt != null) {
	            try {
	            	stmt.close();
	                closeConnection(db);
	            } catch (SQLException ignored) {}
	        }
	    }
	    return refused;	    
	}

}
