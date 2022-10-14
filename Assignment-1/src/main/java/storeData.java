import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class storeData {

    DBConnection dbConnection = new DBConnection();
    Properties properties = new Properties();
    private String TABLE_NAME;
    private String FILE_PATH;
    private final String DATABASE_NAME = "assignment1";
    private static final String COLUMN_ID = "ID";
    private ArrayList<String> Attributes = new ArrayList<String>();
    private List<String> headerList;
    private List<List<String>> rows = new ArrayList<>();
    private StringBuilder loadCSV = new StringBuilder();

    public Boolean createDatabase() throws IOException, SQLException {
        dropDatabase();
        InputStream is = DBConnection.class.getResourceAsStream("login.properties");
        properties.load(is);
        String USER = properties.getProperty("USERNAME");
        String PASSWORD = properties.getProperty("PASSWORD");
        try{
            String CONNECTION_URL = "jdbc:mysql://localhost:3306/";
            Connection conn = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            String createDatabase = "CREATE DATABASE "+DATABASE_NAME;
            dbConnection.setDATABASE_NAME(DATABASE_NAME);
            PreparedStatement createDB = conn.prepareStatement(createDatabase);
            createDB.executeUpdate();
            System.out.println("Database created successfully");
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean dropDatabase(){
        try{
            dbConnection.getDBConnection();
            String dropDatabase = "DROP DATABASE IF EXISTS "+DATABASE_NAME;
            PreparedStatement dropDB = dbConnection.conn.prepareStatement(dropDatabase);
            dropDB.executeUpdate();
            System.out.println("Database dropped successfully");
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean fileExists(String filePath){
        if (filePath==null){
            return false;
        }
        File f = new File(filePath);
        if(f.exists()) {
            return true;
        }
        else {
            System.out.println("File does not exists");
            return false;
        }
    }

    public String tableName(String filePath) {
        if (filePath==null){
            return null;
        }
        if(!fileExists(filePath)){
            System.out.println("File does not exists");
            return null;
        }
        FILE_PATH = filePath;
        Path path = Paths.get(filePath);
        Path fileName = path.getFileName();
        String FILE_NAME = fileName.toString();
        TABLE_NAME = removeFileExtension(FILE_NAME);
        return TABLE_NAME;
    }

    public String removeFileExtension(String fileName) {
        if (fileName.indexOf(".") > 0) {
            return fileName.substring(0, fileName.lastIndexOf("."));
        } else {
            return fileName;
        }
    }

    public List<String> findHeader(String filePath){
        if(!fileExists(filePath)){
            System.out.println("File does not exists");
            return null;
        }
        try{
            dbConnection.getDBConnection();
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String headers = br.readLine();
            headerList = Arrays.asList(headers.split(","));
        }catch (Exception e){
            e.printStackTrace();
        }
        return headerList;
    }

    public Boolean createTable(List<String> headerList){
        if(headerList.isEmpty()){
            return false;
        }
        try {
            dbConnection.getDBConnection();
            BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
            StringBuilder createTable = new StringBuilder();
            createTable.append("CREATE TABLE "+TABLE_NAME+ " ( "+COLUMN_ID+" int NOT NULL AUTO_INCREMENT PRIMARY KEY");
            for (int i=0; i<headerList.size(); i++){
                createTable.append(", "+headerList.get(i)+" varchar(255)");
            }
            createTable.append(" )");
            PreparedStatement pr = dbConnection.conn.prepareStatement(createTable.toString());
            pr.executeUpdate();
            System.out.println("Table created successfully...");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<List<String>> readCSVFile(String filePath){
        if(filePath.isEmpty()){
            return null;
        }
        if(!fileExists(filePath)){
            System.out.println("File does not exists");
            return null;
        }
        try{
            dbConnection.getDBConnection();
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            StringBuilder readCSV = new StringBuilder();
            String rowValues = null;
            br.readLine();
            while ((rowValues = br.readLine())!=null){
                rows.add(Arrays.asList(rowValues.split(",")));
            }
            System.out.println("CSV read successfully...");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rows;
    }

    public boolean loadCSVData(String filePath, List<List<String>> rows){
        if(filePath.isEmpty()){
            return false;
        }
        if(!fileExists(filePath)){
            System.out.println("File does not exists");
            return false;
        }
        if (rows.isEmpty()){
            System.out.println("No data Found");
            return false;
        }
        try{
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            StringBuilder loadDataCSV = new StringBuilder();
            loadCSV.append("Insert into "+TABLE_NAME+" ( ");

            for (int i=0; i<headerList.size(); i++){
                loadCSV.append(headerList.get(i)).append(" ,");
            }
            loadCSV.deleteCharAt(loadCSV.length() - 1);
            loadCSV.append(") Values");
            for (int i = 0; i < rows.size(); i++) {
                StringBuilder data = new StringBuilder();
                StringBuilder temp = new StringBuilder(loadCSV);
                data.append(" ( ");
                for (int j = 0; j < rows.get(i).size(); j++) {
                    data.append("\'").append(rows.get(i).get(j)).append("\'").append(", ");
                }
                data.deleteCharAt(data.length() - 2);
                data.append(") ");
                temp.append(data);
                PreparedStatement ps = dbConnection.conn.prepareStatement(temp.toString());
                ps.executeUpdate();
            }
            System.out.println("CSV data added successfully...");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
