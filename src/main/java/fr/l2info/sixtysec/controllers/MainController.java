package fr.l2info.sixtysec.controllers;

import fr.l2info.sixtysec.AppEntryPoint;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    public void showGameScene() {
        FXMLLoader gameFXML = new FXMLLoader(AppEntryPoint.class.getResource("game-view.fxml"));
        Scene gameScene = null;
        try {
            gameScene = new Scene(gameFXML.load(), 600, 400);
            Stage stage = AppEntryPoint.getStage();
            stage.setScene(gameScene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startNewGame(ActionEvent actionEvent) {
        // Effectuer les initialisations nécessaires avec la classe Game
        showGameScene();
    }

    public void continueGame(ActionEvent actionEvent) {
        // Effectuer les initialisations nécessaires avec la classe Game
        showGameScene();
    }

    public void settings(ActionEvent actionEvent) {

    }

    public void journal(ActionEvent actionEvent) {
        System.out.println("Journal");
    }

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }
}
