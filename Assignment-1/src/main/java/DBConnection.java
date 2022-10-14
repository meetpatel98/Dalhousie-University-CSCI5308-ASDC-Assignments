import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
public class DBConnection {
    Properties properties = new Properties();
    Connection conn;
    String DATABASE_NAME="";

    public boolean getDBConnection() throws ClassNotFoundException, SQLException, IOException {
        try{
//            properties.load(new FileInputStream("src/main/resources/login.properties"));
            InputStream is = DBConnection.class.getResourceAsStream("login.properties");
            properties.load(is);
            String USER = properties.getProperty("USERNAME");
            String PASSWORD = properties.getProperty("PASSWORD");
            String CONNECTION_STRING = "jdbc:mysql://localhost:3306/" + DATABASE_NAME + "?autoReconnect=true&useSSL=false";
            conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public String getDATABASE_NAME() {
        return DATABASE_NAME;
    }
    public void setDATABASE_NAME(String DATABASE_NAME) {
        this.DATABASE_NAME = DATABASE_NAME;
    }
}
