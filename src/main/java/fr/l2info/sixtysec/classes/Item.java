package fr.l2info.sixtysec.classes;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Item implements Comparable<Item>{

    public static final ArrayList<Item> ITEMS = new ArrayList<Item>();

    public static final Item gasMask    = new Item(1, "Masque à gaz");
    public static final Item rifle      = new Item(2, "Fusil");
    public static final Item fireAxe    = new Item(3, "Hache de pompier");
    public static final Item flashLight = new Item(5, "Lampe de poche");

    private int id;
    private String name;

    public Item(int id, String name) {
        this.id = id;
        this.name = name;
        ITEMS.add(this);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
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

