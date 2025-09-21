package kris;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    private Kris kris = new Kris("data/kris.txt");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        assert stage != null : "Stage should not be null";
        
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            assert fxmlLoader != null : "FXMLLoader should be successfully created";
            
            AnchorPane ap = fxmlLoader.load();
            assert ap != null : "FXML should load successfully and return a valid AnchorPane";
            
            Scene scene = new Scene(ap);
            assert scene != null : "Scene should be created successfully";
            
            stage.setScene(scene);
            stage.setTitle("Kris");
            
            MainWindow controller = fxmlLoader.<MainWindow>getController();
            assert controller != null : "Controller should be loaded from FXML";
            
            controller.setKris(kris);
            assert kris != null : "Kris instance should be initialized before setting";
            
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
