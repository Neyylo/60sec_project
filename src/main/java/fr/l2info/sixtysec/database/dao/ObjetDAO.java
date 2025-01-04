package fr.l2info.sixtysec.database.dao;

import fr.l2info.sixtysec.database.models.Objet;
import fr.l2info.sixtysec.database.models.Personne;

import java.sql.SQLException;
import java.util.List;

public interface ObjetDAO {
    void create(Objet objet);
    void update(Objet objet);
    void delete(Objet objet);


    Objet findById(int idObjet);

    List<Objet> getAll();
}
