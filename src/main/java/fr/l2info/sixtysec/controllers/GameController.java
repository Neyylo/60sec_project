package fr.l2info.sixtysec.controllers;

import fr.l2info.sixtysec.AppEntryPoint;
import fr.l2info.sixtysec.classes.*;
import fr.l2info.sixtysec.classes.Character;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class GameController {

    @FXML
    private Button nextDayButton;
    @FXML
    private Tab expeditionTab, resourcesTab, recapTab, indoorEventTab;
    @FXML
    private TabPane tabPane;
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
    private final Map<Character, CheckBox[]> resourcesCheckboxes = new HashMap<>();
    private int foodCheckboxCount = 0;
    private int waterCheckboxCount = 0;
    private Character expeditionCharacter = null;
    private Item expeditionItem = null;
    private boolean indoorEventTabHasBeenSelected = false;
    private List<Character> aliveCharacters = new ArrayList<>();

    public GameController() {
        this.game = MainController.game;
    }

    public Game getGame() {
        return game;
    }

    @FXML
    private void initialize() {
        setupJournalButton();
        updateAliveCharacters();
        updateCharactersSprites();
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

    private void updateCharactersSprites() {
        aliveCharacters.forEach(character -> {
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

    private void updateAliveCharacters() {
        aliveCharacters = game.getCharacters().stream()
                .filter(Character::isAlive)
                .collect(Collectors.toList());
    }

    private void updateRecap() {
        resetExpeditionSetup();
        dayLabel.setText("Jour n°" + game.getDay());
        recapTab.setContent(buildRecapContent());
        resourcesTab.setContent(buildResourcesContent());
        expeditionTab.setContent(buildExpeditionContent());
        setupIndoorEventTab();
        updateTabState();
        updateAliveCharacters();
    }

    private void updateTabState() {
        boolean noCharactersLeft = aliveCharacters.isEmpty();
        nextDayButton.setText(noCharactersLeft ? "Fin..." : "Jour suivant");
        resourcesTab.setDisable(noCharactersLeft);
        expeditionTab.setDisable(noCharactersLeft);
        indoorEventTab.setDisable(noCharactersLeft);
        tabPane.getSelectionModel().select(recapTab);
    }

    private VBox buildRecapContent() {
        VBox pane = new VBox();

        game.getCharacters().forEach(character -> {
            character.checkIfAlive();
            pane.getChildren().add(new Label(character.status()));
        });

        return pane;
    }

    private VBox buildResourcesContent() {
        VBox pane = new VBox();
        pane.getChildren().add(new Label("Il y'a " + game.getFoodCount() + " canettes de soupe et " + game.getWaterCount() + " bouteilles d'eau."));
        pane.getChildren().add(new Label("Choisissez qui pourra boire et manger :"));

        resetResourceState();

        aliveCharacters.forEach(character -> {
            if (!resourcesCheckboxes.containsKey(character)) {
                CheckBox food = createResourceCheckbox(true);
                CheckBox water = createResourceCheckbox(false);
                resourcesCheckboxes.put(character, new CheckBox[]{food, water});
            }
            HBox hbox = new HBox();
            hbox.getChildren().addAll(
                    resourcesCheckboxes.get(character)[0],
                    resourcesCheckboxes.get(character)[1],
                    new Label(" pour " + character));
            pane.getChildren().add(hbox);
        });

        updateResourceCheckboxState();

        return pane;
    }

    private void resetResourceState() {
        foodCheckboxCount = 0;
        waterCheckboxCount = 0;
        resourcesCheckboxes.clear();
    }

    private CheckBox createResourceCheckbox(boolean isFood) {
        CheckBox checkBox = new CheckBox(isFood ? "Soupe " : "Eau ");
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
        cbCharacter.getItems().addAll(aliveCharacters);
        cbCharacter.getSelectionModel()
                   .selectedItemProperty()
                   .addListener((observable, oldValue, newValue) -> {
                       expeditionCharacter = newValue;
        });

        ChoiceBox<Item> cbItem = new ChoiceBox<>();
        cbItem.getItems().add(null);
        cbItem.getItems().addAll(game.getShelterInventory());
        cbItem.getSelectionModel()
              .selectedItemProperty()
              .addListener((observable, oldValue, newValue) -> {
                  expeditionItem = newValue;
        });

        hbox.getChildren().addAll(new Label("Envoyer "), cbCharacter, new Label(" avec "), cbItem);
        pane.getChildren().add(hbox);

        return pane;
    }

    private void resetExpeditionSetup() {
        expeditionCharacter = null;
        expeditionItem = null;
    }

    private void changeExpeditionSetup() {
        game.setExpeditionCharacter(expeditionCharacter);
        game.setExpeditionItem(expeditionItem);
    }

    private void setupIndoorEventTab() {
        indoorEventTabHasBeenSelected = false;
        indoorEventTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue && !indoorEventTabHasBeenSelected) {
                if (IndoorEvent.triggerRandomEvent(this)) {
                    indoorEventTab.setDisable(true);
                    resourcesTab.setContent(buildResourcesContent());
                } else {
                    indoorEventTab.setContent(new Label("Vous n'avez rien entendu à la porte..."));
                }
                indoorEventTabHasBeenSelected = true;
            }
        });
    }

    private void setupNextDayButton() {
        nextDayButton.setOnAction(event -> {
            if (aliveCharacters.isEmpty() || game.getWinningMessage() != null) {
                endGame();
            } else {
                changeExpeditionSetup();
                processDayEnd();
            }
            toggleJournalVisibility(false);
            updateRecap();
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
            OutdoorEvent.triggerRandomEvent(this);
        }
        updateCharactersSprites();
        game.update();
    }

    @FXML
    private void saveGame(ActionEvent event) {
        game.save();
    }

    @FXML
    private void saveAndQuitGame(ActionEvent event) {
        saveGame(event);
        System.exit(0);
    }

    public void endGame() {
        game.clear();
        try {
            FXMLLoader loader = new FXMLLoader(AppEntryPoint.class.getResource("end-view.fxml"));
            Scene endScene = new Scene(loader.load(), 640, 480);
            AppEntryPoint.sceneChange(endScene);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load end-view.fxml", e);
        }
    }
}
