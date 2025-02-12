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
        patronList = new Vector<Patron>(); //constructor
    }


    public Vector<Patron> findPatronsOlderThanDate(String date) {
        // SQL Implementation here
        String query = ("SELECT * FROM " + myTableName + " WHERE (dateOfBirth < '" + date +"')"; //query to find the books older than given date
        Vector allDataRetrieved = getSelectQueryResult(query);
        return processPatronData(allDataRetrieved);

    }


    public Vector<Patron> findPatronsYoungerThan(String date) {
        // SQL Implementation here
        String query = ("SELECT * FROM " + myTableName + " WHERE (dateOfBirth > '" + date +"')"; //query to find the books older than given date
        Vector allDataRetrieved = getSelectQueryResult(query);
        return processPatronData(allDataRetrieved);

    }


    public Vector<Patron> findPatronsAtZipCode(String zip){
        // SQL Implementation here
        String query = ("SELECT * FROM " + myTableName + " WHERE (zip > '" + zip +"')"; //query to find the books older than given date
        Vector allDataRetrieved = getSelectQueryResult(query);
        return processPatronData(allDataRetrieved);

    }

    public Vector<Patron> findPatronsWithNameLike(String name){
        // SQL Implementation here
        String query = "SELECT * FROM " + myTableName + " WHERE name LIKE '%" + name + "%'"; //sql query to find books written by same author
        Vector allDataRetrieved = getSelectQueryResult(query);
        return processPatronData(allDataRetrieved);
    }

    private Vector<Patron> processPatronData(Vector<Patron> allDataRetrieved) {
        if (allDataRetrieved != null) {
            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++) {
                Properties nextPatron = (Properties) allDataRetrieved.elementAt(cnt);
                patronList.add(new Patron(nextPatron));
            }
        }
        return patronList;
    }
}