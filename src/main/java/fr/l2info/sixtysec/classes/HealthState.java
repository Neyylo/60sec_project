package fr.l2info.sixtysec.classes;

import java.util.function.Function;

public class HealthState {

    private int id;
    private String name;
    private Function<Character, Double> effect;

    protected HealthState(int id, String name, Function<Character, Double> effect) {
        this.id = id;
        this.name = name;
        this.effect = effect;
    }

    public static HealthState[] HEALTH_STATES = {
            new HealthState(0, "Healthy", null)
    };
}
