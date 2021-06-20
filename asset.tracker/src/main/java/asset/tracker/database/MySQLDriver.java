package asset.tracker.database;

import java.sql.*;

/**
 * A class for connecting to the local database.
 * 
 * @author Quack
 * 
 */
public final class MySQLDriver {

	private static final String URL = "jdbc:mysql://localhost:3306/test";
	private static final String USER = "root";
	private static final String PW = "the password for your database";
	private Connection connection;

	/**
	 * A no argument constructor that creates a database connection.
	 * 
	 * @throws SQLException thrown when a connection cannot be made.
	 * 
	 */
	public MySQLDriver() throws SQLException {

		this.connection = DriverManager.getConnection(URL, USER, PW);
	}

	/**
	 * A utility method for performing SQL prepared statements.
	 * 
	 * @param query is a string that represents a SQL query.
	 * @return the results of a SQL query.
	 * @throws SQLException thrown if the prepared statement fails.
	 */
	public PreparedStatement prepareStatement(String query) throws SQLException {

		PreparedStatement ps = null;
		ps = this.getConnection().prepareStatement(query);
		return ps;
	}

	/**
	 * Disconnects from the database and closes the connection if it is open.
	 * 
	 * @throws SQLException thrown if the connection cannot be closed.
	 * 
	 */
	public void disconnect() throws SQLException {

		if (this.getConnection() != null & !this.getConnection().isClosed()) {
			this.getConnection().close();
		}
	}

	/**
	 * A getter method for retrieving a database connection.
	 * 
	 * @return the connection to MySQL.
	 */
	public Connection getConnection() {

		return this.connection;
	}

}