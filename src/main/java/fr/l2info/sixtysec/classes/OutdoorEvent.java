package fr.l2info.sixtysec.classes;

import fr.l2info.sixtysec.controllers.GameController;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Function;

public class OutdoorEvent {
    private String name;
    private final double probability;
    private Function<GameController, String> effect;

    protected OutdoorEvent(String name, double probability, Function<GameController, String> effect) {
        this.name = name;
        this.probability = 0.0;
        this.effect = effect;
    }

    public static OutdoorEvent randomEvent() {
        double totalWeight = 0.0;
        for (OutdoorEvent event : OUTDOOR_EVENTS) {
            totalWeight += event.probability;
        }

        Random random = new Random();
        double randomValue = random.nextDouble() * totalWeight;

        double cumulativeWeight = 0.0;
        for (OutdoorEvent event : OUTDOOR_EVENTS) {
            cumulativeWeight += event.probability;
            if (randomValue < cumulativeWeight) {
                return event;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }

    public String happen(GameController gc) {
        return effect.apply(gc);
    }

    public static final OutdoorEvent[] OUTDOOR_EVENTS = {
            new OutdoorEvent("soup",0.5,(gameController)->{
                Game game = gameController.game;
                int quantity = new Random().nextInt(1,5);
                game.foodCount += quantity;
                return " a trouvé "+quantity+" canettes de soupe.";
            }),
            new OutdoorEvent("water",0.7,(gameController)->{
                Game game = gameController.game;
                int quantity = new Random().nextInt(1,5);
                game.waterCount += quantity;
                return " a trouvé "+quantity+" bouteilles d'eau.";
            }),
            new OutdoorEvent("nabil_good",0.005,(gameController)->{
                return "";
            })
    };
}
