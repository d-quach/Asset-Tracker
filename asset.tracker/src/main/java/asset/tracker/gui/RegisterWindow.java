package asset.tracker.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import asset.tracker.manager.AssetTrackingSystem;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Arrays;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * The GUI used to register new user accounts.
 * 
 * @author Quack
 *
 */
public final class RegisterWindow extends JFrame {

	private static final long serialVersionUID = 3372411124565420694L;
	private JPanel registerPane;
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField emailField;
	private JPasswordField passwordField;
	private JPasswordField confirmField;

	/**
	 * Create the frame.
	 */
	public RegisterWindow() {

		setTitle("Asset Tracker");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 350);
		registerPane = new JPanel();
		registerPane.setBackground(Color.DARK_GRAY);
		registerPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		registerPane.setLayout(new BorderLayout(0, 0));
		setContentPane(registerPane);

		JPanel titlePanel = new JPanel();
		titlePanel.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.BLACK, Color.WHITE, Color.BLACK));
		FlowLayout fl_titlePanel = (FlowLayout) titlePanel.getLayout();
		fl_titlePanel.setVgap(10);
		titlePanel.setBackground(Color.LIGHT_GRAY);
		registerPane.add(titlePanel, BorderLayout.NORTH);

		JLabel titleLabel = new JLabel("Register a New Account");
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		titlePanel.add(titleLabel);

		JPanel inputPanel = new JPanel();
		inputPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.BLACK, Color.WHITE, Color.BLACK));
		inputPanel.setBackground(Color.LIGHT_GRAY);
		registerPane.add(inputPanel, BorderLayout.CENTER);
		GridBagLayout gbl_inputPanel = new GridBagLayout();
		gbl_inputPanel.columnWidths = new int[] { 30, 0, 200, 0 };
		gbl_inputPanel.rowHeights = new int[] { 0, 30, 0, 0, 0, 0 };
		gbl_inputPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0 };
		gbl_inputPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		inputPanel.setLayout(gbl_inputPanel);

		JLabel firstNameLabel = new JLabel("First Name");
		firstNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_firstNameLabel = new GridBagConstraints();
		gbc_firstNameLabel.insets = new Insets(10, 0, 9, 5);
		gbc_firstNameLabel.gridx = 1;
		gbc_firstNameLabel.gridy = 0;
		inputPanel.add(firstNameLabel, gbc_firstNameLabel);

		firstNameField = new JTextField();
		firstNameField.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_firstNameField = new GridBagConstraints();
		gbc_firstNameField.anchor = GridBagConstraints.WEST;
		gbc_firstNameField.insets = new Insets(10, 0, 9, 0);
		gbc_firstNameField.gridwidth = 3;
		gbc_firstNameField.gridx = 2;
		gbc_firstNameField.gridy = 0;
		inputPanel.add(firstNameField, gbc_firstNameField);
		firstNameField.setColumns(25);

		JLabel lastNameLabel = new JLabel("Last Name");
		lastNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lastNameLabel = new GridBagConstraints();
		gbc_lastNameLabel.insets = new Insets(0, 0, 9, 5);
		gbc_lastNameLabel.gridx = 1;
		gbc_lastNameLabel.gridy = 1;
		inputPanel.add(lastNameLabel, gbc_lastNameLabel);

		lastNameField = new JTextField();
		lastNameField.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lastNameField = new GridBagConstraints();
		gbc_lastNameField.anchor = GridBagConstraints.WEST;
		gbc_lastNameField.gridwidth = 3;
		gbc_lastNameField.insets = new Insets(0, 0, 9, 0);
		gbc_lastNameField.gridx = 2;
		gbc_lastNameField.gridy = 1;
		inputPanel.add(lastNameField, gbc_lastNameField);
		lastNameField.setColumns(25);

		JLabel emailLabel = new JLabel("Email");
		emailLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_emailLabel = new GridBagConstraints();
		gbc_emailLabel.insets = new Insets(0, 0, 9, 5);
		gbc_emailLabel.gridx = 1;
		gbc_emailLabel.gridy = 2;
		inputPanel.add(emailLabel, gbc_emailLabel);

		emailField = new JTextField();
		emailField.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_emailField = new GridBagConstraints();
		gbc_emailField.anchor = GridBagConstraints.WEST;
		gbc_emailField.gridwidth = 3;
		gbc_emailField.insets = new Insets(0, 0, 9, 0);
		gbc_emailField.gridx = 2;
		gbc_emailField.gridy = 2;
		inputPanel.add(emailField, gbc_emailField);
		emailField.setColumns(25);

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_passwordLabel = new GridBagConstraints();
		gbc_passwordLabel.insets = new Insets(0, 0, 9, 5);
		gbc_passwordLabel.gridx = 1;
		gbc_passwordLabel.gridy = 3;
		inputPanel.add(passwordLabel, gbc_passwordLabel);

		passwordField = new JPasswordField();
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.anchor = GridBagConstraints.WEST;
		gbc_passwordField.gridwidth = 3;
		gbc_passwordField.insets = new Insets(0, 0, 9, 0);
		gbc_passwordField.gridx = 2;
		gbc_passwordField.gridy = 3;
		inputPanel.add(passwordField, gbc_passwordField);
		passwordField.setColumns(25);

		JLabel confirmLabel = new JLabel("Confirm Password");
		confirmLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_confirmLabel = new GridBagConstraints();
		gbc_confirmLabel.insets = new Insets(0, 0, 10, 5);
		gbc_confirmLabel.gridx = 1;
		gbc_confirmLabel.gridy = 4;
		inputPanel.add(confirmLabel, gbc_confirmLabel);

		confirmField = new JPasswordField();
		confirmField.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_confirmField = new GridBagConstraints();
		gbc_confirmField.anchor = GridBagConstraints.WEST;
		gbc_confirmField.insets = new Insets(0, 0, 10, 0);
		gbc_confirmField.gridwidth = 3;
		gbc_confirmField.gridx = 2;
		gbc_confirmField.gridy = 4;
		inputPanel.add(confirmField, gbc_confirmField);
		confirmField.setColumns(25);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		cancelButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				setVisible(false);
				LoginWindow lw = new LoginWindow();
				lw.setVisible(true);
			}
		});

		GridBagConstraints gbc_cancelButton = new GridBagConstraints();
		gbc_cancelButton.gridwidth = 2;
		gbc_cancelButton.insets = new Insets(50, 5, 0, 90);
		gbc_cancelButton.gridx = 0;
		gbc_cancelButton.gridy = 5;
		inputPanel.add(cancelButton, gbc_cancelButton);

		final JButton createButton = new JButton("Create");
		createButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		createButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				createButton.setEnabled(false);

				String firstName = firstNameField.getText();
				String lastName = lastNameField.getText();
				String email = emailField.getText();
				char[] password = passwordField.getPassword();
				char[] confirmation = confirmField.getPassword();

				if (firstName == null || firstName.isBlank() || lastName == null || lastName.isBlank() || email == null
						|| email.isBlank() || password == null || password.length == 0 || confirmation == null
						|| confirmation.length == 0 || Arrays.equals(password, confirmation) == false) {

					showMessageDialog(null, "Please Fill in the Forms Correctly");
					createButton.setEnabled(true);
					return;
				}

				String emailRegex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

				if (!email.matches(emailRegex)) {
					showMessageDialog(null, "An Email Needs to Be in the Format: example@domain.com");
					createButton.setEnabled(true);
					return;
				}

				try {
					AssetTrackingSystem ats = new AssetTrackingSystem();
					ats.connect();

					if (ats.createUser(email, password, firstName, lastName) == true) {
						setVisible(false);
						LoginWindow lw = new LoginWindow();
						lw.setVisible(true);
					}

				} catch (SQLException e1) {
					showMessageDialog(null, "We Couldn't Create an Account");
					createButton.setEnabled(true);
					return;

				} catch (Exception e2) {
					showMessageDialog(null, "We Couldn't Create an Account");
					createButton.setEnabled(true);
					return;
				}
			}
		});

		GridBagConstraints gbc_createButton = new GridBagConstraints();
		gbc_createButton.insets = new Insets(0, 60, 40, 0);
		gbc_createButton.gridx = 2;
		gbc_createButton.gridy = 5;
		inputPanel.add(createButton, gbc_createButton);
	}

}