package fr.l2info.sixtysec.dao;

import fr.l2info.sixtysec.classes.Game;
import fr.l2info.sixtysec.classes.Character;
import fr.l2info.sixtysec.classes.Item;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MyGameDAOImpl implements MyGameDAO {
    private Connection connection;

    public MyGameDAOImpl() {
        connection = DBConnection.getConnection();
    }

    @Override
    public void create(Game game) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO MyGame(id, jour, numSoupe, numEau) VALUES (?, ?, ?, ?)");
            statement.setInt(1,1);
            statement.setInt(2, game.getDay());
            statement.setInt(3,game.getFoodCount());
            statement.setInt(4,game.getWaterCount());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Game game) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE MyGame " +
                    "SET jour = ?, " +
                    "numSoupe = ?, " +
                    "numEau = ? " +
                    "WHERE id = ?");
            statement.setInt(1,game.getDay());
            statement.setInt(2,game.getFoodCount());
            statement.setInt(3,game.getWaterCount());
            statement.setInt(4,1);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Game game) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("DELETE FROM MyGame WHERE id = ?");
            statement.setInt(1,1);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Game getGame() {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM MyGame");
            if (resultSet.next()) {
                int rsJour = resultSet.getInt("jour");
                int rsNumSoupe = resultSet.getInt("numSoupe");
                int rsNumEau = resultSet.getInt("numEau");
                MyCharacterDAOImpl rsCharactersDAO = new MyCharacterDAOImpl();
                MyItemDAOImpl rsItemDAO = new MyItemDAOImpl();
                List<Character> rsCharacters = rsCharactersDAO.getAll();
                List<Item> rsItems = rsItemDAO.getAll();
                return new Game(rsJour, rsNumSoupe, rsNumEau, rsCharacters, rsItems);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
