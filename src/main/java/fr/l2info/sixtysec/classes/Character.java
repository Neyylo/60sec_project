package fr.l2info.sixtysec.classes;

import java.util.ArrayList;

public class Character {
    private int id;
    private String name;
    private int daysWithoutEating;
    private int daysWithoutDrinking;
    private boolean isAlive;

    public static final ArrayList<Character> CHARACTERS = new ArrayList<>();

    public static final Character johnBackflip          = new Character(1,"John Backflip");
    public static final Character martapagnan           = new Character(2,"Martapagnan");
    public static final Character stiveunneDeQuoicoubeh = new Character(3,"Stiveunne de Quoicoubeh");
    public static final Character nolanBebou            = new Character(4,"Nolan Bébou");
    public static final Character matheogingembre       = new Character(5,"Mathéogingembre");

    public Character(int id, String name, int daysWithoutEating, int daysWithoutDrinking, boolean isAlive) {
        this.id = id;
        this.name = name;
        this.daysWithoutEating = daysWithoutEating;
        this.daysWithoutDrinking = daysWithoutDrinking;
        this.isAlive = isAlive;
        CHARACTERS.add(this);
    }

    public Character(int id, String name) {
        this(id, name, 0, 0, true);
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDaysWithoutEating() {
        return daysWithoutEating;
    }

    public void incrementDaysWithoutEating() {
        this.daysWithoutEating++;
    }

    public void decreaseDaysWithoutEating() {
        if (this.daysWithoutEating-2 >= 0) {
            this.daysWithoutEating -= 2;
        } else this.daysWithoutEating = 0;
    }

    public int getDaysWithoutDrinking() {
        return daysWithoutDrinking;
    }

    public void incrementDaysWithoutDrinking() {
        this.daysWithoutDrinking++;
    }

    public void decreaseDaysWithoutDrinking() {
        this.daysWithoutDrinking = 0;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void checkIfAlive() {
        this.isAlive = daysWithoutEating <= 10 && daysWithoutDrinking <= 5;
    }

    public String status() {
        String text = "";
        if (isAlive) {
            if (daysWithoutDrinking <= 1) text += name + " n'a pas très soif.\n";
            else if (daysWithoutDrinking <= 3) text += name + " a soif.\n";
            else if (daysWithoutDrinking <= 5) text += name + " a très soif.\n";
            else {
                text += name + " est mort de soif.\n";
                return text+"\n";
            }
            if (daysWithoutEating <= 2) text += name + " n'a pas très faim.\n";
            else if (daysWithoutEating <= 5) text += name + " a faim.\n";
            else if (daysWithoutEating <= 10) text += name + " a très faim.\n";
            else {
                text += name + " est mort de faim.\n";
                return text+"\n";
            }
            return text+"\n";
        } else return name + " est mort.\n";
    }

    @Override
    public String toString() {
        return name;
    }
}