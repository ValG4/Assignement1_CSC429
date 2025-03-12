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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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

/** The class containing the Account View  for the ATM application */
//==============================================================
public class PatronView extends View
{

    // GUI components
    protected TextField patronId;
    protected TextField address;
    protected TextField city;
    protected TextField dateOfBirth;
    protected TextField email;
    protected TextField name;
    protected TextField stateCode;
    protected TextField status;
    protected TextField zip;

    protected Button cancelButton;

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

        Text titleText = new Text(" Brockport Library ");
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

        Text prompt = new Text("PATRON INFORMATION");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        Text patronIdLabel = new Text(" Patron Id : ");
        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
        patronIdLabel.setFont(myFont);
        patronIdLabel.setWrappingWidth(150);
        patronIdLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(patronIdLabel, 0, 1);

        patronId = new TextField();
        patronId.setEditable(false);
        grid.add(patronId, 1, 1);

        Text addressLabel = new Text(" Address : ");
        addressLabel.setFont(myFont);
        addressLabel.setWrappingWidth(150);
        addressLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(addressLabel, 0, 2);

        address = new TextField();
        address.setEditable(false);
        grid.add(address, 1, 2);

        Text cityLabel = new Text(" City : ");
        cityLabel.setFont(myFont);
        cityLabel.setWrappingWidth(150);
        cityLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(cityLabel, 0, 3);

        city = new TextField();
        city.setEditable(false);
        grid.add(city, 1, 3);

        Text dobLabel = new Text(" Date Of Birth : ");
        dobLabel.setFont(myFont);
        dobLabel.setWrappingWidth(150);
        dobLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(dobLabel, 0, 4);

        dateOfBirth = new TextField();
        dateOfBirth.setEditable(false);
        grid.add(dateOfBirth, 1, 4);

        Text emailLabel = new Text(" Email : ");
        emailLabel.setFont(myFont);
        emailLabel.setWrappingWidth(150);
        emailLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(emailLabel, 0, 5);

        email = new TextField();
        email.setEditable(false);
        grid.add(email, 1, 5);

        Text nameLabel = new Text(" Name : ");
        nameLabel.setFont(myFont);
        nameLabel.setWrappingWidth(150);
        nameLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(nameLabel, 0, 6);

        name = new TextField();
        name.setEditable(false);
        grid.add(name, 1, 6);

        Text stateCodeLabel = new Text(" State Code : ");
        stateCodeLabel.setFont(myFont);
        stateCodeLabel.setWrappingWidth(150);
        stateCodeLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(stateCodeLabel, 0, 7);

        stateCode = new TextField();
        stateCode.setEditable(false);
        grid.add(stateCode, 1, 7);


        Text statusLabel = new Text(" Status : ");
        statusLabel.setFont(myFont);
        statusLabel.setWrappingWidth(150);
        statusLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(statusLabel, 0, 8);

        status = new TextField();
        status.setEditable(false);
        grid.add(status, 1, 8);

        Text zipLabel = new Text(" Zip : ");
        zipLabel.setFont(myFont);
        zipLabel.setWrappingWidth(150);
        zipLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(zipLabel, 0, 9);

        zip = new TextField();
        zip.setEditable(false);
        grid.add(zip, 1, 9);





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

        vbox.getChildren().add(grid);
        vbox.getChildren().add(doneCont);

        return vbox;
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
        patronId.setText((String)myModel.getState("patronId"));
        address.setText((String)myModel.getState("address"));
        city.setText((String)myModel.getState("city"));
        dateOfBirth.setText((String)myModel.getState("dateOfBirth"));
        email.setText((String)myModel.getState("email"));
        name.setText((String)myModel.getState("name"));
        stateCode.setText((String)myModel.getState("stateCode"));
        status.setText((String)myModel.getState("status"));
        zip.setText((String)myModel.getState("zip"));
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
            status.setText(val);
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