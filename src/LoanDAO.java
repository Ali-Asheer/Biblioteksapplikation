import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO {
    // Connect to database to loan book
    public static void addloan(String userName, int book_id) {

        String sql = "INSERT INTO loans (user_Name,book_id,loan_date,return_date ) VALUES (?,?,?,?)";
        String sql1 = "UPDATE books SET available=? WHERE id = ?";
        try {
            Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);


            stmt.setString(1, userName);
            stmt.setInt(2, book_id);
            stmt.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            stmt.setDate(4, java.sql.Date.valueOf(LocalDate.now().plusDays(30)));
            stmt.executeUpdate();

            PreparedStatement stmt1 = conn.prepareStatement(sql1);
            stmt1.setBoolean(1, false);
            stmt1.setInt(2, book_id);
            stmt1.executeUpdate();
            System.out.println("New loan added successfully!!");
        } catch (Exception e) {
            System.out.println("failed to loan book");
            e.printStackTrace();
        }

    }

    // Connect to database to loan book by ID
    public static Books loanBookByID(int id) {


        String sql2 = "SELECT * FROM books WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt2 = conn.prepareStatement(sql2)) {

            stmt2.setInt(1, id);
            try (ResultSet rs = stmt2.executeQuery()) {
                if (rs.next()) {
                    Books book = new Books();
                    book.setId(rs.getInt("id"));
                    book.setTitle(rs.getString("Title"));
                    book.setAuthor(rs.getString("Author"));
                    book.setAvailable(rs.getBoolean("Available"));
                    return book;
                } else {
                    return null; // Return null if no book is found
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Connect to database to loan by book title
    public static Books loanBookByTitle(String title) {
        String sql = "SELECT * FROM books WHERE title = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Books book = new Books();
                    book.setId(rs.getInt("id"));
                    book.setTitle(rs.getString("Title"));
                    book.setAuthor(rs.getString("Author"));
                    book.setAvailable(rs.getBoolean("Available"));
                    return book;
                } else {
                    return null; // Return null if no book is found
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Connect to database to see all the books the user has borrowed
    public static List<UserLoanBook> DisplayMyLoanBooks(String userName) {
        List<UserLoanBook> resultList = new ArrayList<>();

        String sql = "SELECT books.id, books.title, books.author ,loans.loan_date, loans.return_date FROM  loans JOIN books ON loans.book_id = books.id WHERE loans.user_name = ? and books.available = false ";


        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userName);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                UserLoanBook row = new UserLoanBook();
                row.setId(rs.getInt("Id"));
                row.setTitle(rs.getString("Title"));
                row.setAuthor(rs.getString("Author"));
                row.setLoan_date(rs.getDate("Loan_date"));
                row.setReturn_date(rs.getDate("Return_date"));
                resultList.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultList;
    }

    public static void returnBook(int id) {
        String sql = "UPDATE loans JOIN books ON loans.book_id = books.id SET books.available = ? , loans.loan_date= ?, loans.return_date =?  WHERE books.id = ?";


        try {
            Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setBoolean(1, true);
            stmt.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            stmt.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            stmt.setInt(4, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("failed to return book");
            e.printStackTrace();
        }

        //  String sql1 = "UPDATE loans SET book_id = ? WHERE book_id = ?";
        String sql1 = "delete from loans where book_id=?";
        try (Connection conn1 = Database.getConnection();
             PreparedStatement stmt1 = conn1.prepareStatement(sql1)) {
            //  stmt1.setInt(1,0);
            stmt1.setInt(1, id);
            stmt1.executeUpdate();
            System.out.println("New Return added successfully!!");

        } catch (Exception e) {
            System.out.println("failed to return book");
            e.printStackTrace();
        }
    }


}
