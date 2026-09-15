package dao;


import Model.Employee;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface pour le DAO des employés
 * Définit les opérations CRUD sur les employés
 */
public interface IEmployeeDAO {

    /**
     * Ajoute un nouvel employé
     * @param employe L'employé à ajouter
     * @return true si l'ajout a réussi, false sinon
     * @throws SQLException en cas d'erreur SQL
     */
    boolean ajouter(Employee employe) throws SQLException;

    /**
     * Met à jour un employé existant
     * @param employe L'employé avec les nouvelles données
     * @return true si la mise à jour a réussi, false sinon
     * @throws SQLException en cas d'erreur SQL
     */
    boolean mettreAJour(Employee employe) throws SQLException;

    /**
     * Supprime un employé par son ID
     * @param id L'ID de l'employé à supprimer
     * @return true si la suppression a réussi, false sinon
     * @throws SQLException en cas d'erreur SQL
     */
    boolean supprimer(int id) throws SQLException;

    /**
     * Trouve un employé par son ID
     * @param id L'ID de l'employé à trouver
     * @return L'employé trouvé ou null si non trouvé
     * @throws SQLException en cas d'erreur SQL
     */
    Employee trouver(int id) throws SQLException;

    /**
     * Trouve un employé par son matricule
     * @param matricule Le matricule de l'employé
     * @return L'employé trouvé ou null si non trouvé
     * @throws SQLException en cas d'erreur SQL
     */
    Employee trouverParMatricule(String matricule) throws SQLException;

    /**
     * Vérifie si un employé existe par matricule
     * @param matricule Le matricule à vérifier
     * @return true si l'employé existe, false sinon
     * @throws SQLException en cas d'erreur SQL
     */
    boolean existeParMatricule(String matricule) throws SQLException;

    /**
     * Recherche des employés par nom (contient)
     * @param nom Le nom ou partie du nom à rechercher
     * @return Liste des employés correspondants
     * @throws SQLException en cas d'erreur SQL
     */
    List<Employee> rechercherParNom(String nom) throws SQLException;

    /**
     * Liste tous les employés
     * @return Liste de tous les employés
     * @throws SQLException en cas d'erreur SQL
     */
    List<Employee> listerTous() throws SQLException;

    /**
     * Compte le nombre total d'employés
     * @return Le nombre d'employés
     * @throws SQLException en cas d'erreur SQL
     */
    int compter() throws SQLException;

    /**
     * Recherche des employés par département
     * @param departement Le département à rechercher
     * @return Liste des employés du département
     * @throws SQLException en cas d'erreur SQL
     */
    List<Employee> rechercherParDepartement(String departement) throws SQLException;

    /**
     * Recherche des employés par poste
     * @param poste Le poste à rechercher
     * @return Liste des employés avec ce poste
     * @throws SQLException en cas d'erreur SQL
     */
    List<Employee> rechercherParPoste(String poste) throws SQLException;
}