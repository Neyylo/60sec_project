package fr.l2info.sixtysec.classes;

import fr.l2info.sixtysec.controllers.GameController;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class OutdoorEvent extends GameEvent {

    /**
     * Constructeur d'évenement extérieur
     *
     * @param description      La description de l'événement
     * @param goodChoiceText   Texte pour le bon choix
     * @param badChoiceText    Texte pour le mauvais choix
     * @param probability      Probabilité que l'événement se produise (entre 0.0 et 1.0)
     * @param goodChoiceAction Action pour le bon choix
     * @param badChoiceAction  Action pour le mauvais choix
     */
    public OutdoorEvent(String description, String goodChoiceText, String badChoiceText, double probability, Function<Game, String> goodChoiceAction, Function<Game, String> badChoiceAction) {
        super(description, goodChoiceText, badChoiceText, probability, goodChoiceAction, badChoiceAction);
    }

    public static void triggerRandomEvent(GameController gameController) {
        triggerRandomEvent(gameController, EVENTS, true);
    }

    public static final List<GameEvent> EVENTS = new ArrayList<>(List.of(
            new OutdoorEvent(
                    "Nabil vient vous chercher. Allez-vous avec lui ?",
                    "Le rejoindre",
                    "L'abandonner à son sort",
                    0.001,
                    (game -> {
                        game.setWinningMessage("Vous avez gagné... le coeur de Nabil?");
                        return "Nabil est en fait un dieu tout-puissant, qui met fin à l'apocalypse en un claquement de doigt.";
                    }),
                    (game -> {
                        game.getExpeditionCharacter().setAlive(false);
                        return "Nabil est en fait un dieu tout-puissant, qui met fin à votre vie en un claquement de doigt.";
                    })
            ),
            new OutdoorEvent(
                    "Vous apercevez une buisson bouger à côté de vous. Que voulez vous faire ?",
                    "Inspecter le buisson",
                    "Continuer son chemin",
                    0.1,
                    (game -> {
                        if (game.getExpeditionItem().equals(Item.rifle)) {
                            game.foodCount += 10;
                            return "Un loup vous saute dessus, mais vous dégainez votre fusil et lui tirez dessus !\n"
                                 + "Vous transformez ainsi le loup mort en soupe que vous mettez dans 10 canettes.";
                        } else {
                            game.removeItemFromInventory(Item.rifle);
                            return "Un loup vous saute dessus !\n"
                                 + "Il vous mord et vous blesse, et en fuyant, vous perdez votre fusil !";
                        }
                    }),
                    (game -> "Vous entendez un loup grogner derrière vous, et prenez vos jambes à votre cou !")
            ),
            new OutdoorEvent(
                    "Vous apercevez une cabane en tôle. Que voulez vous faire ?",
                    "Aller voir la cabane",
                    "Continuer son chemin",
                    0.1,
                    (game -> {
                        Item item = game.getExpeditionItem();
                        if (item.equals(Item.rifle)) {
                            game.foodCount += 5;
                            game.waterCount += 5;
                            return "Vous vous retrouvez face à un groupe de bandits !\n"
                                 + "Mais, grâce à votre fusil, vous les intimidez et ils vous donnent "
                                 + "5 canettes de soupe et 5 bouteilles d'eau avant de s'enfuir !";
                        } else {
                            game.removeItemFromInventory(item);
                            return "Vous vous retrouvez face à un groupe de bandits !\n"
                                 + "Ils vous blessent, et en fuyant, vous perdez votre "+item.getName()+" !";
                        }
                    }),
                    (game -> "Vous entendez du grabuge et des tirs depuis la cabane, mais vous êtes "
                            + "assez loin pour vous enfuir. Vous rentrez bredouille.")
            ),
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
                    (game -> "Il ne s'est rien passé.")
            ),
            new OutdoorEvent(
                    "Vous apercevez une ruine au loin. Souhaitez vous y aller ?",
                    "Y aller",
                    "Continuer son chemin",
                    0.7,
                    (game -> {
                        int numFood = RANDOM.nextInt(2,5);
                        game.foodCount += numFood;
                        return "Vous avez trouvé " + numFood + " bouteilles d'eau.";
                    }),
                    (game -> "Il ne s'est rien passé.")
            )
    ));
}
