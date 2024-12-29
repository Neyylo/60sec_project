package fr.l2info.sixtysec.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Game {
    public int day;
    public int foodCount;
    public int waterCount;
    public ArrayList<Character> characters = new ArrayList<>();
    public ArrayList<Item> shelterInventory = new ArrayList<>();
    public Item expeditionItem = null;
    public Character expeditionCharacter = null;

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
        ArrayList<Character> tempCharacters = new ArrayList<>(Arrays.asList(Character.CHARACTERS));
        Collections.shuffle(tempCharacters);
        int sizeCharacters = tempCharacters.size();
        characters.addAll(tempCharacters.subList(0, random.nextInt(3, sizeCharacters)));
        foodCount = 2* sizeCharacters;
        waterCount = 2* sizeCharacters;
        ArrayList<Item> tempInventory = new ArrayList<>(Arrays.asList(Item.ITEMS).subList(1,Item.ITEMS.length));
        int sizeInventory = tempInventory.size();
        Collections.shuffle(tempInventory);
        shelterInventory.add(Item.ITEMS[0]);
        shelterInventory.addAll(tempInventory.subList(0, random.nextInt(0, sizeInventory)));
    }

    public void update() {
        day++;
        for (Character character : characters) {
            character.increaseDaysWithoutEating();
            character.increaseDaysWithoutDrinking();
        }
    }
}
