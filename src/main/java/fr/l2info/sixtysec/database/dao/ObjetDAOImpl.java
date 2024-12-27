package fr.l2info.sixtysec.database.dao;

import fr.l2info.sixtysec.database.models.Objet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ObjetDAOImpl implements ObjetDAO {
    private Connection connection;

    public ObjetDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Objet objet) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Objet(idObjet, nom, consommable)")){

            preparedStatement.setInt(1, objet.getIdObjet());
            preparedStatement.setString(2, objet.getNom());
            preparedStatement.setInt(3, objet.getConsommable());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Objet objet) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("UPDATE Objet SET " +
                            "nom = ?," +
                            "consommable = ?" +
                            "WHERE idObjet = ? "
                            )){
            preparedStatement.setString(1, objet.getNom());
            preparedStatement.setInt(2, objet.getConsommable());
            preparedStatement.setInt(3, objet.getIdObjet());

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Objet objet){
        try(PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Objet WHERE idObjet = ?")){
            preparedStatement.setInt(1, objet.getIdObjet());
            preparedStatement.executeUpdate();

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Objet findById(int idObjet) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM OBJET WHERE idObjet = ?")) {

            preparedStatement.setInt(1, idObjet);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int rsIdObjet = rs.getInt("idObjet");
                String rsNom = rs.getString("nom");
                String rsConsommable = rs.getString("consommable");

                return new Objet();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Objet> getAll() {
        List<Objet> objets = new ArrayList<>();

        try{

            Statement statement = connection.createStatement();
            ResultSet rs  = statement.executeQuery("SELECT * FROM OBJET");

            while (rs.next()) {
                int idObjet = rs.getInt("idObjet");
                String nom = rs.getString("nom");
                String rsConsommable = rs.getString("consommable");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}