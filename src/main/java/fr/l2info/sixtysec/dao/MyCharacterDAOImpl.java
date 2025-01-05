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
                    "INSERT INTO MyCharacter (id, nom, joursFaim, joursSoif) " +
                    "VALUES (?, ?, ?, ?)");
            preparedStatement.setInt(1, character.getId());
            preparedStatement.setString(2, character.getName());
            preparedStatement.setInt(3, character.getDaysWithoutEating());
            preparedStatement.setInt(4, character.getDaysWithoutDrinking());
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
                    "joursSoif = ? " +
                    "WHERE id = ?");
            preparedStatement.setString(1, character.getName());
            preparedStatement.setInt(2, character.getDaysWithoutEating());
            preparedStatement.setInt(3, character.getDaysWithoutDrinking());
            preparedStatement.setInt(4, character.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Character character) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM MyCharacter WHERE id = ?");
            preparedStatement.setInt(1, character.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Character findById(int idCharacter) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM MyCharacter WHERE id = ?");
            preparedStatement.setInt(1, idCharacter);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int rsIdCharacter = rs.getInt("idCharacter");
                String rsNom = rs.getString("nom");
                int rsJoursFaim = rs.getInt("joursFaim");
                int rsJoursSoif = rs.getInt("joursSoif");
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
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM MyCharacter");
            while (rs.next()) {
                int rsIdCharacter = rs.getInt("id");
                String rsNom = rs.getString("nom");
                int rsJoursFaim = rs.getInt("joursFaim");
                int rsJoursSoif = rs.getInt("joursSoif");
                characters.add(new Character(rsIdCharacter, rsNom, rsJoursFaim, rsJoursSoif, true));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return characters;
    }
}
