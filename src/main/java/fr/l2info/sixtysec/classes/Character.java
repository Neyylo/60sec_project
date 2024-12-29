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
    public int getDaysWithoutEating() {return daysWithoutEating;}
    public int getDaysWithoutDrinking() {return daysWithoutDrinking;}
    public int getSanity() {return sanity;}
    public HealthState getHealthState() {return healthState;}
    public void setDaysWithoutEating(int daysWithoutEating) {
        this.daysWithoutEating = daysWithoutEating;
    }
    public void increaseDaysWithoutEating() {
        this.daysWithoutEating++;
    }
    public void decreaseDaysWithoutEating() {
        this.daysWithoutEating = Math.max(0,this.daysWithoutEating-3);
    }
    public void setDaysWithoutDrinking(int daysWithoutDrinking) {
        this.daysWithoutDrinking = daysWithoutDrinking;
    }
    public void increaseDaysWithoutDrinking() {
        this.daysWithoutDrinking++;
    }
    public void decreaseDaysWithoutDrinking() {
        if (this.daysWithoutDrinking>0) this.daysWithoutDrinking--;
    }
    public void setSanity(int sanity) {
        this.sanity = sanity;
    }
    public void decreaseSanity(int amount) {
        this.sanity -= amount;
    }
    public void setHealthState(HealthState healthState) {
        this.healthState = healthState;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static final Character[] CHARACTERS = {
            new Character(1,"John Backflip"),
            new Character(2,"Martapagnan"),
            new Character(3,"Stiveunne de Quoicoubeh"),
            new Character(4,"Nolan Bébou"),
            new Character(5,"Mathéogingembre")
    };
}