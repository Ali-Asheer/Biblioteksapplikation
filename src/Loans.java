import java.sql.Date;

public class Loans {

    private int id;
    private String user_name;
    private int book_id;
    private Date loan_date;
    private Date return_date;
    private boolean available;

    public Loans(int id, String user_name, int book_id, Date loan_date, Date return_date, boolean available) {
        this.id = id;
        this.user_name = user_name;
        this.book_id = book_id;
        this.loan_date = loan_date;
        this.return_date = return_date;
        this.available = available;
    }

    public Loans() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public Date getLoan_date(Date loanDate) {
        return loan_date;
    }

    public void setLoan_date(Date loan_date) {
        this.loan_date = loan_date;
    }

    public Date getReturn_date(Date returnDate) {
        return return_date;
    }

    public void setReturn_date(Date return_date) {
        this.return_date = return_date;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Loans{" +
                "id=" + id +
                ", user_name='" + user_name + '\'' +
                ", book_id=" + book_id +
                ", loan_date=" + loan_date +
                ", return_date=" + return_date +
                '}';
    }
}
