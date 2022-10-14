import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class fetchData {
    Properties properties = new Properties();
    private static BufferedWriter bw;
    private int columnCount;

    public boolean createCSV(String databaseName, String sqlQuery) throws IOException {
        if(databaseName == null || databaseName.isEmpty()){
            return false;
        }
        if (sqlQuery == null || sqlQuery.isEmpty()){
            return false;
        }
        InputStream is = DBConnection.class.getResourceAsStream("login.properties");
        properties.load(is);
        String USER = properties.getProperty("USERNAME");
        String PASSWORD = properties.getProperty("PASSWORD");
        String CONNECTION_STRING = "jdbc:mysql://localhost:3306/" + databaseName + "?useSSL=false";

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(sqlQuery);
            ResultSet rs = ps.executeQuery();
            bw = new BufferedWriter(new FileWriter("result.csv"));
            bw.write('\ufeff');
            ResultSetMetaData metaData = rs.getMetaData();
            int numberOfAttributes = metaData.getColumnCount();
            String header = "";
            for (int x = 1; x<=numberOfAttributes; x++) {
                String attributeName = metaData.getColumnName(x);
                header = header.concat(attributeName).concat(",");
            }
            bw.write(header.substring(0, header.length() - 1));
            columnCount = numberOfAttributes;
            while (rs.next()) {
                String row = "";
                for (int i = 1; i <= columnCount; i++) {
                    Object dataObject = rs.getObject(i);
                    String dataString = "";
                    if (dataObject != null)
                    {
                        dataString = dataObject.toString();
                    }
                    row = row.concat(dataString);
                    if (i != columnCount) {
                        row = row.concat(",");
                    }
                }
                bw.newLine();
                bw.write(row);
            }
            System.out.println("CSV created successfully");
            ps.close();
            bw.close();
            return true;
        } catch (Exception e) {
            System.out.println("Message: "+e.getMessage());
            return false;
        }
    }
}
