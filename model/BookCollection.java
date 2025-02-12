package model; //in model folder

// project imports (just copied from account collection, no idea what these do)
import java.util.Properties;
import java.util.Vector;
import javafx.scene.Scene;
import exception.InvalidPrimaryKeyException;
import event.Event;
import database.*;
import impresario.IView;
import userinterface.View;
import userinterface.ViewFactory;


public class BookCollection extends EntityBase implements IView
{
    private static final String myTableName = "Book"; //establish database table name

    private Vector<Book> bookList; //create booklist

    public BookCollection() {
        super(myTableName); //saw this in account collection, looked important
        bookList = new Vector<Book>(); //constructor
    }

    public Vector<Book> findBooksOlderThanDate(String year) {
        // SQL Implementation here
        String query = ("SELECT * FROM " + myTableName + " WHERE (pubYear < '" + year +"')"; //query to find the books older than given date
        Vector allDataRetrieved = getSelectQueryResult(query);
        return processBookData(allDataRetrieved);

    }

    public Vector<Book> findBooksNewerThanDate(String year) {
        // SQL Implementation here                                          IF THE COLUMN/DATABASE USES A VARCHAR, we need single quotes ' instead of " (see photo from class)
        String query = ("SELECT * FROM " + myTableName + " WHERE (pubYear > '" + year +"')"; //query to find the books older than given date
        Vector allDataRetrieved = getSelectQueryResult(query);
        return processBookData(allDataRetrieved);

    }

 //CONFIGURE DBCONFIG.INI HAS THE RIGHT INFORMATION IN IT (IN INSTALL DIRECTORY OF INTELLIJ OR PROJECT)

    public findBooksWithTitleLike(String title) {
        // SQL Implementation here
        String query = "SELECT * FROM " + myTableName + " WHERE bookTitle LIKE '%" + title + "%'"; //sql query to find book titles similar to given string
        Vector allDataRetrieved = getSelectQueryResult(query);
        return processBookData(allDataRetrieved);
    }

    public findBooksWithAuthorLike(String author) {
        // SQL Implementation here
        String query = "SELECT * FROM " + myTableName + " WHERE author LIKE '%" + author + "%'"; //sql query to find books written by same author
        Vector allDataRetrieved = getSelectQueryResult(query);
        return processBookData(allDataRetrieved);

    }
    private Vector<Book> processBookData(Vector<Book> allDataRetrieved) {
        if (allDataRetrieved != null) {
            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++) {
                Properties nextBook= (Properties) allDataRetrieved.elementAt(cnt); //Properties is attribute of EntityBase.java, nextbook is variable declaration
                bookList.add(new Book(nextBook));
            }
        }
        return bookList;
    }
}