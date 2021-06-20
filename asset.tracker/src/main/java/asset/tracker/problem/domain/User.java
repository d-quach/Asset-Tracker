package asset.tracker.problem.domain;

/**
 * A class for modeling user objects.
 * 
 * @author Quack
 *
 */
public final class User {

	private final int id;
	private final String email;
	private final String password;
	private final String firstName;
	private final String lastName;

	/**
	 * User constructor.
	 * 
	 * @param id        the user's id.
	 * @param email     their email.
	 * @param password  their password.
	 * @param firstName their first name.
	 * @param lastName  their last name.
	 */
	public User(int id, String email, String password, String firstName, String lastName) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	/**
	 * Gets the user's id.
	 * 
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the user's email.
	 * 
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Gets the user's password.
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Gets the user's first name.
	 * 
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Gets the user's last name.
	 * 
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

}