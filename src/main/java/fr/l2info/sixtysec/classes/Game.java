package fr.l2info.sixtysec.classes;

import fr.l2info.sixtysec.dao.MyCharacterDAOImpl;
import fr.l2info.sixtysec.dao.MyGameDAOImpl;
import fr.l2info.sixtysec.dao.MyItemDAOImpl;

import java.util.*;
import java.util.stream.Collectors;

public class Game {
    private int day;
    public int foodCount;
    public int waterCount;
    private ArrayList<Character> characters = new ArrayList<>();
    private ArrayList<Item> shelterInventory = new ArrayList<>();
    private Item expeditionItem = null;
    private Character expeditionCharacter = null;
    private String winningMessage = null;

    public Game(int day, int foodCount, int waterCount, List<Character> characters, List<Item> shelterInventory) {
        this.day = day;
        this.foodCount = foodCount;
        this.waterCount = waterCount;
        this.characters.addAll(characters);
        this.shelterInventory.addAll(shelterInventory);
        this.expeditionItem = null;
        this.expeditionCharacter = null;
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

        this.expeditionItem = null;
        this.expeditionCharacter = null;
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

    public String getWinningMessage() {
        return winningMessage;
    }

    public void setWinningMessage(String winningMessage) {
        this.winningMessage = winningMessage;
    }

    public void update() {
        for (Character character : characters) {
            character.incrementDaysWithoutEating();
            character.incrementDaysWithoutDrinking();
        }
        List<Character> deadCharacters = characters.stream()
                                                   .filter(character -> !character.isAlive())
                                                   .collect(Collectors.toList());
        characters.removeAll(deadCharacters);
        expeditionCharacter = null;
        expeditionItem = null;
        incrementDay();
    }

    public void save() {
        MyCharacterDAOImpl characterDAO = new MyCharacterDAOImpl();
        MyItemDAOImpl itemDAO = new MyItemDAOImpl();
        MyGameDAOImpl gameDAO = new MyGameDAOImpl();
        List<Character> lastSavedCharacters = characterDAO.getAll();
        List<Item> lastSavedItems = itemDAO.getAll();
        if (lastSavedCharacters != null && !lastSavedCharacters.isEmpty()) {
            characters.forEach(characterDAO::update);
            lastSavedCharacters.removeAll(characters);
            lastSavedCharacters.forEach(characterDAO::delete);
        } else {
            characters.forEach(characterDAO::create);
        }
        if (lastSavedItems != null && !lastSavedItems.isEmpty()) {
            lastSavedItems.forEach(itemDAO::delete);
            shelterInventory.forEach(itemDAO::create);
        } else {
            shelterInventory.forEach(itemDAO::create);
        }
        if (gameDAO.getGame() != null) {
            gameDAO.update(this);
        } else {
            gameDAO.create(this);
        }
    }

    public void clear() {
        MyCharacterDAOImpl characterDAO = new MyCharacterDAOImpl();
        MyItemDAOImpl itemDAO = new MyItemDAOImpl();
        MyGameDAOImpl gameDAO = new MyGameDAOImpl();
        List<Character> lastSavedCharacters = characterDAO.getAll();
        List<Item> lastSavedItems = itemDAO.getAll();
        if (lastSavedCharacters != null && !lastSavedCharacters.isEmpty()) {
            lastSavedCharacters.forEach(characterDAO::delete);
        }
        if (lastSavedItems != null && !lastSavedItems.isEmpty()) {
            lastSavedItems.forEach(itemDAO::delete);
        }
        if (gameDAO.getGame() != null) {
            gameDAO.delete(this);
        }
    }
}