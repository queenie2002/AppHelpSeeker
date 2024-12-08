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
	
	/*DATABASE DIS/CONNECT-------------------------------------------------------------------------------------------------------------*/

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
	
	/*GET REQUESTS-------------------------------------------------------------------------------------------------------------*/

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
				requestList.add(new Request(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4),rs.getString(5), rs.getString(6)));
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
	
	//Get requests of user of ID
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
				listRequest.add(new Request(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4),rs.getString(5), rs.getString(6)));
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
				requete = new Request(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4),rs.getString(5), rs.getString(6));
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
	
	//Get all requests not verified yet
	@GetMapping("/unverifiedRequests/")
	public List<Request> unverifiedRequests() {
		List<Request> listRequest = new ArrayList<Request>();
		String query = "SELECT * FROM requete WHERE status ='ATTENTE DE VERIFICATION';";
		Connection db = Connect();
		Statement stm = null;
		try {
			stm = db.createStatement();
			ResultSet rs = stm.executeQuery(query);
			while(rs.next()) {
				listRequest.add(new Request(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4),rs.getString(5), rs.getString(6)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Couldn't unverifiedRequests");
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
	
	//Get all requests requested by people in need of help that have been verified by admin and not taken by volunteers yet
	@GetMapping("/acceptedRequestsNeedy/")
	public List<Request> acceptedRequestsNeedy() {
		List<Request> listRequest = new ArrayList<Request>();
		String query = "SELECT * FROM requete WHERE status ='VERIFIEE' AND volontaire IS NULL AND patient IS NOT NULL;";
		Connection db = Connect();
		Statement stm = null;
		try {
			stm = db.createStatement();
			ResultSet rs = stm.executeQuery(query);
			while(rs.next()) {
				listRequest.add(new Request(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4),rs.getString(5), rs.getString(6)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Couldn't acceptedRequestsNeedy");
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
	
	//Get all requests requested by volunteers that have been verified by admin and not taken by people in need of help yet
	@GetMapping("/acceptedRequestsVolunteer/")
	public List<Request> acceptedRequestsVolunteer() {
		List<Request> listRequest = new ArrayList<Request>();
		String query = "SELECT * FROM requete WHERE status ='VERIFIEE' AND volontaire IS NOT NULL AND patient IS NULL;";
		Connection db = Connect();
		Statement stm = null;
		try {
			stm = db.createStatement();
			ResultSet rs = stm.executeQuery(query);
			while(rs.next()) {
				listRequest.add(new Request(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4),rs.getString(5), rs.getString(6)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Couldn't acceptedRequestsVolunteer");
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
	
	/*CREATE REQUEST-------------------------------------------------------------------------------------------------------------*/

	//Add request for person in need of help
	@PostMapping("/addRequestNeedy")
	public Boolean addRequestNeedy(@RequestBody Request newRequest) {
		String query ="INSERT INTO requete(patient,status,description) VALUES (?,?,?)";
		Connection db = Connect();
		PreparedStatement pstm = null;
		Boolean added = false;
		try {
			pstm = db.prepareStatement(query);
			pstm.setInt(1, newRequest.getIdNeedy());
			pstm.setString(2, "ATTENTE DE VERIFICATION");
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
	
	//Add request for person offering help
	@PostMapping("/addRequestVolunteer")
	public Boolean addRequestVolunteer(@RequestBody Request newRequest) {
		String query ="INSERT INTO requete(volontaire,status,description) VALUES (?,?,?)";
		Connection db = Connect();
		PreparedStatement pstm = null;
		Boolean added = false;
		try {
			pstm = db.prepareStatement(query);
			pstm.setInt(1, newRequest.getIdHelper());
			pstm.setString(2, "ATTENTE DE VERIFICATION");
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
	
	/*VERIFY/REFUSE REQUEST-------------------------------------------------------------------------------------------------------------*/
	
	//Admin refuses request of ID and adds a commentary
	//Admin refuses request of ID
	@PutMapping("/adminRefuseRequest/{idRq}")
	public Boolean adminRefuseRequest(@PathVariable("idRq") int idRq, @RequestParam("commentaire") String commentaire) {
	    String query = "UPDATE requete SET commentaire = ?, status = ? WHERE id = ?";
		Connection db = Connect();
		PreparedStatement stmt = null;
		Boolean refused = false;
		try {
			stmt = db.prepareStatement(query);
			stmt.setString(1, "Refus : " + commentaire); 
			stmt.setString(2, "REFUSEE"); 
	        stmt.setInt(3, idRq);
	        
	        int rowsUpdated = stmt.executeUpdate();
	        
	        if (rowsUpdated > 0) {
	        	refused = true;
	        }
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Couldn't adminRefuseRequest");
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
	
	
	//Admin verifies request of ID
	@PutMapping("/adminVerifyRequest/{idRq}")
	public Boolean adminVerifyRequest(@PathVariable("idRq") int idRq) {
	    String query = "UPDATE requete SET status = ? WHERE id = ?";
		Connection db = Connect();
		PreparedStatement stmt = null;
		Boolean verified = false;
		try {
			stmt = db.prepareStatement(query);
			stmt.setString(1, "VERIFIEE"); 
	        stmt.setInt(2, idRq);
	        
	        int rowsUpdated = stmt.executeUpdate();
	        
	        if (rowsUpdated > 0) {
	        	verified = true;
	        }
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Couldn't adminVerifyRequest");
		} finally {
	        if (stmt != null) {
	            try {
	            	stmt.close();
	                closeConnection(db);
	            } catch (SQLException ignored) {}
	        }
	    }
	    return verified;	    
	}

	/*ACCEPT REQUEST-------------------------------------------------------------------------------------------------------------*/

	//Needy/Volunteer accepts a request rqID 
	@PutMapping("/acceptRequest/{idPerson}/{rqId}")
	public Boolean  acceptRequest(@PathVariable("idPerson") int idPerson , @PathVariable("rqId") int rqId) {
		Connection db = Connect();
		String query = "UPDATE requete\n"
	             + "SET volontaire = CASE\n"
	             + "                    WHEN volontaire IS NULL THEN " + idPerson + "\n"
	             + "                    ELSE volontaire\n"
	             + "                 END,\n"
	             + "    patient = CASE\n"
	             + "               WHEN volontaire IS NOT NULL AND patient IS NULL THEN " + idPerson + "\n"
	             + "               ELSE patient\n"
	             + "            END,\n"
	             + "    status = 'ACCEPTE'\n"
	             + "WHERE id = " + rqId + ";";

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
	
	/*ADD A COMMENTARY-------------------------------------------------------------------------------------------------------------*/
	
	//Add a commentary
	@PutMapping("/addCommentary/{idRq}")
	public Boolean addCommentary(@PathVariable("idRq") int idRq, @RequestParam("commentaire") String commentaire) {
	    String query = "UPDATE requete SET commentaire = ? WHERE id = ?";
		Connection db = Connect();
		PreparedStatement stmt = null;
		Boolean added = false;
		try {
			stmt = db.prepareStatement(query);
			stmt.setString(1, "Refus : " + commentaire); 
	        stmt.setInt(2, idRq);
	        
	        int rowsUpdated = stmt.executeUpdate();
	        
	        if (rowsUpdated > 0) {
	        	added = true;
	        }
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Couldn't addCommentary");
		} finally {
	        if (stmt != null) {
	            try {
	            	stmt.close();
	                closeConnection(db);
	            } catch (SQLException ignored) {}
	        }
	    }
	    return added;	    
	}
	
	/*DELETE REQUEST-----------------------------------------------------------------------------------------------------------*/
	
	//Delete a request of ID
	@DeleteMapping("/deleteRequest/{id}")
	public Boolean deleteRequest(@PathVariable("id") int id) {
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
	
	//Delete all requests
	@DeleteMapping("/deleteAllRequests")
	public Boolean deleteAllRequests() {
		String query = "DELETE FROM requete";
		Connection db = Connect();
		Statement stm = null;
		Boolean deleted = false;
		try {
			stm = db.createStatement();
			stm.executeUpdate(query);
			deleted = true;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Couldn't deleteAllRequests");
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

}
