import database.JDBCBroker;
import exception.InvalidPrimaryKeyException;
import exception.PasswordMismatchException;
import model.Book;
import model.BookCollection;
import model.Patron;
import model.PatronCollection;

import java.util.*;

public class Main{
    public static void main(String[] args) throws PasswordMismatchException, InvalidPrimaryKeyException {
        System.out.println("Book system V1");

        JDBCBroker db = new JDBCBroker();
        db.getConnection();

        Book model_book = new Book();
        BookCollection book_col = new BookCollection();
        Patron model_patron = new Patron();
        PatronCollection patron_col = new PatronCollection();



    }

}