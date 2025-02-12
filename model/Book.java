package model;

import java.util.Properties;
import java.util.Vector;
import java.util.Enumeration;
import java.sql.*;

import exception.InvalidPrimaryKeyException;
import exception.PasswordMismatchException;

public abstract class Book extends EntityBase{
    private static String table_name = "Book";

    protected Properties dependencies;
    private String updateStatusMessage = "";

    public Book (String query_id) throws InvalidPrimaryKeyException, PasswordMismatchException {
        super();

        String query = "SELECT * FROM" + table_name + " WHERE (ID= " + query_id + ")";

        Vector dataRetrieved = getSelectQueryResult(query);

        if (dataRetrieved != null){
            int size = dataRetrieved.size();

            if (size !=1){
                throw new InvalidPrimaryKeyException();
            }else{
                Properties retrievedBookData = (Properties)dataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedBookData.propertyNames();
                while(allKeys.hasMoreElements() == true){
                    String nextKey = (String)allKeys.nextElement();
                    String nextValue = retrievedBookData.getProperty(nextKey);

                    if(nextValue != null){
                        persistentState.setProperty(nextKey, nextValue);

                    }
                }
            }
        }else{
            throw new InvalidPrimaryKeyException("More than one value associated with that key");
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


        public boolean verifyOwnership(PatronCollection Patron)
        {
            if (Patron == null)
            {
                return false;
            }
            else
            {
                String PatronID = (String)Patron.getState("ID");
                String myOwnerid = (String)getState("OwnerID");
                // DEBUG System.out.println("Account: custid: " + custid + "; ownerid: " + myOwnerid);

                return (PatronID.equals(myOwnerid));
            }
        }


        public void update() // save()
        {
            updateStateInDatabase();
        }



        private void updateStateInDatabase()
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
                    Integer BookId =
                            insertAutoIncrementalPersistentState(mySchema, persistentState);
                    persistentState.setProperty("BookId", "" + BookId.intValue());
                    updateStatusMessage = "Book data for new Book : " +  persistentState.getProperty("BookId")
                            + "installed successfully in database!";
                }
            }
            catch (SQLException ex)
            {
                updateStatusMessage = "Error in installing Book data in database!";
            }
            //DEBUG System.out.println("updateStateInDatabase " + updateStatusMessage);
        }
    }
}