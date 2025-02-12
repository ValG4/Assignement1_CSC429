import java.util.Properties;
import java.util.Vector;
import java.util.Enumeration;
import java.sql.*;

import database.*;

public class Book {
    private static String table_name = "Book";

    public Book (String query_id) throws InvalidPrimaryKeyException, PasswordMismatchException{
        super(table_name);

        String query = "SELECT * FROM" + Book + " WHERE (ID= " + query_id + ")";

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
                        persistentState.setProperty(nextKey, NextValue);

                    }
                }
            }
        }else{
            throw new InvalidPrimaryKeyException("More than one value associated with that key");
        }

    }
}