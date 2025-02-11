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
        bookList = new Vector(); //constructor
    }

    public Vector<Book> findBooksOlderThanDate(String year) {
        // SQL Implementation here
        String query = ("SELECT * FROM " + Book + " WHERE (pubYear < ? " + year +")"; //query to find the books older than given date
        Vector allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null) //iterting through result of table query to add to new vector
        {
            Vector<Book> OlderBooks = new Vector<Book>(); //create vector of books to return result

            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
            {
                Properties nextBook= (Properties)allDataRetrieved.elementAt(cnt); //properties inhereted from Entitybase

                OlderBooks.add(new Book(nextBook));//update new booklist
            }
        }
        return OlderBooks;
    }





    public Vector<Book> findBooksNewerThanDate(String year) {
        // SQL Implementation here
        String query = ("SELECT * FROM " + Book + " WHERE (pubYear > ? " + year +")"; //query to find the books older than given date
        Vector allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null) //iterting through result of table query to add to new vector
        {
            Vector<Book> NewerBooks = new Vector<Book>(); //create vector of books to return result

            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
            {
                Properties nextBook= (Properties)allDataRetrieved.elementAt(cnt); //properties inhereted from Entitybase

                NewerBooks.add(new Book(nextBook));//update new booklist
            }
        }
        return NewerBooks;
    }



    public findBooksWithTitleLike(String title) {
        // SQL Implementation here
        return null;
    }

    public findBooksWithAuthorLike(String author) {
        // SQL Implementation here
        return null;
    }
}