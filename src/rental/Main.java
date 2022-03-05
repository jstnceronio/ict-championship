package rental;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage stg;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stg = primaryStage;
        stg.setHeight(500);
        stg.getIcons().add(new Image("resources/logo.png"));
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stg.setTitle("Login");
        stg.setScene(new Scene(root));
        stg.getScene().getStylesheets().add(getClass().getResource("/resources/application.css").toExternalForm());
        stg.show();
    }

    public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.getScene().setRoot(pane);
    }

    public void setLogin(User loggedInUser, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("overview.fxml"));
        Parent pane = loader.load();

        OverviewController overviewController = loader.getController();
        overviewController.setLoggedInUser(loggedInUser);
        overviewController.setStage(stg);
        stg.getScene().setRoot(pane);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
