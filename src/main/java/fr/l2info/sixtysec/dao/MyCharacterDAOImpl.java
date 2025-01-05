package fr.l2info.sixtysec.dao;

import fr.l2info.sixtysec.classes.Character;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MyCharacterDAOImpl implements MyCharacterDAO {
    private Connection connection;

    public MyCharacterDAOImpl() {
        this.connection = DBConnection.getConnection();
    }

    @Override
    public void create(Character character) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO MyCharacter (idCharacter, nom, joursFaim, joursSoif, etatSante) " +
                    "VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, character.getId());
            preparedStatement.setString(2, character.getName());
            preparedStatement.setInt(3, character.getDaysWithoutEating());
            preparedStatement.setInt(4, character.getDaysWithoutDrinking());
            //preparedStatement.setInt(5, character.getHealthState());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Character character) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("UPDATE MyCharacter SET " +
                    "nom = ?, " +
                    "joursFaim = ?, " +
                    "joursSoif = ?, " +
                    "etatSante = ? " +
                    "WHERE idCharacter = ?");
            preparedStatement.setString(1, character.getName());
            preparedStatement.setInt(2, character.getDaysWithoutEating());
            preparedStatement.setInt(3, character.getDaysWithoutDrinking());
            //preparedStatement.setInt(4, character.getHealthState());
            preparedStatement.setInt(5, character.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Character character) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM MyCharacter WHERE idCharacter = ?")) {
            preparedStatement.setInt(1, character.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Character findById(int idCharacter) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM MyCharacter WHERE idCharacter = ?")) {

            preparedStatement.setInt(1, idCharacter);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int rsIdCharacter = rs.getInt("idCharacter");
                String rsNom = rs.getString("nom");
                int rsJoursFaim = rs.getInt("joursFaim");
                int rsJoursSoif = rs.getInt("joursSoif");
                int rsAEtatSante = rs.getInt("etatSante");
                // A changer
                return new Character(rsIdCharacter, rsNom, rsJoursFaim, rsJoursSoif, true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Character> getAll() {
        List<Character> characters = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM MyCharacter")
        ) {
            while (rs.next()) {
                int rsIdCharacter = rs.getInt("idCharacter");
                String rsNom = rs.getString("nom");
                int rsJoursFaim = rs.getInt("joursFaim");
                int rsJoursSoif = rs.getInt("joursSoif");
                int rsAEtatSante = rs.getInt("etatSante");
                // A changer
                characters.add(new Character(rsIdCharacter, rsNom, rsJoursFaim, rsJoursSoif, true));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return characters;
    }
}
