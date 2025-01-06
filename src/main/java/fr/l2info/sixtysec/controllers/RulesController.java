package fr.l2info.sixtysec.controllers;

import fr.l2info.sixtysec.AppEntryPoint;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class RulesController {

    /**
     * Returns to the main menu when clicking the Back button.
     */
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