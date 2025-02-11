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


public class PatronCollection extends EntityBase implements IView
{
    private static final String myTableName = "Patron"; //establish database table name

    private Vector<Patron> PatronList; //create booklist

    public PatronCollection() {
        super(myTableName); //saw this in account collection, looked important
        bookList = new Vector(); //constructor
    }


    public Vector<Patron> findPatronsOlderThanDate(String date) {
        // SQL Implementation here
        String query = ("SELECT * FROM " + Patron + " WHERE (dateOfBirth < ? " + date +")"; //query to find the books older than given date
        Vector allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null) //iterting through result of table query to add to new vector
        {
            Vector<Patron> OlderPatrons = new Vector<Book>(); //create vector of books to return result

            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
            {
                Properties nextBook= (Properties)allDataRetrieved.elementAt(cnt); //properties inhereted from Entitybase

                OlderBooks.add(new Book(nextBook));//update new booklist


            }
        }
        return OlderBooks;
    }


    public Vector<Patron> findPatronsYoungerThan(String date) {
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



    public Vector<Patron> findPatronsAtZipCode(String zip){
        // SQL Implementation here
        return null;
    }

    public Vector<Patron> findPatronsWithNameLike(String name){
        // SQL Implementation here
        return null;
    }
}