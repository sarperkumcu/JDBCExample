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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JRadioButton;

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
	static String colAd[] = { "ID", "Ad", "Price", "Km", "Date", "Car ID", "City" };
	static String colFilterData[] = { "Ad", "Brand", "Model", "Price", "Km", "Transmission Type", "Fuel Type", "Color",
			"Date", "City" };

	private static DefaultTableModel fuel_tableModel = new DefaultTableModel(colFuel, 0){
	    @Override 
	    public boolean isCellEditable(int row, int column)
	    {
	        return column != 0;// add your code here
	    }
	};
	private static DefaultTableModel color_tableModel = new DefaultTableModel(colColor, 0){
	    @Override 
	    public boolean isCellEditable(int row, int column)
	    {
	        return column != 0;// add your code here
	    }
	};
	private static DefaultTableModel city_tableModel = new DefaultTableModel(colCity, 0){
	    @Override 
	    public boolean isCellEditable(int row, int column)
	    {
	        return column != 0;// add your code here
	    }
	};
	private static DefaultTableModel transmission_tableModel = new DefaultTableModel(colTransmission, 0){
	    @Override 
	    public boolean isCellEditable(int row, int column)
	    {
	        return column != 0;// add your code here
	    }
	};
	private static DefaultTableModel car_tableModel = new DefaultTableModel(colCar, 0){
	    @Override 
	    public boolean isCellEditable(int row, int column)
	    {
	        return column != 0;// add your code here
	    }
	};
	private static DefaultTableModel ad_tableModel = new DefaultTableModel(colAd, 0){
	    @Override 
	    public boolean isCellEditable(int row, int column)
	    {
	        return column != 0;// add your code here
	    }
	};
	private static DefaultTableModel filterData_tableModel = new DefaultTableModel(colFilterData, 0);

	DefaultComboBoxModel carPageTransmissionTypeModel;
	DefaultComboBoxModel carPageFuelModel;
	DefaultComboBoxModel carPageColorModel;
	DefaultComboBoxModel adPageCarModel;
	DefaultComboBoxModel adPageCityModel;
	DefaultComboBoxModel carTableFuelModel;
	DefaultComboBoxModel carTableTransmissionModel;
	DefaultComboBoxModel carTableColorModel;
	DefaultComboBoxModel adTableCityModel;
	DefaultComboBoxModel adTableCarModel;


	JComboBox fuel_comboBox = new JComboBox();
	JComboBox color_comboBox = new JComboBox();
	JComboBox transmission_comboBox = new JComboBox();
	JComboBox adCar_comboBox = new JComboBox();
	JComboBox adCity_comboBox = new JComboBox();
	JComboBox fuel_table_comboBox = new JComboBox();
	JComboBox transmission_table_comboBox = new JComboBox();
	JComboBox color_table_comboBox = new JComboBox();
	JComboBox city_table_comboBox = new JComboBox();
	JComboBox car_table_comboBox = new JComboBox();



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

	private JTable ad_table;
	private JTextField adAd_insert_textField;
	private JTextField adPrice_insert_textField;
	private JTextField adKm_insert_textField;
	private JTextField km_min_insert_textField;
	private JTextField km_max_insert_textField;
	private JTextField price_min_insert_textField;
	private JTextField price_max_insert_textField;

	private JTable filterData_table;
	private JTextField textField;

	public static void main(String[] args) {
		conn = dbc.connectDatabase();
		dbc.createTables(conn);
		// dbc.initTables(conn);
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

		main_menu_tabbed.addTab("Filter data", filter_data);
		filter_data.setLayout(null);

		km_min_insert_textField = new JTextField();
		km_min_insert_textField.setText("0");
		km_min_insert_textField.setBounds(51, 12, 114, 19);
		filter_data.add(km_min_insert_textField);
		km_min_insert_textField.setColumns(10);

		JLabel label = new JLabel("-");
		label.setBounds(167, 14, 70, 15);
		filter_data.add(label);
		km_max_insert_textField = new JTextField();
		km_max_insert_textField.setText("0");
		km_max_insert_textField.setBounds(177, 12, 114, 19);
		filter_data.add(km_max_insert_textField);
		km_max_insert_textField.setColumns(10);

		JLabel lblKm_1 = new JLabel("KM");
		lblKm_1.setBounds(0, 14, 70, 15);
		filter_data.add(lblKm_1);

		JLabel lblPrice_1 = new JLabel("Price");
		lblPrice_1.setBounds(0, 35, 70, 15);
		filter_data.add(lblPrice_1);

		price_min_insert_textField = new JTextField();
		price_min_insert_textField.setText("0");
		price_min_insert_textField.setBounds(51, 33, 114, 19);
		filter_data.add(price_min_insert_textField);
		price_min_insert_textField.setColumns(10);

		JLabel label_1 = new JLabel("-");
		label_1.setBounds(167, 35, 70, 15);
		filter_data.add(label_1);

		price_max_insert_textField = new JTextField();
		price_max_insert_textField.setText("0");
		price_max_insert_textField.setBounds(177, 33, 114, 19);
		filter_data.add(price_max_insert_textField);
		price_max_insert_textField.setColumns(10);

		JScrollPane filter_city_scrollPane = new JScrollPane();
		filter_city_scrollPane.setBounds(1200, 12, 146, 169);
		filter_data.add(filter_city_scrollPane);

		JList cityList = new JList();

		DefaultListModel cityListModel = new DefaultListModel();
		ArrayList<String> cityNames = dbc.getCityNames(conn);
		for (String cityName : cityNames)
			cityListModel.addElement(cityName);
		cityList.setModel(cityListModel);
		filter_city_scrollPane.setViewportView(cityList);

		JScrollPane filter_fuel_scrollPane = new JScrollPane();
		filter_fuel_scrollPane.setBounds(850, 12, 146, 169);
		filter_data.add(filter_fuel_scrollPane);

		JList fuelList = new JList();

		DefaultListModel fuelListModel = new DefaultListModel();
		ArrayList<String> fuelNames = dbc.getFuelTypes(conn);
		for (String fuelName : fuelNames)
			fuelListModel.addElement(fuelName);
		fuelList.setModel(fuelListModel);
		filter_fuel_scrollPane.setViewportView(fuelList);

		JScrollPane filter_carBrand_scrollPane = new JScrollPane();
		filter_carBrand_scrollPane.setBounds(325, 12, 146, 169);
		filter_data.add(filter_carBrand_scrollPane);

		JList carBrandlist = new JList();

		DefaultListModel carBrandModel = new DefaultListModel();
		ArrayList<String> carBrands = dbc.getCarBrands(conn);
		for (String carBrand : carBrands)
			carBrandModel.addElement(carBrand);
		carBrandlist.setModel(carBrandModel);
		filter_carBrand_scrollPane.setViewportView(carBrandlist);

		JScrollPane filter_carColor_scrollPane = new JScrollPane();
		filter_carColor_scrollPane.setBounds(1025, 12, 146, 169);
		filter_data.add(filter_carColor_scrollPane);

		JList carColorlist = new JList();

		DefaultListModel carColorModel = new DefaultListModel();
		ArrayList<String> carColors = dbc.getColors(conn);
		for (String carColor : carColors)
			carColorModel.addElement(carColor);
		carColorlist.setModel(carColorModel);
		filter_carColor_scrollPane.setViewportView(carColorlist);

		JScrollPane transmision_scrollPane = new JScrollPane();
		transmision_scrollPane.setBounds(675, 12, 146, 169);
		filter_data.add(transmision_scrollPane);

		JList transmissionlist = new JList();

		DefaultListModel transmissionModel = new DefaultListModel();
		ArrayList<String> transmissionNames = dbc.getTransmissionTypes(conn);
		for (String transmissionName : transmissionNames)
			transmissionModel.addElement(transmissionName);
		transmissionlist.setModel(transmissionModel);
		transmision_scrollPane.setViewportView(transmissionlist);

		carBrandlist.getSelectedValue();


		/*
		 * carBrandlist.addMouseListener(new MouseAdapter(){ public void
		 * mouseClicked(MouseEvent e) { if (e.getClickCount() ) { //
		 * al.actionPerformed(new ActionEvent(e.getSource(), e.getID(), "ENTER"));
		 * 
		 * } //e.get } });
		 */

		JScrollPane filter_carModel_scrollPane = new JScrollPane();
		filter_carModel_scrollPane.setBounds(500, 12, 146, 169);
		filter_data.add(filter_carModel_scrollPane);

		JList carModellist = new JList();

		DefaultListModel carModelModel = new DefaultListModel();
		ArrayList<String> carModels = dbc.getCarModels(conn);
		for (String carModel : carModels)
			carModelModel.addElement(carModel);
		carModellist.setModel(carModelModel);
		filter_carModel_scrollPane.setViewportView(carModellist);
		
		carBrandlist.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				carModelModel.removeAllElements();
				ArrayList<String> carModels = dbc.getCarModels(conn,carBrandlist.getSelectedValuesList().toArray());
				for (String carModel : carModels)
					carModelModel.addElement(carModel);
			}
		});

		filterData_table = new JTable(filterData_tableModel);
		filterData_table.setEnabled(false);
		

		updateFilterTable("Ilan_Adi");

		JScrollPane filterData_scrollPane = new JScrollPane(filterData_table);
		filterData_scrollPane.setBounds(12, 186, 1400, 390);
		filter_data.add(filterData_scrollPane);

		textField = new JTextField();
		textField.setText("");
		textField.setBounds(51, 64, 240, 19);
		filter_data.add(textField);
		textField.setColumns(10);
		JButton btnFilter = new JButton("Filter");
		   ButtonGroup modelRadioGroup = new ButtonGroup();
	        
			JRadioButton rdbtnToday = new JRadioButton("Today");
			rdbtnToday.setBounds(1400, 12, 149, 23);
			modelRadioGroup.add(rdbtnToday);
			filter_data.add(rdbtnToday);
			
			JRadioButton rdbtnLastWeek = new JRadioButton("Last Week");
			rdbtnLastWeek.setBounds(1400, 36, 149, 23);
			modelRadioGroup.add(rdbtnLastWeek);
			filter_data.add(rdbtnLastWeek);
			
			JRadioButton rdbtnLastMonth = new JRadioButton("Last Month");
			rdbtnLastMonth.setBounds(1400, 60, 149, 23);
			modelRadioGroup.add(rdbtnLastMonth);
			filter_data.add(rdbtnLastMonth);
			
			JRadioButton rdbtnAllTimes = new JRadioButton("All Times");
			rdbtnAllTimes.setBounds(1400, 84, 149, 23);
			modelRadioGroup.add(rdbtnAllTimes);
			filter_data.add(rdbtnAllTimes);
		btnFilter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				filterData_tableModel.setRowCount(0);
				String date = "";
				if(rdbtnLastMonth.isSelected() )
					date = rdbtnLastMonth.getText();
				if(rdbtnLastWeek.isSelected())
					date = rdbtnLastWeek.getText();
				if(rdbtnToday.isSelected())
					date = rdbtnToday.getText();
				if(rdbtnAllTimes.isSelected())
					date = "";

				ArrayList<Object> filterData= dbc.filterData(price_min_insert_textField.getText(), price_max_insert_textField.getText(),
														km_min_insert_textField.getText(), km_max_insert_textField.getText(),
														carBrandlist.getSelectedValuesList().toArray(),
														carModellist.getSelectedValuesList().toArray(),
														transmissionlist.getSelectedValuesList().toArray(),
														fuelList.getSelectedValuesList().toArray(),
														cityList.getSelectedValuesList().toArray(),
														carColorlist.getSelectedValuesList().toArray(),date,textField.getText());
				//updateFilterTable("Ilan_Adi");
				for (Object o : filterData) {
					if (o instanceof Ad) {
						Object[] data = { ((Ad) o).getAdName(), ((Ad) o).getAdCarBrand(), ((Ad) o).getAdCarModel(),
								((Ad) o).getPrice(), ((Ad) o).getKm(), ((Ad) o).getAdCarTransmissionType(),
								((Ad) o).getAdCarFuelType(), ((Ad) o).getAdCarColorName(), ((Ad) o).getDate(),
								((Ad) o).getCityName() };
						filterData_tableModel.addRow(data);
				filterData_scrollPane.setViewportView(filterData_table);
			}
				}
			}
		});
		btnFilter.setBounds(120, 108, 117, 25);
		filter_data.add(btnFilter);
		
		
		filterData_table.getTableHeader().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int col = filterData_table.columnAtPoint(e.getPoint());
				if (col == 0) {
					updateFilterTable("Ilan_Adi");
				}
				if (col == 1) {
					updateFilterTable("Araba_Marka");
				}
				if (col == 2) {
					updateFilterTable("Araba_Model");
				}
				if (col == 3) {
					updateFilterTable("Ilan_Fiyat");
				}
				if (col == 4) {
					updateFilterTable("Ilan_Km");
				}
				if (col == 5) {
					updateFilterTable("VitesTuru");
				}
				if (col == 6) {
					updateFilterTable("YakitTuru");
				}
				if (col == 7) {
					updateFilterTable("Renk");
				}
				if (col == 8) {
					updateFilterTable("Ilan_Tarih");
				}
				if (col == 9) {
					updateFilterTable("Sehir");
				}
			}
		});

		/*
		 * 
		 * UPDATE
		 * 
		 */
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
		ad.setLayout(null);

		/*
		 * adModel_insert_textField = new JTextField();
		 * adModel_insert_textField.setBounds(100, 50, 114, 19);
		 * ad.add(adModel_insert_textField); adModel_insert_textField.setColumns(10);
		 * 
		 * JLabel lblAdModel = new JLabel("Ad Model"); lblCarModel.setBounds(12, 52, 93,
		 * 19); car.add(lblCarModel);
		 */
		ad_table = new JTable(ad_tableModel);
		updateFilterTable("Ilan_Adi");

		JScrollPane ad_scrollPane = new JScrollPane(ad_table);
		ad_scrollPane.setBounds(12, 186, 1400, 390);
		ad.add(ad_scrollPane);
		ad_scrollPane.setViewportView(ad_table);
		ad_table.getColumnModel().getColumn(1).setPreferredWidth(600);
		
		
		adTableCityModel = new DefaultComboBoxModel(dbc.getCityNames(conn).toArray());
		city_table_comboBox.setModel(adTableCityModel);
		  TableColumn ad_CityColumn = ad_table.getColumnModel().getColumn(6);
		  ad_CityColumn.setCellEditor(new DefaultCellEditor(city_table_comboBox)); //Set
		  DefaultTableCellRenderer rendererCity = new
		  DefaultTableCellRenderer(); 
		  rendererCity.setToolTipText("Click for combo box");
		  ad_CityColumn.setCellRenderer(rendererCity);
		  
			adTableCarModel = new DefaultComboBoxModel(dbc.getCarIDs(conn).toArray());
			car_table_comboBox.setModel(adTableCarModel);
			  TableColumn ad_CarColumn = ad_table.getColumnModel().getColumn(5);
			  ad_CarColumn.setCellEditor(new DefaultCellEditor(car_table_comboBox)); //Set
			  DefaultTableCellRenderer rendererCar = new
			  DefaultTableCellRenderer(); 
			  rendererCar.setToolTipText("Click for combo box");
			  ad_CarColumn.setCellRenderer(rendererCar);
		
		ad_table.getModel().addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
					int col = ad_table.getSelectedColumn();
					int row = ad_table.getSelectedRow();
					System.out.println("Fafafaf " + row);

					if (row != -1) {
						
						String value = ad_table.getModel().getValueAt(row, 0).toString();
						System.out.println(value);
						dbc.updateRecord(conn, value, "Tbl_Ilan", row, col, ad_tableModel);
					}

					updateAdTable();
					updateFilterTable("Ilan_Adi");
					ad_scrollPane.setViewportView(ad_table);
				}
			}
		});

		JButton ad_btnSave = new JButton("Save");
		ad_btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String[] datas = { adAd_insert_textField.getText(), adPrice_insert_textField.getText(),
						adKm_insert_textField.getText(), adCity_comboBox.getSelectedItem().toString(),
						adCar_comboBox.getSelectedItem().toString() };

				dbc.insertRecord(conn, "Tbl_Ilan", datas);
				updateAdTable();
				ad_scrollPane.setViewportView(ad_table);
			}
		});
		ad_btnSave.setBounds(139, 124, 117, 25);

		ad.add(ad_btnSave);

		JButton ad_btnDelete = new JButton("Delete");
		ad_btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = ad_table.getSelectedRow();
				System.out.println("Fransa: " + row);
				if (row != -1) {
					String value = ad_table.getModel().getValueAt(row, 0).toString();
					dbc.deleteRecord(conn, value, adTableName);
				}

				updateAdTable();
				ad_scrollPane.setViewportView(ad_table);
			}
		});
		ad_btnDelete.setBounds(314, 622, 117, 25);
		ad.add(ad_btnDelete);

		adAd_insert_textField = new JTextField();
		adAd_insert_textField.setBounds(12, 78, 424, 19);
		ad.add(adAd_insert_textField);
		adAd_insert_textField.setColumns(10);

		JLabel lblAd = new JLabel("AD");
		lblAd.setBounds(193, 51, 70, 15);
		ad.add(lblAd);

		JLabel lblPrice = new JLabel("Price");
		lblPrice.setBounds(517, 51, 70, 15);
		ad.add(lblPrice);

		adPrice_insert_textField = new JTextField();
		adPrice_insert_textField.setBounds(568, 49, 114, 19);
		ad.add(adPrice_insert_textField);
		adPrice_insert_textField.setColumns(10);

		JLabel lblKm = new JLabel("Km");
		lblKm.setBounds(527, 80, 70, 15);
		ad.add(lblKm);

		adKm_insert_textField = new JTextField();
		adKm_insert_textField.setBounds(568, 78, 114, 19);
		ad.add(adKm_insert_textField);
		adKm_insert_textField.setColumns(10);

		JLabel lblCar = new JLabel("Car");
		lblCar.setBounds(517, 106, 70, 15);
		ad.add(lblCar);

		adCar_comboBox.setBounds(504, 126, 58, 21);
		adPageCarModel = new DefaultComboBoxModel(dbc.getCarIDs(conn).toArray());
		adCar_comboBox.setModel(adPageCarModel);
		ad.add(adCar_comboBox);

		JLabel lblCity = new JLabel("City");
		lblCity.setBounds(612, 106, 70, 15);
		ad.add(lblCity);

		adCity_comboBox.setBounds(612, 124, 60, 24);
		adPageCityModel = new DefaultComboBoxModel(dbc.getCityNames(conn).toArray());
		adCity_comboBox.setModel(adPageCityModel);
		ad.add(adCity_comboBox);

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

		carTableFuelModel = new DefaultComboBoxModel(dbc.getFuelTypes(conn).toArray());
		fuel_table_comboBox.setModel(carTableFuelModel);
		  TableColumn car_fuelTypeColumn = car_table.getColumnModel().getColumn(3);
		  car_fuelTypeColumn.setCellEditor(new DefaultCellEditor(fuel_table_comboBox)); //Set
		  DefaultTableCellRenderer renderer = new
		  DefaultTableCellRenderer(); renderer.setToolTipText("Click for combo box");
		  car_fuelTypeColumn.setCellRenderer(renderer);
		  
		  carTableTransmissionModel = new DefaultComboBoxModel(dbc.getTransmissionTypes(conn).toArray());
			transmission_table_comboBox.setModel(carTableTransmissionModel);
			  TableColumn car_TransmissionTypeColumn = car_table.getColumnModel().getColumn(4);
			  car_TransmissionTypeColumn.setCellEditor(new DefaultCellEditor(transmission_table_comboBox)); //Set
			  DefaultTableCellRenderer rendererTr = new
			  DefaultTableCellRenderer(); rendererTr.setToolTipText("Click for combo box");
			  car_TransmissionTypeColumn.setCellRenderer(rendererTr);
			  
			  carTableColorModel = new DefaultComboBoxModel(dbc.getColorNames(conn).toArray());
				color_table_comboBox.setModel(carTableColorModel);
				  TableColumn car_colorColumn = car_table.getColumnModel().getColumn(5);
				  car_colorColumn.setCellEditor(new DefaultCellEditor(color_table_comboBox)); //Set
				  DefaultTableCellRenderer rendererCol = new
				  DefaultTableCellRenderer(); rendererCol.setToolTipText("Click for combo box");
				  car_colorColumn.setCellRenderer(rendererCol);
		 
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
						System.out.println(value);
						dbc.updateRecord(conn, value, "Tbl_Araba", row, col, car_tableModel);
					}

					updateCarTable();
					updateAdTable();
					updateFilterTable("Ilan_Adi");
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
							transmission_comboBox.getSelectedItem().toString(),
							fuel_comboBox.getSelectedItem().toString(), color_comboBox.getSelectedItem().toString() };

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

		car_table.isCellEditable(0,0);
		frame.setVisible(true);
	}

	public void updateFuelTable() {
		fuel_comboBox.removeAllItems();
		fuel_table_comboBox.removeAllItems();
		ArrayList<Object> fuelObjs = dbc.selectAllQuery("Tbl_YakitTuru", conn);
		fuel_tableModel.setRowCount(0);
		for (Object o : fuelObjs) {
			if (o instanceof Fuel) {
				Object[] data = { ((Fuel) o).getFuelID(), ((Fuel) o).getFuelType() };
				fuel_comboBox.addItem(((Fuel) o).getFuelType());
				fuel_table_comboBox.addItem(((Fuel) o).getFuelType());
				fuel_tableModel.addRow(data);
			}
		}
		updateCarTable();
	}

	public void updateCityTable() {
		adCity_comboBox.removeAllItems();
		ArrayList<Object> cityObjs = dbc.selectAllQuery("Tbl_Sehir", conn);
		city_tableModel.setRowCount(0);
		for (Object o : cityObjs) {
			if (o instanceof City) {
				Object[] data = { ((City) o).getCityID(), ((City) o).getCityName() };
				adCity_comboBox.addItem(((City)o).getCityName());
				city_tableModel.addRow(data);
			}
		}
		updateAdTable();
	}

	public void updateColorTable() {
		color_comboBox.removeAllItems();
		color_table_comboBox.removeAllItems();
		color_tableModel.setRowCount(0);
		ArrayList<Object> colorObjs = dbc.selectAllQuery("Tbl_Renk", conn);
		for (Object o : colorObjs) {
			if (o instanceof Color) {
				Object[] data = { ((Color) o).getColorID(), ((Color) o).getColorName() };
				color_comboBox.addItem(((Color) o).getColorName());
				color_table_comboBox.addItem(((Color) o).getColorName());
				color_tableModel.addRow(data);
			}
		}
		updateCarTable();
	}

	public void updateTransmissionTable() {
		transmission_comboBox.removeAllItems();
		transmission_table_comboBox.removeAllItems();
		transmission_tableModel.setRowCount(0);
		ArrayList<Object> transmissionObjs = dbc.selectAllQuery("Tbl_VitesTuru", conn);
		for (Object o : transmissionObjs) {
			if (o instanceof Transmission) {
				Object[] data = { ((Transmission) o).getTransmissionID(), ((Transmission) o).getTransmissionType() };
				transmission_comboBox.addItem(((Transmission) o).getTransmissionType());
				transmission_table_comboBox.addItem(((Transmission) o).getTransmissionType());

				transmission_tableModel.addRow(data);
			}
		}
		updateCarTable();
	}

	public void updateCarTable() {
		car_tableModel.setRowCount(0);
		adCar_comboBox.removeAllItems();
		ArrayList<Object> carObjs = dbc.selectAllQuery("Tbl_Araba", conn);
		for (Object o : carObjs) {
			if (o instanceof Car) {
				Object[] data = { ((Car) o).getCarID(), ((Car) o).getCarBrand(), ((Car) o).getCarModel(),
						((Car) o).getFuelType(), ((Car) o).getTransmissionType(), ((Car) o).getColorName() };
				
				adCar_comboBox.addItem(((Car) o).getCarID());

				car_tableModel.addRow(data);
			}
		}
		updateAdTable();
	}

	public void updateAdTable() {
		ad_tableModel.setRowCount(0);
		ArrayList<Object> adObjs = dbc.selectAllQuery("Tbl_Ilan", conn);
		for (Object o : adObjs) {
			if (o instanceof Ad) {
				Object[] data = { ((Ad) o).getAdID(), ((Ad) o).getAdName(), ((Ad) o).getPrice(), ((Ad) o).getKm(),
						((Ad) o).getDate(), ((Ad) o).getCarID(), ((Ad) o).getCityName() };
				ad_tableModel.addRow(data);
			}
		}
	}

	public void updateFilterTable() {
		filterData_tableModel.setRowCount(0);
		ArrayList<Object> adObjs = dbc.selectAllQuery("Tbl_Ilan", conn);
		for (Object o : adObjs) {
			if (o instanceof Ad) {
				Object[] data = { ((Ad) o).getAdName(), ((Ad) o).getAdCarBrand(), ((Ad) o).getAdCarModel(),
						((Ad) o).getPrice(), ((Ad) o).getKm(), ((Ad) o).getAdCarTransmissionType(),
						((Ad) o).getAdCarFuelType(), ((Ad) o).getDate(), ((Ad) o).getCityName() };
				filterData_tableModel.addRow(data);
			}
		}
	}

	public void updateFilterTable(String orderBy) {
		filterData_tableModel.setRowCount(0);
		ArrayList<Object> adObjs = dbc.selectAllQuery("Tbl_Ilan", conn, orderBy);
		for (Object o : adObjs) {
			if (o instanceof Ad) {
				Object[] data = { ((Ad) o).getAdName(), ((Ad) o).getAdCarBrand(), ((Ad) o).getAdCarModel(),
						((Ad) o).getPrice(), ((Ad) o).getKm(), ((Ad) o).getAdCarTransmissionType(),
						((Ad) o).getAdCarFuelType(), ((Ad) o).getAdCarColorName(), ((Ad) o).getDate(),
						((Ad) o).getCityName() };
				filterData_tableModel.addRow(data);
			}
		}
	}
}
