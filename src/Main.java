import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws Exception {

        Database.getConnection();  // Connect to database
        Database.createTable();     // Create tables if not exist
        Database.insertIfTableIsEmpty();  // Insert values to table if not exist
        ConsolView.main(args);   // All menus and options
    }
}
