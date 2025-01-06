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

    /**
     * Instantiates a new game using retrieved data.
     *
     * @param day              the day since start
     * @param foodCount        the food count
     * @param waterCount       the water count
     * @param characters       the characters
     * @param shelterInventory the shelter inventory
     */
    public Game(int day, int foodCount, int waterCount, List<Character> characters, List<Item> shelterInventory) {
        this.day = day;
        this.foodCount = foodCount;
        this.waterCount = waterCount;
        this.characters.addAll(characters);
        this.shelterInventory.addAll(shelterInventory);
        this.expeditionItem = null;
        this.expeditionCharacter = null;
    }

    /**
     * Instantiates a new game with random characters and inventory.
     */
    public Game() {
        day = 1;
        Random random = new Random();


        // Random initialisation of the characters
        List<Character> tempCharacters = Character.getCharacters();
        Collections.shuffle(tempCharacters);
        int sizeCharacters = tempCharacters.size();
        characters.addAll(tempCharacters.subList(0, random.nextInt(3,sizeCharacters)));

        // Calculation of the food and water counts by the number of characters
        int characterCount = characters.size();
        foodCount = 2 * characterCount;
        waterCount = 2 * characterCount;

        // Random initialisation of the items
        List<Item> tempInventory = new ArrayList<>(Item.ITEMS);
        Collections.shuffle(tempInventory);
        int sizeInventory = tempInventory.size();
        shelterInventory.addAll(tempInventory.subList(0, random.nextInt(1, sizeInventory + 1)));

        this.expeditionItem = null;
        this.expeditionCharacter = null;
    }

    // Getters and setters
    /**
     * Gets the current day.
     *
     * @return the day
     */
    public int getDay() {
        return day;
    }

    /**
     * Increment the day.
     */
    public void incrementDay() {
        this.day++;
    }

    /**
     * Gets the food count.
     *
     * @return the food count
     */
    public int getFoodCount() {
        return foodCount;
    }

    /**
     * Sets the food count.
     *
     * @param foodCount the new food count
     */
    public void setFoodCount(int foodCount) {
        this.foodCount = foodCount;
    }

    /**
     * Decrements the food count.
     */
    public void decrementFoodCount() {
        if (foodCount > 0) foodCount--;
    }

    /**
     * Gets the water count.
     *
     * @return the water count
     */
    public int getWaterCount() {
        return waterCount;
    }

    /**
     * Sets the water count.
     *
     * @param waterCount the new water count
     */
    public void setWaterCount(int waterCount) {
        this.waterCount = waterCount;
    }

    /**
     * Decrements the water count.
     */
    public void decrementWaterCount() {
        if (waterCount > 0) waterCount--;
    }

    /**
     * Gets the characters.
     *
     * @return the characters
     */
    public ArrayList<Character> getCharacters() {
        return characters;
    }

    /**
     * Gets the shelter inventory.
     *
     * @return the shelter inventory
     */
    public ArrayList<Item> getShelterInventory() {
        return shelterInventory;
    }

    /**
     * Gets the expedition character.
     *
     * @return the expedition character
     */
    public Character getExpeditionCharacter() {
        return expeditionCharacter;
    }

    /**
     * Sets the expedition character.
     *
     * @param expeditionCharacter the new expedition character
     */
    public void setExpeditionCharacter(Character expeditionCharacter) {
        this.expeditionCharacter = expeditionCharacter;
    }

    /**
     * Gets the expedition item.
     *
     * @return the expedition item
     */
    public Item getExpeditionItem() {
        return expeditionItem;
    }

    /**
     * Sets the expedition item.
     *
     * @param expeditionItem the new expedition item
     */
    public void setExpeditionItem(Item expeditionItem) {
        this.expeditionItem = expeditionItem;
    }

    /**
     * Adds a character to the bunker.
     *
     * @param character the character to add
     */
    public void addCharacter(Character character) {
        characters.add(character);
    }

    /**
     * Removes a character from the bunker.
     *
     * @param character the character to remove
     */
    public void removeCharacter(Character character) {
        characters.remove(character);
    }

    /**
     * Adds an item to inventory.
     *
     * @param item the item to add
     */
    public void addItemToInventory(Item item) {
        shelterInventory.add(item);
    }

    /**
     * Remove item from inventory.
     *
     * @param item the item to remove
     */
    public void removeItemFromInventory(Item item) {
        shelterInventory.remove(item);
    }

    /**
     * Gets the winning message.
     *
     * @return the winning message
     */
    public String getWinningMessage() {
        return winningMessage;
    }

    /**
     * Sets winning message.
     *
     * @param winningMessage the winning message
     */
    public void setWinningMessage(String winningMessage) {
        this.winningMessage = winningMessage;
    }

    /**
     * Updates the game :
     *  - increases hunger and thirst of the characters
     *  - removes dead characters from the list
     *  - resets the expedition
     *  - increments the day
     */
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

    /**
     * Save the game to the database using the DAOs
     */
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
        }
        shelterInventory.forEach(itemDAO::create);
        if (gameDAO.getGame() != null) {
            gameDAO.update(this);
        } else {
            gameDAO.create(this);
        }
    }

    /**
     * Clears the game from the database using the DAOs
     */
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