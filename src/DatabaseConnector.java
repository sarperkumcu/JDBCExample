import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Driver;
public class DatabaseConnector {

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

        Connection conn = null;
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
        System.out.println("Goodbye!");
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
				+ "Ilan_ArabaID VARCHAR(10),"
				+ "Ilan_SehirID VARCHAR(5),"
				+ "FOREIGN KEY (Ilan_ArabaID) REFERENCES Tbl_Araba(ArabaID),"
				+ "FOREIGN KEY (Ilan_SehirID) REFERENCES Tbl_Sehir(SehirID),"
				+ "PRIMARY KEY (IlanID) "
				+ ")";
    	
    	String createTbl_Araba = 
    			"CREATE TABLE IF NOT EXISTS Tbl_Araba("
				+ "ArabaID VARCHAR(10) NOT NULL, "
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
				+ "YakitTuruID INT NOT NULL, "
				+ "YakitTuru VARCHAR(20) NOT NULL, "
				+ "PRIMARY KEY (YakitTuruID) "
				+ ")";
    	
     	String createTbl_VitesTuru = 
    			"CREATE TABLE IF NOT EXISTS Tbl_VitesTuru("
				+ "VitesTuruID INT NOT NULL, "
				+ "VitesTuru VARCHAR(20) NOT NULL, "
				+ "PRIMARY KEY (VitesTuruID) "
				+ ")";
    	
    	String createTbl_Renk =
    			"CREATE TABLE IF NOT EXISTS Tbl_Renk("
    			+ "RenkID INT NOT NULL,"
    			+ "Renk VARCHAR(10) NOT NULL,"
    			+ "PRIMARY KEY (RenkID) "
				+ ")";
    	String createTbl_Sehir =
    			"CREATE TABLE IF NOT EXISTS Tbl_Sehir("
    			+ "SehirID VARCHAR(5) NOT NULL,"
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
    		+	"(YakitTuruID, YakitTuru)"
    		+	"VALUES"
    		+	"(0,'Diesel'),"
    		+	"(1,'Gasoline'),"
    		+	"(2,'LPG'),"
    		+	"(3,'Electric'),"
    		+	"(4,'Faa')";
     	String initVitesTuru = 
     			"INSERT IGNORE INTO Tbl_VitesTuru" 
     		+	"(VitesTuruID, VitesTuru)"
     		+	"VALUES"
     		+	"(0,'Manual'),"
     		+	"(1,'Automatic')";
    	
    	String initRenk =
    			"INSERT IGNORE INTO Tbl_Renk" 
    		+	"(RenkID, Renk)"
    		+	"VALUES"
      		+	"(0,'Black'),"
    		+	"(1,'White'),"
    		+	"(2,'Red'),"
    		+	"(3,'Blue'),"
    		+	"(4,'Green'),"
    		+	"(5,'Yellow'),"
    		+	"(6,'Orange'),"
    		+	"(7,'Purple'),"
    		+	"(8,'Navy Blue'),"
    		+	"(9,'Gray'),"
    		+	"(10,'Pink'),"
    		+	"(11,'Turkuaz'),"
    		+	"(12,'Fuchsia')";


    	String initSehir =
    			"INSERT IGNORE INTO Tbl_Renk" 
    		    +	"(RenkID, Renk)"
    		    +	"VALUES"
    		    +	"(1,'Adana'),"
    		    +	"(2,'Adiyaman'),"
    		    +	"(3,'Afyon'),"
    		    +	"(4,'Agri'),"
    		    +	"(5,'Amasya'),"
    		    +	"(6,'Ankara'),"
    		    +	"(7,'Antalya'),"
    		    +	"(8,'Artvin'),"
    		    +	"(9,'Aydin'),"
    		    +	"(10,'Balikesir'),"
    		    +	"(11,'Bilecik'),"
    		    +	"(12,'Bingol')";
    	
    	
    	try {
			statement = connection.createStatement();
			statement.executeUpdate(initYakitTuru);
			statement.executeUpdate(initVitesTuru);
			statement.executeUpdate(initRenk);
		
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
    public void selectQuery(String tableName,Connection conn) {
    	String query;
    	query = "SELECT * FROM "+ tableName;
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, 1001);

			// execute select SQL stetement
			ResultSet rs = preparedStatement.executeQuery();

			
				if(tableName == "Tbl_Renk") {
					ArrayList<Color> colors = new ArrayList<>();
					while (rs.next()) {
						Color color = new Color();
						Integer renkID = rs.getInt("RenkID");
						String renkName = rs.getString("Renk");
						color.setColorID(renkID);
						color.setColorName(renkName);
						colors.add(color);
						
					}
				}
				
				if(tableName == "Tbl_VitesTuru") {
					ArrayList<Transmission> transmissions = new ArrayList<>();
					while (rs.next()) {
						Transmission transmission = new Transmission();
						Integer transmissionID = rs.getInt("VitesTuruID");
						String transmissionType = rs.getString("VitesTuru");
						transmission.setTransmissionID(transmissionID);
						transmission.setTransmissionType(transmissionType);
						transmissions.add(transmission);
						
					}
				}
				
				if(tableName == "Tbl_Sehir") {
					ArrayList<City> cities= new ArrayList<>();
					while (rs.next()) {
						City city = new City();
						Integer cityID = rs.getInt("SehirID");
						String cityName= rs.getString("Sehir");
						city.setCityID(cityID);
						city.setCityName(cityName);
						cities.add(city);
						
					}
				}
				
				if(tableName == "Tbl_YakitTuru") {
					ArrayList<Fuel> fuels= new ArrayList<>();
					while (rs.next()) {
						Fuel fuel= new Fuel();
						Integer fuelID = rs.getInt("YakitTuruID");
						String fuelType= rs.getString("YakitTuruID");
						fuel.setFuelID(fuelID);
						fuel.setFuelType(fuelType);
						fuels.add(fuel);
						
					}
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

	}
    }