package asset.tracker.manager;

import java.io.IOException;
import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.naming.NamingException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import asset.tracker.database.MySQLDriver;
import asset.tracker.problem.domain.Asset;
import asset.tracker.problem.domain.User;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * A class that contains the logic and functionality of the system.
 * 
 * @author Quack
 *
 */
public final class AssetTrackingSystem {

	private static MySQLDriver db;

	/**
	 * A no argument constructor.
	 * 
	 * @throws Exception thrown generally for any exception.
	 */
	public AssetTrackingSystem() throws Exception {

	}

	/**
	 * Instantiates the MySQLDriver object to connect to the database.
	 * 
	 * @throws SQLException thrown if a connection cannot be made.
	 */
	public void connect() throws SQLException {

		db = new MySQLDriver();
	}

	/**
	 * For logging into a user's account.
	 * 
	 * @param email    a user's email.
	 * @param password the user's password.
	 * @return true or false whether or not logging in was successful.
	 * @throws SQLException thrown if the query caused an error.
	 */
	public boolean login(String email, char[] password) throws SQLException {

		PreparedStatement ps = getDB().getConnection()
				.prepareStatement("SELECT * FROM asset_tracker_db.users WHERE email LIKE ?;");
		ps.setString(1, email);
		ResultSet rs = ps.executeQuery();

		User foundUser = null;

		while (rs.next()) {
			int rsId = rs.getInt("id");
			String rsEmail = rs.getString("email");
			String rsPassword = rs.getString("password");
			String rsFirstName = rs.getString("first_name");
			String rsLastName = rs.getString("last_name");

			foundUser = new User(rsId, rsEmail, rsPassword, rsFirstName, rsLastName);
		}

		if (foundUser.getPassword() != null) {
			BCrypt.Result result = BCrypt.verifyer().verify(password, foundUser.getPassword());

			if (result.verified) {
				return true;
			}

		}

		return false;
	}

	/**
	 * For querying a user's ID using their email.
	 * 
	 * @param email a specific user's email.
	 * @return the user's ID.
	 * @throws SQLException thrown if the query caused an error.
	 */
	public int getUserIdByEmail(String email) throws SQLException {

		PreparedStatement ps = getDB().getConnection()
				.prepareStatement("SELECT id FROM asset_tracker_db.users WHERE email LIKE ?");
		ps.setString(1, email);
		ResultSet rs = ps.executeQuery();

		int foundID = -1;

		while (rs.next()) {
			foundID = rs.getInt("id");
		}

		return foundID;
	}

	/**
	 * Retrieves the last asset ID in a table.
	 * 
	 * @return the last asset ID.
	 * @throws SQLException thrown if the query caused an error.
	 */
	public int getLastAssetID() throws SQLException {

		PreparedStatement ps = getDB().getConnection()
				.prepareStatement("SELECT id FROM asset_tracker_db.assets ORDER BY id DESC LIMIT 1");
		ResultSet rs = ps.executeQuery();

		int foundID = -1;

		while (rs.next()) {
			foundID = rs.getInt("id");
		}

		return foundID;
	}

	/**
	 * For creating a new user account.
	 * 
	 * @param email     the user's email.
	 * @param password  the user's password.
	 * @param firstName the user's first name.
	 * @param lastName  the user's last name.
	 * @return true if successful and false otherwise.
	 * @throws SQLException thrown if the query caused an error.
	 */
	public boolean createUser(String email, char[] password, String firstName, String lastName) throws SQLException {

		String hashed = BCrypt.withDefaults().hashToString(12, password);

		PreparedStatement ps = getDB().getConnection().prepareStatement(
				"INSERT INTO asset_tracker_db.users (email, password, first_name, last_name) VALUES (?, ?, ?, ?);");
		ps.setString(1, email);
		ps.setString(2, hashed);
		ps.setString(3, firstName);
		ps.setString(4, lastName);
		int result = ps.executeUpdate();

		return result > 0;
	}

	/**
	 * Inserts an asset into a table.
	 * 
	 * @param userID       the ID of the asset's owner.
	 * @param itemName     the name of the asset.
	 * @param description  the description of the asset.
	 * @param price        the asset's price.
	 * @param dateObtained the date the asset was obtained.
	 * @return true if successful and false otherwise.
	 * @throws SQLException thrown if the query caused an error.
	 */
	public boolean addAsset(int userID, String itemName, String description, Double price, LocalDate dateObtained)
			throws SQLException {

		PreparedStatement ps = getDB().getConnection().prepareStatement(
				"INSERT INTO asset_tracker_db.assets (user_id, item_name, description, price, date_obtained) VALUES (?, ?, ?, ?, ?);");
		ps.setInt(1, userID);
		ps.setString(2, itemName);
		ps.setString(3, description);
		ps.setDouble(4, price);
		ps.setObject(5, dateObtained);
		int result = ps.executeUpdate();

		return result > 0;
	}

	/**
	 * Retrieve the price of an asset using the item and user ID's.
	 * 
	 * @param userID a given user ID.
	 * @param itemID a given item ID.
	 * @return the price of an asset as a double.
	 * @throws SQLException thrown if the query caused an error.
	 */
	public double getPrice(int userID, int itemID) throws SQLException {

		double price = 0;
		PreparedStatement ps = getDB().getConnection()
				.prepareStatement("SELECT price FROM asset_tracker_db.assets WHERE user_id = ? AND item_id = ?;");
		ps.setInt(1, userID);
		ps.setInt(2, itemID);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			price = rs.getInt("price");
		}

		return price;
	}

	/**
	 * Remove an asset from the table using it's ID.
	 * 
	 * @param itemID the ID of a given item.
	 * @throws SQLException thrown if the query caused an error.
	 */
	public void removeAsset(int itemID) throws SQLException {

		PreparedStatement ps = getDB().getConnection()
				.prepareStatement("DELETE FROM asset_tracker_db.assets WHERE id = ?");
		ps.setInt(1, itemID);
		ps.executeUpdate();
	}

	/**
	 * Retrieves all assets that belong to a specific user.
	 * 
	 * @param id the user's ID.
	 * @return an ArrayList that contains all asset objects.
	 * @throws SQLException thrown if the query caused an error.
	 */
	public ArrayList<Asset> retrieveAssetList(int id) throws SQLException {

		ArrayList<Asset> assetList = new ArrayList<>();

		PreparedStatement ps = getDB().getConnection()
				.prepareStatement("SELECT * FROM asset_tracker_db.assets WHERE user_id = ?");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			int itemID = rs.getInt("id");
			int userID = rs.getInt("user_id");
			String itemName = rs.getString("item_name");
			String description = rs.getString("description");
			Double price = rs.getDouble("price");
			LocalDate dateObtained = rs.getDate("date_obtained").toLocalDate();
			Asset asset = new Asset(itemID, userID, itemName, description, price, dateObtained);
			assetList.add(asset);
		}

		return assetList;
	}

	/**
	 * When a user forgets their password a new one is made and emailed to them.
	 * 
	 * @param email a user's email.
	 * @return true or false whether or not the process was successful.
	 * @throws SQLException                 thrown if the query caused an error.
	 * @throws MessagingException           thrown when the message fails to send.
	 * @throws NamingException              thrown when the message cannot be
	 *                                      addressed.
	 * @throws ParserConfigurationException thrown if the parser cannot be
	 *                                      configured.
	 * @throws SAXException                 thrown if the XML file cannot be parsed.
	 * @throws IOException                  thrown if there is an input/output
	 *                                      error.
	 */
	public boolean forgotPassword(String email) throws SQLException, MessagingException, NamingException,
			ParserConfigurationException, SAXException, IOException {

		PreparedStatement ps = getDB().getConnection()
				.prepareStatement("SELECT email FROM asset_tracker_db.users WHERE email LIKE ?;");
		ps.setString(1, email);
		ResultSet rs = ps.executeQuery();

		String rsEmail = null;

		while (rs.next()) {
			rsEmail = rs.getString("email");
		}

		if (rsEmail != null) {
			EmailService es = new EmailService();
			String temporary = generateRandomString();
			String hashed = BCrypt.withDefaults().hashToString(12, temporary.toCharArray());
			String body = "This email contains sensitive information.\nPlease do not share these details with anyone.\nHere is your new password: "
					+ temporary;

			ps = getDB().getConnection()
					.prepareStatement("UPDATE asset_tracker_db.users SET password = ? WHERE email LIKE ?");
			ps.setString(1, hashed);
			ps.setString(2, rsEmail);
			ps.executeUpdate();

			es.sendMail(rsEmail, body);
			return true;
		}

		return false;
	}

	/**
	 * Generates a random password.
	 * 
	 * @return a temporary password.
	 */
	private static String generateRandomString() {

		final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		SecureRandom random = new SecureRandom();
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < 15; i++) {
			int index = random.nextInt(characters.length());
			sb.append(characters.charAt(index));
		}

		return sb.toString();
	}

	/**
	 * A getter method to retrieve the MySQLDriver object.
	 * 
	 * @return the db
	 */
	public MySQLDriver getDB() {

		return db;
	}

}