package asset.tracker.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import asset.tracker.manager.AssetTrackingSystem;
import asset.tracker.problem.domain.Asset;

import java.awt.event.*;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Vector;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * The GUI used for the core functionality.
 * 
 * @author Quack
 *
 */
public final class AssetWindow extends JFrame {

	private static final long serialVersionUID = -1801454370574959871L;
	private JPanel assetPane;
	private JTextField itemField;
	private JTextField descriptionField;
	private JTextField priceField;
	private JTextField dateField;
	private JTextField totalValueField;
	private DefaultTableModel dtm;
	private JTable assetTable;
	private AssetTrackingSystem ats;
	private int currentUserID;
	private double totalValue;
	private NumberFormat cf;
	private NumberFormat nf;

	/**
	 * Create the frame.
	 * 
	 * @throws Exception thrown generally for any exception.
	 */
	public AssetWindow(final int UserID) throws Exception {

		ats = new AssetTrackingSystem();
		currentUserID = UserID;
		cf = NumberFormat.getCurrencyInstance();
		cf.setMaximumFractionDigits(2);
		nf = NumberFormat.getNumberInstance();
		nf.setGroupingUsed(false);
		setResizable(false);
		setTitle("Asset Tracker");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		assetPane = new JPanel();
		assetPane.setBackground(Color.DARK_GRAY);
		assetPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(assetPane);
		assetPane.setLayout(new BorderLayout(0, 0));

		JPanel addPanel = new JPanel();
		addPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.BLACK, Color.WHITE, Color.BLACK));
		addPanel.setBackground(Color.LIGHT_GRAY);
		assetPane.add(addPanel, BorderLayout.NORTH);
		GridBagLayout gbl_addPanel = new GridBagLayout();
		gbl_addPanel.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_addPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_addPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_addPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		addPanel.setLayout(gbl_addPanel);

		JLabel addLabel = new JLabel("Add an Asset");
		addLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		GridBagConstraints gbc_addLabel = new GridBagConstraints();
		gbc_addLabel.insets = new Insets(0, 5, 5, 5);
		gbc_addLabel.gridx = 0;
		gbc_addLabel.gridy = 0;
		addPanel.add(addLabel, gbc_addLabel);

		JLabel itemLabel = new JLabel("Item Name");
		itemLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_itemLabel = new GridBagConstraints();
		gbc_itemLabel.insets = new Insets(0, 0, 5, 5);
		gbc_itemLabel.gridx = 1;
		gbc_itemLabel.gridy = 1;
		addPanel.add(itemLabel, gbc_itemLabel);

		itemField = new JTextField();
		itemField.setToolTipText("Assign a Name to This Asset");
		itemField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		itemField.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_itemField = new GridBagConstraints();
		gbc_itemField.insets = new Insets(0, 0, 5, 0);
		gbc_itemField.anchor = GridBagConstraints.WEST;
		gbc_itemField.gridx = 3;
		gbc_itemField.gridy = 1;
		addPanel.add(itemField, gbc_itemField);
		itemField.setColumns(30);

		JLabel descriptionLabel = new JLabel("Description");
		descriptionLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_descriptionLabel = new GridBagConstraints();
		gbc_descriptionLabel.insets = new Insets(0, 0, 5, 5);
		gbc_descriptionLabel.gridx = 1;
		gbc_descriptionLabel.gridy = 2;
		addPanel.add(descriptionLabel, gbc_descriptionLabel);

		descriptionField = new JTextField();
		descriptionField.setToolTipText("Give a Description of This Asset");
		descriptionField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		descriptionField.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_descriptionField = new GridBagConstraints();
		gbc_descriptionField.insets = new Insets(0, 0, 5, 0);
		gbc_descriptionField.anchor = GridBagConstraints.WEST;
		gbc_descriptionField.gridx = 3;
		gbc_descriptionField.gridy = 2;
		addPanel.add(descriptionField, gbc_descriptionField);
		descriptionField.setColumns(30);

		JLabel priceLabel = new JLabel("Price");
		priceLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_priceLabel = new GridBagConstraints();
		gbc_priceLabel.insets = new Insets(0, 0, 5, 5);
		gbc_priceLabel.gridx = 1;
		gbc_priceLabel.gridy = 3;
		addPanel.add(priceLabel, gbc_priceLabel);

		priceField = new JTextField();
		priceField.setToolTipText("How Much Is This Asset Worth?");
		priceField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		priceField.setHorizontalAlignment(SwingConstants.CENTER);
		priceField.setColumns(30);
		GridBagConstraints gbc_priceField = new GridBagConstraints();
		gbc_priceField.anchor = GridBagConstraints.WEST;
		gbc_priceField.insets = new Insets(0, 0, 5, 0);
		gbc_priceField.gridx = 3;
		gbc_priceField.gridy = 3;
		addPanel.add(priceField, gbc_priceField);

		JLabel dateLabel = new JLabel("Date Obtained (yyyy-MM-dd)");
		dateLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_dateLabel = new GridBagConstraints();
		gbc_dateLabel.insets = new Insets(0, 0, 10, 5);
		gbc_dateLabel.gridx = 1;
		gbc_dateLabel.gridy = 4;
		addPanel.add(dateLabel, gbc_dateLabel);

		dateField = new JTextField();
		dateField.setToolTipText("What Is the Date That This Asset Was Obtained?");
		dateField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		dateField.setHorizontalAlignment(SwingConstants.CENTER);
		dateField.setColumns(30);
		GridBagConstraints gbc_dateField = new GridBagConstraints();
		gbc_dateField.insets = new Insets(0, 0, 10, 0);
		gbc_dateField.anchor = GridBagConstraints.WEST;
		gbc_dateField.gridx = 3;
		gbc_dateField.gridy = 4;
		addPanel.add(dateField, gbc_dateField);

		final JButton addButton = new JButton("Add");
		addButton.setToolTipText("Fill in the Form and Press This Button to Add an Asset");
		addButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		addButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				addButton.setEnabled(false);

				String itemName = itemField.getText();
				String description = descriptionField.getText();
				Double price = null;
				LocalDate dateObtained = null;
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

				try {
					price = Double.parseDouble(priceField.getText());
					dateObtained = LocalDate.parse(dateField.getText(), formatter);

					if (dateObtained.isAfter(LocalDate.now())) {
						showMessageDialog(null, "Please Enter a Date That Is Not in the Future");
						addButton.setEnabled(true);
						return;

					} else if (dateObtained.isBefore(LocalDate.of(1900, 01, 01))) {
						showMessageDialog(null, "Please Enter a Reasonable Date");
						addButton.setEnabled(true);
						return;
					}

				} catch (NullPointerException | NumberFormatException e1) {
					showMessageDialog(null, "Please Fill in the Forms Correctly");
					addButton.setEnabled(true);
					return;

				} catch (DateTimeParseException e3) {
					showMessageDialog(null, "Please Fill in the Date as yyyy-MM-dd");
					addButton.setEnabled(true);
					return;
				}

				if (itemName == null || itemName.isBlank() || description == null || description.isBlank()
						|| price == null) {
					showMessageDialog(null, "Please Fill in the form correctly");
					addButton.setEnabled(true);
					return;
				}

				if (price < 0) {
					showMessageDialog(null, "The price should be greater than zero");
					addButton.setEnabled(true);
					return;
				}

				try {
					ats.addAsset(currentUserID, itemName, description, price, dateObtained);
					totalValue += price;
					dtm.addRow(new Object[] { ats.getLastAssetID(), itemName, description, cf.format(price),
							dateObtained });
					totalValueField.setText(cf.format(totalValue));
				} catch (SQLException e1) {
					e1.printStackTrace();
					System.out.println("There Seems to Be an Issue Here.");
					addButton.setEnabled(true);
				}

				itemField.setText(null);
				descriptionField.setText(null);
				priceField.setText(null);
				dateField.setText(null);
				addButton.setEnabled(true);
			}
		});

		GridBagConstraints gbc_addButton = new GridBagConstraints();
		gbc_addButton.gridwidth = 2;
		gbc_addButton.insets = new Insets(0, 0, 10, 190);
		gbc_addButton.gridx = 2;
		gbc_addButton.gridy = 5;
		addPanel.add(addButton, gbc_addButton);

		JPanel assetPanel = new JPanel();
		assetPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.BLACK, Color.WHITE, Color.BLACK));
		assetPanel.setBackground(Color.LIGHT_GRAY);
		assetPane.add(assetPanel, BorderLayout.CENTER);
		GridBagLayout gbl_assetPanel = new GridBagLayout();
		gbl_assetPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_assetPanel.rowHeights = new int[] { 0, 0, 0 };
		gbl_assetPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_assetPanel.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		assetPanel.setLayout(gbl_assetPanel);

		JLabel currentLabel = new JLabel("Current Assets");
		currentLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		GridBagConstraints gbc_currentLabel = new GridBagConstraints();
		gbc_currentLabel.insets = new Insets(5, 5, 5, 5);
		gbc_currentLabel.gridx = 0;
		gbc_currentLabel.gridy = 0;
		assetPanel.add(currentLabel, gbc_currentLabel);

		JScrollPane assetScrollPane = new JScrollPane();
		assetScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		assetScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_assetScrollPane = new GridBagConstraints();
		gbc_assetScrollPane.gridwidth = 2;
		gbc_assetScrollPane.fill = GridBagConstraints.BOTH;
		gbc_assetScrollPane.gridx = 0;
		gbc_assetScrollPane.gridy = 1;
		assetPanel.add(assetScrollPane, gbc_assetScrollPane);

		assetTable = new JTable();
		assetTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		assetTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		assetTable.setDefaultEditor(Object.class, null);
		dtm = new DefaultTableModel(0, 0);
		String[] columnNames = { "ID", "Item Name", "Description", "Price", "Date Obtained" };
		dtm.setColumnIdentifiers(columnNames);
		assetTable.setModel(dtm);
		DeleteRowFromTableAction deleteAction = new DeleteRowFromTableAction(assetTable, dtm);
		InputMap im = assetTable.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		ActionMap am = assetTable.getActionMap();
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "deleteRow");
		am.put("deleteRow", deleteAction);

		DefaultTableCellRenderer centerRender = new DefaultTableCellRenderer();
		centerRender.setHorizontalAlignment(JLabel.CENTER);

		assetTable.getColumnModel().getColumn(0).setCellRenderer(centerRender);
		assetTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		assetTable.getColumnModel().getColumn(1).setCellRenderer(centerRender);
		assetTable.getColumnModel().getColumn(1).setPreferredWidth(400);
		assetTable.getColumnModel().getColumn(2).setCellRenderer(centerRender);
		assetTable.getColumnModel().getColumn(2).setPreferredWidth(400);
		assetTable.getColumnModel().getColumn(3).setCellRenderer(centerRender);
		assetTable.getColumnModel().getColumn(3).setPreferredWidth(250);
		assetTable.getColumnModel().getColumn(4).setCellRenderer(centerRender);
		assetTable.getColumnModel().getColumn(4).setPreferredWidth(140);

		ArrayList<Asset> al = ats.retrieveAssetList(this.currentUserID);
		Asset currentAsset;

		for (int i = 0; i < al.size(); i++) {
			currentAsset = al.get(i);
			totalValue += currentAsset.getPrice();
			dtm.addRow(new Object[] { currentAsset.getId(), currentAsset.getItemName(), currentAsset.getDescription(),
					cf.format(currentAsset.getPrice()), currentAsset.getDateObtained() });
		}

		assetTable.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.BLACK, Color.WHITE, Color.BLACK));
		assetScrollPane.setViewportView(assetTable);

		JPanel southPanel = new JPanel();
		southPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.BLACK, Color.WHITE, Color.BLACK));
		southPanel.setBackground(Color.LIGHT_GRAY);
		assetPane.add(southPanel, BorderLayout.SOUTH);
		GridBagLayout gbl_southPanel = new GridBagLayout();
		gbl_southPanel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_southPanel.rowHeights = new int[] { 0, 0, 0 };
		gbl_southPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_southPanel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		southPanel.setLayout(gbl_southPanel);

		JToolBar southToolBar = new JToolBar();
		southToolBar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		southToolBar.setFloatable(false);
		JButton deleteButton = southToolBar.add(deleteAction);
		deleteButton.setToolTipText("Select Any Number of Rows and Press This Button to Delete Them");
		deleteButton.setText("Delete Selected Rows");
		southToolBar.addSeparator(new Dimension(200, 5));
		JLabel totalValueLabel = new JLabel("Total Combined Value");
		southToolBar.add(totalValueLabel);
		southToolBar.addSeparator(new Dimension(10, 5));
		totalValueField = new JTextField();
		totalValueField.setColumns(25);
		totalValueField.setEditable(false);
		totalValueField.setHorizontalAlignment(SwingConstants.CENTER);
		totalValueField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		totalValueField.setText(cf.format(totalValue));
		southToolBar.add(totalValueField);

		GridBagConstraints gbc_southToolBar = new GridBagConstraints();
		gbc_southToolBar.insets = new Insets(3, 0, 5, 0);
		gbc_southToolBar.gridx = 8;
		gbc_southToolBar.gridy = 0;
		southPanel.add(southToolBar, gbc_southToolBar);

		JButton logoutButton = new JButton("Log Out");
		GridBagConstraints gbc_logoutButton = new GridBagConstraints();
		gbc_logoutButton.insets = new Insets(0, 0, 0, 62);
		gbc_logoutButton.gridx = 0;
		gbc_logoutButton.gridy = 1;
		southPanel.add(logoutButton, gbc_logoutButton);
		logoutButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		logoutButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				logout();
				setVisible(false);
				LoginWindow lw = new LoginWindow();
				lw.setVisible(true);
			}
		});
	}

	/**
	 * Upon logout, reset the currentUserID back to -1 and disconnect from the
	 * database.
	 * 
	 */
	private void logout() {

		currentUserID = -1;

		try {
			ats.getDB().disconnect();

		} catch (SQLException e) {
			System.out.println("There Seems to Be an Issue Here.");
		}
	}

	/**
	 * An abstract class that holds the getter methods for a table and model. A
	 * template for creating an action.
	 * 
	 * @author Quack
	 *
	 * @param <T> extends JTable
	 * @param <M> extends TableModel
	 */
	public abstract class AbstractTableAction<T extends JTable, M extends TableModel> extends AbstractAction {

		private static final long serialVersionUID = -4302887280621815484L;
		private T table;
		private M model;

		public AbstractTableAction(T table, M model) {

			this.table = table;
			this.model = model;
		}

		public T getTable() {

			return table;
		}

		public M getModel() {

			return model;
		}

	}

	/**
	 * Used to delete the selected rows from the asset table.
	 * 
	 * @author Quack
	 *
	 */
	public class DeleteRowFromTableAction extends AbstractTableAction<JTable, DefaultTableModel> {

		private static final long serialVersionUID = -2529929317387780565L;

		/**
		 * Attaches listeners to the parameters for performing an action.
		 * 
		 * @param table the given JTable object.
		 * @param model the given DefaultTableModel object.
		 */
		public DeleteRowFromTableAction(JTable table, DefaultTableModel model) {

			super(table, model);
			putValue(NAME, "Delete selected rows");
			putValue(SHORT_DESCRIPTION, "Delete selected rows");

			table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

				@Override
				public void valueChanged(ListSelectionEvent e) {
					setEnabled(getTable().getSelectedRowCount() > 0);
				}
			});
			setEnabled(getTable().getSelectedRowCount() > 0);
		}

		/**
		 * Deletes selected assets from the GUI and database.
		 */
		@SuppressWarnings("rawtypes")
		@Override
		public void actionPerformed(ActionEvent e) {

			int confirmation = JOptionPane.showConfirmDialog(null,
					"Please Confirm That You Want to Delete the Selected Rows.", "Removing Assets", 2,
					JOptionPane.WARNING_MESSAGE);

			if (confirmation == 0) {

				JTable table = getTable();

				if (table.getSelectedRowCount() > 0) {

					ArrayList<Vector> selectedRows = new ArrayList<>(25);
					DefaultTableModel model = getModel();
					Vector rowData = model.getDataVector();

					for (int row : table.getSelectedRows()) {

						int modelRow = table.convertRowIndexToModel(row);
						Vector rowValue = (Vector) rowData.get(modelRow);
						selectedRows.add(rowValue);
					}

					for (Vector rowValue : selectedRows) {

						int rowIndex = rowData.indexOf(rowValue);
						model.removeRow(rowIndex);

						int itemID = (int) rowValue.elementAt(0);
						String currentValue = (String) rowValue.elementAt(3);
						totalValue -= Double.parseDouble(currentValue.replaceAll("[^\\d.]", ""));

						try {
							ats.removeAsset(itemID);
						} catch (SQLException e1) {

						}
					}
					totalValueField.setText(cf.format(totalValue));
				}
			}
		}

	}

}