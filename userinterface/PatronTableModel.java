package userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

public class PatronTableModel {
    private final SimpleStringProperty address;
    private final SimpleStringProperty city;
    private final SimpleStringProperty dateOfBirth;
    private final SimpleStringProperty email;
    private final SimpleStringProperty name;
    private final SimpleStringProperty patronId;
    private final SimpleStringProperty stateCode;
    private final SimpleStringProperty status;
    private final SimpleStringProperty zip;

    //----------------------------------------------------------------------------
    public AccountTableModel(Vector<String> accountData)
    {
        address =  new SimpleStringProperty(accountData.elementAt(0));
        city =  new SimpleStringProperty(accountData.elementAt(1));
        dateOfBirth =  new SimpleStringProperty(accountData.elementAt(2));
        email =  new SimpleStringProperty(accountData.elementAt(3));
        name = new SimpleStringProperty(accountData.elementAt(4));
        patronId = new SimpleStringProperty(accountData.elementAt(5));
        stateCode = new SimpleStringProperty(accountData.elementAt(6));
        status = new SimpleStringProperty(accountData.elementAt(7));
        zip = new SimpleStringProperty(accountData.elementAt(8));
    }

    //----------------------------------------------------------------------------
    public String getPatronId() {
        return patronId.get();
    }

    public String getAddress() {
        return address.get();
    }

    //----------------------------------------------------------------------------
    public void setAddress(String number) {
        address.set(number);
    }

    //----------------------------------------------------------------------------
    public String getCity() {
        return city.get();
    }

    //----------------------------------------------------------------------------
    public void setCity(String aType) {
        city.set(aType);
    }

    //----------------------------------------------------------------------------
    public String getDateOfBirth() {
        return dateOfBirth.get();
    }

    //----------------------------------------------------------------------------
    public void setDateOfBirth(String bal) {
        dateOfBirth.set(bal);
    }

    //----------------------------------------------------------------------------
    public String getEmail() {
        return email.get();
    }

    //----------------------------------------------------------------------------
    public void setEmail(String charge)
    {
        email.set(charge);
    }


}
