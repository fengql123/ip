package kris;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import kris.command.Command;
import kris.exception.KrisException;

public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Kris kris;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image krisImage = new Image(this.getClass().getResourceAsStream("/images/kris.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void setKris(Kris k) {
        kris = k;
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        
        dialogContainer.getChildren().add(DialogBox.getUserDialog(input, userImage));
        userInput.clear();
        
        try {
            Command c = Parser.parse(input);
            if (c.isExit()) {
                Platform.exit();
                return;
            }
            String response = kris.getResponse(input);
            dialogContainer.getChildren().add(DialogBox.getKrisDialog(response, krisImage));
        } catch (KrisException e) {
            dialogContainer.getChildren().add(DialogBox.getKrisDialog("OOPS!!! " + e.getMessage(), krisImage));
        }
    }
}