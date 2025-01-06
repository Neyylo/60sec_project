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

    /**
     * Tries to get if a game is saved in the database when initializing.
     */
    @FXML
    public void initialize() {
        game = myGameDAO.getGame();
        if (game == null) continueButton.setDisable(true);
    }

    /**
     * Shows the game scene.
     */
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

    /**
     * Shows the rules scene.
     */
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

    /**
     * Show the rules scene when pressing the rules button.
     *
     * @param actionEvent
     */
    public void showRules(ActionEvent actionEvent) {
        showRulesScene();
    }

    /**
     * Creates a new game and shows the game scene when
     * pressing the New Game button.
     *
     * @param actionEvent
     */
    public void startNewGame(ActionEvent actionEvent) {
        if (game != null) game.clear();
        game = new Game();
        showGameScene();
    }

    /**
     * Uses the game saved in the database and shows the game scene when
     * pressing the Continue button.
     *
     * @param actionEvent
     */
    public void continueGame(ActionEvent actionEvent) {
        showGameScene();
    }

    /**
     * Exits the game when pressing the Quit button.
     *
     * @param actionEvent
     */
    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }
}
