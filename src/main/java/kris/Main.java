package kris;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    private Kris kris = new Kris("data/kris.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Kris");
            stage.setMinHeight(620);
            stage.setMinWidth(400);
            stage.setResizable(true);
            fxmlLoader.<MainWindow>getController().setKris(kris);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
