package asset.tracker.gui;

import static javax.swing.JOptionPane.showMessageDialog;
import java.awt.*;

import javax.mail.MessagingException;
import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.border.*;
import asset.tracker.manager.AssetTrackingSystem;
import java.awt.event.*;
import java.sql.SQLException;

/**
 * The GUI for retrieving a forgotten password.
 * 
 * @author Quack
 *
 */
public final class ForgotWindow extends JFrame {

	private static final long serialVersionUID = -8304131029680274209L;
	private JPanel forgotPane;
	private JTextField emailField;

	/**
	 * Create the frame.
	 */
	public ForgotWindow() {

		setTitle("Asset Tracker");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 350);
		forgotPane = new JPanel();
		forgotPane.setBackground(Color.DARK_GRAY);
		forgotPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		forgotPane.setLayout(new BorderLayout(0, 0));
		setContentPane(forgotPane);

		JPanel titlePanel = new JPanel();
		titlePanel.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.BLACK, Color.WHITE, Color.BLACK));
		titlePanel.setBackground(Color.LIGHT_GRAY);
		FlowLayout fl_titlePanel = (FlowLayout) titlePanel.getLayout();
		fl_titlePanel.setVgap(10);
		forgotPane.add(titlePanel, BorderLayout.NORTH);

		JLabel titleLabel = new JLabel("Forgotten Password");
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		titlePanel.add(titleLabel);

		JPanel inputPanel = new JPanel();
		inputPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.BLACK, Color.WHITE, Color.BLACK));
		inputPanel.setBackground(Color.LIGHT_GRAY);
		forgotPane.add(inputPanel, BorderLayout.CENTER);
		GridBagLayout gbl_inputPanel = new GridBagLayout();
		gbl_inputPanel.columnWidths = new int[] { 50, 50, 300, 30, 0 };
		gbl_inputPanel.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_inputPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_inputPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		inputPanel.setLayout(gbl_inputPanel);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		cancelButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				setVisible(false);
				LoginWindow lw = new LoginWindow();
				lw.setVisible(true);
			}
		});

		JLabel emailLabel = new JLabel("Email");
		emailLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_emailLabel = new GridBagConstraints();
		gbc_emailLabel.insets = new Insets(50, 0, 5, 5);
		gbc_emailLabel.gridx = 1;
		gbc_emailLabel.gridy = 0;
		inputPanel.add(emailLabel, gbc_emailLabel);

		emailField = new JTextField();
		emailField.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_emailField = new GridBagConstraints();
		gbc_emailField.fill = GridBagConstraints.HORIZONTAL;
		gbc_emailField.insets = new Insets(50, 0, 5, 5);
		gbc_emailField.gridx = 2;
		gbc_emailField.gridy = 0;
		inputPanel.add(emailField, gbc_emailField);
		emailField.setColumns(25);

		final JButton resetButton = new JButton("Reset Password");
		resetButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		resetButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				resetButton.setEnabled(false);

				String email = emailField.getText();

				if (email == null || email.isBlank()) {
					showMessageDialog(null, "Please Fill in the Form Correctly");
					resetButton.setEnabled(true);
					return;
				}

				try {
					AssetTrackingSystem ats = new AssetTrackingSystem();
					ats.connect();

					if (ats.forgotPassword(email) == true) {
						setVisible(false);
						LoginWindow lw = new LoginWindow();
						lw.setVisible(true);

					} else {
						showMessageDialog(null, "This Email Was Not Found");
						resetButton.setEnabled(true);
						return;
					}

				} catch (HeadlessException | SQLException | MessagingException | NamingException e1) {
					showMessageDialog(null, "The Password Could Not Be Sent");
					resetButton.setEnabled(true);
					return;

				} catch (Exception e2) {
					showMessageDialog(null, "The Password Could Not Be Sent");
					resetButton.setEnabled(true);
					return;
				}
			}
		});

		GridBagConstraints gbc_resetButton = new GridBagConstraints();
		gbc_resetButton.insets = new Insets(15, 0, 5, 20);
		gbc_resetButton.gridx = 2;
		gbc_resetButton.gridy = 1;
		inputPanel.add(resetButton, gbc_resetButton);
		GridBagConstraints gbc_cancelButton = new GridBagConstraints();
		gbc_cancelButton.insets = new Insets(85, 5, 5, 5);
		gbc_cancelButton.gridx = 0;
		gbc_cancelButton.gridy = 2;
		inputPanel.add(cancelButton, gbc_cancelButton);
	}

}