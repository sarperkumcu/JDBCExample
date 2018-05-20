import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.table.DefaultTableModel;

import java.sql.Driver;


public class DatabaseConnector {
	static boolean ascDesc = true;
    Connection conn = null;
	static String filteredDataQuery = "SELECT * FROM Tbl_Ilan AS ilan " 
			+ " INNER JOIN Tbl_Araba AS araba ON araba.ArabaID = ilan.Ilan_ArabaID "
			+ "INNER JOIN Tbl_VitesTuru AS vites ON vites.VitesTuruID = araba.Araba_VitesTuruID "
			+ "INNER JOIN Tbl_YakitTuru AS yakit ON yakit.YakitTuruID = araba.Araba_YakitTuruID "
			+ "INNER JOIN Tbl_Sehir AS sehir ON sehir.SehirID = ilan.Ilan_SehirID "
			+ "INNER JOIN Tbl_Renk AS renk ON renk.RenkID = araba.Araba_RenkID ";
			
	static String whereClause ="";
	static String orderBy = "";

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
				+ "Ilan_Adi VARCHAR(100) NOT NULL, "
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
				+ "Araba_Model VARCHAR(50) NOT NULL, "
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
    		+	"(Renk)"
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
    		    +	"('Mercedes - Benz','C',(SELECT VitesTuruID from Tbl_VitesTuru WHERE VitesTuru = 'Half Automatic'),(SELECT YakitTuruID from Tbl_YakitTuru WHERE YakitTuru = 'Diesel'),(SELECT RenkID from Tbl_Renk WHERE Renk = 'Black')),"
    		    +	"('Mercedes - Benz','E',(SELECT VitesTuruID from Tbl_VitesTuru WHERE VitesTuru = 'Automatic'),(SELECT YakitTuruID from Tbl_YakitTuru WHERE YakitTuru = 'Gasoline'),(SELECT RenkID from Tbl_Renk WHERE Renk = 'Blue')),"
    		    +	"('Mercedes - Benz','C',(SELECT VitesTuruID from Tbl_VitesTuru WHERE VitesTuru = 'Manual'),(SELECT YakitTuruID from Tbl_YakitTuru WHERE YakitTuru = 'Gasoline'),(SELECT RenkID from Tbl_Renk WHERE Renk = 'Red')),"
    		    +	"('Mercedes - Benz','C',(SELECT VitesTuruID from Tbl_VitesTuru WHERE VitesTuru = 'Automatic'),(SELECT YakitTuruID from Tbl_YakitTuru WHERE YakitTuru = 'LPG'),(SELECT RenkID from Tbl_Renk WHERE Renk = 'Red')),"
    	   		+	"('Saab','9-3',(SELECT VitesTuruID from Tbl_VitesTuru WHERE VitesTuru = 'Automatic'),(SELECT YakitTuruID from Tbl_YakitTuru WHERE YakitTuru = 'Gasoline'),(SELECT RenkID from Tbl_Renk WHERE Renk = 'Black')),"
    			+	"('Audi','A4',(SELECT VitesTuruID from Tbl_VitesTuru WHERE VitesTuru = 'Half Automatic'),(SELECT YakitTuruID from Tbl_YakitTuru WHERE YakitTuru = 'Diesel'),(SELECT RenkID from Tbl_Renk WHERE Renk = 'Black')),"
				+	"('Honda','Jazz',(SELECT VitesTuruID from Tbl_VitesTuru WHERE VitesTuru = 'Half Automatic'),(SELECT YakitTuruID from Tbl_YakitTuru WHERE YakitTuru = 'Gasoline'),(SELECT RenkID from Tbl_Renk WHERE Renk = 'Black')),"
				+	"('Volkswagen','Passat',(SELECT VitesTuruID from Tbl_VitesTuru WHERE VitesTuru = 'Half Automatic'),(SELECT YakitTuruID from Tbl_YakitTuru WHERE YakitTuru = 'Diesel'),(SELECT RenkID from Tbl_Renk WHERE Renk = 'White')),"
				+	"('Audi','A7',(SELECT VitesTuruID from Tbl_VitesTuru WHERE VitesTuru = 'Half Automatic'),(SELECT YakitTuruID from Tbl_YakitTuru WHERE YakitTuru = 'Diesel'),(SELECT RenkID from Tbl_Renk WHERE Renk = 'Black')),"
				+	"('Ford','Focus',(SELECT VitesTuruID from Tbl_VitesTuru WHERE VitesTuru = 'Half Automatic'),(SELECT YakitTuruID from Tbl_YakitTuru WHERE YakitTuru = 'Diesel'),(SELECT RenkID from Tbl_Renk WHERE Renk = 'Blue')),"
				+	"('Fiat','Marea',(SELECT VitesTuruID from Tbl_VitesTuru WHERE VitesTuru = 'Manual'),(SELECT YakitTuruID from Tbl_YakitTuru WHERE YakitTuru = 'Diesel'),(SELECT RenkID from Tbl_Renk WHERE Renk = 'Gray')),"
				+	"('Porsche','911',(SELECT VitesTuruID from Tbl_VitesTuru WHERE VitesTuru = 'Half Automatic'),(SELECT YakitTuruID from Tbl_YakitTuru WHERE YakitTuru = 'Gasoline'),(SELECT RenkID from Tbl_Renk WHERE Renk = 'Black')),"
				+	"('Mercedes','E',(SELECT VitesTuruID from Tbl_VitesTuru WHERE VitesTuru = 'Half Automatic'),(SELECT YakitTuruID from Tbl_YakitTuru WHERE YakitTuru = 'Gasoline'),(SELECT RenkID from Tbl_Renk WHERE Renk = 'White')),"
				+	"('Mini','Cooper',(SELECT VitesTuruID from Tbl_VitesTuru WHERE VitesTuru = 'Automatic'),(SELECT YakitTuruID from Tbl_YakitTuru WHERE YakitTuru = 'Gasoline'),(SELECT RenkID from Tbl_Renk WHERE Renk = 'White')),"
				+	"('Maserati','Ghibli',(SELECT VitesTuruID from Tbl_VitesTuru WHERE VitesTuru = 'Half Automatic'),(SELECT YakitTuruID from Tbl_YakitTuru WHERE YakitTuru = 'Diesel'),(SELECT RenkID from Tbl_Renk WHERE Renk = 'Turkuaz')),"
				+	"('Mercedes','E',(SELECT VitesTuruID from Tbl_VitesTuru WHERE VitesTuru = 'Half Automatic'),(SELECT YakitTuruID from Tbl_YakitTuru WHERE YakitTuru = 'Gasoline'),(SELECT RenkID from Tbl_Renk WHERE Renk = 'Black')),"
				+	"('Audi','A4',(SELECT VitesTuruID from Tbl_VitesTuru WHERE VitesTuru = 'Half Automatic'),(SELECT YakitTuruID from Tbl_YakitTuru WHERE YakitTuru = 'Diesel'),(SELECT RenkID from Tbl_Renk WHERE Renk = 'White')),"
				+	"('Mercedes - Benz','CLA',(SELECT VitesTuruID from Tbl_VitesTuru WHERE VitesTuru = 'Half Automatic'),(SELECT YakitTuruID from Tbl_YakitTuru WHERE YakitTuru = 'Diesel'),(SELECT RenkID from Tbl_Renk WHERE Renk = 'White')),"
				+	"('Mercedes - Benz','S',(SELECT VitesTuruID from Tbl_VitesTuru WHERE VitesTuru = 'Half Automatic'),(SELECT YakitTuruID from Tbl_YakitTuru WHERE YakitTuru = 'Diesel'),(SELECT RenkID from Tbl_Renk WHERE Renk = 'Black')),"
				+	"('Volkswagen','Passat',(SELECT VitesTuruID from Tbl_VitesTuru WHERE VitesTuru = 'Automatic'),(SELECT YakitTuruID from Tbl_YakitTuru WHERE YakitTuru = 'Diesel'),(SELECT RenkID from Tbl_Renk WHERE Renk = 'White')),"
				+	"('Citroen','Confort',(SELECT VitesTuruID from Tbl_VitesTuru WHERE VitesTuru = 'Automatic'),(SELECT YakitTuruID from Tbl_YakitTuru WHERE YakitTuru = 'Diesel'),(SELECT RenkID from Tbl_Renk WHERE Renk = 'Gray')),"
				+	"('Renault','Megane',(SELECT VitesTuruID from Tbl_VitesTuru WHERE VitesTuru = 'Manual'),(SELECT YakitTuruID from Tbl_YakitTuru WHERE YakitTuru = 'LPG'),(SELECT RenkID from Tbl_Renk WHERE Renk = 'White'))";
    		
    	String initIlan = 
    			"INSERT INTO Tbl_Ilan"
    			+	"(Ilan_Adi,Ilan_Fiyat,Ilan_Km,Ilan_Tarih,Ilan_SehirID,Ilan_ArabaID)"
    			+	"VALUES"
    			+	"('FIRSATTIR - Ilk Sahibinden -250 Hp - Orjinal', "
    			+	"49500,119500,'2018-05-17',1,5),"
    			+ 	"('TERTEMIZ AUDI A4 SIFIR SANSIMAN 8 ILERI TAM OTOMATIK',"
    			+	"102000,196000,'2018-05-18',1,6),"
    			+ 	"('SAHIBINDEN OTOMATIK FULL+FULL SIFIR AYARINDA',"
    			+	"75500,18000,'2018-05-17',10,7),"
    			+ 	"('MSG MOTORS GUVENCESIYLE VW PASSAT 1.6 TDI DSG',"
    			+	"125000,67082,'2018-05-04',8,8),"
    			+ 	"('BLACK MOTORS 2011 MODEL AUDI A7 3.0 TDI QUATTRO BAYI CIKISLI',"
    			+	"253000,183000,'2018-05-15',4,9),"
    			+ 	"('SAHIBINDEN FORD FOCUS ST LINE 1.5 TDCI POWERSHIFT',"
    			+	"112999,13000,'2018-04-30',2,10),"
    			+ 	"('Fiat Marea Sorunsuz Bakimli KELEPIR...!!!',"
    			+	"23000,18300,'2018-05-18',5,11),"
    			+ 	"('AUTOSTORE 2012 PORSCHE 911 CARRERA S-SPORT CRONO PDKLI 54.395 KM',"
    			+	"630000,54395,'2018-05-04',3,12),"
    			+ 	"('Hatasiz mercedes e 180',"
    			+	"192000,79000,'2018-05-17',2,13),"
    			+ 	"('Sahibinden 2016 CABRIO Borusan Premium paket full+full !!!',"
    			+	"124500,29000,'2018-05-17',6,14),"
    			+ 	"('Sahibinden Fermas Cikisli',"
    			+	"580000,16000,'2018-04-15',7,15),"
    			+ 	"('AUTOSTORE 2016 MERCEDES E200 AMG 9G-TRONIC - 9.748 km',"
    			+	"285000,9748,'2018-05-11',2,16),"
    			+ 	"('Orjinal S-Line. Degisensiz Tramersiz !!',"
    			+	"125500,194000,'2018-03-27',5,17),"
    			+ 	"('2016 Model CLA 180 d AMG beyaz ORJINAL',"
    			+	"170000,25000,'2018-04-30',1,18),"
    			+ 	"('AUTOSTORE 2015 MERCEDES S350 BLUETEC LONG 4 MATIC VIZYON - BAYI',"
    			+	"735000,101460,'2018-05-11',8,19),"
    			+ 	"('BOYASIZ -2018- VW PASSAT 1.6TDi IMPRESSION DSG - 10.123 KM',"
    			+	"156750,10123,'2018-04-30',2,20),"
    			+ 	"('2015 Y.KASA C4 1.6 B.HDI 120 HP T.OTOMATIK NAVI ANINDA KREDI ILE',"
    			+	"69750,38000,'2018-05-15',4,21),"
    			+ 	"('ACIL-KAZASIZ-BOYASIZ-LED PAKET - GORUS PAKET - %100 Uyumlu LPG',"
    			+	"74000,12000,'2018-05-15',11,22)";
    	try {
			statement = connection.createStatement();
			statement.executeUpdate(initYakitTuru);
			statement.executeUpdate(initVitesTuru);
			statement.executeUpdate(initRenk);
			statement.executeUpdate(initSehir);
			statement.executeUpdate(initAraba);
			statement.executeUpdate(initIlan);
		
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
				
				if(tableName.equals("Tbl_Ilan")) {
					ArrayList<Object> ads= new ArrayList<>();
					while (rs.next()) {
						Ad ad= new Ad();
						Integer adID = rs.getInt("IlanID");
						String adName= rs.getString("Ilan_Adi");
						Integer adPrice= rs.getInt("Ilan_Fiyat");
						Integer adKm= rs.getInt("Ilan_Km");
						Date adDate= rs.getDate("Ilan_Tarih");
						Integer adCarID = rs.getInt("Ilan_ArabaID");
						Integer adCityID = rs.getInt("Ilan_SehirID");
						String adCityName = selectCertainData(conn, "Tbl_Sehir", "Sehir", "SehirID", adCityID);
						String adCarBrand = selectCertainData(conn, "Tbl_Araba", "Araba_Marka", "ArabaID", adCarID);
						String adCarModel = selectCertainData(conn, "Tbl_Araba", "Araba_Model", "ArabaID", adCarID);

						ad.setAdCarBrand(adCarBrand);
						ad.setAdCarModel(adCarModel);
						ad.setAdID(adID);
						ad.setAdName(adName);
						ad.setCarID(adCarID);
						ad.setCityID(adCityID);
						ad.setCityName(adCityName);
						ad.setDate(adDate);
						ad.setKm(adKm);
						ad.setPrice(adPrice);
						
						ads.add(ad);
					}
					return ads;

				}
				
				/*if(tableName.equals("Tbl_Ilan")) {
					ArrayList<Object> ads= new ArrayList<>();
					while (rs.next()) {
						Ad ad= new Ad();
						Integer adID = rs.getInt("IlanID");
						String adName= rs.getString("Ilan_Adi");
						Integer price= rs.getInt("Ilan_Fiyat");
						Integer km= rs.getInt("Ilan_Km");
						Date date= rs.getDate("Ilan_Tarih");
						Integer carID = rs.getInt("Ilan_ArabaID");
						Integer cityID = rs.getInt("Ilan_SehirID");
						String cityName = selectCertainData(conn, "Tbl_Sehir", "Sehir", "SehirID", cityID);
							
						ad.setAdID(adID);
						ad.setAdName(adName);
						ad.setPrice(price);
						ad.setKm(km);
						ad.setDate(date);
						ad.setCarID(carID);
						ad.setCityID(cityID);
						ad.setCityName(cityName);
						
						ads.add(ad);
					}
					return ads;

				}*/
				
				

			

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
    
	public ArrayList<Object> selectAllQuery(String tableName, Connection conn, String order) {
		filteredDataQuery = "SELECT * FROM Tbl_Ilan AS ilan " 
				+ " INNER JOIN Tbl_Araba AS araba ON araba.ArabaID = ilan.Ilan_ArabaID "
				+ "INNER JOIN Tbl_VitesTuru AS vites ON vites.VitesTuruID = araba.Araba_VitesTuruID "
				+ "INNER JOIN Tbl_YakitTuru AS yakit ON yakit.YakitTuruID = araba.Araba_YakitTuruID "
				+ "INNER JOIN Tbl_Sehir AS sehir ON sehir.SehirID = ilan.Ilan_SehirID "
				+ "INNER JOIN Tbl_Renk AS renk ON renk.RenkID = araba.Araba_RenkID ";
		orderBy = "";
		orderBy = orderBy + "ORDER BY " + order;
		PreparedStatement preparedStatement = null;
		if (!ascDesc) {
			orderBy = orderBy + " DESC";

		}
		filteredDataQuery = filteredDataQuery + whereClause + orderBy;
		
		try {
			preparedStatement = conn.prepareStatement(filteredDataQuery);
			// preparedStatement.setInt(1, 1001);

			// execute select SQL stetement
			ResultSet rs = preparedStatement.executeQuery();

			if (tableName.equals("Tbl_Ilan")) {
				ArrayList<Object> ads = new ArrayList<>();
				while (rs.next()) {
					Ad ad = new Ad();
					Integer adID = rs.getInt("IlanID");
					String adName = rs.getString("Ilan_Adi");
					Integer adPrice = rs.getInt("Ilan_Fiyat");
					Integer adKm = rs.getInt("Ilan_Km");
					Date adDate = rs.getDate("Ilan_Tarih");
					Integer adCarID = rs.getInt("Ilan_ArabaID");
					Integer adCityID = rs.getInt("Ilan_SehirID");
					Integer adCarTransmissionTypeID = rs.getInt("Araba_VitesTuruID");
					Integer adCarFuelTypeID = rs.getInt("Araba_YakitTuruID");
					String adCityName = selectCertainData(conn, "Tbl_Sehir", "Sehir", "SehirID", adCityID);
					String adCarBrand = selectCertainData(conn, "Tbl_Araba", "Araba_Marka", "ArabaID", adCarID);
					String adCarModel = selectCertainData(conn, "Tbl_Araba", "Araba_Model", "ArabaID", adCarID);
					String adCarTransmissionType = selectCertainData(conn, "Tbl_VitesTuru", "VitesTuru", "VitesTuruID", adCarTransmissionTypeID);
					String adCarFuelType = selectCertainData(conn, "Tbl_YakitTuru", "YakitTuru", "YakitTuruID", adCarFuelTypeID);
					Integer adCarColorID = rs.getInt("Araba_RenkID");
					String adCarColorName = rs.getString("Renk");

					ad.setAdCarBrand(adCarBrand);
					ad.setAdCarModel(adCarModel);
					ad.setAdID(adID);
					ad.setAdName(adName);
					ad.setCarID(adCarID);
					ad.setCityID(adCityID);
					ad.setCityName(adCityName);
					ad.setDate(adDate);
					ad.setKm(adKm);
					ad.setPrice(adPrice);
					ad.setAdCarTransmissionTypeID(adCarTransmissionTypeID);
					ad.setAdCarFuelTypeID(adCarFuelTypeID);
					ad.setAdCarTransmissionType(adCarTransmissionType);
					ad.setAdCarFuelType(adCarFuelType);
					ad.setAdCarColorID(adCarColorID);
					ad.setAdCarColorName(adCarColorName);
					ads.add(ad);
				}
				ascDesc = (ascDesc) ? false : true;
				return ads;

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
    	if(tableName.equals("Tbl_Ilan")) {
    		insertTableSQL = "INSERT INTO Tbl_Ilan"
				+ "(Ilan_Adi,Ilan_Fiyat,Ilan_Km,Ilan_Tarih,Ilan_ArabaID,Ilan_SehirID)"
    			+ "VALUES"
				+ "('"+ datas[0] + "'," + datas[1] + "," + datas[2] + ",CURDATE()," + datas[4] + ",(SELECT SehirID from Tbl_Sehir WHERE Sehir = '" + datas[3] + "'))";
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
    public void updateRecord(Connection connection, String id, String tableName,Integer row,Integer col, DefaultTableModel dtm) {
    	String query = "sa";
    	PreparedStatement ps = null;
    	if(tableName.equals("Tbl_Araba")) {
			try {
				if(col == 1) {
					query = "UPDATE "+ tableName +" SET Araba_Marka = ? WHERE ArabaID = "+id;
					 ps = connection.prepareStatement(query);
			         ps.setString(1, (String) dtm.getValueAt(row, 1));
				}
				if(col == 2) {
					query = "UPDATE "+ tableName +" SET Araba_Model= ? WHERE ArabaID = "+id;
					ps = connection.prepareStatement(query);
			         ps.setString(1, (String) dtm.getValueAt(row, 2));
				}
				if(col == 3) {
					query = "UPDATE "+ tableName +" SET Araba_VitesTuruID = ? WHERE ArabaID = "+id;
					ps = connection.prepareStatement(query);
			         ps.setString(1, (String) dtm.getValueAt(row, 3));
				}
				if(col == 4) {
					query = "UPDATE "+ tableName +" SET Araba_YakitTuruID = ? WHERE ArabaID = "+id;
					 ps = connection.prepareStatement(query);
			         ps.setString(1, (String) dtm.getValueAt(row, 4));
				}
				if(col == 5) {
					query = "UPDATE "+ tableName +" SET Araba_RenkID = ? WHERE ArabaID = "+id;
					ps = connection.prepareStatement(query);
			         ps.setString(1, (String) dtm.getValueAt(row, 5));
				}
		    

         ps.executeUpdate(); 
         ps.close();

     } catch (Exception ex) {
         System.out.println("An error occured");
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

    public ArrayList<String> getCityNames(Connection connection){
    	ArrayList<String> cities = new ArrayList<String>();
    	String query = "SELECT Sehir FROM Tbl_Sehir ORDER BY Sehir"; 
    	PreparedStatement stm;
    	ResultSet rs = null;
		try {
			stm = connection.prepareStatement(query);
		

    	 rs = stm.executeQuery(query); 

    	while (rs.next()) { 
    	    String city = rs.getString("Sehir"); 
    	    // add group names to the array list
    	    cities.add(city);
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

    	
    	
    	return cities;
    }
    public ArrayList<String> getCarIDs(Connection connection){
    	ArrayList<String> carIDs = new ArrayList<String>();
    	String query = "SELECT ArabaID FROM Tbl_Araba ORDER BY ArabaID"; 
    	PreparedStatement stm;
    	ResultSet rs = null;
		try {
			stm = connection.prepareStatement(query);
		

    	 rs = stm.executeQuery(query); 

    	while (rs.next()) { 
    	    String carID = rs.getString("ArabaID"); 
    	    // add group names to the array list
    	    carIDs.add(carID);
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
    	
    	return carIDs;
    }
    
    public ArrayList<String> getCarModels(Connection connection){
    	ArrayList<String> carModels = new ArrayList<String>();
    	String query = "SELECT DISTINCT Araba_Model FROM Tbl_Araba ORDER BY Araba_Model"; 
    	PreparedStatement stm;
    	ResultSet rs = null;
		try {
			stm = connection.prepareStatement(query);
		

    	 rs = stm.executeQuery(query); 

    	while (rs.next()) { 
    	    String carModel = rs.getString("Araba_Model"); 
    	    // add group names to the array list
    	    carModels.add(carModel);
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
    	
    	return carModels;
    }
    
    public ArrayList<String> getCarBrands(Connection connection){
    	ArrayList<String> carBrands = new ArrayList<String>();
    	String query = "SELECT DISTINCT Araba_Marka FROM Tbl_Araba ORDER BY Araba_Marka"; 
    	PreparedStatement stm;
    	ResultSet rs = null;
		try {
			stm = connection.prepareStatement(query);
		

    	 rs = stm.executeQuery(query); 

    	while (rs.next()) { 
    	    String carBrand = rs.getString("Araba_Marka"); 
    	    // add group names to the array list
    	    carBrands.add(carBrand);
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
    	
    	return carBrands;
    }
    
    public ArrayList<String> getColors(Connection connection){
    	ArrayList<String> colors = new ArrayList<String>();
    	String query = "SELECT DISTINCT Renk FROM Tbl_Renk ORDER BY Renk"; 
    	PreparedStatement stm;
    	ResultSet rs = null;
		try {
			stm = connection.prepareStatement(query);
		

    	 rs = stm.executeQuery(query); 

    	while (rs.next()) { 
    	    String color = rs.getString("Renk"); 
    	    // add group names to the array list
    	    colors.add(color);
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
    	
    	return colors;
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
    
    public ArrayList<Object> filterData(String minPrice,String maxPrice,String minKm,String maxKm,
    							Object[] brands,Object[] models,Object[] transmissions,Object[] fuels,Object[] cities,Object[] colors,
    							String date,String carAd){
    	LocalDate currentDate = LocalDate.now();  // Source Date
    	LocalDate destDate = null;  // Adding a day to source date.
    	String destDateS = "";
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Setting date format

    	if(date.equals("Today")) {
    		destDate = currentDate.plusDays(-1);
        	destDateS = destDate.format(formatter); 


    	}
    	if(date.equals("Last Week")) {
    		destDate = currentDate.plusDays(-7);
        	destDateS = destDate.format(formatter); 


    	}
    	if(date.equals("Last Month")) {
    		destDate = currentDate.plusDays(-30);
        	destDateS = destDate.format(formatter); 

    	}
    	String currentDateS = currentDate.format(formatter);

    	if(minPrice.equals(""))
    		minPrice = "0";
    	if(maxPrice.equals(""))
    		maxPrice = "0";
    	if(minKm.equals(""))
    		minPrice = "0";
    	if(maxKm.equals(""))
    		maxPrice = "0";
    	
    	
		filteredDataQuery = "SELECT * FROM Tbl_Ilan AS ilan " 
							+ " INNER JOIN Tbl_Araba AS araba ON araba.ArabaID = ilan.Ilan_ArabaID "
							+ "INNER JOIN Tbl_VitesTuru AS vites ON vites.VitesTuruID = araba.Araba_VitesTuruID "
							+ "INNER JOIN Tbl_YakitTuru AS yakit ON yakit.YakitTuruID = araba.Araba_YakitTuruID "
							+ "INNER JOIN Tbl_Sehir AS sehir ON sehir.SehirID = ilan.Ilan_SehirID "
							+ "INNER JOIN Tbl_Renk AS renk ON renk.RenkID = araba.Araba_RenkID ";
		PreparedStatement preparedStatement = null;
		whereClause = "WHERE (";
		if(brands.length != 0) {
			int i = brands.length;
			int counter=0;
			for(Object brand : brands) {
				whereClause = whereClause + "araba.Araba_Marka = '" + brand.toString() + "'";
				counter++;
				if(counter != i)
					whereClause = whereClause + " OR ";
			}
			whereClause = whereClause + ")";
		}
		
		if(models.length != 0) {
			if(whereClause.length() > 7)
				whereClause = whereClause + " AND ( ";
			int i = models.length;
			int counter=0;
			for(Object model : models) {
				whereClause = whereClause + "araba.Araba_Model = '" + model.toString() + "'";
				counter++;
				if(counter != i)
					whereClause = whereClause + " OR ";
			}
			whereClause = whereClause + ")";
		}
		if(transmissions.length != 0) {
			if(whereClause.length() > 7)
				whereClause = whereClause + " AND ( ";
			int i = transmissions.length;
			int counter=0;
			for(Object transmission : transmissions) {
				whereClause = whereClause + "vites.VitesTuru = '" + transmission.toString() + "'";
				counter++;
				if(counter != i)
					whereClause = whereClause + " OR ";
			}
			whereClause = whereClause + ")";
		}
		
		if(fuels.length != 0) {
			if(whereClause.length() > 7)
				whereClause = whereClause + " AND ( ";
			int i = fuels.length;
			int counter=0;
			for(Object fuel : fuels) {
				whereClause = whereClause + "yakit.YakitTuru = '" + fuel.toString() + "'";
				counter++;
				if(counter != i)
					whereClause = whereClause + " OR ";
			}
			whereClause = whereClause + ")";
		}
		
		if(cities.length != 0) {
			if(whereClause.length() > 7)
				whereClause = whereClause + " AND ( ";
			int i = cities.length;
			int counter=0;
			for(Object city : cities) {
				whereClause = whereClause + "sehir.Sehir = '" + city.toString() + "'";
				counter++;
				if(counter != i)
					whereClause = whereClause + " OR ";
			}
			whereClause = whereClause + ")";
		}
		
		if(colors.length != 0) {
			if(whereClause.length() > 7)
				whereClause = whereClause + " AND ( ";
			int i = colors.length;
			int counter=0;
			for(Object color : colors) {
				whereClause = whereClause + "renk.Renk = '" + color.toString() + "'";
				counter++;
				if(counter != i)
					whereClause = whereClause + " OR ";
			}
			whereClause = whereClause + ")";
		}
		if(!minPrice.equals("0")  || !maxPrice.equals("0")) {
			if(whereClause.length() > 7)
				whereClause = whereClause + " AND ( ";
				whereClause = whereClause + "ilan.Ilan_Fiyat > " + minPrice + " AND ilan.Ilan_Fiyat < " + maxPrice + ")";
		}
		if(!minPrice.equals("0")  || !maxPrice.equals("0")) {
			if(whereClause.length() > 7)
				whereClause = whereClause + " AND ( ";
				whereClause = whereClause + "ilan.Ilan_Km > " + minKm + " AND ilan.Ilan_Km < " + maxKm + ")";
		}
		if(!destDateS.equals("")) {
			if(whereClause.length() > 7)
				whereClause = whereClause + " AND ( ";
				whereClause = whereClause + "ilan.Ilan_Tarih BETWEEN '" + destDateS + "' AND '" + currentDateS + "')";
		}
		if(!carAd.equals("")) {
			if(whereClause.length() > 7)
				whereClause = whereClause + " AND ( ";
				whereClause = whereClause + "ilan.Ilan_Adi LIKE '%" + carAd + "%')";
		}
		
		if(whereClause.length() < 8 )
			whereClause = "";
		filteredDataQuery = filteredDataQuery + whereClause + orderBy;
		try {
			preparedStatement = conn.prepareStatement(filteredDataQuery);
			// preparedStatement.setInt(1, 1001);

			// execute select SQL stetement
			ResultSet rs = preparedStatement.executeQuery();

				ArrayList<Object> ads = new ArrayList<>();
				while (rs.next()) {
					Ad ad = new Ad();
					Integer adID = rs.getInt("IlanID");
					String adName = rs.getString("Ilan_Adi");
					Integer adPrice = rs.getInt("Ilan_Fiyat");
					Integer adKm = rs.getInt("Ilan_Km");
					Date adDate = rs.getDate("Ilan_Tarih");
					Integer adCarID = rs.getInt("Ilan_ArabaID");
					Integer adCityID = rs.getInt("Ilan_SehirID");
					Integer adCarTransmissionTypeID = rs.getInt("Araba_VitesTuruID");
					Integer adCarFuelTypeID = rs.getInt("Araba_YakitTuruID");
					String adCityName = selectCertainData(conn, "Tbl_Sehir", "Sehir", "SehirID", adCityID);
					String adCarBrand = selectCertainData(conn, "Tbl_Araba", "Araba_Marka", "ArabaID", adCarID);
					String adCarModel = selectCertainData(conn, "Tbl_Araba", "Araba_Model", "ArabaID", adCarID);
					String adCarTransmissionType = selectCertainData(conn, "Tbl_VitesTuru", "VitesTuru", "VitesTuruID", adCarTransmissionTypeID);
					String adCarFuelType = selectCertainData(conn, "Tbl_YakitTuru", "YakitTuru", "YakitTuruID", adCarFuelTypeID);
					Integer adCarColorID = rs.getInt("Araba_RenkID");
					String adCarColorName = rs.getString("Renk");

					ad.setAdCarBrand(adCarBrand);
					ad.setAdCarModel(adCarModel);
					ad.setAdID(adID);
					ad.setAdName(adName);
					ad.setCarID(adCarID);
					ad.setCityID(adCityID);
					ad.setCityName(adCityName);
					ad.setDate(adDate);
					ad.setKm(adKm);
					ad.setPrice(adPrice);
					ad.setAdCarTransmissionTypeID(adCarTransmissionTypeID);
					ad.setAdCarFuelTypeID(adCarFuelTypeID);
					ad.setAdCarTransmissionType(adCarTransmissionType);
					ad.setAdCarFuelType(adCarFuelType);
					ad.setAdCarColorID(adCarColorID);
					ad.setAdCarColorName(adCarColorName);
					ads.add(ad);
				}
				return ads;


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
    }