package fr.l2info.sixtysec.classes;

import java.util.Random;
import java.util.function.Function;

public class OutdoorEvent {
    private int id;
    private String description;
    private double probability;
    private Function<Object, String> effect;

    protected OutdoorEvent(int id, String description, double probability, Function<Object, String> effect) {
        this.id = id;
        this.description = description;
        this.probability = 0.0;
        this.effect = effect;
    }

    public OutdoorEvent randomEvent() {
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

    public static final OutdoorEvent[] OUTDOOR_EVENTS = {
            new OutdoorEvent(1, "", 0.0, null)
    };
}
