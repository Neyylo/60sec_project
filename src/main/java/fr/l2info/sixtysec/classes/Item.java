package fr.l2info.sixtysec.classes;

public class Item {
    private int id;
    private String name;

    protected Item(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static final Item[] ITEMS = {
        new Item(3, "Masque à gaz"),
        new Item(4,"Fusil")
    };
}
