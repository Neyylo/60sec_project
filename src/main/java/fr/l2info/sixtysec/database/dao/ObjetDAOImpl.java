package fr.l2info.sixtysec.database.dao;

import com.mysql.cj.xdevapi.Client;
import fr.l2info.sixtysec.database.models.Objet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ObjetDAOImpl implements ObjetDAO {
    private Connection connection;

    public ObjetDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Objet objet) {

    }

    @Override
    public void update(Objet objet) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(("UPDATE Objet SET " +
                "nom = ?," +
                "consommable = ?" +
                "WHERE idObjet = ? "
                ))){
            preparedStatement.setString(1, objet.getNom());
            preparedStatement.setInt(2, objet.getConsommable());
            preparedStatement.setInt(3, objet.getIdObjet());

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Objet objet) {

    }

    @Override
    public Objet findById(Integer id) {
        return null;
    }

    @Override
    public List<Objet> getAll() {
        return List.of();
    }
}