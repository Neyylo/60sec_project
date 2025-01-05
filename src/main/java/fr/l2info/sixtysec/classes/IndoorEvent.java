package fr.l2info.sixtysec.classes;

import fr.l2info.sixtysec.controllers.GameController;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class IndoorEvent extends GameEvent {
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
    public IndoorEvent(String description,
                       String goodChoiceText,
                       String badChoiceText,
                       double probability,
                       Function<Game, String> goodChoiceAction,
                       Function<Game, String> badChoiceAction) {
        super(description, goodChoiceText, badChoiceText, probability, goodChoiceAction, badChoiceAction);
    }

    public static boolean triggerRandomEvent(GameController gameController) {
        return triggerRandomEvent(gameController, EVENTS, false);
    }

    public static final List<GameEvent> EVENTS = new ArrayList<>(List.of(
            new IndoorEvent(
                    "Nabil vient vous chercher. Allez-vous avec lui ?",
                    "Le rejoindre",
                    "L'abandonner à son sort",
                    1.0,
                    (game -> {
                        game.setWinningMessage("Vous avez gagné... le coeur de Nabil?");
                        return "Nabil est en fait un dieu tout-puissant, qui guérit tous vos maux et met fin à l'apocalypse en un claquement de doigt.";
                    }),
                    (game -> {
                        game.foodCount /= 2;
                        game.waterCount /= 2;
                        return "Nabil est en fait un dieu tout-puissant, qui réduit vos rations de moitié par pur mécontentement en un claquement de doigt.";
                    })
            ),
            new IndoorEvent(
                    "Nabil vient vous chercher. Allez-vous avec lui ?",
                    "Le rejoindre",
                    "L'abandonner à son sort",
                    1.0,
                    (game -> {
                        game.setWinningMessage("Vous avez gagné... le coeur de Nabil?");
                        return "Nabil est en fait un dieu tout-puissant, qui guérit tous vos maux et met fin à l'apocalypse en un claquement de doigt.";
                    }),
                    (game -> {
                        game.foodCount /= 2;
                        game.waterCount /= 2;
                        return "Nabil est en fait un dieu tout-puissant, qui réduit vos rations de moitié par pur mécontentement en un claquement de doigt.";
                    })
            )
    ));
}
