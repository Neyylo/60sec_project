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
            preparedStatement = connection.prepareStatement("INSERT INTO MyItem(idItem, nom, consommable)");
            preparedStatement.setInt(1, item.getId());
            preparedStatement.setString(2, item.getName());
            preparedStatement.setString(3, item.getTag().toString());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Item item) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("UPDATE MyItem SET  nom = ?, consommable = ? WHERE idItem = ? ");
            preparedStatement.setString(1, item.getName());
            preparedStatement.setString(2, item.getTag().toString());
            preparedStatement.setInt(3, item.getId());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Item item){
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM MyItem WHERE idItem = ?");
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
            preparedStatement = connection.prepareStatement("SELECT * FROM MyItem WHERE idItem = ?");
            preparedStatement.setInt(1, idItem);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int rsIdItem = rs.getInt("idItem");
                String rsNom = rs.getString("nom");
                String rsTag = rs.getString("consommable");
                for (Item.Tag tag : Item.Tag.values()) {
                    if (rsTag.equals(tag.toString())) {
                        return new Item(rsIdItem, rsNom, tag);
                    }
                }
                throw new IllegalStateException("Item tag does not exist");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Item> getAll() {
        List<Item> items = new ArrayList<>();

        try{
            Statement statement = connection.createStatement();
            ResultSet rs  = statement.executeQuery("SELECT * FROM MyItem");

            while (rs.next()) {
                int rsIdItem = rs.getInt("idItem");
                String rsName = rs.getString("nom");
                String rsTag = rs.getString("consommable");
                for (Item.Tag tag : Item.Tag.values()) {
                    if (rsTag.equals(tag.toString())) {
                         new Item(rsIdItem, rsName, tag);
                    }
                }
                throw new IllegalStateException("Item tag not found");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}