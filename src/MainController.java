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

public class MainController {

	private JFrame frame;
	private JTextField txtFuelType;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		DatabaseConnector dbc = new DatabaseConnector();
		Connection conn = dbc.connectDatabase();
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
		
		frame.setVisible(true);
	}
}
