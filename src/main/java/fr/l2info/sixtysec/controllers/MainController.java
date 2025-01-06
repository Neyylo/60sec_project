package fr.l2info.sixtysec.controllers;

import fr.l2info.sixtysec.AppEntryPoint;
import fr.l2info.sixtysec.classes.Game;
import fr.l2info.sixtysec.dao.MyGameDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

public class MainController {
    public static Game game;
    public static MyGameDAOImpl myGameDAO = new MyGameDAOImpl();

    @FXML
    private Button continueButton;

    @FXML
    public void initialize() {
        game = myGameDAO.getGame();
        if (game == null) continueButton.setDisable(true);
    }

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

    public void showRulesScene() {
        FXMLLoader rulesFXML = new FXMLLoader(AppEntryPoint.class.getResource("rules-view.fxml"));
        Scene rulesScene = null;
        try {
            rulesScene = new Scene(rulesFXML.load(), 640, 480);
            AppEntryPoint.sceneChange(rulesScene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showRules(ActionEvent actionEvent) {
        showRulesScene();
    }

    public void startNewGame(ActionEvent actionEvent) {
        if (game != null) game.clear();
        game = new Game();
        showGameScene();
    }

    public void continueGame(ActionEvent actionEvent) {
        showGameScene();
    }

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }
}
