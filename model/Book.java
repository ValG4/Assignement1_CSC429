package model;

import java.util.*;
import java.sql.*;

import com.sun.javafx.css.StyleCacheEntry;
import database.*;

import exception.InvalidPrimaryKeyException;
import exception.PasswordMismatchException;
import javafx.scene.Scene;
import javafx.stage.Stage;
import userinterface.BookView;
import userinterface.View;
import userinterface.MainStageContainer;
import model.Librarian;
import userinterface.*;

public class Book extends EntityBase{
    private static String table_name = "Book";
    protected Properties dependencies;
    protected Properties persistentState;
    private String updateStatusMessage = "";

    protected Librarian myLibrarian;
    protected Stage myStage;
    //----------------------------------------------------------
    //Constructor taking in new data to create a new Book
    //----------------------------------------------------------
    public Book(Librarian lib)
    {
        super(table_name);
        persistentState = new Properties();
        myStage = MainStageContainer.getInstance();
        myLibrarian = lib;
    }

    public Book (String query_id) throws InvalidPrimaryKeyException{
        super(table_name);

        String query = "SELECT * FROM " + table_name + " WHERE (ID= " + query_id + ")";

        Vector<Properties> dataRetrieved = getSelectQueryResult(query);

        if (dataRetrieved != null) {
            int size = dataRetrieved.size();

            if (size != 1) {
                throw new InvalidPrimaryKeyException("More than one entry with that primary key");
            } else {
                Properties retrievedBookData = (Properties) dataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedBookData.propertyNames();
                while (allKeys.hasMoreElements() == true) {
                    String nextKey = (String) allKeys.nextElement();
                    String nextValue = retrievedBookData.getProperty(nextKey);

                    if (nextValue != null) {
                        persistentState.setProperty(nextKey, nextValue);

                    }
                }
            }
        } else {
            throw new InvalidPrimaryKeyException("More than one value associated with that key");
        }
    }
    public Book(Properties props){
        super(table_name);

        setDependencies();

        persistentState = new Properties();
        Enumeration allKeys = props.propertyNames();
        while (allKeys.hasMoreElements() == true){
            String nextKey = (String)allKeys.nextElement();
            String nextValue = props.getProperty(nextKey);
            if(nextValue != null){
                persistentState.setProperty(nextKey, nextValue);
            }
        }
    }

    public void processNewBook(Properties p) {
        // Set the book data from the Properties object into the persistentState
        persistentState = new Properties();
        persistentState.setProperty("bookTitle", p.getProperty("bookTitle"));
        persistentState.setProperty("author", p.getProperty("author"));
        persistentState.setProperty("pubYear", p.getProperty("pubYear"));
        persistentState.setProperty("status", p.getProperty("status"));

        // Now that the book data is set, insert the book into the database
        try {
            // Call the method to update the database
            updateStateInDatabase();

            // If successful, display a success message
            System.out.println("Book successfully added to the database!");
        } catch (Exception ex) {
            // If an error occurs during database insertion, display an error message
            System.out.println("Failed to add book to the database.");
            ex.printStackTrace();
        }
    }

    private void setDependencies(){
        dependencies = new Properties();
        myRegistry.setDependencies(dependencies);
    }

    public Object getState(String key){
        if (key.equals("UpdateStatusMessage") == true)
            return updateStatusMessage;
        return persistentState.getProperty(key);
    }

    public void stateChangeRequest(String key, Object value)
    {

        myRegistry.updateSubscribers(key, this);
    }

    public static int compare(Book a, Book b)
    {
        String aNum = (String)a.getState("BookId");
        String bNum = (String)b.getState("BookId");

        return aNum.compareTo(bNum);
    }

    public Vector<String> getEntryListView()
    {
        Vector<String> v = new Vector<String>();

        v.addElement(persistentState.getProperty("bookId"));
        v.addElement(persistentState.getProperty("bookTitle"));
        v.addElement(persistentState.getProperty("author"));
        v.addElement(persistentState.getProperty("pubYear"));
        v.addElement(persistentState.getProperty("status"));

        return v;
    }

    public void createAndShowBookView() {

        Scene currentScene = (Scene)myLibrarian.myViews.get("BookView");

        if (currentScene == null) {

            View newView = new BookView(this);
            currentScene = new Scene(newView);
            myLibrarian.myViews.put("BookView", currentScene);
        }
        myLibrarian.swapToView(currentScene);
    }


    public void updateStateInDatabase()
    {
        try
        {
            if (persistentState.getProperty("BookId") != null)
            {
                // update
                Properties whereClause = new Properties();
                whereClause.setProperty("BookId",
                        persistentState.getProperty("BookId"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Book data for book id : " + persistentState.getProperty("AccountNumber") + " updated successfully in database!";
            }
            else
            {
                // insert


                Integer BookId = insertAutoIncrementalPersistentState(mySchema, persistentState);


                persistentState.setProperty("BookId", "" + BookId.intValue());
                updateStatusMessage = "Book data for new Book : " +  persistentState.getProperty("BookId") + "installed successfully in database!";
            }
        }
        catch (SQLException ex)
        {
            updateStatusMessage = "Error in installing Book data in database!";
        }
        //DEBUG System.out.println("updateStateInDatabase " + updateStatusMessage);
    }

    protected void initializeSchema(String table_name){
        if(mySchema == null){
            mySchema = getSchemaInfo(table_name);
        }
    }
}
