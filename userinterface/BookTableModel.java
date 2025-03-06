package userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

public class BookTableModel {
    private final SimpleStringProperty BookId;
    private final SimpleStringProperty pubYear;
    private final SimpleStringProperty author;
    private final SimpleStringProperty BookTitle;
    private final SimpleStringProperty status;

    //----------------------------------------------------------------------------
    public AccountTableModel(Vector<String> accountData)
    {
        BookId =  new SimpleStringProperty(accountData.elementAt(1));
        pubYear =  new SimpleStringProperty(accountData.elementAt(3));
        author =  new SimpleStringProperty(accountData.elementAt(0));
        BookTitle =  new SimpleStringProperty(accountData.elementAt(2));
        status = new SimpleStringProperty(accountData.elementAt(4));
    }

    //----------------------------------------------------------------------------
    public String getBookId() {
        return BookId.get();
    }

    public String getPubYear() {
        return pubYear.get();
    }

    //----------------------------------------------------------------------------
    public void setPubYear(String number) {
        pubYear.set(number);
    }

    //----------------------------------------------------------------------------
    public String getAuthor() {
        return author.get();
    }

    //----------------------------------------------------------------------------
    public void setAuthor(String aType) {
        author.set(aType);
    }

    //----------------------------------------------------------------------------
    public String getBookTitle() {
        return BookTitle.get();
    }

    //----------------------------------------------------------------------------
    public void setBookTitle(String bal) {
        BookTitle.set(bal);
    }

    //----------------------------------------------------------------------------
    public String getStatus() {
        return status.get();
    }

    //----------------------------------------------------------------------------
    public void setStatus(String charge)
    {
        status.set(charge);
    }
}
