package fr.l2info.sixtysec.controllers;

import fr.l2info.sixtysec.AppEntryPoint;
import fr.l2info.sixtysec.classes.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class MainController {
    public static Game game;

    public void showGameScene() {
        FXMLLoader gameFXML = new FXMLLoader(AppEntryPoint.class.getResource("game-view.fxml"));
        Scene gameScene = null;
        try {
            gameScene = new Scene(gameFXML.load(), 640, 480);
            AppEntryPoint.sceneChange(gameScene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startNewGame(ActionEvent actionEvent) {
        game = new Game();
        showGameScene();
    }

    public void continueGame(ActionEvent actionEvent) {
        // Effectuer les initialisations nécessaires avec la classe Game et les DAO
        showGameScene();
    }

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }
}
