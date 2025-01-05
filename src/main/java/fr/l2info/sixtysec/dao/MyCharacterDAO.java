package fr.l2info.sixtysec.dao;

import fr.l2info.sixtysec.classes.Character;
import java.util.List;

public interface MyCharacterDAO {
    void create(Character character);
    void update(Character character);
    void delete(Character character);
    Character findById(int idCharacter);
    List<Character> getAll();
}
