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

// project imports
import impresario.IModel;

/** The class containing the Transaction Choice View  for the ATM application */
//==============================================================
public class TransactionChoiceView extends View
{

    // other private data
    private final int labelWidth = 120;
    private final int labelHeight = 25;

    // GUI components

    private Button addBookButton;
    private Button addPatronButton;
    private Button retrieveBookTitleButton;
    private Button retrieveBookYearButton;
    private Button retrievePatronDateButton;
    private Button retrievePatronZipButton

    private Button cancelButton;

    private MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public TransactionChoiceView(IModel teller)
    {
        super(teller, "TransactionChoiceView");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // Add a title for this panel
        container.getChildren().add(createTitle());

        // how do you add white space?
        container.getChildren().add(new Label(" "));

        // create our GUI components, add them to this Container
        container.getChildren().add(createFormContents());

        container.getChildren().add(createStatusLog("             "));

        getChildren().add(container);

        populateFields();

        myModel.subscribe("TransactionError", this);
    }

    // Create the labels and fields
    //-------------------------------------------------------------
    private VBox createTitle()
    {
        VBox container = new VBox(10);
        Text titleText = new Text("       Brockport Library          ");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setWrappingWidth(300);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);
        container.getChildren().add(titleText);

        String accountHolderGreetingName = (String)myModel.getState("Name");
        Text welcomeText = new Text("Welcome, " + accountHolderGreetingName + "!");
        welcomeText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        welcomeText.setWrappingWidth(300);
        welcomeText.setTextAlignment(TextAlignment.CENTER);
        welcomeText.setFill(Color.DARKGREEN);
        container.getChildren().add(welcomeText);

        Text inquiryText = new Text("What do you wish to do today?");
        inquiryText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        inquiryText.setWrappingWidth(300);
        inquiryText.setTextAlignment(TextAlignment.CENTER);
        inquiryText.setFill(Color.BLACK);
        container.getChildren().add(inquiryText);

        return container;
    }


    // Create the navigation buttons
    //-------------------------------------------------------------
    private VBox createFormContents()
    {

        VBox container = new VBox(15);

        // create the buttons, listen for events, add them to the container
        HBox dCont = new HBox(10);
        dCont.setAlignment(Pos.CENTER);
        addBookButton = new Button("Add Book");
        addBookButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        addBookButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("Add Book", null);
            }
        });
        dCont.getChildren().add(addBookButton);

        container.getChildren().add(dCont);

        HBox wCont = new HBox(10);
        wCont.setAlignment(Pos.CENTER);
        addPatronButton = new Button("Add Patron");
        addPatronButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        addPatronButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("Add Patron", null);
            }
        });
        wCont.getChildren().add(addPatronButton);

        container.getChildren().add(wCont);

        HBox tCont = new HBox(10);
        tCont.setAlignment(Pos.CENTER);
        retrieveBookTitleButton = new Button("Search Book by Title");
        retrieveBookTitleButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        retrieveBookTitleButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("Search Book by Title", null);
            }
        });
        tCont.getChildren().add(retrieveBookTitleButton);

        container.getChildren().add(tCont);

        HBox biCont = new HBox(10);
        biCont.setAlignment(Pos.CENTER);
        retrieveBookYearButton = new Button("Search Book by Year");
        retrieveBookYearButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        retrieveBookYearButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("Search book by Year", null);
            }
        });
        biCont.getChildren().add(retrieveBookYearButton);

        container.getChildren().add(biCont);

        HBox iscCont = new HBox(10);
        iscCont.setAlignment(Pos.CENTER);
        retrievePatronDateButton = new Button("Search Patron by Date");
        retrievePatronDateButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        retrievePatronDateButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("Search Patron by Date", null);
            }
        });
        iscCont.getChildren().add(retrievePatronDateButton);

        container.getChildren().add(iscCont);

        HBox qCont = new HBox(10);
        qCont.setAlignment(Pos.CENTER);
        retrievePatronZipButton = new Button("Search Patron by Zip");
        retrievePatronZipButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        retrievePatronZipButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("Search Patron by Zip", null);
            }
        });
        qCont.getChildren().add(retrievePatronZipButton);

        container.getChildren().add(qCont);

        HBox doneCont = new HBox(10);
        doneCont.setAlignment(Pos.CENTER);
        cancelButton = new Button("Logout");
        cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("Logout", null);
            }
        });
        doneCont.getChildren().add(cancelButton);

        container.getChildren().add(doneCont);

        return container;
    }

    // Create the status log field
    //-------------------------------------------------------------
    private MessageView createStatusLog(String initialMessage)
    {

        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    //-------------------------------------------------------------
    public void populateFields()
    {

    }


    //---------------------------------------------------------
    public void updateState(String key, Object value)
    {
        if (key.equals("TransactionError") == true)
        {
            // display the passed text
            displayErrorMessage((String)value);
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
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }
}

