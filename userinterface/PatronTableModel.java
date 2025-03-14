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
    public PatronTableModel(Vector<String> accountData)
    {
        address =  new SimpleStringProperty(accountData.elementAt(1));
        city =  new SimpleStringProperty(accountData.elementAt(2));
        dateOfBirth =  new SimpleStringProperty(accountData.elementAt(3));
        email =  new SimpleStringProperty(accountData.elementAt(4));
        name = new SimpleStringProperty(accountData.elementAt(5));
        patronId = new SimpleStringProperty(accountData.elementAt(0));
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

    public String getName() {
        return name.get();
    }

    //----------------------------------------------------------------------------
    public void setName(String charge)
    {
        name.set(charge);
    }

    public String getStateCode() {
        return stateCode.get();
    }

    //----------------------------------------------------------------------------
    public void setStateCode(String charge)
    {
        stateCode.set(charge);
    }

    public String getStatus() {
        return status.get();
    }

    //----------------------------------------------------------------------------
    public void setStatus(String charge)
    {
        status.set(charge);
    }

    public String getZip() {
        return zip.get();
    }

    //----------------------------------------------------------------------------
    public void setZip(String charge)
    {
        zip.set(charge);
    }


}
