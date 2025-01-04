package fr.l2info.sixtysec.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Game {
    private int day;
    public int foodCount;
    public int waterCount;
    private ArrayList<Character> characters = new ArrayList<>();
    private ArrayList<Item> shelterInventory = new ArrayList<>();
    private Item expeditionItem = null;
    private Character expeditionCharacter = null;

    public Game(int day, int foodCount, int waterCount, ArrayList<Character> characters, ArrayList<Item> shelterInventory, Item expeditionItem, Character expeditionCharacter) {
        this.day = day;
        this.foodCount = foodCount;
        this.waterCount = waterCount;
        this.characters.addAll(characters);
        this.shelterInventory.addAll(shelterInventory);
        this.expeditionItem = expeditionItem;
        this.expeditionCharacter = expeditionCharacter;
    }

    public Game() {
        day = 1;
        Random random = new Random();

        // Initialisation aléatoire des personnages
        ArrayList<Character> tempCharacters = new ArrayList<>(Character.CHARACTERS);
        Collections.shuffle(tempCharacters);
        int sizeCharacters = tempCharacters.size();
        characters.addAll(tempCharacters.subList(0, random.nextInt(3, sizeCharacters)));

        // Calcul des ressources initiales en fonction du nombre de personnages
        int characterCount = characters.size();
        foodCount = 2 * characterCount;
        waterCount = 2 * characterCount;

        // Initialisation aléatoire des items
        ArrayList<Item> tempInventory = new ArrayList<>(Item.ITEMS);
        Collections.shuffle(tempInventory);
        int sizeInventory = tempInventory.size();
        shelterInventory.addAll(tempInventory.subList(0, random.nextInt(1, sizeInventory + 1)));
    }

    // Getters and setters
    public int getDay() {
        return day;
    }
    public void incrementDay() {
        this.day++;
    }
    public int getFoodCount() {
        return foodCount;
    }
    public void setFoodCount(int foodCount) {
        this.foodCount = foodCount;
    }
    public void decrementFoodCount() {
        if (foodCount > 0) foodCount--;
    }
    public int getWaterCount() {
        return waterCount;
    }
    public void setWaterCount(int waterCount) {
        this.waterCount = waterCount;
    }
    public void decrementWaterCount() {
        if (waterCount > 0) waterCount--;
    }
    public ArrayList<Character> getCharacters() {
        return characters;
    }
    public ArrayList<Item> getShelterInventory() {
        return shelterInventory;
    }
    public Character getExpeditionCharacter() {
        return expeditionCharacter;
    }
    public void setExpeditionCharacter(Character expeditionCharacter) {
        this.expeditionCharacter = expeditionCharacter;
    }
    public Item getExpeditionItem() {
        return expeditionItem;
    }
    public void setExpeditionItem(Item expeditionItem) {
        this.expeditionItem = expeditionItem;
    }

    // Business logic
    public void update() {
        // Logic to update the game state at the end of each day
        for (Character character : characters) {
            character.incrementDaysWithoutEating();
            character.incrementDaysWithoutDrinking();
        }

        // Increment day count
        incrementDay();
    }

    public void addCharacter(Character character) {
        characters.add(character);
    }

    public void removeCharacter(Character character) {
        characters.remove(character);
    }

    public void addItemToInventory(Item item) {
        shelterInventory.add(item);
    }

    public void removeItemFromInventory(Item item) {
        shelterInventory.remove(item);
    }
}