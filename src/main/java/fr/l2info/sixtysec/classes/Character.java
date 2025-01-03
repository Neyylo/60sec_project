package fr.l2info.sixtysec.classes;

import java.util.ArrayList;

public class Character {
    private int id;
    private String name;
    private int daysWithoutEating;
    private int daysWithoutDrinking;
    private boolean isAlive;

    public static final ArrayList<Character> CHARACTERS = new ArrayList<Character>();

    static {
        final Character johnBackflip          = new Character(1,"John Backflip");
        final Character martapagnan           = new Character(2,"Martapagnan");
        final Character stiveunneDeQuoicoubeh = new Character(3,"Stiveunne de Quoicoubeh");
        final Character nolanBebou            = new Character(4,"Nolan Bébou");
        final Character matheogingembre       = new Character(5,"Mathéogingembre");
    }

    public Character(int id, String name) {
        this.id = id;
        this.name = name;
        this.daysWithoutEating = 0;
        this.daysWithoutDrinking = 0;
        this.isAlive = true;
        CHARACTERS.add(this);
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDaysWithoutEating() {
        return daysWithoutEating;
    }

    public void incrementDaysWithoutEating() {
        this.daysWithoutEating++;
    }

    public void decreaseDaysWithoutEating() {
        if (this.daysWithoutEating > 0) {
            this.daysWithoutEating--;
        }
    }

    public int getDaysWithoutDrinking() {
        return daysWithoutDrinking;
    }

    public void incrementDaysWithoutDrinking() {
        this.daysWithoutDrinking++;
    }

    public void decreaseDaysWithoutDrinking() {
        if (this.daysWithoutDrinking > 0) {
            this.daysWithoutDrinking--;
        }
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void checkIfAlive() {
        if (daysWithoutEating > 10 || daysWithoutDrinking > 5) {
            this.isAlive = false;
        }
    }

    public String status() {
        String text = "";
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
    }

    @Override
    public String toString() {
        return name;
    }
}