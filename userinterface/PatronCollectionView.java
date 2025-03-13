package userinterface;

// system imports
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Vector;
import java.util.Enumeration;

// project imports
import impresario.IModel;
import model.Book;
import model.BookCollection;
import model.Patron;
import model.PatronCollection;

//==============================================================================
public class PatronCollectionView extends View
{
    protected TableView<PatronTableModel> tableOfPatrons;
    protected Button cancelButton;
    protected Button submitButton;

    protected MessageView statusLog;


    //--------------------------------------------------------------------------
    public PatronCollectionView(IModel wsc)
    {
        super(wsc, "PatronCollectionView");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // create our GUI components, add them to this panel
        container.getChildren().add(createTitle());
        container.getChildren().add(createFormContent());

        // Error message area
        container.getChildren().add(createStatusLog("                                            "));

        getChildren().add(container);

        populateFields();
    }

    //--------------------------------------------------------------------------
    protected void populateFields()
    {
        getEntryTableModelValues();
    }

    //--------------------------------------------------------------------------
    protected void getEntryTableModelValues()
    {

        ObservableList<PatronTableModel> tableData = FXCollections.observableArrayList();
        try
        {
            PatronCollection accountCollection = (PatronCollection) myModel.getState("PatronId");

            Vector entryList = (Vector)accountCollection.getState("Patrons");
            Enumeration entries = entryList.elements();

            while (entries.hasMoreElements() == true)
            {
                Patron nextPatron = (Patron)entries.nextElement();
                Vector<String> view = nextPatron.getEntryListView();

                // add this list entry to the list
                PatronTableModel nextTableRowData = new PatronTableModel(view);
                tableData.add(nextTableRowData);

            }

            tableOfPatrons.setItems(tableData);
        }
        catch (Exception e) {//SQLException e) {
            // Need to handle this exception
        }
    }

    // Create the title container
    //-------------------------------------------------------------
    private Node createTitle()
    {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        Text titleText = new Text(" Brockport Library/PatronCollectionView ");
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

        Text prompt = new Text("LIST OF PATRONS");
        prompt.setWrappingWidth(350);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        tableOfPatrons = new TableView<PatronTableModel>();
        tableOfPatrons.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

//        TableColumn patronIdColumn = new TableColumn("Patron Id") ;
//        patronIdColumn.setMinWidth(100);
//        patronIdColumn.setCellValueFactory(
//                new PropertyValueFactory<BookTableModel, String>("patronId"));

        TableColumn<PatronTableModel, Integer> patronIdColumn = new TableColumn<>("Patron Id");
        patronIdColumn.setMinWidth(100);
        patronIdColumn.setCellValueFactory(new PropertyValueFactory<PatronTableModel, Integer>("patronId"));

        TableColumn addressColumn = new TableColumn("Address") ;
        addressColumn.setMinWidth(100);
        addressColumn.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("address"));

        TableColumn cityColumn = new TableColumn("City") ;
        cityColumn.setMinWidth(100);
        cityColumn.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("city"));

        TableColumn dobColumn = new TableColumn("Date Of Birth") ;
        dobColumn.setMinWidth(100);
        dobColumn.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("dateOfBirth"));

        TableColumn emailColumn = new TableColumn("Email") ;
        emailColumn.setMinWidth(100);
        emailColumn.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("email"));

        TableColumn nameColumn = new TableColumn("Name") ;
        nameColumn.setMinWidth(100);
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("name"));

        TableColumn stateCodeColumn = new TableColumn("State Code") ;
        stateCodeColumn.setMinWidth(100);
        stateCodeColumn.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("stateCode"));

        TableColumn statusColumn = new TableColumn("Status") ;
        statusColumn.setMinWidth(100);
        statusColumn.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("status"));

        TableColumn zipColumn = new TableColumn("Zip") ;
        zipColumn.setMinWidth(100);
        zipColumn.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("zip"));

        tableOfPatrons.getColumns().addAll(patronIdColumn,
                addressColumn, cityColumn, dobColumn, emailColumn, nameColumn, stateCodeColumn, statusColumn, zipColumn);

        tableOfPatrons.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                if (event.isPrimaryButtonDown() && event.getClickCount() >=2 ){
                    processAccountSelected();
                }
            }
        });
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(115, 150);
        scrollPane.setContent(tableOfPatrons);

        cancelButton = new Button("Back");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                /**
                 * Process the Cancel button.
                 * The ultimate result of this action is that the transaction will tell the teller to
                 * to switch to the transaction choice view. BUT THAT IS NOT THIS VIEW'S CONCERN.
                 * It simply tells its model (controller) that the transaction was canceled, and leaves it
                 * to the model to decide to tell the teller to do the switch back.
                 */
                //----------------------------------------------------------
                clearErrorMessage();
                myModel.stateChangeRequest("CancelAccountList", null);
                goToHomeView();
            }
        });

        HBox btnContainer = new HBox(100);
        btnContainer.setAlignment(Pos.CENTER);
        btnContainer.getChildren().add(cancelButton);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(scrollPane);
        vbox.getChildren().add(btnContainer);

        return vbox;
    }



    //--------------------------------------------------------------------------
    public void updateState(String key, Object value)
    {
    }

    //--------------------------------------------------------------------------
    protected void processAccountSelected()
    {
        PatronTableModel selectedItem = tableOfPatrons.getSelectionModel().getSelectedItem();

        if(selectedItem != null)
        {
            String selectedBookId = selectedItem.getPatronId();

            myModel.stateChangeRequest("Patron Selected", selectedBookId);
        }
    }

    //--------------------------------------------------------------------------
    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
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

	/*
	//--------------------------------------------------------------------------
	public void mouseClicked(MouseEvent click)
	{
		if(click.getClickCount() >= 2)
		{
			processAccountSelected();
		}
	}
   */

}