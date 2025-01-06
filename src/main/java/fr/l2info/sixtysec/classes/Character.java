package fr.l2info.sixtysec.classes;

import java.util.ArrayList;
import java.util.List;

public class Character {
    private int id;
    private String name;
    private int daysWithoutEating;
    private int daysWithoutDrinking;
    private boolean isAlive;

    public static ArrayList<Character> CHARACTERS = new ArrayList<>(List.of(
            new Character(1,"John Backflip"),
            new Character(2,"Martapagnan"),
            new Character(3,"Stiveunne de Quoicoubeh"),
            new Character(4,"Nolan Bébou"),
            new Character(5,"Mathéogingembre")
    ));

    /**
     * Instantiates a new Character.
     *
     * @param id                  the id
     * @param name                the name
     * @param daysWithoutEating   the days without eating
     * @param daysWithoutDrinking the days without drinking
     * @param isAlive             the is alive
     */
    public Character(int id, String name, int daysWithoutEating, int daysWithoutDrinking, boolean isAlive) {
        this.id = id;
        this.name = name;
        this.daysWithoutEating = daysWithoutEating;
        this.daysWithoutDrinking = daysWithoutDrinking;
        this.isAlive = isAlive;
    }

    /**
     * Instantiates a new Character.
     *
     * @param id   the id
     * @param name the name
     */
    public Character(int id, String name) {
        this(id, name, 0, 0, true);
    }

    /**
     * Gets id.
     *
     * @return the id
     */
// Getters and setters
    public int getId() {
        return id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets days without eating.
     *
     * @return the days without eating
     */
    public int getDaysWithoutEating() {
        return daysWithoutEating;
    }

    /**
     * Sets days without eating.
     *
     * @param daysWithoutEating the days without eating
     */
    public void setDaysWithoutEating(int daysWithoutEating) {
        this.daysWithoutEating = daysWithoutEating;
    }

    /**
     * Increments days without eating.
     */
    public void incrementDaysWithoutEating() {
        this.daysWithoutEating++;
    }

    /**
     * Decreases days without eating.
     */
    public void decreaseDaysWithoutEating() {
        if (this.daysWithoutEating-3 >= 0) {
            this.daysWithoutEating -= 3;
        } else this.daysWithoutEating = 0;
    }

    /**
     * Gets days without drinking.
     *
     * @return the days without drinking
     */
    public int getDaysWithoutDrinking() {
        return daysWithoutDrinking;
    }

    /**
     * Sets days without drinking.
     *
     * @param daysWithoutDrinking the days without drinking
     */
    public void setDaysWithoutDrinking(int daysWithoutDrinking) {
        this.daysWithoutDrinking = daysWithoutDrinking;
    }

    /**
     * Increments days without drinking.
     */
    public void incrementDaysWithoutDrinking() {
        this.daysWithoutDrinking++;
    }

    /**
     * Decreases days without drinking.
     */
    public void decreaseDaysWithoutDrinking() {
        this.daysWithoutDrinking = 0;
    }

    /**
     * Returns if the character is alive.
     *
     * @return if the character is alive
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Sets alive.
     *
     * @param alive the boolean
     */
    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    /**
     * Checks if the character is alive.
     */
    public void checkIfAlive() {
        this.isAlive = daysWithoutEating <= 10 && daysWithoutDrinking <= 5;
    }

    /**
     * Returns the status/condition of the character.
     *
     * @return the status
     */
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
        } else {
            if (daysWithoutDrinking > 5) return name + " est mort de soif.\n";
            else if (daysWithoutEating > 10) return name + " est mort de faim.\n";
            else return name + " est mort.\n";
        }
    }

    /**
     * Returns all the characters in CHARACTERS.
     *
     * @return the list containing all the characters
     */
    public static List<Character> getCharacters() {
        List<Character> tmp = new ArrayList<>();
        CHARACTERS.forEach(character -> tmp.add(character.clone()));
        return tmp;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Character) {
            return id == ((Character) o).id;
        }
        return false;
    }

    @Override
    public Character clone() {
        return new Character(id, name, daysWithoutEating, daysWithoutDrinking, isAlive);
    }
}