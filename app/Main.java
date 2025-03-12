import database.*;
import exception.InvalidPrimaryKeyException;
import exception.PasswordMismatchException;
import impresario.IModel;
import model.*;
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