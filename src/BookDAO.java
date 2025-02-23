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
    public List<UserLoanBook> viewBook() {

        List<UserLoanBook> resultList = new ArrayList<>();
        String sql = "SELECT b.id, b.title, b.author , b.available, l.loan_date, l.return_date, l.user_name FROM books b left JOIN loans l ON b.id = l.book_id";

        try {
            Connection conn = Database.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                UserLoanBook row = new UserLoanBook();
                row.setId(rs.getInt("Id"));
                row.setTitle(rs.getString("Title"));
                row.setAuthor(rs.getString("Author"));
                row.setAvailable(rs.getBoolean("Available"));
                row.setLoan_date(rs.getDate("Loan_date"));
                row.setReturn_date(rs.getDate("Return_date"));
                row.setUser_name(rs.getString("User_name"));

                resultList.add(row);
            }


        } catch (Exception e) {
            System.out.println("failed to view books");
            e.printStackTrace();
        }
        return resultList;
    }

    // Connect to database to search a book by author name in database
    public static List<UserLoanBook> searchBookWithAuthor(String author) {
        List<UserLoanBook> resultList = new ArrayList<>();
        String sql = "SELECT b.id, b.title, b.author , b.available, l.loan_date, l.return_date, l.user_name FROM books b left JOIN loans l ON b.id = l.book_id WHERE author = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, author);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                UserLoanBook row = new UserLoanBook();
                row.setId(rs.getInt("Id"));
                row.setTitle(rs.getString("Title"));
                row.setAuthor(rs.getString("Author"));
                row.setAvailable(rs.getBoolean("Available"));
                row.setLoan_date(rs.getDate("Loan_date"));
                row.setReturn_date(rs.getDate("Return_date"));
                row.setUser_name(rs.getString("User_name"));
                resultList.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultList;
    }


}





