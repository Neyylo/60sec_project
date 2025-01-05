package fr.l2info.sixtysec.dao;

import fr.l2info.sixtysec.classes.Item;

import java.util.List;

public interface MyItemDAO {
    void create(Item objet);
    void update(Item objet);
    void delete(Item objet);

    Item findById(int idItem);

    List<Item> getAll();
}
