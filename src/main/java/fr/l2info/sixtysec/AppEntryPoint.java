package fr.l2info.sixtysec;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class AppEntryPoint extends Application {
    private static Stage stage;

    public static Stage getStage() {
        return stage;
    }

    public static void sceneChange(Scene scene) {
        Stage stage = getStage();
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), stage.getScene().getRoot());
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> {
            // Once the transition finished, change the scene
            stage.setScene(scene);
            // Add a fade in transition to the new scene
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), scene.getRoot());
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });

        fadeOut.play();

        setWindowDrag(stage, scene);
    }

    private static void setWindowDrag(Stage stage, Scene scene) {
        final double[] offsetX = new double[1];
        final double[] offsetY = new double[1];
        final double dragAreaHeight = 40;

        scene.setOnMousePressed(event -> {
            if (event.getY() <= dragAreaHeight) {
                offsetX[0] = event.getSceneX();
                offsetY[0] = event.getSceneY();
            }
        });

        scene.setOnMouseDragged(event -> {
            if (event.getY() <= dragAreaHeight) {
                stage.setX(event.getScreenX() - offsetX[0]);
                stage.setY(event.getScreenY() - offsetY[0]);
            }
        });
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        FXMLLoader mainMenu = new FXMLLoader(AppEntryPoint.class.getResource("main-view.fxml"));
        Scene mainScene = new Scene(mainMenu.load(), 640, 480);
        stage.setTitle("60 seconds!");
        stage.setScene(mainScene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
        setWindowDrag(stage, mainScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}