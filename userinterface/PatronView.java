// specify the package
package userinterface;

// system imports
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Properties;

// project imports
import impresario.IModel;
import model.*;

/** The class containing the Account View  for the ATM application */
//==============================================================
public class PatronView extends View
{

    // GUI components
    protected TextField address;
    protected TextField city;
    protected TextField dateOfBirth;
    protected TextField email;
    protected TextField name;
    protected TextField stateCode;
    protected TextField zip;

    protected Button cancelButton;
    protected Button submitButton;
    protected ComboBox<String> status;



    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public PatronView(IModel account)
    {
        super(account, "PatronView");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // Add a title for this panel
        container.getChildren().add(createTitle());

        // create our GUI components, add them to this Container
        container.getChildren().add(createFormContent());

        container.getChildren().add(createStatusLog("             "));

        getChildren().add(container);

        populateFields();

        myModel.subscribe("ServiceCharge", this);
        myModel.subscribe("UpdateStatusMessage", this);
    }


    // Create the title container
    //-------------------------------------------------------------
    private Node createTitle()
    {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        Text titleText = new Text(" Brockport Library/PAtronView ");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setWrappingWidth(300);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);
        container.getChildren().add(titleText);

        return container;
    }

    // Create the main form content
    //-------------------------------------------------------------
    private VBox createFormContent()
    {
        VBox vbox = new VBox(10);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
//        he name, address, city, stateCode, zip, email and dateOfBirth fields
//        should not be empty, and that the dateOfBirth field should have a value between
//‘1920-01-01’ and ‘2006-01-01’ (we want a patron to be at least 18 as of January
//        1).
        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);


        Text prompt = new Text("PATRON INFORMATION");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);  // This spans 2 columns, so it stays at the top

        Text nameLabel = new Text(" Name : ");
        nameLabel.setFont(myFont);
        nameLabel.setWrappingWidth(150);
        nameLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(nameLabel, 0, 1);  // Move this to row 1
        name = new TextField();
        name.setEditable(true);
        grid.add(name, 1, 1); // Put the input field in the same row

// Address Field
        Text addressLabel = new Text(" Address : ");
        addressLabel.setFont(myFont);
        addressLabel.setWrappingWidth(150);
        addressLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(addressLabel, 0, 2);  // Row 2
        address = new TextField();
        address.setEditable(true);
        grid.add(address, 1, 2);

// City Field
        Text cityLabel = new Text(" City : ");
        cityLabel.setFont(myFont);
        cityLabel.setWrappingWidth(150);
        cityLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(cityLabel, 0, 3);  // Row 3
        city = new TextField();
        city.setEditable(true);
        grid.add(city, 1, 3);

// State Code Field
        Text stateCodeLabel = new Text(" State Code : ");
        stateCodeLabel.setFont(myFont);
        stateCodeLabel.setWrappingWidth(150);
        stateCodeLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(stateCodeLabel, 0, 4);  // Row 4
        stateCode = new TextField();
        stateCode.setEditable(true);
        grid.add(stateCode, 1, 4);

// Zip Field
        Text zipLabel = new Text(" Zip : ");
        zipLabel.setFont(myFont);
        zipLabel.setWrappingWidth(150);
        zipLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(zipLabel, 0, 5);  // Row 5
        zip = new TextField();
        zip.setEditable(true);
        grid.add(zip, 1, 5);

// Email Field
        Text emailLabel = new Text(" Email : ");
        emailLabel.setFont(myFont);
        emailLabel.setWrappingWidth(150);
        emailLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(emailLabel, 0, 6);  // Row 6
        email = new TextField();
        email.setEditable(true);
        grid.add(email, 1, 6);

// Date of Birth Field
        Text dobLabel = new Text(" Date Of Birth : ");
        dobLabel.setFont(myFont);
        dobLabel.setWrappingWidth(150);
        dobLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(dobLabel, 0, 7);  // Row 7
        dateOfBirth = new TextField();
        dateOfBirth.setEditable(true);
        grid.add(dateOfBirth, 1, 7);

        vbox.getChildren().add(grid);

        Text statusLabel = new Text(" Status : ");
        statusLabel.setFont(myFont);
        statusLabel.setWrappingWidth(150);
        statusLabel.setTextAlignment(TextAlignment.RIGHT);
        status = new ComboBox<String>();
        status.getItems().addAll("Available", "Checked Out");  // Add the status options
        status.setValue("Available");  // Set the default value to "Active"
        status.setEditable(false);

        grid.add(statusLabel, 0, 8);
        grid.add(status, 1, 8);

        HBox doneCont = new HBox(10);
        doneCont.setAlignment(Pos.CENTER);
        cancelButton = new Button("Back");
        cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                goToHomeView();
            }
        });
        doneCont.getChildren().add(cancelButton);
        vbox.getChildren().add(doneCont);


        HBox subCont = new HBox(10);
        subCont.setAlignment(Pos.CENTER_LEFT);
        submitButton = new Button("Submit");
        submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                processAction();
            }
        });
        subCont.getChildren().add(submitButton);
        vbox.getChildren().add(subCont);



        return vbox;
    }

    public void processAction()
    {

        String Name = name.getText().trim();
        String Address = address.getText().trim();
        String City = city.getText().trim();
        String StateCode = stateCode.getText().trim();
        String Zip = zip.getText().trim();
        String Email = email.getText().trim();
        String DateOfBirth = dateOfBirth.getText().trim();
        String statusB = status.getValue();


        Properties props = new Properties();

        // Verify that the author field is not empty
        if (Name.isEmpty())
        {
            displayErrorMessage("Name field cannot be empty!");
            name.requestFocus();
            // Verify that the title field is not empty
        }
        else if (Address.isEmpty())
        {
            displayErrorMessage("Address field cannot be empty!");
            address.requestFocus();
        }
        else if (City.isEmpty())
        {
            displayErrorMessage("City cannot be empty!");
            city.requestFocus();
        }
        else if (StateCode.isEmpty())
        {
            displayErrorMessage("StateCode cannot be empty!");
            stateCode.requestFocus();
        }
        else if (Zip.isEmpty())
        {
            displayErrorMessage("Zip cannot be empty!");
            zip.requestFocus();
        }
        else if (Email.isEmpty())
        {
            displayErrorMessage("Email cannot be empty!");
            email.requestFocus();
        }

        else if (Integer.parseInt(dateOfBirth.getText().split("-")[0]) < 1920 || Integer.parseInt(dateOfBirth.getText().split("-")[0]) > 2006) {
            displayErrorMessage("Date of birth should be between '1920-01-01' and '2006-01-01'!");
            dateOfBirth.requestFocus();
        }
        else
        {
            props.setProperty("name", name.getText());
            props.setProperty("address", address.getText());
            props.setProperty("city", city.getText());
            props.setProperty("stateCode", stateCode.getText());
            props.setProperty("zip", zip.getText());
            props.setProperty("email", email.getText());
            props.setProperty("dateOfBirth", dateOfBirth.getText()); // Make sure the format is correct
            props.setProperty("status", status.getValue());  // Assuming 'status' is a TextField, adjust accordingly


            try {
                myModel.stateChangeRequest("book id", props);
                displayMessage("SUCCESS!");
                Patron temp = new Patron(props);
                temp.processNewPatron(props);
            }
            catch(Exception ex)
            {
                displayErrorMessage("FAILED");
                ex.printStackTrace();
            }
            // state request change with the data
        }
    }


    // Create the status log field
    //-------------------------------------------------------------
    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    //-------------------------------------------------------------
    public void populateFields()
    {
        name.setText((String)myModel.getState("name"));
        address.setText((String)myModel.getState("address"));
        city.setText((String)myModel.getState("city"));
        stateCode.setText((String)myModel.getState("stateCode"));
        zip.setText((String)myModel.getState("zip"));
        email.setText((String)myModel.getState("email"));
        status.setValue("Available");  // Set the default value to "Active"
        status.setValue((String)myModel.getState("status"));
        dateOfBirth.setText((String)myModel.getState("dateOfBirth"));
    }

    /**
     * Update method
     */
    //---------------------------------------------------------
    public void updateState(String key, Object value)
    {
        clearErrorMessage();

        if (key.equals("Status") == true)
        {
            String val = (String)value;
            status.setValue(val);
            displayMessage("Status Updated to:  " + val);
        }
    }

    /**
     * Display error message
     */
    //----------------------------------------------------------
    public void displayErrorMessage(String message)
    {
        statusLog.displayErrorMessage(message);
    }

    /**
     * Display info message
     */
    //----------------------------------------------------------
    public void displayMessage(String message)
    {
        statusLog.displayMessage(message);
    }

    /**
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }

    private void goToHomeView() {
        // Create the Home (Librarian) view
        LibrarianView homeView = new LibrarianView(myModel);  // Pass model or any required parameters

        // Create the scene for the Home view
        Scene homeScene = new Scene(homeView);  // Create a scene from the home view

        // Get the Stage (window) and change the scene back to Home view
        Stage stage = (Stage) getScene().getWindow();  // Get the current window's stage
        stage.setScene(homeScene);  // Set the scene to Home (LibrarianView)
    }

}

//---------------------------------------------------------------
//	Revision History:
//