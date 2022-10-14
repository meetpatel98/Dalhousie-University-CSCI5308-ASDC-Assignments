import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class test_storeData {

    storeData sd = new storeData();

    @Before
    public void init()
    {
        sd = Mockito.mock(storeData.class);
    }

    @Test
    public void TestWhenDatabaseNameIsEmpty() throws IOException {
        Mockito.when(sd.tableName("student.csv")).thenReturn("student");
    }

    @Test
    public void TestReaderWhenDBisCreated() throws SQLException, IOException {
        storeData obj = new storeData();
        Assert.assertTrue(obj.createDatabase());
    }

    @Test
    public void TestWhenDbIsDropped() {
        storeData obj = new storeData();
        Assert.assertTrue(obj.dropDatabase());
    }

    @Test
    public void removeFileExtension(){
        storeData obj = new storeData();
        Assert.assertEquals("student",obj.removeFileExtension("student.txt"));
    }

    @Test
    public void findHeader(){
        List<String> list = new ArrayList<String>();
        storeData obj = new storeData();
        list.add("Student_Name");
        list.add("Gender");
        list.add("Country");
        Assert.assertEquals(list,obj.findHeader("src/main/java/student.csv"));
    }

    @Test
    public void TestFileExists(){
        storeData obj = new storeData();
        Assert.assertTrue(obj.fileExists("src/main/java/student.csv"));
    }

    @Test
    public void tableNameFileExits(){
        storeData obj = new storeData();
        Assert.assertNull(obj.tableName("src/main/java/student1.csv"));
    }

    @Test
    public void TestWhenFileNotExists(){
        storeData obj = new storeData();
        Assert.assertNull(obj.tableName("src/main/java/student1.csv"));
    }

    @Test
    public void TestWhenFilePathIsEmpty(){
        storeData obj = new storeData();
        Assert.assertNull(obj.findHeader(""));
    }

    @Test
    public void TempWhenFileNotExists(){
        storeData obj = new storeData();
        Assert.assertNull(obj.findHeader("src/main/java/student1.csv"));
    }

    @Test
    public void TestWhenCSVFileNotExists(){
        storeData obj = new storeData();
        Assert.assertNull(obj.readCSVFile("src/main/java/student1.csv"));
    }

    @Test
    public void TestWhenCSVFilePathIsEmpty(){
        storeData obj = new storeData();
        Assert.assertNull(obj.readCSVFile(""));
    }

    @Test
    public void TestWhenLoadCSVPathNotExist(){
        storeData obj = new storeData();
        List<List<String>> rows = new ArrayList<>();
        Assert.assertFalse(obj.loadCSVData("src/main/java/student1.csv",rows));
    }

    @Test
    public void TestWhenLoadCSVRowsEmpty(){
        storeData obj = new storeData();
        List<List<String>> rows = new ArrayList<>();
        Assert.assertFalse(obj.loadCSVData("src/main/java/student.csv",rows));
    }

    @Test
    public void TestWhenLoadCSVFilePathEmpty(){
        storeData obj = new storeData();
        List<List<String>> rows = new ArrayList<>();
        Assert.assertFalse(obj.loadCSVData("",rows));
    }

    @Test
    public void TestWhenHeaderDoesNotExists(){
        storeData obj = new storeData();
        List<String> headerList = new ArrayList<>();
        Assert.assertFalse(obj.createTable(headerList));
    }

    @Test
    public void TestWhenFilePathIsNull(){
        storeData obj = new storeData();
        Assert.assertFalse(obj.fileExists(null));
    }

}
