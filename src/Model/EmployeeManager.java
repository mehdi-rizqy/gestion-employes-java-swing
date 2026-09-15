package Model;


import dao.EmployeeDAO;
import dao.UtilisateurDAO;

import java.sql.SQLException;
import java.util.List;

/**
 * Classe de gestion des employés (couche métier)
 * Coordonne les opérations entre les contrôleurs et les DAO (agit comme une façade ou un service métier) elle contient les règles de gestion (validation des employés, calcul de l'ancienneté) et orchestre les appels aux DAO.
 */
public class EmployeeManager {
    private final EmployeeDAO employeeDAO;
    private final UtilisateurDAO utilisateurDAO;

    /**
     * Constructeur avec injection des dépendances
     */
    public EmployeeManager(EmployeeDAO employeeDAO, UtilisateurDAO utilisateurDAO) {
        this.employeeDAO = employeeDAO;
        this.utilisateurDAO = utilisateurDAO;
    }

    /**
     * Vérifie si des utilisateurs existent dans la base
     */
    public boolean utilisateursExistent() throws SQLException {
        return utilisateurDAO.existent();
    }

    /**
     * Crée un utilisateur administrateur par défaut
     */
    public void creerUtilisateurParDefaut() throws SQLException {
        Utilisateur admin = new Utilisateur("admin", "admin123", "admin");
        utilisateurDAO.ajouter(admin);
    }

    /**
     * Vérifie si un employé avec ce matricule existe
     */
    public boolean employeExiste(String matricule) throws SQLException {
        return employeeDAO.existeParMatricule(matricule);
    }

    /**
     * Trouve un employé par son matricule
     */
    public Employee trouverEmployeeParMatricule(String matricule) throws SQLException {
        return employeeDAO.trouverParMatricule(matricule);
    }

    /**
     * Recherche des employés par nom (contient)
     */
    public List<Employee> rechercherParNom(String nom) throws SQLException {
        return employeeDAO.rechercherParNom(nom);
    }

    /**
     * Liste tous les employés
     */
    public List<Employee> listerTousEmployees() throws SQLException {
        return employeeDAO.listerTous();
    }

    /**
     * Met à jour un employé
     */
    public boolean mettreAJourEmployee(Employee employe) throws SQLException {
        return employeeDAO.mettreAJour(employe);
    }

    /**
     * Ajoute un employé
     */
    public boolean ajouterEmployee(Employee employe) throws SQLException {
        return employeeDAO.ajouter(employe);
    }

    /**
     * Supprime un employé par ID
     */
    public boolean supprimerEmployee(int id) throws SQLException {
        return employeeDAO.supprimer(id);
    }

    /**
     * Trouve un employé par ID
     */
    public Employee trouverEmployee(int id) throws SQLException {
        return employeeDAO.trouver(id);
    }

    /**
     * Vérifie les identifiants d'un utilisateur
     */
    public boolean verifierIdentifiants(String username, String password) throws SQLException {
        return utilisateurDAO.verifierIdentifiants(username, password);
    }

    /**
     * Trouve un utilisateur par son nom d'utilisateur
     */
    public Utilisateur trouverUtilisateur(String username) throws SQLException {
        return utilisateurDAO.trouver(username);
    }

    /**
     * Ajoute un nouvel utilisateur
     */
    public boolean ajouterUtilisateur(Utilisateur utilisateur) throws SQLException {
        return utilisateurDAO.ajouter(utilisateur);
    }

    /**
     * Supprime un utilisateur
     */
    public boolean supprimerUtilisateur(String username) throws SQLException {
        return utilisateurDAO.supprimer(username);
    }

    /**
     * Valide les données d'un employé
     */
    public String validerEmployee(Employee employe) {
        if (employe == null) {
            return "L'employé ne peut pas être null";
        }

        if (employe.getMatricule() == null || employe.getMatricule().trim().isEmpty()) {
            return "Le matricule est obligatoire";
        }

        if (employe.getNom() == null || employe.getNom().trim().isEmpty()) {
            return "Le nom est obligatoire";
        }

        if (employe.getPrenom() == null || employe.getPrenom().trim().isEmpty()) {
            return "Le prénom est obligatoire";
        }

        if (employe.getSalaire() < 0) {
            return "Le salaire ne peut pas être négatif";
        }

        return null; // Aucune erreur
    }

    /**
     * Calcule l'ancienneté d'un employé en années
     */
    public int calculerAnciennete(Employee employe) {
        if (employe.getDateEmbauche() == null) {
            return 0;
        }

        java.util.Date maintenant = new java.util.Date();
        long diff = maintenant.getTime() - employe.getDateEmbauche().getTime();
        long diffYears = diff / (1000L * 60 * 60 * 24 * 365);
        return (int) diffYears;
    }
}