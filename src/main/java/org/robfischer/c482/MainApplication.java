package org.robfischer.c482;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The main class for the Inventory Management System application.
 * <p>
 * FUTURE ENHANCEMENT: The functionality and user experience of the software could be improved by adding the ability to
 * double-click either a part or product on the main screen, and be taken directly to the edit page.
 * </p><p>
 * FUTURE ENHANCEMENT: The search boxes used throughout the application could be modified to update the TableViews in
 * real time, rather than requiring the user to press Enter to search.
 * </p>
 *
 * @author Rob Fischer
 */
public class MainApplication extends Application {

    /**
     * The next product ID to use when adding additional products. Starts at 1,000. All product IDs are even.
     */
    public static int nextProductId = 1000;
    /**
     * The next part ID to use when adding additional parts. Starts at 5,001. All part IDs are odd.
     */
    public static int nextPartId = 5001;

    /**
     * Starts our JavaFX Application
     *
     * @param stage The starting stage.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/org/robfischer/c482/view/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 820, 400);
        stage.setTitle("C482 Performance Assessment");
        stage.setScene(scene);
        stage.show();
    }


    /**
     * JavaDoc directory is located in c482/doc/
     * <p>
     * Launches the program.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}
