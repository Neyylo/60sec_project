package fr.l2info.sixtysec.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class HealthState {

    private int id;
    private String name;
    private Consumer<Character> effect;
    private Item cancellingItem;

    public static List<HealthState> HEALTH_STATES = new ArrayList<>();

    public HealthState(int id, String name, Consumer<Character> effect, Item cancellingItem) {
        this.id = id;
        this.name = name;
        this.effect = effect;
        this.cancellingItem = cancellingItem;
        HEALTH_STATES.add(this);
    }

    @Override
    public String toString() { return name; }

    @Override
    public boolean equals(Object o) {
        if (o instanceof HealthState) {
            HealthState other = (HealthState)o;
            return id == other.id;
        }
        return false;
    }

    public static final HealthState injured = new HealthState(1, "Blessé(e)",
            (character -> {}), Item.medKit);
}
