## Library Application

 I have created a simple library application using Java, JDBC and MySQL. The application allows users to borrow and return books and view their loans. Administrators  able to add and remove books from the library catalog and view the loan status of all books

The application starts by asking the user if he wants to log in as an administrator.
If the user answers Yes, he will be asked to enter the password. After verifying the correctness of the entry, a list of options for the administrator will appear, which includes:
-  Add book.
-  Remove book.
-  Display all books.
-  Search a books by author.
-  Quit.

And if the user answers No, he will log in as a user. A list of options for the user will appear, which includes:
- Loan a book.
- Return a book.
- Display all books.
- Display my loan books.
- Quit.

When borrowing and returning, this is done by ID and the title of the book. And display all books for administrator contains more details than user.
