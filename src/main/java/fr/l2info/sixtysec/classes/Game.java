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
    public ArrayList<Item> explorationInventory = new ArrayList<>();

    public Game() {}

    public void startGame() {

    }

    public void newGame() {
        day = 1;
        Random random = new Random();
        ArrayList<Character> tempCharacters = new ArrayList<>(Arrays.asList(Character.CHARACTERS));
        Collections.shuffle(tempCharacters);
        int sizeCharacters = tempCharacters.size();
        characters.addAll(tempCharacters.subList(0, random.nextInt(3, sizeCharacters)));
        foodCount = 2* sizeCharacters;
        waterCount = 2* sizeCharacters;
        explorationInventory.clear();
        ArrayList<Item> tempInventory = new ArrayList<>(Arrays.asList(Item.ITEMS));
        Collections.shuffle(tempInventory);
        int sizeInventory = tempInventory.size();
        shelterInventory.addAll(tempInventory.subList(0, random.nextInt(0, sizeInventory)));
    }

    public void continueGame() {
        // Faire en sorte de récupérer les données de la sauvegarde avec les DAO
    }

    public void saveGame() {
        /* Faire en sorte de stocker les données de la sauvegarde avec les DAO
        * Soit tout supprimer dans la BDD et tout remettre
        * Soit update tout ce qui est déjà dans la BDD et supprimer ce qui n'est plus censé y être
        * ex: Personnage avec une maladie = update, personnage mort = supprimé
        * */

    }
}
