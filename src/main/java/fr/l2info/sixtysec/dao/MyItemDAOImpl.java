package fr.l2info.sixtysec.dao;

import fr.l2info.sixtysec.classes.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MyItemDAOImpl implements MyItemDAO {
    private Connection connection;

    public MyItemDAOImpl() {
        this.connection = DBConnection.getConnection();
    }

    @Override
    public void create(Item item) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO MyItem(id, nom)" +
                    "VALUES (?, ?)");
            preparedStatement.setInt(1, item.getId());
            preparedStatement.setString(2, item.getName());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Item item) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("UPDATE MyItem SET  nom = ?, WHERE id = ? ");
            preparedStatement.setString(1, item.getName());
            preparedStatement.setInt(2, item.getId());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Item item){
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM MyItem WHERE id = ?");
            preparedStatement.setInt(1, item.getId());
            preparedStatement.executeUpdate();

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Item findById(int idItem) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM MyItem WHERE id = ?");
            preparedStatement.setInt(1, idItem);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int rsIdItem = rs.getInt("id");
                String rsNom = rs.getString("nom");
                return new Item(rsIdItem, rsNom);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Item> getAll() {
        List<Item> items = new ArrayList<>();
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM MyItem");

            while (rs.next()) {
                int rsIdItem = rs.getInt("id");
                String rsName = rs.getString("nom");
                items.add(new Item(rsIdItem, rsName));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

}