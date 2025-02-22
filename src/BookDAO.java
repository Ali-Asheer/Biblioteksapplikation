import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    // Connect to database to add a book to database
    public void addBook(String title, String author) {

        String sql = "INSERT INTO books (title, author) VALUES (?,?)";

        try {
            Connection conn = Database.getConnection();

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, title);
            stmt.setString(2, author);

            stmt.executeUpdate();
            System.out.println("New book added successfully!!");

        } catch (Exception e) {
            System.out.println("failed to add book");
            e.printStackTrace();
        }
    }

    // Connect to database to delete a book from database
    public void deleteBook(int id) {

        String sql = "DELETE FROM books WHERE id = ?";

        try {
            Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("The book is deleted successfully!!");

        } catch (Exception e) {
            System.out.println("failed to delete book");
            e.printStackTrace();
        }
    }

    // Connect to database to see all book in database
    public List<Books> viewBook() {

        List<Books> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try {
            Connection conn = Database.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Books book = new Books();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("Title"));
                book.setAuthor(rs.getString("Author"));
                book.setAvailable(rs.getBoolean("Available"));
                books.add(book);
            }
        } catch (Exception e) {
            System.out.println("failed to view books");
            e.printStackTrace();
        }
        return books;
    }

    // Connect to database to search a book by author name in database
    public static List<Books> searchBookWithAuthor(String author) {
        List<Books> books = new ArrayList<>();

        String sql = "SELECT * FROM books WHERE author = ?";
        try (
                Connection conn = Database.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, author);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Books book = new Books();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("Title"));
                book.setAuthor(rs.getString("Author"));
                book.setAvailable(rs.getBoolean("Available"));
                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }


}
