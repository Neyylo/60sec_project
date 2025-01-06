package fr.l2info.sixtysec.classes;

import fr.l2info.sixtysec.controllers.GameController;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

public abstract class GameEvent {

    protected final String description;
    protected final String goodChoiceText;
    protected final String badChoiceText;
    protected final Function<Game,String> goodChoiceAction;
    protected final Function<Game,String> badChoiceAction;
    protected final double probability;

    protected static final Random RANDOM = new Random();

    /**
     * GameEvent constructor.
     *
     * @param description      Event description
     * @param goodChoiceText   Text for the good choice
     * @param badChoiceText    Text for the bad choice
     * @param probability      Probability of the event happening (between 0 and 1)
     * @param goodChoiceAction Action for the good choice
     * @param badChoiceAction  Action for the bad choice
     */
    public GameEvent(String description, String goodChoiceText, String badChoiceText, double probability,
                        Function<Game,String> goodChoiceAction, Function<Game,String> badChoiceAction) {
        if (probability < 0.0 || probability > 1.0) {
            throw new IllegalArgumentException("La probabilité doit être entre 0.0 et 1.0");
        }
        this.description = description;
        this.goodChoiceText = goodChoiceText;
        this.badChoiceText = badChoiceText;
        this.probability = probability;
        this.goodChoiceAction = goodChoiceAction;
        this.badChoiceAction = badChoiceAction;
    }

    /**
     * Chooses a random event in the list of events and triggers it.
     *
     * @param gameController    the actual GameController
     * @param events            the list of events
     * @param alwaysPlayAnEvent if the trigger should always play an event
     * @return has an event been triggered
     */
    protected static boolean triggerRandomEvent(GameController gameController, List<GameEvent> events, boolean alwaysPlayAnEvent) {
        GameEvent randomEvent = null;
        for (int i = 0; i < events.size()-1; i++) {
            GameEvent event = events.get(i);
            if (RANDOM.nextDouble() < event.probability) {
                randomEvent = event;
                break;
            }
        }
        if (randomEvent == null && alwaysPlayAnEvent) randomEvent = events.get(events.size()-1);
        if (randomEvent != null) {
            randomEvent.trigger(gameController);
            return true;
        } else {
            return false;
        }

    }

    /**
     * Displays the event in a pop-up for the player to interact with
     *
     * @param gameController the actual GameController
     */
    protected void trigger(GameController gameController) {
        Game game = gameController.getGame();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Événement");
        alert.setHeaderText(description);
        alert.setContentText("Choisissez une action :");

        ButtonType goodChoice = new ButtonType(goodChoiceText);
        ButtonType badChoice = new ButtonType(badChoiceText);

        alert.getButtonTypes().setAll(goodChoice, badChoice);

        alert.showAndWait().ifPresent(choice -> {
            String summaryText = "";
            if (choice == goodChoice) {
                summaryText = goodChoiceAction.apply(game);
            } else if (choice == badChoice) {
                summaryText = badChoiceAction.apply(game);
            }
            showSummary(summaryText);
            if (game.getWinningMessage() != null) gameController.endGame();
        });

        game.getExpeditionCharacter().incrementDaysWithoutDrinking();
        game.getExpeditionCharacter().incrementDaysWithoutEating();
    }

    /**
     * Shows the summary of the event in another pop-up.
     *
     * @param text the summary of the event
     */
    public void showSummary(String text) {
        Alert summaryAlert = new Alert(Alert.AlertType.INFORMATION);
        summaryAlert.setTitle("Résumé de l'événement");
        summaryAlert.setHeaderText(text);
        summaryAlert.showAndWait();
    }
}
