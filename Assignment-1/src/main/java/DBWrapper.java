import java.util.List;

public class DBWrapper {
    public static void main(String[] args) throws Exception {
        storeData sd = new storeData();
        fetchData fd = new fetchData();
        if (args[0].equals("init")){
            sd.createDatabase();
            sd.tableName(args[1]);
            List<String> headerList = sd.findHeader(args[1]);
            sd.createTable(headerList);
            List<List<String>> rows = sd.readCSVFile(args[1]);
            sd.loadCSVData(args[1], rows);
        }else if (args[0].equals("query")) {
            fd.createCSV(args[1], args[2]);
        }
    }
}
