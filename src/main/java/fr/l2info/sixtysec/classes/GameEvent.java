package fr.l2info.sixtysec.classes;

import fr.l2info.sixtysec.controllers.GameController;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

public abstract class GameEvent {

    protected final String description; // Description de l'événement
    protected final String goodChoiceText; // Texte pour le bon choix
    protected final String badChoiceText; // Texte pour le mauvais choix
    protected final Function<Game,String> goodChoiceAction; // Action pour le bon choix
    protected final Function<Game,String> badChoiceAction; // Action pour le mauvais choix
    protected final double probability; // Probabilité d'occurrence de l'événement

    protected static final Random RANDOM = new Random();

    /**
     * Constructeur d'évenement
     *
     * @param description      La description de l'événement
     * @param goodChoiceText   Texte pour le bon choix
     * @param badChoiceText    Texte pour le mauvais choix
     * @param probability      Probabilité que l'événement se produise (entre 0.0 et 1.0)
     * @param goodChoiceAction Action pour le bon choix
     * @param badChoiceAction  Action pour le mauvais choix
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
     * Choisit un événement aléatoire dans une liste et le déclenche
     *
     * @param gameController le contrôleur de jeu actuel
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
     * Affiche l'événement sous forme de pop-up et exécute l'action choisie
     *
     * @param gameController le contrôleur de jeu actuel
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
    }

    public void showSummary(String text) {
        Alert summaryAlert = new Alert(Alert.AlertType.INFORMATION);
        summaryAlert.setTitle("Résumé de l'événement");
        summaryAlert.setHeaderText(text);
        summaryAlert.showAndWait();
    }
}
