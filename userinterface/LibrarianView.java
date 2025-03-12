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
public class LibrarianView extends View
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
    private Button retrievePatronZipButton;

    private Button cancelButton;

    private MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public LibrarianView(IModel teller)
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
        Text welcomeText = new Text("Welcome !");
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
                changeToAddBookView();
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
                changeToAddPatronView();
            }
        });
        wCont.getChildren().add(addPatronButton);

        container.getChildren().add(wCont);

        HBox tCont = new HBox(10);
        tCont.setAlignment(Pos.CENTER);
        retrieveBookTitleButton = new Button("Search Book");
        retrieveBookTitleButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        retrieveBookTitleButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("Search Book by Title", null);
                changeToBookSearchView();
            }
        });
        tCont.getChildren().add(retrieveBookTitleButton);

        container.getChildren().add(tCont);


        HBox qCont = new HBox(10);
        qCont.setAlignment(Pos.CENTER);
        retrievePatronZipButton = new Button("Search Patron");
        retrievePatronZipButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        retrievePatronZipButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("Search Patron by Zip", null);
                changeToPatronSearchView();
            }
        });
        qCont.getChildren().add(retrievePatronZipButton);

        container.getChildren().add(qCont);

        HBox doneCont = new HBox(10);
        doneCont.setAlignment(Pos.CENTER);
        cancelButton = new Button("Exit");
        cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("Exit", null);
                logoutAndExit();
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
    private void changeToAddBookView() {
        // Create the Add Book view (or scene)
        BookView addBookView = new BookView(myModel);  // Create view with model
        Scene addBookScene = new Scene(addBookView);  // Create scene from view

        // Get the Stage (window) and change the scene
        Stage stage = (Stage) getScene().getWindow();  // Get the current window's stage
        stage.setScene(addBookScene);  // Change to Add Book scene
    }

    private void changeToAddPatronView() {
        // Create the Add Patron view (or scene)
        PatronView addPatronView = new PatronView(myModel);  // Create view with model
        Scene addPatronScene = new Scene(addPatronView);  // Create scene from view

        // Get the Stage (window) and change the scene
        Stage stage = (Stage) getScene().getWindow();  // Get the current window's stage
        stage.setScene(addPatronScene);  // Change to Add Patron scene
    }

    private void changeToBookSearchView() {
        // Create the Add Patron view (or scene)
        BookSearchView booksearchView = new BookSearchView(myModel);  // Create view with model
        Scene bookSearchScene = new Scene(booksearchView);  // Create scene from view

        // Get the Stage (window) and change the scene
        Stage stage = (Stage) getScene().getWindow();  // Get the current window's stage
        stage.setScene(bookSearchScene);  // Change to Add Patron scene
    }

    private void changeToPatronSearchView() {
        // Create the Add Patron view (or scene)
        PatronSearchView psearchView = new PatronSearchView(myModel);  // Create view with model
        Scene pSearchScene = new Scene(psearchView);  // Create scene from view

        // Get the Stage (window) and change the scene
        Stage stage = (Stage) getScene().getWindow();  // Get the current window's stage
        stage.setScene(pSearchScene);  // Change to Add Patron scene
    }

    private void logoutAndExit(){
        System.exit(0);
    }

}

