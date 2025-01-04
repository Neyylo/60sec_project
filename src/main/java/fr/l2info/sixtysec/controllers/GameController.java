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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class GameController {

    @FXML
    private Button nextDayButton;
    @FXML
    private Tab expeditionTab, resourcesTab, recapTab;
    @FXML
    private Label dayLabel;
    @FXML
    private Button journalButton;
    @FXML
    private TitledPane journalWindow;
    @FXML
    private ImageView char1;
    @FXML
    private ImageView char2;
    @FXML
    private ImageView char3;
    @FXML
    private ImageView char4;
    @FXML
    private ImageView char5;

    private Game game;
    private final List<Character> deadCharacters = new ArrayList<>();
    private final Map<Character, CheckBox[]> resourcesCheckboxes = new HashMap<>();
    private int foodCheckboxCount = 0;
    private int waterCheckboxCount = 0;

    public GameController() {
        this.game = MainController.game;
    }

    @FXML
    private void initialize() {
        updateCharacters();
        setupJournalButton();
    }

    private void setupJournalButton() {
        journalButton.setOnAction(this::showJournal);
    }

    private void showJournal(ActionEvent actionEvent) {
        toggleJournalVisibility(true);
        updateRecap();
        setupNextDayButton();
    }

    private void toggleJournalVisibility(boolean visible) {
        journalButton.setDisable(visible);
        journalButton.setVisible(!visible);
        journalWindow.setDisable(!visible);
        journalWindow.setVisible(visible);
        journalWindow.setExpanded(visible);
    }

    private void updateCharacters() {
        game.getCharacters().forEach(character -> {
            switch (character.getId()) {
                case 1:
                    char1.setVisible(true);
                    break;
                case 2:
                    char2.setVisible(true);
                    break;
                case 3:
                    char3.setVisible(true);
                    break;
                case 4:
                    char4.setVisible(true);
                    break;
                case 5:
                    char5.setVisible(true);
                    break;
            }
        });
    }

    private void updateRecap() {
        dayLabel.setText("Jour n°" + game.getDay());
        recapTab.setContent(buildRecapContent());
        resourcesTab.setContent(buildResourcesContent());
        expeditionTab.setContent(buildExpeditionContent());
        updateTabState();
    }

    private void updateTabState() {
        boolean noCharactersLeft = game.getCharacters().isEmpty();
        nextDayButton.setText(noCharactersLeft ? "Fin..." : "Jour suivant");
        resourcesTab.setDisable(noCharactersLeft);
        expeditionTab.setDisable(noCharactersLeft);
    }

    private VBox buildRecapContent() {
        VBox pane = new VBox();
        if (game.getExpeditionCharacter() != null && !game.getExpeditionCharacter().isAlive()) {
            pane.getChildren().add(new Label(game.getExpeditionCharacter() + " n'est pas revenu de l'expédition."));
        }

        List<Character> deadCharacters = new ArrayList<>();

        game.getCharacters().forEach(character -> {
            character.checkIfAlive();
            pane.getChildren().add(new Label(character.status()));
            if (!character.isAlive()) deadCharacters.add(character);
        });

        game.getCharacters().removeAll(deadCharacters);

        return pane;
    }

    private VBox buildResourcesContent() {
        VBox pane = new VBox();
        pane.getChildren().add(new Label("Il y'a " + game.getFoodCount() + " canettes de soupe et " + game.getWaterCount() + " bouteilles d'eau."));
        pane.getChildren().add(new Label("Choisissez qui pourra boire et manger :"));

        resetResourceState();

        game.getCharacters().forEach(character -> {
            HBox hbox = new HBox();
            CheckBox food = createResourceCheckbox(true);
            CheckBox water = createResourceCheckbox(false);
            hbox.getChildren().addAll(food, water, new Label(" pour " + character));
            resourcesCheckboxes.put(character, new CheckBox[]{food, water});
            pane.getChildren().add(hbox);
        });

        return pane;
    }

    private void resetResourceState() {
        foodCheckboxCount = 0;
        waterCheckboxCount = 0;
        resourcesCheckboxes.clear();
    }

    private CheckBox createResourceCheckbox(boolean isFood) {
        CheckBox checkBox = new CheckBox(isFood ? "Soupe" : "Eau");
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (isFood) {
                foodCheckboxCount += newValue ? 1 : -1;
            } else {
                waterCheckboxCount += newValue ? 1 : -1;
            }
            updateResourceCheckboxState();
        });
        return checkBox;
    }

    private void updateResourceCheckboxState() {
        boolean disableFood = foodCheckboxCount >= game.getFoodCount();
        boolean disableWater = waterCheckboxCount >= game.getWaterCount();

        resourcesCheckboxes.forEach((character, checkboxes) -> {
            if (!checkboxes[0].isSelected()) checkboxes[0].setDisable(disableFood);
            if (!checkboxes[1].isSelected()) checkboxes[1].setDisable(disableWater);
        });
    }

    private VBox buildExpeditionContent() {
        VBox pane = new VBox(new Label("Choisissez qui envoyer en expédition :"));
        HBox hbox = new HBox();
        ChoiceBox<Character> cbCharacter = new ChoiceBox<>();
        cbCharacter.getItems().add(null);
        cbCharacter.getItems().addAll(game.getCharacters());
        cbCharacter.getSelectionModel()
                   .selectedItemProperty()
                   .addListener((observable, oldValue, newValue) -> {
                       game.setExpeditionCharacter(newValue);
        });

        ChoiceBox<Item> cbItem = new ChoiceBox<>();
        cbItem.getItems().add(null);
        cbItem.getItems().addAll(game.getShelterInventory().stream()
                .filter(item -> item.hasTag(Item.Tag.EXPEDITION))
                .collect(Collectors.toList()));
        cbItem.getSelectionModel()
              .selectedItemProperty()
              .addListener((observable, oldValue, newValue) -> {
                  game.setExpeditionItem(newValue);
        });

        hbox.getChildren().addAll(new Label("Envoyer "), cbCharacter, new Label(" avec "), cbItem);
        pane.getChildren().add(hbox);

        return pane;
    }

    private void setupNextDayButton() {
        nextDayButton.setOnAction(event -> {
            if (game.getCharacters().isEmpty()) {
                endGame();
            } else {
                processDayEnd();
                game.update();
                updateRecap();
            }
            toggleJournalVisibility(false);
        });
    }

    private void processDayEnd() {
        resourcesCheckboxes.forEach((character, checkboxes) -> {
            if (checkboxes[0].isSelected()) {
                character.decreaseDaysWithoutEating();
                game.decrementFoodCount();
            }
            if (checkboxes[1].isSelected()) {
                character.decreaseDaysWithoutDrinking();
                game.decrementWaterCount();
            }
        });
        if (game.getExpeditionCharacter() != null) {
            if(game.getExpeditionItem() != null) {
                game.removeItemFromInventory(game.getExpeditionItem());
            }
            OutdoorEvent.triggerRandomEvent(game);
        }
        updateCharacters();
    }

    @FXML
    private void saveGame(ActionEvent event) {
        // Save game logic here
    }

    @FXML
    private void saveAndQuitGame(ActionEvent event) {
        saveGame(event);
        System.exit(0);
    }

    private void endGame() {
        try {
            FXMLLoader loader = new FXMLLoader(AppEntryPoint.class.getResource("end-view.fxml"));
            Scene endScene = new Scene(loader.load(), 640, 480);
            AppEntryPoint.sceneChange(endScene);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load end-view.fxml", e);
        }
    }
}
