import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

public class test_fetchData {

    fetchData fd;

    @Before
    public void init()
    {
        fd = Mockito.mock(fetchData.class);
    }

    @Test
    public void TestWhenDatabaseNameIsEmpty() throws IOException {
        String databaseName = "";
        String sql = "select * from student";
        Mockito.when(fd.createCSV(databaseName,sql)).thenReturn(false);
    }

    @Test
    public void TestWhenDatabaseNameNull() throws IOException {
        fetchData obj = new fetchData();
        String sql = "select * from student";
        Assert.assertFalse(obj.createCSV(null,sql));
    }

    @Test
    public void TestWhenSQLIsEmpty() throws IOException {
        fetchData obj = new fetchData();
        String databaseName = "assignment1";
        String sql = "";
        Assert.assertFalse(obj.createCSV(databaseName,sql));
    }

    @Test
    public void TestWhenSQLIsNull() throws IOException {
        fetchData obj = new fetchData();
        String databaseName = "assignment1";
        Assert.assertFalse(obj.createCSV(databaseName,null));
    }
}
