package insa.fr.msa.UserManagementService.ressources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import insa.fr.msa.UserManagementService.model.User;

@RestController
public class UserManagementRessource {

	private static final Logger logger = LoggerFactory.getLogger(UserManagementRessource.class);
	
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
			return conn;
		} catch (SQLException e) {
			throw new RuntimeException("Unable to connect to the database", e);
		}
	}

	public void closeConnection(Connection conn) {
	    if (conn != null) {
	        try {
	            conn.close();
	            System.out.println("Database connection closed successfully.");
	        } catch (SQLException e) {
	            System.err.println("Failed to close database connection: " + e.getMessage());
	        }
	    }
	}
	
	//Add user, returns the user's ID
	@PostMapping("/addUser")
	public int addRequest(@RequestBody User newUser) {
		String query = "INSERT INTO users(nom,mdp,status,mail) VALUES (?,?,?,?)";
		Connection db = Connect();
		PreparedStatement pstm = null;
		try {
			pstm = db.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			pstm.setString(1, newUser.getNom());
			pstm.setString(2, newUser.getMdp());
			pstm.setString(3, newUser.getStatus());
			pstm.setString(4, newUser.getMail());

			pstm.executeUpdate();

			ResultSet generatedKeys = pstm.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            return generatedKeys.getInt(1);
	        } else {
	            throw new SQLException("Inserting user failed, no ID obtained.");
	        }
		} catch (SQLException e) {
			throw new RuntimeException("Unable to add user to database with name : " + newUser.getNom(), e);
		} finally {
	        // Close resources
	        if (pstm != null) {
	            try {
	                pstm.close();
	            } catch (SQLException ignored) {}
	        }
	    }
	}

	//Get a list of all users' information
	@GetMapping("/users")
	public List<User> getAllUsers() {
		List<User> userList = new ArrayList<User>();
		String query = "SELECT * FROM users";
		Connection db = Connect();
		Statement stm = null;
		try {
			stm = db.createStatement();
			ResultSet rs = stm.executeQuery(query);
			while (rs.next()) {
				userList.add(
						new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
			}
			return userList;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Couldn't getAllUsers");
			return null;
		} finally {
	        // Close resources
	        if (stm != null) {
	            try {
	            	stm.close();
	            } catch (SQLException ignored) {}
	        }
	    }
	}

	//Given an ID, get the user's information
	@GetMapping("/users/{id}")
	public User getUserInformationById(@PathVariable("id") int id) {
		String query = "SELECT * FROM users WHERE id = " + id;
		Connection db = Connect();
		Statement stm = null;
		try {
			stm = db.createStatement();
			ResultSet rs = stm.executeQuery(query);
			if (rs.next()) {
				User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
				return user;
			}
			logger.info("Couldn't find a user with id : " + id);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Couldn't getUser with id : " + id);
		} finally {
	        // Close resources
	        if (stm != null) {
	            try {
	            	stm.close();
	            } catch (SQLException ignored) {}
	        }
	    }
		return null;
	}

	//Given an ID, deletes the user
	@DeleteMapping("/delUser/{id}")
	public String delUser(@PathVariable("id") int id) {
		String query = "DELETE FROM users WHERE id = " + id;
		Connection db = Connect();
		Statement stm = null;
		try {
			stm = db.createStatement();
			stm.executeUpdate(query);
			return "User Deleted";
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Couldn't deleteUser with id : " + id);
			return "User NOT Deleted";
		} finally {
	        // Close resources
	        if (stm != null) {
	            try {
	            	stm.close();
	            } catch (SQLException ignored) {}
	        }
	    }
	}

}
