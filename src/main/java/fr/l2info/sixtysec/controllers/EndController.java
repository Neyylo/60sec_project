package fr.l2info.sixtysec.controllers;

import fr.l2info.sixtysec.AppEntryPoint;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class EndController {
    @FXML
    private Button mainMenuButton;

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
