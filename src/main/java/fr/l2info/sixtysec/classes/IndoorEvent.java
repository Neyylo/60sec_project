package fr.l2info.sixtysec.classes;

import fr.l2info.sixtysec.controllers.GameController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class IndoorEvent extends GameEvent {
    /**
     * IndoorEvent constructor.
     *
     * @param description      Event description
     * @param goodChoiceText   Text for the good choice
     * @param badChoiceText    Text for the bad choice
     * @param probability      Probability of the event happening (between 0 and 1)
     * @param goodChoiceAction Action for the good choice
     * @param badChoiceAction  Action for the bad choice
     */
    public IndoorEvent(String description,
                       String goodChoiceText,
                       String badChoiceText,
                       double probability,
                       Function<Game, String> goodChoiceAction,
                       Function<Game, String> badChoiceAction) {
        super(description, goodChoiceText, badChoiceText, probability, goodChoiceAction, badChoiceAction);
    }

    /**
     * Triggers a random indoor event.
     *
     * @param gameController the game controller
     * @return if the event has been triggered
     */
    public static boolean triggerRandomEvent(GameController gameController) {
        return triggerRandomEvent(gameController, EVENTS, false);
    }

    /**
     * List of indoor events.
     */
    public static final List<GameEvent> EVENTS = new ArrayList<>(List.of(
            new IndoorEvent(
                    "Vous recevez une lettre glissée sous la porte. Elle vous offre une rencontre avec un mystérieux groupe. Que voulez-vous faire ?",
                    "Répondre à l'invitation",
                    "Ignorer la lettre",
                    0.05,
                    (game -> {
                        game.setWinningMessage("Vous avez rejoint une nouvelle communauté !");
                        return "Le groupe était amical et a partagé ses vivres avec vous. L'apocalypse devient un peu plus supportable.";
                    }),
                    (game -> {
                        game.foodCount -= 2;
                        game.waterCount -= 2;
                        return "Vous avez ignoré l'invitation. Le groupe a pris cela pour une menace et a dérobé 2 canettes et 2 bouteilles pendant votre sommeil.";
                    })
            ),

            new IndoorEvent(
                    "Quelqu'un toque à la porte du bunker. Que voulez vous faire ?",
                    "Ouvrir la porte",
                    "Ne pas ouvrir",
                    0.05,
                    (game -> {
                        game.setWinningMessage("Vous avez été sauvés !");
                        return "Des militaires vous attendaient à l'entrée du bunker !\n" +
                               "Vous allez être transportés dans un abri temporaire, avant de trouver une nouvelle maison...";
                    }),
                    (game -> "Il ne s'est rien passé...")
            ),

            new IndoorEvent(
                    "Quelqu'un toque à la porte du bunker. Que voulez vous faire ?",
                    "Ignorer et ne faire aucun bruit",
                    "Ouvrir la porte",
                    0.1,
                    (game ->
                            "Il ne s'est rien passé..."
                    ),
                    (game -> {
                        ArrayList<Character> temp = new ArrayList<>(game.getCharacters());
                        Collections.shuffle(temp);
                        temp.get(0).setAlive(false);
                        String charName = temp.get(0).getName();
                        return "C'est des bandits ! Vous n'auriez jamais dû leur ouvrir. Ils réclament " + charName + ". Ils vont sûrement le déguster...";
                    })
            ),

            new IndoorEvent(
                    "Quelqu'un toque à la porte du bunker et se présente comme la mère de Matheo. Que voulez vous faire ?",
                    "Ne pas ouvrir. C'est sûrement un piège...",
                    "Ouvrir la porte",
                    0.1,
                    (game -> "Il ne s'est rien passé" ),
                    (game -> {
                        ArrayList<Character> temp = new ArrayList<>(game.getCharacters());
                        Character characNoEat = temp.get(0);
                        for (int i = 1; i < temp.size(); i++) {
                            if  (temp.get(i).getDaysWithoutEating() > characNoEat.getDaysWithoutEating()) {characNoEat = temp.get(i);}
                        };
                        String charName = characNoEat.getName();
                        characNoEat.setAlive(false);
                        return "C'est bien elle. Cependant elle remarque que vous n'avez pas fait le ménage dans votre bunker. Elle se met donc à vous taper un par un.\n"
                                +"Par manque de force, car il n'a pas assez manger, " + charName + " décède suite aux coups reçus.";
                    })
            ),

            new IndoorEvent(
                    "Quelqu'un toque à la porte du bunker. Que voulez vous faire ?",
                    "Ouvrir la porte",
                    "Ignorer et ne faire aucun bruit",
                    0.2,
                    ( game -> {
                        game.foodCount += 4;
                        game.waterCount += 4;
                        return "C'était le service de distribution de vivres. Ils vous disent que les secours ne devraient pas tarder à arriver. Courage !\n+ 4 soupes et 4 Bouteilles d'eau";
                    }),
                    (game -> {
                        game.waterCount -= 1;
                        return "Vous entendez une voix s'éloignant et qui vous dit 'On avait des vivres pour vous, tant pis !'.\nEnervé vous jetez 1 bouteille d'eau contre le mur.";
                    })
            ),

            new IndoorEvent(
                    "Vous entendez une voix venant de l'extérieur, c'est peut-être les secours ! Que voulez vous faire ?",
                    "Ouvrir la porte",
                    "Ignorer et ne faire aucun bruit",
                    0.2,
                    (game -> {
                        game.foodCount -= 3;
                        game.waterCount -= 3;
                        return "Oh non des bandits ! Ils entrent et vous dérobent 3 canettes de soupes et 3 bouteilles d'eau...";
                    }),
                    (game -> "Il ne s'est rien passé")
            )
    ));
}
