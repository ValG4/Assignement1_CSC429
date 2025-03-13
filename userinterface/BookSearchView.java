package userinterface;

import impresario.IModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import userinterface.View;
import model.Librarian;

public class BookSearchView extends View {

    protected Button submit;
    protected Button back;
    protected Label titleLabel;
    protected TextField bookTitle;

    protected Librarian myLibrarian;

    public BookSearchView(IModel lib) {
        super(lib, "BookSearchView");

        if (lib instanceof Librarian) {
            myLibrarian = (Librarian) lib;
        } else {
            System.err.println("Error: Passed IModel is not a Librarian instance!");
            myLibrarian = null;
        }

        //myLibrarian = lib;
        // Create the container for showing our contents
        VBox container = new VBox(10);					//VBox with a spacing of 10
        container.setPadding(new Insets(15, 5, 15, 5));	//Padding

        // Adds a title for this panel (top of the screen)
        container.getChildren().add(createTitle());		// See method below

        container.getChildren().add(createFormContent());

        getChildren().add(container);

    }

    //----------------------------------------------------------
    //Creates the title of our GUI
    //----------------------------------------------------------
    private Node createTitle() {

        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        //Change below if desired
        Text titleText = new Text("SEARCH BOOKS/BooKSearchView");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        titleText.setWrappingWidth(300);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKOLIVEGREEN);
        container.getChildren().add(titleText);

        return container;
    }
    private VBox createFormContent() {
        //All GUI stuff goes inside here

        // THE FOLLOWING IS JUST FOR TESTING - REPLACE IT (COPIED FROM MITRA)
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);

        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(10, 10, 30, 10));
        titleLabel = new Label("Book Title:");
        bookTitle = new TextField();

        GridPane buttons = new GridPane();
        buttons.setHgap(100);
        buttons.setAlignment(Pos.CENTER);
        submit = new Button("Submit");
        back = new Button("Back");
        buttons.add(back, 0, 0);
        buttons.add(submit, 1, 0);

        back.setOnAction(e -> {
            goToHomeView();
        });

        submit.setOnAction(e -> {		//Not Started
            if (bookTitle.getText().isEmpty()) {
                System.out.println("Error, cannot be empty");	//Replace wih message view
            }
            else {
                String title = bookTitle.getText();
                //System.out.println(title);
                myLibrarian.searchBooks(title);
            }
        });



        hbox.getChildren().addAll(titleLabel, bookTitle);

        vbox.getChildren().add(hbox);
        vbox.getChildren().add(buttons);
        return vbox;
    }

    //----------------------------------------------------------
    //Abstract Methods inherited from View - Leave Blank
    //----------------------------------------------------------
    public void updateState(String arg0, Object arg1) {
        // TODO Auto-generated method stub
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
