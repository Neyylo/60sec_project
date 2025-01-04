package fr.l2info.sixtysec.classes;

import java.util.ArrayList;

public class Item {
    public enum Tag {
        HEALTH, EXPEDITION
    }

    public static final ArrayList<Item> ITEMS = new ArrayList<Item>();

    static {
        final Item gasMask    = new Item(1, "Masque à gaz", Item.Tag.EXPEDITION);
        final Item rifle      = new Item(2, "Fusil", Item.Tag.EXPEDITION);
        final Item fireAxe    = new Item(3, "Hache de pompier", Item.Tag.EXPEDITION);
        final Item medKit     = new Item(4, "Medkit", Item.Tag.HEALTH);
        final Item flashLight = new Item(5, "Lampe de poche", Item.Tag.EXPEDITION);
        final Item cardGame   = new Item(6, "Paquet de cartes", Item.Tag.HEALTH);
        final Item checkers   = new Item(7, "Jeu d'échecs", Item.Tag.HEALTH);
    }

    private int id;
    private String name;
    private Tag tag;

    protected Item(int id, String name, Tag tag) {
        this.id = id;
        this.name = name;
        this.tag = tag;
        ITEMS.add(this);
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean hasTag(Tag tag) {
        return this.tag==tag;
    }

}

