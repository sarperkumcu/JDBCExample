import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class MainController {

	private JFrame frame;
	private JTextField txtFuelType;
	private JTextField fuel_insert_textField;
	private final String fuelTableName = "Tbl_YakitTuru";
	private final String transmissionTableName = "Tbl_VitesTuru";
	private final String cityTableName = "Tbl_Sehir";
	private final String adTableName = "Tbl_Ilan";
	private final String carTableName = "Tbl_Araba";
	private final String colorTableName = "Tbl_Renk";


	/**
	 * Launch the application.
	 */
	static DatabaseConnector dbc = new DatabaseConnector();
	static Connection conn;
	private JTable fuel_table;
	public static void main(String[] args) {
		 conn = dbc.connectDatabase();
		dbc.createTables(conn);
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
		  Dimension min = new Dimension(750,750);
		    frame.setMinimumSize(min);
		    frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		    Toolkit toolkit = Toolkit.getDefaultToolkit();
		    Dimension max = toolkit.getScreenSize();
		    frame.setMaximumSize(max);
		//frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		JPanel main_menu = new JPanel();
		frame.getContentPane().add(main_menu, "name_14749222077628");
		main_menu.setLayout(new CardLayout(0, 0));
		
		JTabbedPane main_menu_tabbed = new JTabbedPane(JTabbedPane.TOP);
		main_menu.add(main_menu_tabbed, "name_15026400323007");
		JPanel update_data = new JPanel();
		//frame.getContentPane().add(update_data, "name_14889183967799");
		update_data.setLayout(new CardLayout(0, 0));

		JTabbedPane update_menu_tabbed = new JTabbedPane(JTabbedPane.TOP);
		update_data.add(update_menu_tabbed, "name_15032863038707");
		
		JPanel filter_data = new JPanel();
		//frame.getContentPane().add(filter_data, "name_14894919628649");
		filter_data.setLayout(new CardLayout(0, 0));
		
		
		main_menu_tabbed.addTab("Filter data", filter_data);
		main_menu_tabbed.addTab("Update data", update_data);
		//update_data.add(fuel, "name_15525985634051");
		
		JPanel color = new JPanel();
		update_menu_tabbed.addTab("Color", color);
		
		JPanel fuel = new JPanel();
		//update_data.add(color, "name_15544891609718");

		update_menu_tabbed.addTab("Fuel", fuel);
		fuel.setLayout(null);
		
		fuel_insert_textField = new JTextField();
		fuel_insert_textField.setBounds(100, 50, 114, 19);
		fuel.add(fuel_insert_textField);
		fuel_insert_textField.setColumns(10);
		
		JLabel lblTypeName = new JLabel("Type Name");
		lblTypeName.setBounds(12, 52, 93, 19);
		fuel.add(lblTypeName);
		
		JButton fuel_btnSave = new JButton("Save");
		fuel_btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(!fuel_insert_textField.getText().trim().isEmpty()) {
					dbc.insertRecord(conn,fuel_insert_textField.getText());
				}
			}
		});
		fuel_btnSave.setBounds(56, 106, 117, 25);
		fuel.add(fuel_btnSave);
		String col[] = {"ID","Fuel Type"};
		DefaultTableModel tableModel = new DefaultTableModel(col, 0);
		Object[] objs = {1, "Arsenal"};
		tableModel.addRow(objs);
				
				JButton fuel_btnDelete = new JButton("Delete");
				fuel_btnDelete.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						int row = fuel_table.getSelectedRow();
						String value = fuel_table.getModel().getValueAt(row, 0).toString();
						dbc.deleteRecord(conn,value,fuelTableName);
					}
				});
				fuel_btnDelete.setBounds(588, 493, 117, 25);
				fuel.add(fuel_btnDelete);
				
				JScrollPane fuel_scrollPane = new JScrollPane(fuel_table);
				fuel_scrollPane.setBounds(28, 303, 421, 290);
				fuel.add(fuel_scrollPane);
				
						fuel_table = new JTable(tableModel);
						fuel_scrollPane.setViewportView(fuel_table);
		
		frame.setVisible(true);
	}
}
