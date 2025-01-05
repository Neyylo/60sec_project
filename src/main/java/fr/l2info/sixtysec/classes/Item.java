package fr.l2info.sixtysec.classes;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Item implements Comparable<Item>{
    public enum Tag {
        HEALTH, EXPEDITION
    }

    public static final ArrayList<Item> ITEMS = new ArrayList<Item>();

    public static final Item gasMask    = new Item(1, "Masque à gaz", Item.Tag.EXPEDITION);
    public static final Item rifle      = new Item(2, "Fusil", Item.Tag.EXPEDITION);
    public static final Item fireAxe    = new Item(3, "Hache de pompier", Item.Tag.EXPEDITION);
    public static final Item medKit     = new Item(4, "Medkit", Item.Tag.HEALTH);
    public static final Item flashLight = new Item(5, "Lampe de poche", Item.Tag.EXPEDITION);
    public static final Item cardGame   = new Item(6, "Paquet de cartes", Item.Tag.HEALTH);
    public static final Item checkers   = new Item(7, "Jeu d'échecs", Item.Tag.HEALTH);

    private int id;
    private String name;
    private Tag tag;

    public Item(int id, String name, Tag tag) {
        this.id = id;
        this.name = name;
        this.tag = tag;
        ITEMS.add(this);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Tag getTag() {
        return tag;
    }

    public boolean hasTag(Tag tag) {
        return this.tag==tag;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Item) {
            Item other = (Item) obj;
            return this.id == other.id;
        }
        return false;
    }

    @Override
    public int compareTo(@NotNull Item o) {
        return this.id - o.id;
    }
}

