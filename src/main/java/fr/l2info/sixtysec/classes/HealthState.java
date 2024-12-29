package fr.l2info.sixtysec.classes;

import java.util.function.Consumer;

public class HealthState {

    private int id;
    private String name;
    private Consumer<Character> effect;

    protected HealthState(int id, String name, Consumer<Character> effect) {
        this.id = id;
        this.name = name;
        this.effect = effect;
    }

    public static HealthState[] HEALTH_STATES = {
            new HealthState(0, "Healthy", null),
            new HealthState(0, "Radiation Sickness", (character) -> character.decreaseSanity(2))
    };
}
