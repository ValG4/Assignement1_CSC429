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
import model.Librarian;

//==============================================================================
public class BookCollectionView extends View
{
    protected TableView<BookTableModel> tableOfBooks;
    protected Button cancelButton;
    private Librarian theLibrarian;

    protected MessageView statusLog;

    Librarian librarian = new Librarian();
    BookCollectionView view;


    //--------------------------------------------------------------------------
    public BookCollectionView(IModel wsc)
    {
        super(wsc, "BookCollectionView");

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
        //view = new BookCollectionView(librarian);
    }

    //--------------------------------------------------------------------------
    protected void populateFields()
    {
        getEntryTableModelValues();
    }

    //--------------------------------------------------------------------------
    protected void getEntryTableModelValues()
    {

        ObservableList<BookTableModel> tableData = FXCollections.observableArrayList();
        try
        {
            BookCollection accountCollection = (BookCollection) myModel.getState("BookList");

            Vector entryList = (Vector)accountCollection.getState("Books");
            Enumeration entries = entryList.elements();

            while (entries.hasMoreElements() == true)
            {
                Book nextAccount = (Book)entries.nextElement();
                Vector<String> view = nextAccount.getEntryListView();

                // add this list entry to the list
                BookTableModel nextTableRowData = new BookTableModel(view);
                tableData.add(nextTableRowData);

            }

            tableOfBooks.setItems(tableData);
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

        Text titleText = new Text(" Brockport Library/BookCollectionView ");
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

        Text prompt = new Text("LIST OF BOOKS");
        prompt.setWrappingWidth(350);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        tableOfBooks = new TableView<BookTableModel>();
        tableOfBooks.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        TableColumn bookIdColumn = new TableColumn("Book Id") ; //MAY HAVE TO REIMPLEMENT THIS
        bookIdColumn.setMinWidth(100);
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<BookTableModel, String>("bookId"));


        TableColumn bookTitleColumn = new TableColumn("Book Title") ;
        bookTitleColumn.setMinWidth(100);
        bookTitleColumn.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("bookTitle"));

        TableColumn authorColumn = new TableColumn("Author") ;
        authorColumn.setMinWidth(100);
        authorColumn.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("author"));

        TableColumn pubYearColumn = new TableColumn("Publication Year") ;
        pubYearColumn.setMinWidth(100);
        pubYearColumn.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("pubYear"));

        TableColumn statusColumn = new TableColumn("Status") ;
        statusColumn.setMinWidth(100);
        statusColumn.setCellValueFactory(
                new PropertyValueFactory<BookTableModel, String>("status"));

        tableOfBooks.getColumns().addAll(bookIdColumn, bookTitleColumn, authorColumn, pubYearColumn, statusColumn);

        tableOfBooks.setOnMousePressed(new EventHandler<MouseEvent>() {
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
        scrollPane.setContent(tableOfBooks);



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
                theLibrarian = new Librarian();
                //goToHomeView();
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
        BookTableModel selectedItem = tableOfBooks.getSelectionModel().getSelectedItem();

        if(selectedItem != null)
        {
            String selectedBookId = selectedItem.getBookId();

            myModel.stateChangeRequest("Book Selected", selectedBookId);
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