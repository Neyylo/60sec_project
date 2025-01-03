package fr.l2info.sixtysec.classes;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class OutdoorEvent {

    private final String description; // Description de l'événement
    private final String goodChoiceText; // Texte pour le bon choix
    private final String badChoiceText; // Texte pour le mauvais choix
    private final Function<Game,String> goodChoiceAction; // Action pour le bon choix
    private final Function<Game,String> badChoiceAction; // Action pour le mauvais choix
    private final double probability; // Probabilité d'occurrence de l'événement

    private static final Random RANDOM = new Random();

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
    public OutdoorEvent(String description, String goodChoiceText, String badChoiceText, double probability,
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
     * Choisit un événement aléatoire dans une liste et le déclenche.
     *
     * @param game Le jeu actuel.
     */
    public static void triggerRandomEvent(Game game) {
        OutdoorEvent randomEvent = null;
        for (int i = 0; i < EVENTS.size()-1; i++) {
            OutdoorEvent event = EVENTS.get(i);
            if (RANDOM.nextDouble() < event.probability) {
                randomEvent = event;
                break;
            }
        }
        if (randomEvent == null) { randomEvent = EVENTS.get(EVENTS.size()-1); }
        randomEvent.trigger(game);
    }

    /**
     * Affiche l'événement sous forme de pop-up et exécute l'action choisie
     *
     * @param game le jeu actuel
     */
    private void trigger(Game game) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Événement extérieur");
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
        });
    }

    public void showSummary(String text) {
        Alert summaryAlert = new Alert(Alert.AlertType.INFORMATION);
        summaryAlert.setTitle("Résumé de l'événement");
        summaryAlert.setHeaderText(text);
        summaryAlert.showAndWait();
    }

    public static final List<OutdoorEvent> EVENTS = new ArrayList<>(List.of(
        new OutdoorEvent(
                "Vous apercevez une cabane au loin. Souhaitez vous y aller ?",
                "Y aller",
                "Continuer son chemin",
                0.7,
                (game -> {
                    int numWater = RANDOM.nextInt(2,5);
                    game.waterCount += numWater;
                    return "Vous avez trouvé " + numWater + " bouteilles d'eau.";
                }),
                (game -> {
                    return "Il ne s'est rien passé.";
                })
        )
    ));
}
