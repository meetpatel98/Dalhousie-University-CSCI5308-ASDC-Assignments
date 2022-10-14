import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

public class DBConnectionTest {

    @Test
    public void TestWhenDBConnect() throws IOException, SQLException, ClassNotFoundException {
        DBConnection dbConnection = new DBConnection();
        Assert.assertTrue(dbConnection.getDBConnection());
    }

}
