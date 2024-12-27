package fr.l2info.sixtysec.database.dao;

import fr.l2info.sixtysec.database.models.Personne;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonneDAOImpl implements PersonneDAO {
    private Connection connection;

    public PersonneDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Personne personne) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("INSERT INTO Personne (idPersonne, nom, prenom, sanite, joursFaim, joursSoif, A_ETAT_SANTE) VALUES (?, ?, ?, ?, ?, ?, ?)")
        ) {
            preparedStatement.setInt(1, personne.getIdPersonne());
            preparedStatement.setString(2, personne.getNom());
            preparedStatement.setString(3, personne.getPrenom());
            preparedStatement.setInt(4, personne.getSanite());
            preparedStatement.setInt(5, personne.getJoursFaim());
            preparedStatement.setInt(6, personne.getJoursSoif());
            preparedStatement.setInt(7, personne.getA_ETAT_SANTE());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Personne personne) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("UPDATE Personne SET " +
                             "nom = ?, " +
                             "prenom = ?, " +
                             "sanite = ?, " +
                             "joursFaim = ?, " +
                             "joursSoif = ?, " +
                             "A_ETAT_SANTE = ? " +
                             "WHERE idPersonne = ?")
        ) {
            preparedStatement.setString(1, personne.getNom());
            preparedStatement.setString(2, personne.getPrenom());
            preparedStatement.setInt(3, personne.getSanite());
            preparedStatement.setInt(4, personne.getJoursFaim());
            preparedStatement.setInt(5, personne.getJoursSoif());
            preparedStatement.setInt(6, personne.getA_ETAT_SANTE());
            preparedStatement.setInt(7, personne.getIdPersonne());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Personne personne) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Personne WHERE idPersonne = ?")) {
            preparedStatement.setInt(1, personne.getIdPersonne());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Personne findById(int idPersonne) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Personne WHERE idPersonne = ?")) {

            preparedStatement.setInt(1, idPersonne);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int rsIdPersonne = rs.getInt("idPersonne");
                String rsNom = rs.getString("nom");
                String rsPrenom = rs.getString("prenom");
                int rsSanite = rs.getInt("sanite");
                int rsJoursFaim = rs.getInt("joursFaim");
                int rsJoursSoif = rs.getInt("joursSoif");
                int rsAEtatSante = rs.getInt("A_ETAT_SANTE");

                return new Personne(rsIdPersonne, rsNom, rsPrenom, rsSanite, rsJoursFaim, rsJoursSoif, rsAEtatSante);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Personne> getAll() {
        List<Personne> personnes = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM Personne")
        ) {
            while (rs.next()) {
                int idPersonne = rs.getInt("idPersonne");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                int sanite = rs.getInt("sanite");
                int joursFaim = rs.getInt("joursFaim");
                int joursSoif = rs.getInt("joursSoif");
                int aEtatSante = rs.getInt("A_ETAT_SANTE");

                personnes.add(new Personne(idPersonne, nom, prenom, sanite, joursFaim, joursSoif, aEtatSante));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return personnes;
    }
}
