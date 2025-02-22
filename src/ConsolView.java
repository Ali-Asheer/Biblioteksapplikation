import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ConsolView {
    private static final BookDAO bookDAO = new BookDAO();
    static Scanner scanner = new Scanner(System.in);

    // All menus and options
    public static void main(String[] args) {

        boolean userIsAdmin = false;
        boolean loginToAdmin = true;
        Scanner scanner = new Scanner(System.in);
        Integer adminsPassword = 1111;

        while (loginToAdmin) {

            System.out.print("Do you want to login as admin? (1) Yes / (2) No : ");
            String loginAdmin = scanner.next();
            if (Objects.equals(loginAdmin, "1")) {
                System.out.print("Password: ");
                Integer userAdminInput = scanner.nextInt();
                if (userAdminInput.equals(adminsPassword)) {
                    userIsAdmin = true;
                    loginToAdmin = false; //ends loginToAdmin loop if user types right password
                } else {
                    System.out.println("Wrong password. Try again : ");
                }
            } else if (Objects.equals(loginAdmin, "2")) {

                loginToAdmin = false;
            }
        }


        boolean showMenuLoop = true;
        while (showMenuLoop) {
            Integer userMenuChoice = null;
            System.out.println();
            System.out.println("---- Menu ----");
            if (!userIsAdmin) {
                System.out.println("1: Loan a book");
                System.out.println("2: Return a book");
                System.out.println("3: Display all books");
                System.out.println("4: Display my loan books");
                System.out.println("5: Quit");
            }
            if (userIsAdmin) {
                System.out.println("1: Add book");
                System.out.println("2: Remove book");
                System.out.println("3: Display all books");
                System.out.println("4: Search a books by author");
                System.out.println("5: Quit");
            }
            System.out.println();
            System.out.println("-- Choose a number --");
            while (true) {
                try {
                    userMenuChoice = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    throw new RuntimeException(e);
                }
            }

            switch (userMenuChoice) {
                case 1: {
                    if (userIsAdmin) {
                        addBook();
                    } else loanBook();
                    break;
                }

                case 2: {
                    if (userIsAdmin) {
                        deleteBook();
                    } else {
                        returnBook();
                    }
                    break;
                }

                case 3: {
                    viewBook();
                    break;
                }

                case 4: {
                    if (userIsAdmin) {
                        SearchBook();
                    } else {
                        System.out.print("Enter your name: ");
                        String userName = scanner.next();
                        DisplayMyLoanBooks(userName);
                    }
                    break;
                }
                case 5: {
                    System.out.println("Quitting program");
                    showMenuLoop = false;
                    break;
                }

            }
        }
    }

    // To search a book by author name
    public static void SearchBook() {
        String avail;
        System.out.print("Enter the book´s author you want to search: ");
        String author = scanner.nextLine();
        List<Books> books = BookDAO.searchBookWithAuthor(author);

        if (!books.isEmpty()) {
            for (Books searchBook : books) {
                if (searchBook.isAvailable()) {
                    avail = "Yes";
                } else {
                    avail = "No";
                }
                System.out.println("Book ID:" + searchBook.getId() + " .  Title:" + searchBook.getTitle() + " .  Author:" + searchBook.getAuthor() + " .  Available:" + avail);
            }
        } else {
            System.out.println("There is no such as author");
        }
    }

    // To add a book to the library
    public static void addBook() {
        System.out.print("Titel: ");
        String title = scanner.nextLine();
        System.out.print("Auther: ");
        String author = scanner.nextLine();
        bookDAO.addBook(title, author);
    }

    // To delete a book from the library
    private static void deleteBook() {
        System.out.print("Enter book ID to delete: ");
        int id = scanner.nextInt();
        bookDAO.deleteBook(id);
    }

    // To view all books in the library
    private static void viewBook() {
        String avail;
        List<Books> books = bookDAO.viewBook();

        for (Books book : books) {
            if (book.isAvailable()) {
                avail = "Yes";
            } else {
                avail = "No";
            }
            //   System.out.println( book.getId() + " | " + book.getTitle() + " | " + book.getAuthor()+ " | " + avail);
            System.out.println("Book ID:" + book.getId() + " .  Title:" + book.getTitle() + " .  Author:" + book.getAuthor() + " .  Available:" + avail);
        }

    }

    // Menu to loan a book by ID,Title,Author
    private static void loanBook() {
        boolean showMenu = true;

        System.out.print("Enter your name: ");
        String userName = scanner.next();

        while (showMenu) {
            String option = "loan";
            IdTitleMenu(userName, option);

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    loanBookById(userName);
                    showMenu = false;
                    break;
                case 2:
                    loanBookByTitle(userName);
                    showMenu = false;
                    break;
                case 3:
                    showMenu = false;
                    break;
            }
        }
    }

    // Menu to return a book by ID,Title
    private static void returnBook() {
        boolean showMenu = true;

        System.out.print("Enter your name: ");
        String userName = scanner.next();

        if (DisplayMyLoanBooks(userName)) {

            while (showMenu) {
                String option = "return";
                IdTitleMenu(userName, option);

                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        returnBookById(userName);
                        showMenu = false;
                        break;
                    case 2:
                        returnBookByTitle(userName);
                        showMenu = false;
                        break;
                    case 3:
                        showMenu = false;
                        break;

                }
            }
        }


    }

    // To loan a book by ID
    private static void loanBookById(String userName) {
        System.out.print("Enter the book´s ID you want to borrow: ");
        int id = scanner.nextInt();
        Books book = LoanDAO.loanBookByID(id);
        if (book != null) {

            if (book.isAvailable()) {
                LoanDAO.addloan(userName, book.getId());
            } else {
                System.out.println("This book is not available");
                loanBookById(userName);
            }
        } else {
            System.out.println("( There is no such as id )");

        }
    }

    // To loan a book by Title
    private static void loanBookByTitle(String userName) {
        System.out.print("Enter the book´s title you want to borrow: ");
        String title = scanner.nextLine();
        Books book = LoanDAO.loanBookByTitle(title);
        if (book != null) {

            if (book.isAvailable()) {
                LoanDAO.addloan(userName, book.getId());
            } else {
                System.out.println("This book is not available");
                loanBookByTitle(userName);
            }
        } else {
            System.out.println("( There is no such as title )");
        }


    }

    // To return a book by ID
    private static void returnBookById(String userName) {

        List<UserLoanBook> resultList = LoanDAO.DisplayMyLoanBooks(userName);

        System.out.print("Enter the book´s ID you want to return: ");
        int id = scanner.nextInt();
        boolean found = false;

        for (UserLoanBook obj : resultList) {
            if (obj.getId() == id) {
                found = true;
                break;
            }
        }

        if (found) {
            LoanDAO.returnBook(id);
        } else {
            System.out.println("( There is no such as id )");
        }


    }

    // To return a book by Title
    private static void returnBookByTitle(String userName) {

        List<UserLoanBook> resultList = LoanDAO.DisplayMyLoanBooks(userName);

        System.out.print("Enter the book´s Title you want to return: ");
        String titlee = scanner.nextLine();
        boolean found = false;
        int ID = 0;

        for (UserLoanBook obj : resultList) {

            if (Objects.equals(obj.getTitle(), titlee)) {
                found = true;
                ID = obj.getId();
                break;
            }
        }

        if (found) {
            LoanDAO.returnBook(ID);
        } else {
            System.out.println("( There is no such as title )");
        }


    }

    // To see all the books the user has borrowed
    private static Boolean DisplayMyLoanBooks(String userName) {
        boolean DidLoan = true;
        List<UserLoanBook> resultList = LoanDAO.DisplayMyLoanBooks(userName);

        if (!resultList.isEmpty()) {
            System.out.println();
            System.out.println("( " + userName + "'s list of borrowed book )");
            System.out.println("-----------------------------------------------------------------------------------------------------");
            for (UserLoanBook book1 : resultList) {

                System.out.println("Book ID:" + book1.getId() + " . Title:" + book1.getTitle() + " . Author:" + book1.getAuthor() + " . Loan Date:" + book1.getLoan_date() + " . Return Date:" + book1.getReturn_date());
            }
        } else {
            System.out.println("( " + userName + " did not borrow any book. )");
            DidLoan = false;
            return DidLoan;
        }

        return DidLoan;
    }

    // to select by id or title
    private static void IdTitleMenu(String userName, String option) {
        System.out.println("-----------------------------------------------------------------------------------------------------");
        System.out.println(" Hi " + userName + ". You can " + option + " a book by:");
        System.out.println("(1) Id");
        System.out.println("(2) Title");
        System.out.println("(3) *** To main menu ***");
        System.out.print(" Choose 1 or 2 or 3 : ");
    }
}


