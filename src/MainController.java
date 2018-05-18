import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;

public class MainController {

	private JFrame frame;
	private JTextField txtFuelType;
	private final String fuelTableName = "Tbl_YakitTuru";
	private final String transmissionTableName = "Tbl_VitesTuru";
	private final String cityTableName = "Tbl_Sehir";
	private final String adTableName = "Tbl_Ilan";
	private final String carTableName = "Tbl_Araba";
	private final String colorTableName = "Tbl_Renk";

	static String colFuel[] = { "ID", "Fuel Type" };
	static String colColor[] = { "ID", "Color Name" };
	static String colCity[] = { "ID", "City Name" };
	static String colTransmission[] = { "ID", "Transmission Types" };
	static String colCar[] = { "ID", "Brand", "Model", "Fuel", "Transmission", "Color" };

	private static DefaultTableModel fuel_tableModel = new DefaultTableModel(colFuel, 0);
	private static DefaultTableModel color_tableModel = new DefaultTableModel(colColor, 0);
	private static DefaultTableModel city_tableModel = new DefaultTableModel(colCity, 0);
	private static DefaultTableModel transmission_tableModel = new DefaultTableModel(colTransmission, 0);
	private static DefaultTableModel car_tableModel = new DefaultTableModel(colCar, 0);
	
	DefaultComboBoxModel carPageTransmissionTypeModel;
	DefaultComboBoxModel carPageFuelModel;
	DefaultComboBoxModel carPageColorModel;
	
	JComboBox fuel_comboBox = new JComboBox();
	JComboBox color_comboBox = new JComboBox();
	JComboBox transmission_comboBox = new JComboBox();

	
	/**
	 * Launch the application.
	 */
	static DatabaseConnector dbc = new DatabaseConnector();
	static Connection conn;
	private JTable fuel_table;
	private JTextField fuel_update_textField;
	private JTextField fuel_insert_textField;

	private JTextField city_insert_textField;
	private JTextField city_update_textField;
	private JTable city_table;

	private JTextField transmission_insert_textField;
	private JTable transmission_table;

	private JTextField color_update_textField;
	private JTextField color_insert_textField;
	private JTable color_table;

	private JTextField carModel_insert_textField;
	private JTable car_table;
	private JTextField carBrand_insert_textField;

	public static void main(String[] args) {
		conn = dbc.connectDatabase();
		dbc.createTables(conn);
		//dbc.initTables(conn);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainController window = new MainController();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainController() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		Dimension min = new Dimension(750, 750);
		frame.setMinimumSize(min);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension max = toolkit.getScreenSize();
		frame.setMaximumSize(max);
		// frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));

		JPanel main_menu = new JPanel();
		frame.getContentPane().add(main_menu, "name_14749222077628");
		main_menu.setLayout(new CardLayout(0, 0));

		JTabbedPane main_menu_tabbed = new JTabbedPane(JTabbedPane.TOP);
		main_menu.add(main_menu_tabbed, "name_15026400323007");
		JPanel update_data = new JPanel();
		// frame.getContentPane().add(update_data, "name_14889183967799");
		update_data.setLayout(new CardLayout(0, 0));

		JTabbedPane update_menu_tabbed = new JTabbedPane(JTabbedPane.TOP);
		update_data.add(update_menu_tabbed, "name_15032863038707");

		JPanel filter_data = new JPanel();
		// frame.getContentPane().add(filter_data, "name_14894919628649");
		filter_data.setLayout(new CardLayout(0, 0));

		main_menu_tabbed.addTab("Filter data", filter_data);
		main_menu_tabbed.addTab("Update data", update_data);
		// update_data.add(fuel, "name_15525985634051");
		/*
		 * 
		 * 
		 * 
		 * CITY
		 * 
		 * 
		 * 
		 */

		JPanel city = new JPanel();
		update_menu_tabbed.addTab("City", city);

		city.setLayout(null);

		city_insert_textField = new JTextField();
		city_insert_textField.setBounds(100, 50, 114, 19);
		city.add(city_insert_textField);
		city_insert_textField.setColumns(10);

		JLabel lblCityName = new JLabel("City Name");
		lblCityName.setBounds(12, 52, 93, 19);
		city.add(lblCityName);
		updateCityTable();
		city_table = new JTable(city_tableModel);

		JScrollPane city_scrollPane = new JScrollPane(city_table);
		city_scrollPane.setBounds(28, 303, 421, 290);
		city.add(city_scrollPane);
		city_scrollPane.setViewportView(city_table);
		city_table.getModel().addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
					int col = city_table.getSelectedColumn();
					int row = city_table.getSelectedRow();
					System.out.println("Fafafaf " + row);

					if (row != -1) {
						String value = city_table.getModel().getValueAt(row, 0).toString();
						dbc.updateRecord(conn, value, "Tbl_Sehir", row, city_tableModel);
					}

					updateCityTable();
					city_scrollPane.setViewportView(city_table);
				}
			}
		});

		JButton city_btnSave = new JButton("Save");
		city_btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (!city_insert_textField.getText().trim().isEmpty()) {
					dbc.insertRecord(conn, "Tbl_Sehir", city_insert_textField.getText());
					updateCityTable();
					city_scrollPane.setViewportView(city_table);
				}
			}
		});
		city_btnSave.setBounds(56, 106, 117, 25);
		city.add(city_btnSave);

		JButton city_btnDelete = new JButton("Delete");
		city_btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = city_table.getSelectedRow();
				System.out.println("Fransa: " + row);
				if (row != -1) {
					String value = city_table.getModel().getValueAt(row, 0).toString();
					dbc.deleteRecord(conn, value, cityTableName);
				}

				updateCityTable();
				city_scrollPane.setViewportView(city_table);
			}
		});
		city_btnDelete.setBounds(588, 493, 117, 25);
		city.add(city_btnDelete);

		/*
		 * 
		 * 
		 * 
		 * TRANSMISSION
		 * 
		 * 
		 * 
		 */
		JPanel transmission = new JPanel();
		update_menu_tabbed.addTab("Transmission", transmission);

		transmission.setLayout(null);

		transmission_insert_textField = new JTextField();
		transmission_insert_textField.setBounds(100, 50, 114, 19);
		transmission.add(transmission_insert_textField);
		transmission_insert_textField.setColumns(10);

		JLabel lblTransmissionName = new JLabel("Transmission Type");
		lblTransmissionName.setBounds(12, 52, 93, 19);
		transmission.add(lblTransmissionName);
		updateTransmissionTable();
		transmission_table = new JTable(transmission_tableModel);

		JScrollPane transmission_scrollPane = new JScrollPane(transmission_table);
		transmission_scrollPane.setBounds(28, 303, 421, 290);
		transmission.add(transmission_scrollPane);
		transmission_scrollPane.setViewportView(transmission_table);
		transmission_table.getModel().addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
					int col = transmission_table.getSelectedColumn();
					int row = transmission_table.getSelectedRow();
					System.out.println("Fafafaf " + row);

					if (row != -1) {
						String value = transmission_table.getModel().getValueAt(row, 0).toString();
						dbc.updateRecord(conn, value, "Tbl_VitesTuru", row, transmission_tableModel);
					}

					updateTransmissionTable();
					transmission_scrollPane.setViewportView(transmission_table);
				}
			}
		});

		JButton transmission_btnSave = new JButton("Save");
		transmission_btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (!transmission_insert_textField.getText().trim().isEmpty()) {
					dbc.insertRecord(conn, "Tbl_VitesTuru", transmission_insert_textField.getText());
					updateTransmissionTable();
					transmission_scrollPane.setViewportView(transmission_table);
				}
			}
		});
		transmission_btnSave.setBounds(56, 106, 117, 25);
		transmission.add(transmission_btnSave);

		JButton transmission_btnDelete = new JButton("Delete");
		transmission_btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = transmission_table.getSelectedRow();
				System.out.println("Fransa: " + row);
				if (row != -1) {
					String value = transmission_table.getModel().getValueAt(row, 0).toString();
					dbc.deleteRecord(conn, value, transmissionTableName);
				}

				updateTransmissionTable();
				transmission_scrollPane.setViewportView(transmission_table);
			}
		});
		transmission_btnDelete.setBounds(588, 493, 117, 25);
		transmission.add(transmission_btnDelete);

		/*
		 * 
		 * 
		 * AD
		 * 
		 * 
		 */

		JPanel ad = new JPanel();
		update_menu_tabbed.addTab("Ad", ad);

		/************
		 * 
		 * 
		 * COLOR
		 * 
		 **************/
		JPanel color = new JPanel();
		update_menu_tabbed.addTab("Color", color);
		color.setLayout(null);

		color_insert_textField = new JTextField();
		color_insert_textField.setBounds(100, 50, 114, 19);
		color.add(color_insert_textField);
		color_insert_textField.setColumns(10);

		JLabel lblColorName = new JLabel("Color Name");
		lblColorName.setBounds(12, 52, 93, 15);
		color.add(lblColorName);
		updateColorTable();
		color_table = new JTable(color_tableModel);

		color_tableModel.addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent e) {
				System.out.println("Table Changed");
				if (e.getType() == TableModelEvent.UPDATE) {
					String value;
					int row = color_table.getSelectedRow();
					try {
						value = color_table.getModel().getValueAt(row, 0).toString();
						dbc.updateRecord(conn, value, "Tbl_Renk", row, color_tableModel);
					} catch (Exception ex) {
						System.out.println("An error occured");
					}
					updateColorTable();
				}
			}
		});

		JScrollPane color_scrollPane = new JScrollPane(color_table);
		color_scrollPane.setBounds(28, 303, 421, 290);
		color.add(color_scrollPane);

		JButton color_btnSave = new JButton("Save");
		color_btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (!color_insert_textField.getText().trim().isEmpty()) {
					dbc.insertRecord(conn, "Tbl_Renk", color_insert_textField.getText());
					updateColorTable();
					color_scrollPane.setViewportView(color_table);
				}
			}
		});
		color_btnSave.setBounds(57, 109, 117, 25);
		color.add(color_btnSave);

		JButton color_btnDelete = new JButton("Delete");
		color_btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String value;
				int row = color_table.getSelectedRow();
				try {
					value = color_table.getModel().getValueAt(row, 0).toString();
					dbc.deleteRecord(conn, value, colorTableName);
				} catch (Exception ex) {
					System.out.println("An error occured");
				}

				updateColorTable();
				color_scrollPane.setViewportView(color_table);

			}
		});
		color_btnDelete.setBounds(588, 493, 117, 25);
		color.add(color_btnDelete);

		color_update_textField = new JTextField();
		color_update_textField.setBounds(588, 349, 114, 19);
		color.add(color_update_textField);
		color_update_textField.setColumns(10);

		JLabel lblNewColorType = new JLabel("New Color Name");
		lblNewColorType.setBounds(467, 349, 114, 15);
		color.add(lblNewColorType);

		JButton color_btnUpdate = new JButton("Update");
		color_btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = color_table.getSelectedRow();
				String value = color_table.getModel().getValueAt(row, 0).toString();
				updateColorTable();
				color_scrollPane.setViewportView(color_table);
			}
		});
		color_btnUpdate.setBounds(588, 408, 117, 25);
		color.add(color_btnUpdate);

		/*****
		 * 
		 * FUEL
		 * 
		 ******/
		JPanel fuel = new JPanel();
		// update_data.add(color, "name_15544891609718");

		update_menu_tabbed.addTab("Fuel", fuel);
		fuel.setLayout(null);

		fuel_insert_textField = new JTextField();
		fuel_insert_textField.setBounds(100, 50, 114, 19);
		fuel.add(fuel_insert_textField);
		fuel_insert_textField.setColumns(10);

		JLabel lblTypeName = new JLabel("Type Name");
		lblTypeName.setBounds(12, 52, 93, 19);
		fuel.add(lblTypeName);
		updateFuelTable();
		fuel_table = new JTable(fuel_tableModel);

		JScrollPane fuel_scrollPane = new JScrollPane(fuel_table);
		fuel_scrollPane.setBounds(28, 303, 421, 290);
		fuel.add(fuel_scrollPane);
		fuel_scrollPane.setViewportView(fuel_table);
		fuel_table.getModel().addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
					int col = fuel_table.getSelectedColumn();
					int row = fuel_table.getSelectedRow();
					System.out.println("Fafafaf " + row);

					if (row != -1) {
						String value = fuel_table.getModel().getValueAt(row, 0).toString();
						dbc.updateRecord(conn, value, "Tbl_YakitTuru", row, fuel_tableModel);
					}

					updateFuelTable();
					fuel_scrollPane.setViewportView(fuel_table);
					fuel_comboBox.setModel(carPageFuelModel);
					
				}
			}
		});

		JButton fuel_btnSave = new JButton("Save");
		fuel_btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (!fuel_insert_textField.getText().trim().isEmpty()) {
					dbc.insertRecord(conn, "Tbl_YakitTuru", fuel_insert_textField.getText());
					updateFuelTable();
					fuel_scrollPane.setViewportView(fuel_table);
				}
			}
		});
		fuel_btnSave.setBounds(56, 106, 117, 25);
		fuel.add(fuel_btnSave);

		JButton fuel_btnDelete = new JButton("Delete");
		fuel_btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = fuel_table.getSelectedRow();
				System.out.println("Fransa: " + row);
				if (row != -1) {
					String value = fuel_table.getModel().getValueAt(row, 0).toString();
					dbc.deleteRecord(conn, value, fuelTableName);
				}

				updateFuelTable();
				fuel_scrollPane.setViewportView(fuel_table);
			}
		});
		fuel_btnDelete.setBounds(588, 493, 117, 25);
		fuel.add(fuel_btnDelete);

		fuel_update_textField = new JTextField();
		fuel_update_textField.setBounds(588, 349, 114, 19);
		fuel.add(fuel_update_textField);
		fuel_update_textField.setColumns(10);

		JLabel lblNewFuelType = new JLabel("New Fuel Type");
		lblNewFuelType.setBounds(467, 349, 114, 15);
		fuel.add(lblNewFuelType);

		JButton fuel_btnUpdate = new JButton("Update");
		fuel_btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = fuel_table.getSelectedRow();
				String value = fuel_table.getModel().getValueAt(row, 0).toString();
				updateFuelTable();
				fuel_scrollPane.setViewportView(fuel_table);
			}
		});
		fuel_btnUpdate.setBounds(588, 408, 117, 25);
		fuel.add(fuel_btnUpdate);

		/*
		 * 
		 * CAR
		 * 
		 * 
		 */

		JPanel car = new JPanel();
		update_menu_tabbed.addTab("Car", car);
		car.setLayout(null);

		carModel_insert_textField = new JTextField();
		carModel_insert_textField.setBounds(100, 50, 114, 19);
		car.add(carModel_insert_textField);
		carModel_insert_textField.setColumns(10);

		JLabel lblCarModel = new JLabel("Car Model");
		lblCarModel.setBounds(12, 52, 93, 19);
		car.add(lblCarModel);
		car_table = new JTable(car_tableModel);
		updateCarTable();

		JScrollPane car_scrollPane = new JScrollPane(car_table);
		car_scrollPane.setBounds(12, 186, 700, 390);
		car.add(car_scrollPane);
		car_scrollPane.setViewportView(car_table);
		car_table.getModel().addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
					int col = car_table.getSelectedColumn();
					int row = car_table.getSelectedRow();
					System.out.println("Fafafaf " + row);

					if (row != -1) {
						String value = car_table.getModel().getValueAt(row, 0).toString();
						dbc.updateRecord(conn, value, "Tbl_Araba", row, car_tableModel);
					}

					updateCarTable();
					car_scrollPane.setViewportView(car_table);
				}
			}
		});

		JButton car_btnSave = new JButton("Save");
		car_btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (!carModel_insert_textField.getText().trim().isEmpty()) {
					String[] datas = { carBrand_insert_textField.getText(), carModel_insert_textField.getText(), 
							transmission_comboBox.getSelectedItem().toString(),fuel_comboBox.getSelectedItem().toString(),color_comboBox.getSelectedItem().toString()};
					
					dbc.insertRecord(conn, "Tbl_Araba", datas);
					updateCarTable();
					car_scrollPane.setViewportView(car_table);
				}
			}
		});
		car_btnSave.setBounds(185, 122, 117, 25);

		car.add(car_btnSave);

		JButton car_btnDelete = new JButton("Delete");
		car_btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = car_table.getSelectedRow();
				System.out.println("Fransa: " + row);
				if (row != -1) {
					String value = car_table.getModel().getValueAt(row, 0).toString();
					dbc.deleteRecord(conn, value, carTableName);
				}

				updateCarTable();
				car_scrollPane.setViewportView(car_table);
			}
		});
		car_btnDelete.setBounds(314, 622, 117, 25);
		car.add(car_btnDelete);

		JLabel lblCarBrand = new JLabel("Car Brand");
		lblCarBrand.setBounds(232, 52, 70, 15);
		car.add(lblCarBrand);

		carBrand_insert_textField = new JTextField();
		carBrand_insert_textField.setBounds(331, 50, 114, 19);
		car.add(carBrand_insert_textField);
		carBrand_insert_textField.setColumns(10);

		transmission_comboBox.setBounds(616, 47, 96, 24);
		carPageTransmissionTypeModel = new DefaultComboBoxModel(dbc.getTransmissionTypes(conn).toArray());
		transmission_comboBox.setModel(carPageTransmissionTypeModel);
		car.add(transmission_comboBox);

		JLabel lblTransmission = new JLabel("Transmission");
		lblTransmission.setBounds(491, 54, 102, 15);
		car.add(lblTransmission);

		JLabel lblFuel = new JLabel("Fuel");
		lblFuel.setBounds(491, 98, 70, 15);
		car.add(lblFuel);

		fuel_comboBox.setBounds(616, 93, 96, 24);
		carPageFuelModel = new DefaultComboBoxModel(dbc.getFuelTypes(conn).toArray());
		fuel_comboBox.setModel(carPageFuelModel);
		car.add(fuel_comboBox);

		JLabel lblColor = new JLabel("Color");
		lblColor.setBounds(491, 144, 70, 15);
		car.add(lblColor);

		
		color_comboBox.setBounds(616, 139, 96, 24);
		carPageColorModel = new DefaultComboBoxModel(dbc.getColorNames(conn).toArray());
		color_comboBox.setModel(carPageColorModel);
		car.add(color_comboBox);

		frame.setVisible(true);
	}

	public void updateFuelTable() {
		fuel_comboBox.removeAllItems();
		ArrayList<Object> fuelObjs = dbc.selectAllQuery("Tbl_YakitTuru", conn);
		fuel_tableModel.setRowCount(0);
		for (Object o : fuelObjs) {
			if (o instanceof Fuel) {
				Object[] data = { ((Fuel) o).getFuelID(), ((Fuel) o).getFuelType() };
				fuel_comboBox.addItem(((Fuel) o).getFuelType());
				fuel_tableModel.addRow(data);
			}
		}
		updateCarTable();
	}

	public void updateCityTable() {
		ArrayList<Object> cityObjs = dbc.selectAllQuery("Tbl_Sehir", conn);
		city_tableModel.setRowCount(0);
		for (Object o : cityObjs) {
			if (o instanceof City) {
				Object[] data = { ((City) o).getCityID(), ((City) o).getCityName() };
				city_tableModel.addRow(data);
			}
		}
	}

	public void updateColorTable() {
		color_comboBox.removeAllItems();
		color_tableModel.setRowCount(0);
		ArrayList<Object> colorObjs = dbc.selectAllQuery("Tbl_Renk", conn);
		for (Object o : colorObjs) {
			if (o instanceof Color) {
				Object[] data = { ((Color) o).getColorID(), ((Color) o).getColorName() };
				color_comboBox.addItem(((Color) o).getColorName());
				color_tableModel.addRow(data);
			}
		}
		updateCarTable();
	}

	public void updateTransmissionTable() {
		transmission_comboBox.removeAllItems();
		transmission_tableModel.setRowCount(0);
		ArrayList<Object> transmissionObjs = dbc.selectAllQuery("Tbl_VitesTuru", conn);
		for (Object o : transmissionObjs) {
			if (o instanceof Transmission) {
				Object[] data = { ((Transmission) o).getTransmissionID(), ((Transmission) o).getTransmissionType() };
				transmission_comboBox.addItem(((Transmission) o).getTransmissionType());
				transmission_tableModel.addRow(data);
			}
		}
		updateCarTable();
	}

	public void updateCarTable() {
		car_tableModel.setRowCount(0);
		ArrayList<Object> carObjs = dbc.selectAllQuery("Tbl_Araba", conn);
		for (Object o : carObjs) {
			if (o instanceof Car) {
				Object[] data = { ((Car) o).getCarID(), ((Car) o).getCarBrand(), ((Car) o).getCarModel(),
						((Car) o).getFuelType(), ((Car) o).getTransmissionType(), ((Car) o).getColorName() };
				car_tableModel.addRow(data);
			}
		}
	}
}
