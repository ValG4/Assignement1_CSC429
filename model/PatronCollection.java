package model; //in model folder

// project imports (just copied from account collection, no idea what these do)
import java.util.Properties;
import java.util.Vector;
//import javafx.scene.Scene;
import exception.InvalidPrimaryKeyException;
import event.Event;
import database.*;
import impresario.IView;
//import userinterface.View;
//import userinterface.ViewFactory;


public class PatronCollection
{
    private static final String myTableName = "Patron"; //establish database table name

    private Vector<Patron> PatronList; //create booklist

    public PatronCollection() {
        super(myTableName); //saw this in account collection, looked important
        patronList = new Vector<Patron>(); //constructor
    }


    public Vector<Patron> findPatronsOlderThanDate(String date) {
        // SQL Implementation here
        String query = ("SELECT * FROM " + myTableName + " WHERE (dateOfBirth < '" + date +"')"); //query to find the books older than given date
        Vector allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null) //iterting through result of table query to add to new vector
        {
            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
            {
                Properties nextPatron= (Properties)allDataRetrieved.elementAt(cnt); //properties inhereted from Entitybase

                patronList.add(new Patron(nextPatron));//update new booklist
            }
        }
        return patronList;
    }


    public Vector<Patron> findPatronsYoungerThan(String date) {
        // SQL Implementation here
        String query = ("SELECT * FROM " + myTableName + " WHERE (dateOfBirth > '" + date +"')"); //query to find the books older than given date
        Vector allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null) //iterting through result of table query to add to new vector
        {
            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
            {
                Properties nextPatron= (Properties)allDataRetrieved.elementAt(cnt); //properties inhereted from Entitybase
                patronList.add(new Patron(nextPatron));//update new booklist
            }
        }
        return patronList;
    }


    public Vector<Patron> findPatronsAtZipCode(String zip){

        String query = ("SELECT * FROM " + myTableName + " WHERE (zip > '" + zip +"')"); //query to find the books older than given date
        Vector allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null) //iterting through result of table query to add to new vector
        {
            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
            {
                Properties nextPatron= (Properties)allDataRetrieved.elementAt(cnt); //properties inhereted from Entitybase
                patronList.add(new Patron(nextPatron));//update new booklist
            }
        }
        return patronList;
    }

    public Vector<Patron> findPatronsWithNameLike(String name){
        // SQL Implementation here
        String query = "SELECT * FROM " + myTableName + " WHERE name LIKE '%" + name + "%'"; //sql query to find books written by same author
        Vector allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null) //iterting through result of table query to add to new vector
        {
            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
            {
                Properties nextBook= (Properties)allDataRetrieved.elementAt(cnt); //properties inhereted from Entitybase
                patronList.add(new Book(nextBook));//update booklist
            }
        }
        return patronList;
    }
}