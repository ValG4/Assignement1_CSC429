package model;


import java.util.Properties;
import java.util.Vector;
import java.util.Enumeration;
import java.sql.*;

import database.*;
import exception.InvalidPrimaryKeyException;
import exception.PasswordMismatchException;
import javafx.scene.Scene;
import javafx.stage.Stage;
import userinterface.PatronView;
import userinterface.View;

public class Patron extends EntityBase{
    private static String table_name = "Patron";

    protected Properties persistentState;
    protected Properties dependencies;
    private String updateStatusMessage = "";

    protected Librarian myLibrarian;
    protected Stage myStage;


    //----------------------------------------------------------
    //Constructor used by librarian
    //----------------------------------------------------------
    public Patron(Librarian lib)
    {
        super(table_name);
        myLibrarian = lib;
        persistentState = new Properties();

    }

    public Patron (String query_id) throws InvalidPrimaryKeyException, PasswordMismatchException {
        super(table_name);

        String query = "SELECT * FROM" + table_name + " WHERE (ID= " + query_id + ")";

        Vector<Properties> dataRetrieved = getSelectQueryResult(query);

        if (dataRetrieved != null){
            int size = dataRetrieved.size();

            if (size !=1) throw new InvalidPrimaryKeyException("Wrong number of primary keys");
            else{
                Properties retrievedPatronData = (Properties)dataRetrieved.elementAt(0);
                //persistentState = new Properties();

                Enumeration allKeys = retrievedPatronData.propertyNames();
                while(allKeys.hasMoreElements() == true){
                    String nextKey = (String)allKeys.nextElement();
                    String nextValue = retrievedPatronData.getProperty(nextKey);

                    if(nextValue != null){
                        //persistentState.setProperty(nextKey, NextValue);

                    }
                }
            }
        }else{
            throw new InvalidPrimaryKeyException("More than one value associated with that key");
        }

    }

    public void createAndShowPatronView() {

        Scene currentScene = (Scene)myLibrarian.myViews.get("PatronView");

        if (currentScene == null) {

            View newView = new PatronView(this);
            currentScene = new Scene(newView);
            myLibrarian.myViews.put("PatronView", currentScene);
        }
        myLibrarian.swapToView(currentScene);
    }

    public Patron(Properties props){
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
        String aNum = (String)a.getState("PatronId");
        String bNum = (String)b.getState("PatronId");

        return aNum.compareTo(bNum);
    }

    public Vector<String> getEntryListView()
    {
        Vector<String> v = new Vector<String>();

        v.addElement(persistentState.getProperty("PatronId"));
        v.addElement(persistentState.getProperty("address"));
        v.addElement(persistentState.getProperty("city"));
        v.addElement(persistentState.getProperty("dateOfBirth"));
        v.addElement(persistentState.getProperty("email"));
        v.addElement(persistentState.getProperty("name"));
        v.addElement(persistentState.getProperty("stateCode"));
        v.addElement(persistentState.getProperty("status"));
        v.addElement(persistentState.getProperty("zip"));

        return v;
    }


    public void updateStateInDatabase()
    {
        try
        {
            if (persistentState.getProperty("PatronId") != null)
            {
                // update
                Properties whereClause = new Properties();
                whereClause.setProperty("PatronId",
                        persistentState.getProperty("PatronId"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Patron data for Patron id : " + persistentState.getProperty("PatronId") + " updated successfully in database!";
            }
            else
            {
                // insert
                Integer PatronId =
                        insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("PatronId", "" + PatronId.intValue());
                updateStatusMessage = "Patron data for new Patron : " +  persistentState.getProperty("PatronId")
                        + "installed successfully in database!";
            }
        }
        catch (SQLException ex)
        {
            updateStatusMessage = "Error in installing Patron data in database!";
        }
        //DEBUG System.out.println("updateStateInDatabase " + updateStatusMessage);
    }

    protected void initializeSchema(String table_name){
        if(mySchema == null){
            mySchema = getSchemaInfo(table_name);
        }
    }
}