import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import java.sql.Driver;
public class DatabaseConnector {
    Connection conn = null;

    public Connection connectDatabase(){
    // JDBC driver name and database URL
        System.out.println("Trying to connect");
    final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    final String DB_URL = "jdbc:mysql://localhost:3306/Cars"
           // + "?verifyServerCertificate=true"
            + "?useSSL=false&useUnicode=yes&characterEncoding=UTF-8";
            //+ "&requireSSL=true";

    //  Database credentials
    final String USER = "root";
     final String PASS = "sarper";

        //Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            Statement stmt = (Statement) conn.createStatement();
            stmt.executeQuery("SET NAMES 'UTF8'");
            stmt.executeQuery("SET CHARACTER SET 'UTF8'");
            stmt.close();

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } 
        return conn;
}
    public void createTables(Connection connection) {
    	
		Statement statement = null;
    	
    	String createTbl_Ilan = 
    			"CREATE TABLE IF NOT EXISTS Tbl_Ilan("
				+ "IlanID int NOT NULL AUTO_INCREMENT, "
				+ "Ilan_Adi VARCHAR(20) NOT NULL, "
				+ "Ilan_Fiyat INT NOT NULL, "
				+ "Ilan_Km INT NOT NULL,"
				+ "Ilan_Tarih DATE NOT NULL,"
				+ "Ilan_ArabaID INT,"
				+ "Ilan_SehirID INT,"
				+ "FOREIGN KEY (Ilan_ArabaID) REFERENCES Tbl_Araba(ArabaID),"
				+ "FOREIGN KEY (Ilan_SehirID) REFERENCES Tbl_Sehir(SehirID),"
				+ "PRIMARY KEY (IlanID) "
				+ ")";
    	
    	String createTbl_Araba = 
    			"CREATE TABLE IF NOT EXISTS Tbl_Araba("
				+ "ArabaID INT NOT NULL AUTO_INCREMENT, "
				+ "Araba_Marka VARCHAR(20) NOT NULL, "
				+ "Araba_Model VARCHAR(20) NOT NULL, "
				+ "Araba_VitesTuruID INT,"
				+ "Araba_YakitTuruID INT,"
				+ "Araba_RenkID INT,"
				+ "FOREIGN KEY (Araba_VitesTuruID) REFERENCES Tbl_VitesTuru(VitesTuruID),"
				+ "FOREIGN KEY (Araba_YakitTuruID) REFERENCES Tbl_YakitTuru(YakitTuruID),"
				+ "FOREIGN KEY (Araba_RenkID) REFERENCES Tbl_Renk(RenkID),"
				+ "PRIMARY KEY (ArabaID) "
				+ ")";
    	
    	String createTbl_YakitTuru = 
    			"CREATE TABLE IF NOT EXISTS Tbl_YakitTuru("
				+ "YakitTuruID INT NOT NULL AUTO_INCREMENT, "
				+ "YakitTuru VARCHAR(20) NOT NULL, "
				+ "PRIMARY KEY (YakitTuruID) "
				+ ")";
    	
     	String createTbl_VitesTuru = 
    			"CREATE TABLE IF NOT EXISTS Tbl_VitesTuru("
				+ "VitesTuruID INT NOT NULL AUTO_INCREMENT, "
				+ "VitesTuru VARCHAR(20) NOT NULL, "
				+ "PRIMARY KEY (VitesTuruID) "
				+ ")";
    	
    	String createTbl_Renk =
    			"CREATE TABLE IF NOT EXISTS Tbl_Renk("
    			+ "RenkID INT NOT NULL AUTO_INCREMENT,"
    			+ "Renk VARCHAR(10) NOT NULL,"
    			+ "PRIMARY KEY (RenkID) "
				+ ")";
    	String createTbl_Sehir =
    			"CREATE TABLE IF NOT EXISTS Tbl_Sehir("
    			+ "SehirID INT NOT NULL AUTO_INCREMENT,"
    			+ "Sehir VARCHAR(12) NOT NULL,"
    			+ "PRIMARY KEY (SehirID) "
				+ ")";
    	
    	
    	try {
			statement = connection.createStatement();
			statement.execute(createTbl_YakitTuru);
			statement.execute(createTbl_Renk);
			statement.execute(createTbl_VitesTuru);
			statement.execute(createTbl_Sehir);
			statement.execute(createTbl_Araba);
			statement.execute(createTbl_Ilan);
		
			System.out.println("DONE!");
			
		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
    
    }
    public void initTables(Connection connection) {
		Statement statement = null;
    	
    	String initYakitTuru = 
    			"INSERT IGNORE INTO Tbl_YakitTuru" 
    		+	"(YakitTuru)"
    		+	"VALUES"
    		+	"('Diesel'),"
    		+	"('Gasoline'),"
    		+	"('LPG'),"
    		+	"('Electric')";
     	String initVitesTuru = 
     			"INSERT IGNORE INTO Tbl_VitesTuru" 
     		+	"(VitesTuru)"
     		+	"VALUES"
     		+	"('Manual'),"
     		+	"('Half Automatic'),"
     		+	"('Automatic')";
    	
    	String initRenk =
    			"INSERT IGNORE INTO Tbl_Renk" 
    		+	"( Renk)"
    		+	"VALUES"
      		+	"('Black'),"
    		+	"('White'),"
    		+	"('Red'),"
    		+	"('Blue'),"
    		+	"('Green'),"
    		+	"('Yellow'),"
    		+	"('Orange'),"
    		+	"('Purple'),"
    		+	"('Navy Blue'),"
    		+	"('Gray'),"
    		+	"('Pink'),"
    		+	"('Turkuaz'),"
    		+	"('Fuchsia')";


    	String initSehir =
    			"INSERT IGNORE INTO Tbl_Sehir" 
    		    +	"(Sehir)"
    		    +	"VALUES"
    		    +	"('Adana'),"
    		    +	"('Adiyaman'),"
    		    +	"('Afyon'),"
    		    +	"('Agri'),"
    		    +	"('Amasya'),"
    		    +	"('Ankara'),"
    		    +	"('Antalya'),"
    		    +	"('Artvin'),"
    		    +	"('Aydin'),"
    		    +	"('Balikesir'),"
    		    +	"('Bilecik'),"
    		    +	"('Bingol')";
    	String initAraba =
    			"INSERT INTO Tbl_Araba" 
    		    +	"(Araba_Marka,Araba_Model,Araba_VitesTuruID,Araba_YakitTuruID,Araba_RenkID)"
    		    +	"VALUES"
    		    +	"('Mercedes - Benz','C 200 D BlueTEC',(SELECT VitesTuruID from Tbl_VitesTuru WHERE VitesTuru = 'Half Automatic'),(SELECT YakitTuruID from Tbl_YakitTuru WHERE YakitTuru = 'Diesel'),(SELECT RenkID from Tbl_Renk WHERE Renk = 'Black')),"
    		    +	"('Mercedes - Benz','E 400 CDI Avantgarde',(SELECT VitesTuruID from Tbl_VitesTuru WHERE VitesTuru = 'Automatic'),(SELECT YakitTuruID from Tbl_YakitTuru WHERE YakitTuru = 'Gasoline'),(SELECT RenkID from Tbl_Renk WHERE Renk = 'Blue')),"
    		    +	"('Mercedes - Benz','C180 Exclusivea',(SELECT VitesTuruID from Tbl_VitesTuru WHERE VitesTuru = 'Manual'),(SELECT YakitTuruID from Tbl_YakitTuru WHERE YakitTuru = 'Gasoline'),(SELECT RenkID from Tbl_Renk WHERE Renk = 'Red')),"
    		    +	"('Mercedes - Benz','C 200d AMG',(SELECT VitesTuruID from Tbl_VitesTuru WHERE VitesTuru = 'Automatic'),(SELECT YakitTuruID from Tbl_YakitTuru WHERE YakitTuru = 'LPG'),(SELECT RenkID from Tbl_Renk WHERE Renk = 'Red'))";
    	   	
    	
    	try {
			statement = connection.createStatement();
			statement.executeUpdate(initYakitTuru);
			statement.executeUpdate(initVitesTuru);
			statement.executeUpdate(initRenk);
			statement.executeUpdate(initSehir);
			statement.executeUpdate(initAraba);
		
			System.out.println("All Fine.");
			
		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
    }
    public ArrayList<Object> selectAllQuery(String tableName,Connection conn) {
    	String query;
    	query = "SELECT * FROM "+ tableName + "";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement(query);
			//preparedStatement.setInt(1, 1001);

			// execute select SQL stetement
			ResultSet rs = preparedStatement.executeQuery();

			
				if(tableName.equals("Tbl_Renk")) {
					ArrayList<Object> colors = new ArrayList<>();
					while (rs.next()) {
						Color color = new Color();
						Integer renkID = rs.getInt("RenkID");
						String renkName = rs.getString("Renk");
						color.setColorID(renkID);
						color.setColorName(renkName);
						colors.add(color);
					}
					return colors;
				}
				
				if(tableName.equals("Tbl_VitesTuru")) {
					ArrayList<Object> transmissions = new ArrayList<>();
					while (rs.next()) {
						Transmission transmission = new Transmission();
						Integer transmissionID = rs.getInt("VitesTuruID");
						String transmissionType = rs.getString("VitesTuru");
						transmission.setTransmissionID(transmissionID);
						transmission.setTransmissionType(transmissionType);
						transmissions.add(transmission);
					}
					return transmissions;

				}
				
				if(tableName.equals("Tbl_Sehir")) {
					ArrayList<Object> cities= new ArrayList<>();
					while (rs.next()) {
						City city = new City();
						Integer cityID = rs.getInt("SehirID");
						String cityName= rs.getString("Sehir");
						city.setCityID(cityID);
						city.setCityName(cityName);
						cities.add(city);
					}
					return cities;

				}
				
				if(tableName.equals("Tbl_YakitTuru")) {
					ArrayList<Object> fuels= new ArrayList<>();
					while (rs.next()) {
						Fuel fuel= new Fuel();
						Integer fuelID = rs.getInt("YakitTuruID");
						String fuelType= rs.getString("YakitTuru");
						fuel.setFuelID(fuelID);
						fuel.setFuelType(fuelType);
						fuels.add(fuel);
					}
					return fuels;

				}
				if(tableName.equals("Tbl_Araba")) {
					ArrayList<Object> cars= new ArrayList<>();
					while (rs.next()) {
						Car car= new Car();
						Integer carID = rs.getInt("ArabaID");
						String carBrand= rs.getString("Araba_Marka");
						String carModel= rs.getString("Araba_Model");
						Integer transmissionTypeID= rs.getInt("Araba_VitesTuruID");
						Integer fuelTypeID= rs.getInt("Araba_YakitTuruID");
						Integer colorID = rs.getInt("Araba_RenkID");
						String transmissionType = selectCertainData(conn, "Tbl_VitesTuru", "VitesTuru", "VitesTuruID", transmissionTypeID);
						String fuelType = selectCertainData(conn, "Tbl_YakitTuru", "YakitTuru", "YakitTuruID", fuelTypeID);
						String colorName = selectCertainData(conn, "Tbl_Renk", "Renk", "RenkID", colorID);

						car.setCarID(carID);
						car.setCarBrand(carBrand);
						car.setCarModel(carModel);
						car.setTransmissionTypeID(transmissionTypeID);
						car.setFuelTypeID(fuelTypeID);
						car.setColorID(colorID);
						car.setTransmissionType(transmissionType);
						car.setFuelType(fuelType);
						car.setColorName(colorName);

						cars.add(car);
					}
					return cars;

				}
				
				

			

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		
		return null;

	}
    
    public void insertRecord(Connection connection,String tableName,String data) {
    	Statement statement = null;
    	String insertTableSQL = "sa";
    	if(tableName.equals("Tbl_YakitTuru")) {
    		insertTableSQL = "INSERT INTO Tbl_YakitTuru"
				+ "(YakitTuru) " 
    			+ "VALUES"
				+ "('"+ data + "')";
    	}
    	
    	if(tableName.equals("Tbl_Renk")) {
    		insertTableSQL = "INSERT INTO Tbl_Renk"
				+ "(Renk) " 
    			+ "VALUES"
				+ "('"+ data + "')";
    	}
    	if(tableName.equals("Tbl_Sehir")) {
    		insertTableSQL = "INSERT INTO Tbl_Sehir"
				+ "(Sehir) " 
    			+ "VALUES"
				+ "('"+ data + "')";
    	}
    	
    	if(tableName.equals("Tbl_VitesTuru")) {
    		insertTableSQL = "INSERT INTO Tbl_VitesTuru"
				+ "(VitesTUru) " 
    			+ "VALUES"
				+ "('"+ data + "')";
    	}

		try {
			statement = connection.createStatement();

			System.out.println(insertTableSQL);

			statement.executeUpdate(insertTableSQL);

			System.out.println("Record was inserted into table.");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
    }
    

    public void insertRecord(Connection connection,String tableName,String[] datas) {
    	Statement statement = null;
    	String insertTableSQL = "sa";
    	if(tableName.equals("Tbl_Araba")) {
    		insertTableSQL = "INSERT INTO Tbl_Araba"
				+ "(Araba_Marka,Araba_Model,Araba_VitesTuruID,Araba_YakitTuruID,Araba_RenkID)"
    			+ "VALUES"
				+ "('"+ datas[0] + "','" + datas[1] + "',(SELECT VitesTuruID from Tbl_VitesTuru WHERE VitesTuru = '" + datas[2] + "'),(SELECT YakitTuruID from Tbl_YakitTuru WHERE YakitTuru = '" + datas[3] + "'),(SELECT RenkID from Tbl_Renk WHERE Renk = '" + datas[4] + "'))";
    	}
    	

		try {
			statement = connection.createStatement();

			System.out.println(insertTableSQL);

			statement.executeUpdate(insertTableSQL);

			System.out.println("Record was inserted into table.");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
    }
    
    public void deleteRecord(Connection connection,String ID,String table) {
    	Statement statement = null;
    	String deleteTableSQL = ".";
    	if(table.equals("Tbl_YakitTuru")) {
    		deleteTableSQL =  "DELETE FROM "  + table + " WHERE YakitTuruID = " + Integer.parseInt(ID);
    	}
    	if(table.equals("Tbl_VitesTuru")) {
    		deleteTableSQL =  "DELETE FROM "  + table + " WHERE VitesTuruID = " + Integer.parseInt(ID);
    	}
    	if(table.equals("Tbl_Sehir")) {
    		deleteTableSQL =  "DELETE FROM "  + table + " WHERE SehirID = " + Integer.parseInt(ID);
    	}
    	if(table.equals("Tbl_Renk")) {
    		deleteTableSQL =  "DELETE FROM "  + table + " WHERE RenkID = " + Integer.parseInt(ID);
    	}
    	if(table.equals("Tbl_Araba")) {
    		deleteTableSQL =  "DELETE FROM "  + table + " WHERE ArabaID = " + Integer.parseInt(ID);
    	}
    	if(table.equals("Tbl_Ilan")) {
    		deleteTableSQL =  "DELETE FROM "  + table + " WHERE IlanID = " + Integer.parseInt(ID);
    	}

		try {
			statement = connection.createStatement();

			System.out.println(deleteTableSQL);

			// execute delete SQL stetement
			statement.execute(deleteTableSQL);

			System.out.println("Record was deleted table!");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

    }
    
    public void updateRecord(Connection connection, String id, String tableName,Integer row, DefaultTableModel dtm) {
    	if(tableName.equals("Tbl_YakitTuru")) {
    			try {
            PreparedStatement ps = connection.prepareStatement("UPDATE "+ tableName +" SET YakitTuru = ? WHERE YakitTuruID = "+id);
    
             ps.setString(1, (String) dtm.getValueAt(row, 1));
   
             ps.executeUpdate(); 
             ps.close();
   
         } catch (Exception ex) {
             System.out.println("An error occured");
         }
    	}
    	
       	if(tableName.equals("Tbl_Renk")) {
			try {
        PreparedStatement ps = connection.prepareStatement("UPDATE "+ tableName +" SET Renk = ? WHERE RenkID = "+id);
         ps.setString(1, (String) dtm.getValueAt(row, 1));

         ps.executeUpdate(); 
         ps.close();

     } catch (Exception ex) {
       System.out.println("An error occured");
     }
	}
       	
      	if(tableName.equals("Tbl_Sehir")) {
    			try {
            PreparedStatement ps = connection.prepareStatement("UPDATE "+ tableName +" SET Sehir = ? WHERE SehirID = "+id);
             ps.setString(1, (String) dtm.getValueAt(row, 1));

             ps.executeUpdate(); 
             ps.close();

         } catch (Exception ex) {
           System.out.println("An error occured");
         }
    	}
      	
    	if(tableName.equals("Tbl_VitesTuru")) {
			try {
        PreparedStatement ps = connection.prepareStatement("UPDATE "+ tableName +" SET VitesTuru = ? WHERE VitesTuruID = "+id);
         ps.setString(1, (String) dtm.getValueAt(row, 1));

         ps.executeUpdate(); 
         ps.close();

     } catch (Exception ex) {
       System.out.println("An error occured");
     }
	}
    
    }
    
    public ArrayList<String> getColorNames(Connection connection){
    	ArrayList<String> colorNames = new ArrayList<String>();
    	String query = "SELECT Renk FROM Tbl_Renk ORDER BY Renk"; 
    	PreparedStatement stm;
    	ResultSet rs = null;
		try {
			stm = connection.prepareStatement(query);
		

    	 rs = stm.executeQuery(query); 

    	while (rs.next()) { 
    	    String colorName = rs.getString("Renk"); 
    	    // add group names to the array list
    	    colorNames.add(colorName);
    	} 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			    	try {
						rs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 

		}

    	
    	
    	return colorNames;
    }
    
    public ArrayList<String> getTransmissionTypes(Connection connection){
    	ArrayList<String> transmissionTypes = new ArrayList<String>();
    	String query = "SELECT VitesTuru FROM Tbl_VitesTuru ORDER BY VitesTuru"; 
    	PreparedStatement stm;
    	ResultSet rs = null;
		try {
			stm = connection.prepareStatement(query);
		

    	 rs = stm.executeQuery(query); 

    	while (rs.next()) { 
    	    String transmissionType = rs.getString("VitesTuru"); 
    	    // add group names to the array list
    	    transmissionTypes.add(transmissionType);
    	} 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			    	try {
						rs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 

		}

    	
    	
    	return transmissionTypes;
    }
    
    
    public ArrayList<String> getFuelTypes(Connection connection){
    	ArrayList<String> fuelTypes = new ArrayList<String>();
    	String query = "SELECT YakitTuru FROM Tbl_YakitTuru ORDER BY YakitTuru"; 
    	PreparedStatement stm;
    	ResultSet rs = null;
		try {
			stm = connection.prepareStatement(query);
		

    	 rs = stm.executeQuery(query); 

    	while (rs.next()) { 
    	    String fuelType = rs.getString("YakitTuru"); 
    	    // add group names to the array list
    	    fuelTypes.add(fuelType);
    	} 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			    	try {
						rs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 

		}

    	
    	
    	return fuelTypes;
    }
    
    
    public String selectCertainData(Connection connection, String tableName, String dataName, String IDName, Integer ID) {
    	String data = "problem:)";
    	String query = "SELECT " + dataName + " FROM " + tableName + " WHERE " + IDName + " = " + ID; 
    	PreparedStatement stm;
    	ResultSet rs = null;
		try {
			stm = connection.prepareStatement(query);
		

    	 rs = stm.executeQuery(query); 

    	while (rs.next()) { 
    	    data= rs.getString(dataName); 
    	    // add group names to the array list
    	} 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			    	try {
						rs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 

		}

    	
    	
    	return data;
    }
    
    }