package fr.l2info.sixtysec.classes;

import fr.l2info.sixtysec.controllers.GameController;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class OutdoorEvent extends GameEvent {

    /**
     * OutdoorEvent constructor.
     *
     * @param description      Event description
     * @param goodChoiceText   Text for the good choice
     * @param badChoiceText    Text for the bad choice
     * @param probability      Probability of the event happening (between 0 and 1)
     * @param goodChoiceAction Action for the good choice
     * @param badChoiceAction  Action for the bad choice
     */
    public OutdoorEvent(String description, String goodChoiceText, String badChoiceText, double probability, Function<Game, String> goodChoiceAction, Function<Game, String> badChoiceAction) {
        super(description, goodChoiceText, badChoiceText, probability, goodChoiceAction, badChoiceAction);
    }

    /**
     * Triggers a random outdoor event.
     *
     * @param gameController the actual game controller
     */
    public static void triggerRandomEvent(GameController gameController) {
        triggerRandomEvent(gameController, EVENTS, true);
    }

    /**
     * List of outdoor events.
     */
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
                    "Vous apercevez un militaire au loin. Que voulez vous faire ?",
                    "Courir vers lui pour demander de l'aide",
                    "Se diriger tranquillement vers lui sans se précipiter",
                    0.001,
                    (game -> {
                        if (game.getExpeditionItem().equals(Item.gasMask)) {
                            game.setWinningMessage("Vous êtes sauvés ! Les renforts arrivent.");
                            return "Le militaire vous demande la localisation de votre bunker et envoie des renforts si jamais il reste des survivants";
                        }else {
                            game.getExpeditionCharacter().setAlive(false);
                            return "Essouflé, vous inhalez trop d'air pollué. Vous mourez avant d'avoir pu appeler au secours... Quel indignité.";
                        }
                    }),
                    (game -> {
                        if (game.getExpeditionItem().equals(Item.flashLight)) {
                            game.setWinningMessage("Vous êtes sauvés !");
                            return "Le militaire remarque vos appels de phare et se dirige vers vous.\n"
                                    +"Il vous demande la localisation de votre bunker et envoie des renforts si jamais il reste des survivants";
                        }else {
                            return "Vous êtes bien trop lent... Vous le perdez de vu.";
                        }
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
                    "Vous apercevez une silouhette au loin. Que voulez vous faire ?",
                    "Aller voir l'inconnu",
                    "Fuir dans le direction opposée",
                    0.1,
                    (game -> {
                        if (game.getExpeditionItem().equals(Item.rifle)) {
                            game.foodCount += 5;
                            game.waterCount += 5;
                            return "Vous l'intimidez avec votre fusil car vous êtes un homme FORT !\n"
                                    + "Vous lui volez les vivres qu'il avait sur lui.\n+5 soupes et 5 bouteilles d'eau.";
                        } else if(game.getExpeditionItem().equals(Item.fireAxe)){
                            game.foodCount += 5;
                            game.waterCount += 5;
                            return "Vous l'intimidez avec votre hache car vous êtes un homme FORT !\n"
                                    + "Vous lui volez les vivres qu'il avait sur lui.\n+5 soupes et 5 bouteilles d'eau.";
                        }else {
                            game.foodCount += 2;
                            return "Vous faites la rencontre d'un aimable aventurier comme vous ! Il partage avec vous ses trouvailles. Vous gagnez 2 soupes.";
                        }
                    }),
                    (game -> "L'inconnu vous prend pour un fou.")
            ),

            new OutdoorEvent(
                    "Vous apercevez une cabane au loin. Souhaitez vous y aller ?",
                    "Y aller",
                    "Continuer son chemin",
                    0.4,
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
                    0.4,
                    (game -> {
                        int numFood = RANDOM.nextInt(2,5);
                        game.waterCount += numFood;
                        return "Vous avez trouvé " + numFood + " canettes de soupe.";
                    }),
                    (game -> "Il ne s'est rien passé.")
            ),

            new OutdoorEvent(
                    "Après de longues heures de marche vous ne trouvez rien... Que Souhaitez vous faire ?",
                    "Rentrer au bunker les mains vides",
                    "Continuer de chercher",
                    0.7,
                    (game -> "Il ne s'est rien passé."),
                    (game -> {
                        String ItemName = game.getExpeditionItem().getName();
                        game.removeItemFromInventory(game.getExpeditionItem());
                        return "Vous n'avez toujours rien trouvé... Vous décidez de rentrer.\n"
                                + "Malheureusement à cause de la fatigue vous perdez votre " + ItemName + ".";
                    })
            )
    ));
}
