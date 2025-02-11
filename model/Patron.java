import java.util.Properties;
import java.util.Vector;
import java.util.Enumeration;
import java.sql.*;

import database.*;

public class Patron {
    private static String table_name = "Patron";



    public Patron (String query_id) throws InvalidPrimaryKeyException, PasswordMismatchException{
        super(table_name);

        String query = "SELECT * FROM" + patron + " WHERE (ID= " + query_Id + ")";

        Vector dataRetrieved = getSelectQueryResult(query);

        if (dataRetrieved != null){
            int size = dataRetrieved.size();

            if (size !=1){
                throw new InvalidPrimaryKeyException();
            }else{
                Properties retrievedPatronData = (Properties)dataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedPatronData.propertyNames();
                while(allKeys.hasMoreElements() == true){
                    String nextKey = (String)allKeys.nextElement();
                    String nextValue = retrievedPatronData.getProperty(nextKey);

                    if(nextValue != null){
                        persistentState.setProperty(nextKey, NextValue);

                    }
                }
            }
        }else{
            throw new InvalidPrimaryKeyException();
        }

    }
}