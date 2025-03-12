import database.*;
import exception.InvalidPrimaryKeyException;
import exception.PasswordMismatchException;
import impresario.IModel;
import model.*;

import java.sql.SQLException;
import java.util.*;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;

// project imports
import event.Event;

import model.Librarian;
import userinterface.MainStageContainer;
import userinterface.WindowPosition;

import static javafx.application.Application.launch;

public class Main extends Application{
    private Librarian theLibrarian;

    private Stage mainStage;

    public void start(Stage primaryStage) {

        JDBCBroker db = null;
        try {
            db = JDBCBroker.getInstance();
            if (db == null) {
                throw new NullPointerException("JDBCBroker instance is null.");
            }
            db.getConnection();
        } catch (NullPointerException e) {
            System.err.println("Error: JDBCBroker instance is null. " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }

        MainStageContainer.setStage(primaryStage, "Library System");
        mainStage = MainStageContainer.getInstance();

        mainStage.setOnCloseRequest(new EventHandler <javafx.stage.WindowEvent>() {
            @Override
            public void handle(javafx.stage.WindowEvent event) {
                System.exit(0);
            }
        });

        try {
            theLibrarian = new Librarian();
        }
        catch (Exception e) {
            System.out.println("Could not create Librarian");
            e.printStackTrace();
        }

        WindowPosition.placeCenter(mainStage);
        mainStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}