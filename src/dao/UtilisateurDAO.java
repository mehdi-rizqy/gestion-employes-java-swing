package dao;


import Model.Utilisateur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation du DAO pour les utilisateurs
 * Gère les opérations CRUD sur la table "utilisateurs" en base de données
 */
public class UtilisateurDAO implements IUtilisateurDAO {
    private Connection connection;

    /**
     * Constructeur avec connexion fournie
     */
    public UtilisateurDAO(Connection connection) {
        this.connection = connection;
    }


    @Override
    public boolean ajouter(Utilisateur utilisateur) {
        String sql = "INSERT INTO utilisateurs (username, password, role) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, utilisateur.getUsername());
            statement.setString(2, utilisateur.getPassword()); // Mot de passe en clair
            statement.setString(3, utilisateur.getRole());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout de l'utilisateur: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean mettreAJour(Utilisateur utilisateur) {
        String sql = "UPDATE utilisateurs SET password = ?, role = ? WHERE username = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, utilisateur.getPassword());
            statement.setString(2, utilisateur.getRole());
            statement.setString(3, utilisateur.getUsername());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de l'utilisateur: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean supprimer(String username) {
        String sql = "DELETE FROM utilisateurs WHERE username = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression de l'utilisateur: " + e.getMessage(), e);
        }
    }

    @Override
    public Utilisateur trouver(String username) {
        String sql = "SELECT id, username, password, role FROM utilisateurs WHERE username = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return creerUtilisateurDepuisResultSet(resultSet);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche de l'utilisateur: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existe(String username) {
        String sql = "SELECT COUNT(*) FROM utilisateurs WHERE username = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la vérification de l'existence: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existent() {
        String sql = "SELECT COUNT(*) FROM utilisateurs";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la vérification des utilisateurs: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean verifierIdentifiants(String username, String password) {
        String sql = "SELECT password FROM utilisateurs WHERE username = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String motDePasseStocke = resultSet.getString("password");
                    // Comparaison simple (sans hachage pour simplifier)
                    return motDePasseStocke.equals(password);
                }
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la vérification des identifiants: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean changerMotDePasse(String username, String nouveauMotDePasse) {
        String sql = "UPDATE utilisateurs SET password = ? WHERE username = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nouveauMotDePasse);
            statement.setString(2, username);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du changement de mot de passe: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean changerRole(String username, String nouveauRole) {
        String sql = "UPDATE utilisateurs SET role = ? WHERE username = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nouveauRole);
            statement.setString(2, username);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du changement de rôle: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Utilisateur> listerTous() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String sql = "SELECT id, username, password, role FROM utilisateurs ORDER BY username";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                utilisateurs.add(creerUtilisateurDepuisResultSet(resultSet));
            }
            return utilisateurs;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du listing des utilisateurs: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Utilisateur> listerParRole(String role) {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String sql = "SELECT id, username, password, role FROM utilisateurs WHERE role = ? ORDER BY username";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, role);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    utilisateurs.add(creerUtilisateurDepuisResultSet(resultSet));
                }
            }
            return utilisateurs;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du listing par rôle: " + e.getMessage(), e);
        }
    }

    @Override
    public int compter() {
        String sql = "SELECT COUNT(*) FROM utilisateurs";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du comptage des utilisateurs: " + e.getMessage(), e);
        }
    }

    @Override
    public int compterParRole(String role) {
        String sql = "SELECT COUNT(*) FROM utilisateurs WHERE role = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, role);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du comptage par rôle: " + e.getMessage(), e);
        }
    }

    /**
     * Crée un objet Utilisateur à partir d'un ResultSet
     */
    private Utilisateur creerUtilisateurDepuisResultSet(ResultSet resultSet) throws SQLException {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(resultSet.getInt("id"));
        utilisateur.setUsername(resultSet.getString("username"));
        utilisateur.setPassword(resultSet.getString("password"));
        utilisateur.setRole(resultSet.getString("role"));
        return utilisateur;
    }
}