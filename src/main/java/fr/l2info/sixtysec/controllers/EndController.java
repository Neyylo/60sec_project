package fr.l2info.sixtysec.controllers;

import fr.l2info.sixtysec.AppEntryPoint;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class EndController {

    @FXML
    private ImageView endImage;
    @FXML
    private Label endMessage;

    @FXML
    public void initialize() {
        String winningMessage = MainController.game.getWinningMessage();
        if (winningMessage != null) {
            endMessage.setText(winningMessage);
            Image image = new Image(AppEntryPoint.class.getResourceAsStream("images/rules_and_win.jpg"));
            endImage.setImage(image);
        }
    }

    @FXML
    public void returnToMainMenu() {
        FXMLLoader mainMenu = new FXMLLoader(AppEntryPoint.class.getResource("main-view.fxml"));
        try {
            Scene mainScene = new Scene(mainMenu.load(), 640, 480);
            AppEntryPoint.sceneChange(mainScene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
