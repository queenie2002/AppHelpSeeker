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

	@Value("${db.uri}")
	private String dbUri;

	@Value("${db.name}")
	private String dbName;

	@Value("${db.login}")
	private String dbLogin;

	@Value("${db.pwd}")
	private String dbPwd;

	public Connection Connect() {
		Connection conn;
		try {
			conn = DriverManager.getConnection(dbUri, dbLogin, dbPwd);
			return conn;
		} catch (SQLException e) {
			throw new RuntimeException("Unable to connect to the database", e);
		}
	}

	@PostMapping("/addUser")
	public String addRequest(@RequestBody User newUser) {
		String query = "INSERT INTO users(nom,mdp,status,mail) VALUES (?,?,?,?)";
		Connection db = Connect();
		PreparedStatement pstm;
		try {
			pstm = db.prepareStatement(query);
			pstm.setString(1, newUser.getNom());
			pstm.setString(2, newUser.getMdp());
			pstm.setString(3, newUser.getStatus());
			pstm.setString(4, newUser.getMail());

			pstm.executeUpdate();

			return "Added" + newUser.toString();
		} catch (SQLException e) {
			throw new RuntimeException("Unable to add user to database with name : " + newUser.getNom(), e);
		}
	}

	@GetMapping("/users")
	public List<User> getAllUsers() {
		List<User> userList = new ArrayList<User>();
		String query = "SELECT * FROM users";
		Connection db = Connect();
		Statement stm;
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
		}
	}

	@GetMapping("/users/{id}")
	public User getUserInformationById(@PathVariable("id") int id) {
		String query = "SELECT * FROM users WHERE id = " + id;
		Connection db = Connect();
		Statement stm;
		try {
			stm = db.createStatement();
			ResultSet rs = stm.executeQuery(query);
			if (rs.next()) {
				User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
				return user;
			}
			logger.info("Couldn't find a user with id : " + id);
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Couldn't getUser with id : " + id);
		}
		return null;
	}

	@DeleteMapping("/delUser/{id}")
	public String delUser(@PathVariable("id") int id) {
		String query = "DELETE FROM users WHERE id = " + id;
		Connection db = Connect();
		Statement stm;
		try {
			stm = db.createStatement();
			stm.executeUpdate(query);
			return "User Deleted";
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Couldn't deleteUser with id : " + id);
			return "User NOT Deleted";
		}
	}

}
