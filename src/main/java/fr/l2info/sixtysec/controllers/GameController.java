package fr.l2info.sixtysec.controllers;

import fr.l2info.sixtysec.AppEntryPoint;
import fr.l2info.sixtysec.classes.Character;
import fr.l2info.sixtysec.classes.Game;
import fr.l2info.sixtysec.classes.Item;
import fr.l2info.sixtysec.classes.OutdoorEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GameController {
    @FXML
    public Button nextDayButton;
    @FXML
    public Tab leisureTab;
    @FXML
    public Tab expeditionTab;
    @FXML
    public Tab resourcesTab;
    @FXML
    public Tab recapTab;
    @FXML
    public Label dayLabel;
    @FXML
    public Button journalButton;
    @FXML
    public TitledPane journalWindow;
    @FXML
    public AnchorPane anchorPane;

    public Game game = MainController.game;
    public ArrayList<Character> deadCharacters = new ArrayList<>();
    private HashMap<Character, CheckBox[]> resourcesCheckboxes = new HashMap<>();
    private int foodCheckboxCount = 0, waterCheckboxCount = 0;
    private String expeditionMessage = null;

    public void showJournal(ActionEvent actionEvent) {
        journalButton.setDisable(true);
        journalButton.setVisible(false);
        journalWindow.setDisable(false);
        journalWindow.setVisible(true);
        nextDayRecap();
        addNextDayListener();
    }

    public void nextDayRecap() {
        dayLabel.setText("Jour n°"+game.day);
        recapTab.setContent(recapContent());
        resourcesTab.setContent(resourcesContent());
        expeditionTab.setContent(expeditionContent());
        if (game.characters.isEmpty()) {
            nextDayButton.setText("Fin...");
            resourcesTab.setDisable(true);
            expeditionTab.setDisable(true);
        }
    }

    private VBox recapContent() {
        VBox pane = new VBox();
        if(!game.characters.contains(game.expeditionCharacter) && expeditionMessage != null) {
            pane.getChildren().add(new Label(game.expeditionCharacter + " n'est pas revenu de l'expédition."));
        }
        for (Character character: game.characters) {
            String text = character.toString();
            boolean deadByHunger = false, deadByThirst = false;
            int daysWithoutEating = character.getDaysWithoutEating();
            int daysWithoutDrinking = character.getDaysWithoutDrinking();
            if (daysWithoutEating <= 1) text += " n'a pas très faim.\n";
            else if (daysWithoutEating <= 5) text += " a faim.\n";
            else if (daysWithoutEating <= 10) text += " a très faim.\n";
            else deadByHunger = true;
            text += character;
            if (daysWithoutDrinking <= 1) text += " n'a pas soif.\n";
            else if (daysWithoutDrinking <= 3) text += " a soif.\n";
            else if (daysWithoutDrinking <= 5) text += " a très soif.\n";
            else deadByThirst = true;
            if (deadByHunger && deadByThirst) { text = character + " est mort de faim et de soif."; }
            else if (deadByHunger) { text = character + " est mort de faim."; }
            else if (deadByThirst) { text = character + " est mort de soif."; }
            if (deadByHunger || deadByThirst) { deadCharacters.add(character); }
            pane.getChildren().add(new Label(text));
            pane.setSpacing(10);
        }
        for (Character character: deadCharacters) {
            game.characters.remove(character);
        }
        return pane;
    }

    private VBox resourcesContent() {
        VBox pane = new VBox();
        foodCheckboxCount = 0; waterCheckboxCount = 0;
        pane.getChildren().add(new Label("Il y'a "+game.foodCount+" canettes de soupe et "+game.waterCount+" bouteilles d'eau."));
        pane.getChildren().add(new Label("Choisissez qui pourra boire et manger :"));
        resourcesCheckboxes.clear();
        for (Character character: game.characters) {
            HBox hbox = new HBox();
            CheckBox food = new CheckBox("Soupe ");
            food.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) foodCheckboxCount++;
                else foodCheckboxCount--;
                updateCheckboxState();
            });
            CheckBox water = new CheckBox("Eau ");
            water.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) waterCheckboxCount++;
                else waterCheckboxCount--;
                updateCheckboxState();
            });
            resourcesCheckboxes.put(character, new CheckBox[]{food, water});
            hbox.getChildren().addAll(food, water, new Label(" pour "+character.toString()));
            pane.getChildren().add(hbox);
        }
        return pane;
    }

    private void updateCheckboxState() {
        boolean disableFood = foodCheckboxCount >= game.foodCount;
        boolean disableWater = waterCheckboxCount >= game.waterCount;
        resourcesCheckboxes.forEach((character, checkboxes) -> {
            if (!checkboxes[0].isSelected()) checkboxes[0].setDisable(disableFood);
            if (game.foodCount < 0) checkboxes[0].setDisable(true);
            if (!checkboxes[1].isSelected()) checkboxes[1].setDisable(disableWater);
            if (game.waterCount < 0) checkboxes[1].setDisable(true);
        });
    }

    private VBox expeditionContent() {
        VBox pane = new VBox(new Label("Choisissez qui envoyer en expédition :"));
        HBox hbox = new HBox();
        ChoiceBox<Character> cbCharacter = new ChoiceBox<>();
        cbCharacter.getItems().add(null);
        ChoiceBox<Item> cbItem = new ChoiceBox<>();
        cbItem.getItems().add(null);
        for (Character character : game.characters) cbCharacter.getItems().add(character);
        for (Item item : game.shelterInventory) if (item.hasTag(Item.Tag.EXPEDITION)) cbItem.getItems().add(item);
        hbox.getChildren().addAll(new Label("Envoyer "), cbCharacter, new Label(" avec "), cbItem);
        pane.getChildren().add(hbox);
        return pane;
    }

    private void addNextDayListener() {
        nextDayButton.setOnAction((ActionEvent event) -> {
            if (game.characters.isEmpty()) {
                endGame();
            } else {
                resourcesCheckboxes.forEach( (character, checkboxes) -> {
                    if (checkboxes[0].isSelected()) {
                        character.decreaseDaysWithoutEating();
                        game.foodCount--;
                    }
                    if (checkboxes[1].isSelected()) {
                        character.decreaseDaysWithoutDrinking();
                        game.waterCount--;
                    }
                });

                game.update();
                nextDayRecap();
            }
        });
    }

    @FXML
    private void saveGame(ActionEvent event) {

    }

    @FXML
    private void saveAndQuitGame(ActionEvent event) {
        saveGame(event);
        System.exit(0);
    }

    private void endGame() {
        FXMLLoader loader = new FXMLLoader(AppEntryPoint.class.getResource("end-view.fxml"));
        Scene endScene = null;
        try {
            endScene = new Scene(loader.load(), 640, 480);
            Stage stage = AppEntryPoint.getStage();
            stage.setScene(endScene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
