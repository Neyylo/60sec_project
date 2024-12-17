package fr.l2info.sixtysec.classes;

public class Character {
    private int id;
    private String name;
    private int daysWithoutEating, daysWithoutDrinking;
    private int sanity;
    private HealthState healthState;

    public Character(int id, String name) {
        this.id = id;
        this.name = name;
        this.daysWithoutEating = 0;
        this.daysWithoutDrinking = 0;
        this.sanity = 100;
        this.healthState = null;
    }

    public int getId() {return id;}
    public String getName() {return name;}
    public void setDaysWithoutEating(int daysWithoutEating) {
        this.daysWithoutEating = daysWithoutEating;
    }
    public void setDaysWithoutDrinking(int daysWithoutDrinking) {
        this.daysWithoutDrinking = daysWithoutDrinking;
    }

    public static final Character[] characters = {
            new Character(1,""),
            new Character(2,""),
            new Character(3,""),
            new Character(4,""),
            new Character(5,"")
    };
}
