package fr.l2info.sixtysec.database.dao;

import com.mysql.cj.xdevapi.Client;
import fr.l2info.sixtysec.database.models.Personne;

import java.util.List;

public interface PersonneDAO {
    void create(Personne personne);
    void update(Personne personne);
    void delete(Personne personne);
    Personne findById(int idPersonne);
    List<Personne> getAll();
}
