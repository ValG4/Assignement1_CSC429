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
import javafx.scene.control.cell.PropertyValueFactory;
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

import java.util.Properties;


// project imports
import impresario.IModel;

// project imports
import impresario.IModel;

/** The class containing the Account View  for the ATM application */
//==============================================================
public class BookView extends View
{

    // GUI components
    protected TextField bookId;
    protected TextField author;
    protected TextField pubYear;
    protected TextField bookTitle;
    protected ComboBox<String> status;

    protected Button cancelButton;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public BookView(IModel account)
    {
        super(account, "BookView");

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

        Text prompt = new Text("BOOK INFORMATION");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        Text bookIdLabel = new Text(" Book Id : ");
        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
        bookIdLabel.setFont(myFont);
        bookIdLabel.setWrappingWidth(150);
        bookIdLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(bookIdLabel, 0, 1);

        bookId = new TextField();
        bookId.setEditable(false);
        grid.add(bookId, 1, 1);

        Text bookTitleLabel = new Text(" Book Title : ");
        bookTitleLabel.setFont(myFont);
        bookTitleLabel.setWrappingWidth(150);
        bookTitleLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(bookTitleLabel, 0, 2);

        bookTitle = new TextField();
        bookTitle.setEditable(false);
        grid.add(bookTitle, 1, 2);

        Text authorLabel = new Text(" Author : ");
        authorLabel.setFont(myFont);
        authorLabel.setWrappingWidth(150);
        authorLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(authorLabel, 0, 3);

        author = new TextField();
        author.setEditable(false);
        grid.add(author, 1, 3);

        Text pubYearLabel = new Text(" Publication Year : ");
        pubYearLabel.setFont(myFont);
        pubYearLabel.setWrappingWidth(150);
        pubYearLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(pubYearLabel, 0, 4);

        pubYear = new TextField();
        pubYear.setEditable(false);
        grid.add(pubYear, 1, 4);

        Text statusLabel = new Text(" Status : ");
        statusLabel.setFont(myFont);
        statusLabel.setWrappingWidth(150);
        statusLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(statusLabel, 0, 5);

        status = new ComboBox<String>();
        status.setEditable(false);
        grid.add(status, 1, 5);





        HBox doneCont = new HBox(10);
        doneCont.setAlignment(Pos.CENTER);
        cancelButton = new Button("Back");
        cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                myModel.stateChangeRequest("BookCancelled", null);
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

    public void processAction()
    {
        String title = bookTitle.getText().trim();
        String authors = author.getText().trim();
        String pubYears = pubYear.getText().trim();
        String statusB = status.getValue();
        Properties props = new Properties();

        // Verify that the author field is not empty
        if (authors.isEmpty()){
            displayErrorMessage("Author field cannot be empty!");
            author.requestFocus();
            // Verify that the title field is not empty
        } else if (title.isEmpty()) {
            displayErrorMessage("Title field cannot be empty!");
            bookTitle.requestFocus();
        } else if (pubYears.isEmpty()) {
            displayErrorMessage("Publishing year cannot be empty!");
            pubYear.requestFocus();
            // Verify that the publication year is within the range 1800 - 2024
        } else if (Integer.parseInt(pubYears) < 1800 || Integer.parseInt(pubYears) > 2025){
            displayErrorMessage("Publication year should be in between 1800 and 2024!");
            pubYear.requestFocus();
        } else {
            props.setProperty("bookTitle", title);
            props.setProperty("author", authors);
            props.setProperty("pubYear", pubYears);
            props.setProperty("status", statusB);

            try {
                myModel.stateChangeRequest("insertBook", props);
                displayMessage("SUCCESS!");
            }
            catch(Exception ex)
            {
                displayErrorMessage("FAILED");
                ex.printStackTrace();
            }
            // state request change with the data
        }
    }










    //----------------------------------------------------------------
    public void populateFields()
    {
        bookId.setText((String)myModel.getState("bookId"));
        bookTitle.setText((String)myModel.getState("bookTitle"));
        author.setText((String)myModel.getState("author"));
        pubYear.setText((String)myModel.getState("pubYear"));
        status.setValue((String)myModel.getState("status"));
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

}

//---------------------------------------------------------------
//	Revision History:
//