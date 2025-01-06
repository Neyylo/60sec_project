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

/**
 * The type Game controller.
 */
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

    private final Game game = MainController.game;
    private final Map<Character, CheckBox[]> resourcesCheckboxes = new HashMap<>();
    private int foodCheckboxCount = 0;
    private int waterCheckboxCount = 0;
    private Character expeditionCharacter = null;
    private Item expeditionItem = null;
    private boolean indoorEventTabHasBeenSelected = false;
    private List<Character> aliveCharacters = new ArrayList<>();

    /**
     * Gets the current game.
     *
     * @return the game
     */
    public Game getGame() {
        return game;
    }

    /**
     * Initializes the game scene.
     */
    @FXML
    private void initialize() {
        setupJournalButton();
        updateAliveCharacters();
        updateCharactersSprites();
    }

    /**
     * Setups the action when clicking the journal button.
     */
    private void setupJournalButton() {
        journalButton.setOnAction(this::showJournal);
    }

    /**
     * Shows the journal content and update it.
     *
     * @param actionEvent
     */
    private void showJournal(ActionEvent actionEvent) {
        toggleJournalVisibility(true);
        updateRecap();
        setupNextDayButton();
    }

    /**
     * Toggles the journal window and button.
     *
     * @param visible is the journal visible
     */
    private void toggleJournalVisibility(boolean visible) {
        journalButton.setDisable(visible);
        journalButton.setVisible(!visible);
        journalWindow.setDisable(!visible);
        journalWindow.setVisible(visible);
        journalWindow.setExpanded(visible);
    }

    /**
     * Updates the characters sprites to be shown or not.
     */
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

    /**
     * Updates the list of characters that are alive.
     */
    private void updateAliveCharacters() {
        aliveCharacters = game.getCharacters().stream()
                .filter(Character::isAlive)
                .collect(Collectors.toList());
    }

    /**
     * Updates all the tabs of the journal.
     */
    private void updateRecap() {
        resetExpeditionSetup();
        dayLabel.setText("Jour n°" + game.getDay());
        recapTab.setContent(buildRecapContent());
        resourcesTab.setContent(buildResourcesContent());
        expeditionTab.setContent(buildExpeditionContent());
        indoorEventTabHasBeenSelected = false;
        setupIndoorEventTab();
        updateTabState();
        updateAliveCharacters();
    }

    /**
     * Disables the tabs if all characters are dead.
     */
    private void updateTabState() {
        boolean noCharactersLeft = aliveCharacters.isEmpty();
        nextDayButton.setText(noCharactersLeft ? "Fin..." : "Jour suivant");
        resourcesTab.setDisable(noCharactersLeft);
        expeditionTab.setDisable(noCharactersLeft);
        indoorEventTab.setDisable(noCharactersLeft);
        tabPane.getSelectionModel().select(recapTab);
    }

    /**
     * Returns the VBox containing the status of all alive characters.
     *
     * @return the VBox containing the summary
     */
    private VBox buildRecapContent() {
        VBox pane = new VBox();

        game.getCharacters().forEach(character -> {
            pane.getChildren().add(new Label(character.status()));
        });

        return pane;
    }

    /**
     * Returns the checkboxes allowing to manage the resources between characters inside a VBox.
     *
     * @return the VBox containing the checkboxes
     */
    private VBox buildResourcesContent() {
        VBox pane = new VBox();
        pane.getChildren().add(new Label("Il y'a " + game.getFoodCount() + " canettes de soupe et " + game.getWaterCount() + " bouteilles d'eau."));
        pane.getChildren().add(new Label("Choisissez qui pourra boire et manger :"));

        resetResourceState();

        // For each character, maps its checkboxes to the character and adds it to the container
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

    /**
     * Resets the checkboxes that manages the resources.
     */
    private void resetResourceState() {
        foodCheckboxCount = 0;
        waterCheckboxCount = 0;
        resourcesCheckboxes.clear();
    }

    /**
     * Returns a checkbox that will be linked to a character and either the food or water count.
     *
     * @param isFood is the checkbox for food or water
     * @return the checkbox
     */
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

    /**
     * Checks the number of checkboxes you check when assigning resources.
     * Disables the unchecked checkboxes if there's no more resource to assign.
     */
    private void updateResourceCheckboxState() {
        boolean disableFood = foodCheckboxCount >= game.getFoodCount();
        boolean disableWater = waterCheckboxCount >= game.getWaterCount();

        resourcesCheckboxes.forEach((character, checkboxes) -> {
            if (!checkboxes[0].isSelected()) checkboxes[0].setDisable(disableFood);
            if (!checkboxes[1].isSelected()) checkboxes[1].setDisable(disableWater);
        });
    }

    /**
     * Returns the content of the expedition tab inside a VBox.
     * Inside, there's 2 ChoiceBoxes :
     *  - the first allows to choose which character to send in expedition,
     *  - the second allows to choose which item to take in the expedition.
     *
     * @return the VBox containing the choiceboxes
     */
    private VBox buildExpeditionContent() {
        VBox pane = new VBox(new Label("Choisissez qui envoyer en expédition :"));
        HBox hbox = new HBox();

        // For all alive characters, add it to the ChoiceBox which, when selected, will prepare the expedition character
        ChoiceBox<Character> cbCharacter = new ChoiceBox<>();
        cbCharacter.getItems().add(null);
        cbCharacter.getItems().addAll(aliveCharacters);
        cbCharacter.getSelectionModel()
                   .selectedItemProperty()
                   .addListener((observable, oldValue, newValue) -> {
                       expeditionCharacter = newValue;
        });

        // For all items in the shelter, add it to the ChoiceBox which, when selected, will prepare the expedition item
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

    /**
     * Resets the expedition character and item.
     */
    private void resetExpeditionSetup() {
        expeditionCharacter = null;
        expeditionItem = null;
    }

    /**
     * Changes the expedition character and item.
     */
    private void changeExpeditionSetup() {
        game.setExpeditionCharacter(expeditionCharacter);
        game.setExpeditionItem(expeditionItem);
    }

    /**
     * Setups the indoor event tab.
     * If you click on the tab, it tries to trigger an event :
     *  - if no event triggers, it displays that nothing is happening at the door,
     *  - if an event happens, it shows up as a pop-up.
     */
    private void setupIndoorEventTab() {
        indoorEventTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue && !indoorEventTabHasBeenSelected) {
                if (IndoorEvent.triggerRandomEvent(this)) {
                    indoorEventTab.setDisable(true);
                    recapTab.setContent(buildRecapContent());
                    resourcesTab.setContent(buildResourcesContent());
                    expeditionTab.setContent(buildExpeditionContent());
                } else {
                    indoorEventTab.setContent(new Label("Vous n'avez rien entendu à la porte..."));
                }
                indoorEventTabHasBeenSelected = true;
            }
        });
    }

    /**
     * Setups the button to pass to the next day.
     * Checks if there's no character alive to end the game.
     * Else, changes the expedition setup and processes the end of the day.
     * Finally, it closes the journal and updates its content for the next day.
     */
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

    /**
     * Processes the end of a day.
     * If a character has been sent to go on an expedition, triggers an outdoor event.
     * Updates the game and all the sprites.
     */
    private void processDayEnd() {
        // Looks at all the resources checkboxes to know which character to assign each resource.
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

    /**
     * Saves the game.
     *
     * @param event
     */
    @FXML
    private void saveGame(ActionEvent event) {
        game.save();
    }

    /**
     * Saves the game, then quit.
     *
     * @param event
     */
    @FXML
    private void saveAndQuitGame(ActionEvent event) {
        saveGame(event);
        System.exit(0);
    }

    /**
     * Ends the game and switches to the end scene.
     */
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
