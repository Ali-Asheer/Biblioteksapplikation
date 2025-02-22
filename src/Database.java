import java.sql.*;

public class Database {

    // Connect to database
    public static Connection getConnection() throws Exception {

        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306";
            String user = "root";
            String password = "ali123";
            Class.forName(driver);

            Connection conn = DriverManager.getConnection(url, user, password);
            conn.createStatement().executeUpdate("CREATE DATABASE IF NOT EXISTS BIBLIOTEK");
            PreparedStatement create = conn.prepareStatement("USE BIBLIOTEK");
            create.executeUpdate();
            return conn;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    // Create tables if not exist
    public static void createTable() throws Exception {
        try {
            Connection conn = getConnection();
            PreparedStatement create = conn.prepareStatement("CREATE TABLE IF NOT EXISTS books (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "title VARCHAR(255) NOT NULL, " +
                    "author VARCHAR(255) NOT NULL, " +
                    "available BOOLEAN NOT NULL DEFAULT TRUE)");

            create.executeUpdate();
            create = conn.prepareStatement("CREATE TABLE IF NOT EXISTS loans (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "user_name VARCHAR(255) NOT NULL, " +
                    "book_id INT NOT NULL, " +
                    "loan_date DATE NOT NULL, " +
                    "return_date DATE, " +
                    "FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE ON UPDATE CASCADE)");


            create.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("Function complete.");
        }

    }

    // Insert values to table if not exist
    public static void insertIfTableIsEmpty() {
        String checkSql = "SELECT 1 FROM books";
        String insertSql = "INSERT INTO books (id, title, author, available ) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSql);
             ResultSet rs = pstmt.executeQuery(checkSql)) {
            if (!rs.next()) {
                conn.setAutoCommit(false);
                Object[][] bookData = {
                        {1, "Pride and Prejudice", "Jane Austen", true},
                        {2, "My Story", "Kamala Das", true},
                        {3, "To the Lighthouse", "Virginia Woolf", true},
                        {4, "War and Peace", "Leo Tolstoy ", true},
                        {5, "Crime and Punishment", "Fyodor Dostoevsky", true},
                        {6, "Norwegian Wood", "Haruki Murakami", true},
                        {7, "Things Fall Apart", "Chinua Achebe", true},
                        {8, "The Handmaid’s Tale", "Margaret Atwood", true},
                        {9, "Gitanjali", "Rabindranath Tagore", true},
                        {10, "The God of Small Things", "Arundhati Roy", true},
                        {11, "A Suitable Boy", "Vikram Seth", true},
                        {12, "Great Expectations", "Charles Dickens", true}
                };
                for (Object[] book : bookData) {
                    pstmt.setInt(1, (Integer) book[0]);
                    pstmt.setString(2, (String) book[1]);
                    pstmt.setString(3, (String) book[2]);
                    pstmt.setBoolean(4, (Boolean) book[3]);

                    pstmt.addBatch(); // Add to batch
                }
                int[] updateCounts = pstmt.executeBatch(); // Execute batch
                conn.commit();

                System.out.println("Inserted rows: " + updateCounts.length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}