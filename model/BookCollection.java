package model; //in model folder

// project imports (just copied from account collection, no idea what these do)
import java.util.*;

import exception.PasswordMismatchException;
import javafx.scene.Scene;
import exception.InvalidPrimaryKeyException;
import event.Event;
import database.*; //all files from database package
import impresario.IView;
import javafx.stage.Stage;
import userinterface.BookCollectionView;
import userinterface.MainStageContainer;
import userinterface.View;
//import userinterface.View;
//import userinterface.ViewFactory;


public class BookCollection extends EntityBase
{
    private static final String myTableName = "Book"; //establish database table name

    private Vector<Book> bookList; //create booklist

    protected Librarian myLibrarian;
    protected Stage myStage;
    protected String bookTitle;

    //----------------------------------------------------------
    //Constructor for use with Librarian
    //----------------------------------------------------------
    public BookCollection(Librarian lib) {	//String title
        super(myTableName);
        myStage = MainStageContainer.getInstance();
        myLibrarian = lib;
        bookList = new Vector<Book>();
    }

    public BookCollection() {
        super(myTableName); //saw this in account collection, looked important
        bookList = new Vector<Book>(); //constructor
    }

    public void createAndShowBookCollectionView() {

        Scene currentScene = (Scene)myLibrarian.myViews.get("BookCollectionView");

        if (currentScene == null) {

            View newView = new BookCollectionView(this);
            currentScene = new Scene(newView);
            myLibrarian.myViews.put("BookCollectionView", currentScene);
        }
        myLibrarian.swapToView(currentScene);
    }

    public Vector<Book> findBooksOlderThanDate(String year) {
        // SQL Implementation here
        String query = "SELECT * FROM " + myTableName + " WHERE (pubYear < '" + year +"')"; //query to find the books older than given date
        Vector allDataRetrieved = getSelectQueryResult(query);
        return processBookData(allDataRetrieved);

    }

    public Vector<Book> findBooksNewerThanDate(String year) {
        // SQL Implementation here                                          IF THE COLUMN/DATABASE USES A VARCHAR, we need single quotes ' instead of " (see photo from class)
        String query = "SELECT * FROM " + myTableName + " WHERE (pubYear > '" + year +"')"; //query to find the books older than given date
        Vector allDataRetrieved = getSelectQueryResult(query);
        return processBookData(allDataRetrieved);

    }

 //CONFIGURE DBCONFIG.INI HAS THE RIGHT INFORMATION IN IT (IN INSTALL DIRECTORY OF INTELLIJ OR PROJECT)

    public Vector<Book> findBooksWithTitleLike(String title) {
        // SQL Implementation here
        String query = "SELECT * FROM " + myTableName + " WHERE bookTitle LIKE '%" + title + "%'"; //sql query to find book titles similar to given string
        Vector allDataRetrieved = getSelectQueryResult(query);
        return processBookData(allDataRetrieved);
    }

    public Vector<Book> findBooksWithAuthorLike(String author) {
        // SQL Implementation here
        String query = "SELECT * FROM " + myTableName + " WHERE author LIKE '%" + author + "%'"; //sql query to find books written by same author
        Vector allDataRetrieved = getSelectQueryResult(query);
        return processBookData(allDataRetrieved);

    }





    private Vector<Book> processBookData(Vector allDataRetrieved)  {
        if (allDataRetrieved != null) {
            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++) {
                Properties nextBook=(Properties)allDataRetrieved.elementAt(cnt); //Properties are attribute of EntityBase.java, nextbook is variable declaratio
                bookList.add(new Book(nextBook));
            }
        }
        return bookList;
    }

    //Imported from AccountCollection/EntityBase to avoid having to use abstract





    protected void initializeSchema(String tableName)
    {
        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }

    public Object getState(String key) {
        if (key.equals("Books"))
            return bookList;
        else
        if (key.equals("BookList")) {
            return this;
        }
        return null;
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value) {

        myRegistry.updateSubscribers(key, this);
    }

}