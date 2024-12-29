package fr.l2info.sixtysec.classes;

public class Item {
    public enum Tag {
        HEALTH, EXPEDITION
    }

    private int id;
    private String name;
    private Tag tag;

    protected Item(int id, String name, Tag tag) {
        this.id = id;
        this.name = name;
        this.tag = tag;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean hasTag(Tag tag) {
        return this.tag==tag;
    }

    public static final Item[] ITEMS = {
            new Item(1, "Masque à gaz", Tag.EXPEDITION),
            new Item(2, "Fusil", Tag.EXPEDITION),
            new Item(3, "Hache de pompier", Tag.EXPEDITION),
            new Item(4, "Medkit", Tag.HEALTH),
            new Item(5, "Lampe de poche", Tag.EXPEDITION),
            new Item(6, "Paquet de cartes", Tag.HEALTH),
            new Item(7, "Jeu d'échecs", Tag.HEALTH)
    };
}
