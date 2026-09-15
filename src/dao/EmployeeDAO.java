package dao;

import Model.Employee;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation du DAO pour les employés
 * Gère les opérations CRUD sur la table "employe" en base de données
 */
public class EmployeeDAO implements IEmployeeDAO {
    private Connection connection;

    /**
     * Constructeur avec connexion fournie
     */
    public EmployeeDAO(Connection connection) {
        this.connection = connection;
    }


    @Override
    public boolean ajouter(Employee employe) {
        String sql = "INSERT INTO employe (matricule, nom, prenom, poste, salaire, departement, date_embauche) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, employe.getMatricule());
            statement.setString(2, employe.getNom());
            statement.setString(3, employe.getPrenom());
            statement.setString(4, employe.getPoste());
            statement.setDouble(5, employe.getSalaire());
            statement.setString(6, employe.getDepartement());

            if (employe.getDateEmbauche() != null) {
                statement.setDate(7, new java.sql.Date(employe.getDateEmbauche().getTime()));
            } else {
                statement.setDate(7, null);
            }

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout de l'employé: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean mettreAJour(Employee employe) {
        String sql = "UPDATE employe SET matricule = ?, nom = ?, prenom = ?, poste = ?, " +
                "salaire = ?, departement = ?, date_embauche = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, employe.getMatricule());
            statement.setString(2, employe.getNom());
            statement.setString(3, employe.getPrenom());
            statement.setString(4, employe.getPoste());
            statement.setDouble(5, employe.getSalaire());
            statement.setString(6, employe.getDepartement());

            if (employe.getDateEmbauche() != null) {
                statement.setDate(7, new java.sql.Date(employe.getDateEmbauche().getTime()));
            } else {
                statement.setDate(7, null);
            }

            statement.setInt(8, employe.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de l'employé: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean supprimer(int id) {
        String sql = "DELETE FROM employe WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression de l'employé: " + e.getMessage(), e);
        }
    }

    @Override
    public Employee trouver(int id) {
        String sql = "SELECT * FROM employe WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return creerEmployeDepuisResultSet(resultSet);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche de l'employé: " + e.getMessage(), e);
        }
    }

    @Override
    public Employee trouverParMatricule(String matricule) {
        String sql = "SELECT * FROM employe WHERE matricule = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, matricule);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return creerEmployeDepuisResultSet(resultSet);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche par matricule: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existeParMatricule(String matricule) {
        String sql = "SELECT COUNT(*) FROM employe WHERE matricule = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, matricule);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la vérification d'existence: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Employee> rechercherParNom(String nom) {
        List<Employee> employes = new ArrayList<>();
        String sql = "SELECT * FROM employe WHERE nom LIKE ? OR prenom LIKE ? ORDER BY nom, prenom";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            String termeRecherche = "%" + nom + "%";
            statement.setString(1, termeRecherche);
            statement.setString(2, termeRecherche);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    employes.add(creerEmployeDepuisResultSet(resultSet));
                }
            }
            return employes;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche par nom: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Employee> listerTous() {
        List<Employee> employes = new ArrayList<>();
        String sql = "SELECT * FROM employe ORDER BY nom, prenom";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                employes.add(creerEmployeDepuisResultSet(resultSet));
            }
            return employes;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du listing des employés: " + e.getMessage(), e);
        }
    }

    @Override
    public int compter() {
        String sql = "SELECT COUNT(*) FROM employe";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du comptage des employés: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Employee> rechercherParDepartement(String departement) {
        List<Employee> employes = new ArrayList<>();
        String sql = "SELECT * FROM employe WHERE departement = ? ORDER BY nom, prenom";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, departement);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    employes.add(creerEmployeDepuisResultSet(resultSet));
                }
            }
            return employes;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche par département: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Employee> rechercherParPoste(String poste) {
        List<Employee> employes = new ArrayList<>();
        String sql = "SELECT * FROM employe WHERE poste = ? ORDER BY nom, prenom";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, poste);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    employes.add(creerEmployeDepuisResultSet(resultSet));
                }
            }
            return employes;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche par poste: " + e.getMessage(), e);
        }
    }

    /**
     * Crée un objet Employee à partir d'un ResultSet
     */
    private Employee creerEmployeDepuisResultSet(ResultSet resultSet) throws SQLException {
        Employee employe = new Employee();
        employe.setId(resultSet.getInt("id"));
        employe.setMatricule(resultSet.getString("matricule"));
        employe.setNom(resultSet.getString("nom"));
        employe.setPrenom(resultSet.getString("prenom"));
        employe.setPoste(resultSet.getString("poste"));
        employe.setSalaire(resultSet.getDouble("salaire"));
        employe.setDepartement(resultSet.getString("departement"));
        employe.setDateEmbauche(resultSet.getDate("date_embauche"));
        return employe;
    }
}