package asset.tracker.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import asset.tracker.manager.AssetTrackingSystem;
import static javax.swing.JOptionPane.showMessageDialog;
import java.awt.event.*;

/**
 * The main GUI for logging in.
 * 
 * @author Quack
 *
 */
public final class LoginWindow extends JFrame {

	private static final long serialVersionUID = 3666025290942314870L;
	private JPanel loginPane;
	private JTextField emailField;
	private JPasswordField passwordField;

	/**
	 * Create the frame.
	 */
	public LoginWindow() {

		setResizable(false);
		setTitle("Asset Tracker");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 350);
		loginPane = new JPanel();
		loginPane.setBackground(Color.DARK_GRAY);
		loginPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(loginPane);
		loginPane.setLayout(new BorderLayout(0, 0));

		JPanel titlePanel = new JPanel();
		titlePanel.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.BLACK, Color.WHITE, Color.BLACK));
		titlePanel.setBackground(Color.LIGHT_GRAY);
		FlowLayout fl_titlePanel = (FlowLayout) titlePanel.getLayout();
		fl_titlePanel.setVgap(10);
		loginPane.add(titlePanel, BorderLayout.NORTH);

		JLabel titleLabel = new JLabel("Login");
		titleLabel.setForeground(Color.BLACK);
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		titlePanel.add(titleLabel);

		JPanel inputPanel = new JPanel();
		inputPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.BLACK, Color.WHITE, Color.BLACK));
		inputPanel.setBackground(Color.LIGHT_GRAY);
		inputPanel.setForeground(Color.WHITE);
		loginPane.add(inputPanel, BorderLayout.CENTER);
		GridBagLayout gbl_inputPanel = new GridBagLayout();
		gbl_inputPanel.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_inputPanel.rowHeights = new int[] { 0, 20, 0, 0 };
		gbl_inputPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_inputPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		inputPanel.setLayout(gbl_inputPanel);

		JLabel emailLabel = new JLabel("Email");
		emailLabel.setForeground(Color.BLACK);
		emailLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_emailLabel = new GridBagConstraints();
		gbc_emailLabel.insets = new Insets(40, 40, 20, 5);
		gbc_emailLabel.gridx = 1;
		gbc_emailLabel.gridy = 0;
		inputPanel.add(emailLabel, gbc_emailLabel);

		emailField = new JTextField();
		emailField.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_emailField = new GridBagConstraints();
		gbc_emailField.anchor = GridBagConstraints.WEST;
		gbc_emailField.gridwidth = 3;
		gbc_emailField.insets = new Insets(40, 0, 20, 0);
		gbc_emailField.gridx = 2;
		gbc_emailField.gridy = 0;
		inputPanel.add(emailField, gbc_emailField);
		emailField.setColumns(25);

		JLabel passwordLabel = new JLabel("Password ");
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_passwordLabel = new GridBagConstraints();
		gbc_passwordLabel.insets = new Insets(0, 40, 10, 5);
		gbc_passwordLabel.gridx = 1;
		gbc_passwordLabel.gridy = 1;
		inputPanel.add(passwordLabel, gbc_passwordLabel);

		passwordField = new JPasswordField();
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.anchor = GridBagConstraints.WEST;
		gbc_passwordField.gridwidth = 3;
		gbc_passwordField.insets = new Insets(0, 0, 10, 0);
		gbc_passwordField.gridx = 2;
		gbc_passwordField.gridy = 1;
		inputPanel.add(passwordField, gbc_passwordField);
		passwordField.setColumns(25);

		final JButton enterButton = new JButton("Enter");
		enterButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		enterButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				enterButton.setEnabled(false);

				try {
					String email = emailField.getText();
					char[] password = passwordField.getPassword();

					if (email == null || email.isBlank() || password == null || password.length == 0) {
						showMessageDialog(null, "Please Fill in the Forms Correctly");
						enterButton.setEnabled(true);
						return;
					}

					AssetTrackingSystem ats = new AssetTrackingSystem();
					ats.connect();
					boolean success = ats.login(email, password);

					if (success == false) {
						showMessageDialog(null, "Logging in Was Unsuccessful");
						enterButton.setEnabled(true);
						return;
					}

					if (success == true) {
						setVisible(false);
						int currentUserId = ats.getUserIdByEmail(email);
						AssetWindow aw = new AssetWindow(currentUserId);
						aw.setVisible(true);
					}

				} catch (Exception e1) {
					showMessageDialog(null, "Logging in Was Unsuccessful");
					e1.printStackTrace();
					enterButton.setEnabled(true);
					return;
				}
			}
		});

		GridBagConstraints gbc_enterButton = new GridBagConstraints();
		gbc_enterButton.insets = new Insets(5, 0, 0, 0);
		gbc_enterButton.anchor = GridBagConstraints.SOUTH;
		gbc_enterButton.gridwidth = 5;
		gbc_enterButton.gridx = 0;
		gbc_enterButton.gridy = 2;
		inputPanel.add(enterButton, gbc_enterButton);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.BLACK, Color.WHITE, Color.BLACK));
		bottomPanel.setBackground(Color.LIGHT_GRAY);
		loginPane.add(bottomPanel, BorderLayout.SOUTH);
		GridBagLayout gbl_bottomPanel = new GridBagLayout();
		gbl_bottomPanel.columnWidths = new int[] { 89, 0, 89, 0, 0, 0, 0, 0 };
		gbl_bottomPanel.rowHeights = new int[] { 50, 0 };
		gbl_bottomPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_bottomPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		bottomPanel.setLayout(gbl_bottomPanel);

		JButton createButton = new JButton("Create Account");
		createButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		createButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				setVisible(false);
				RegisterWindow rw = new RegisterWindow();
				rw.setVisible(true);
			}
		});

		GridBagConstraints gbc_createButton = new GridBagConstraints();
		gbc_createButton.insets = new Insets(0, 0, 0, 5);
		gbc_createButton.gridx = 1;
		gbc_createButton.gridy = 0;
		bottomPanel.add(createButton, gbc_createButton);

		JButton forgotButton = new JButton("Forgot Password");
		forgotButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		forgotButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				setVisible(false);
				ForgotWindow fw = new ForgotWindow();
				fw.setVisible(true);
			}
		});

		GridBagConstraints gbc_forgotButton = new GridBagConstraints();
		gbc_forgotButton.insets = new Insets(0, 0, 0, 90);
		gbc_forgotButton.anchor = GridBagConstraints.WEST;
		gbc_forgotButton.gridx = 3;
		gbc_forgotButton.gridy = 0;
		bottomPanel.add(forgotButton, gbc_forgotButton);
	}

}