package dao;


import Model.Utilisateur;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface pour le DAO des utilisateurs
 * Définit les opérations CRUD sur les utilisateurs
 */
public interface IUtilisateurDAO {

    /**
     * Ajoute un nouvel utilisateur
     * @param utilisateur L'utilisateur à ajouter
     * @return true si l'ajout a réussi, false sinon
     * @throws SQLException en cas d'erreur SQL
     */
    boolean ajouter(Utilisateur utilisateur) throws SQLException;

    /**
     * Met à jour un utilisateur existant
     * @param utilisateur L'utilisateur avec les nouvelles données
     * @return true si la mise à jour a réussi, false sinon
     * @throws SQLException en cas d'erreur SQL
     */
    boolean mettreAJour(Utilisateur utilisateur) throws SQLException;

    /**
     * Supprime un utilisateur par son nom d'utilisateur
     * @param username Le nom d'utilisateur à supprimer
     * @return true si la suppression a réussi, false sinon
     * @throws SQLException en cas d'erreur SQL
     */
    boolean supprimer(String username) throws SQLException;

    /**
     * Trouve un utilisateur par son nom d'utilisateur
     * @param username Le nom d'utilisateur à trouver
     * @return L'utilisateur trouvé ou null si non trouvé
     * @throws SQLException en cas d'erreur SQL
     */
    Utilisateur trouver(String username) throws SQLException;

    /**
     * Vérifie si un utilisateur existe par son nom d'utilisateur
     * @param username Le nom d'utilisateur à vérifier
     * @return true si l'utilisateur existe, false sinon
     * @throws SQLException en cas d'erreur SQL
     */
    boolean existe(String username) throws SQLException;

    /**
     * Vérifie si des utilisateurs existent dans la base
     * @return true si au moins un utilisateur existe, false sinon
     * @throws SQLException en cas d'erreur SQL
     */
    boolean existent() throws SQLException;

    /**
     * Vérifie les identifiants d'un utilisateur
     * @param username Le nom d'utilisateur
     * @param password Le mot de passe
     * @return true si les identifiants sont valides, false sinon
     * @throws SQLException en cas d'erreur SQL
     */
    boolean verifierIdentifiants(String username, String password) throws SQLException;

    /**
     * Change le mot de passe d'un utilisateur
     * @param username Le nom d'utilisateur
     * @param nouveauMotDePasse Le nouveau mot de passe
     * @return true si le changement a réussi, false sinon
     * @throws SQLException en cas d'erreur SQL
     */
    boolean changerMotDePasse(String username, String nouveauMotDePasse) throws SQLException;

    /**
     * Change le rôle d'un utilisateur
     * @param username Le nom d'utilisateur
     * @param nouveauRole Le nouveau rôle
     * @return true si le changement a réussi, false sinon
     * @throws SQLException en cas d'erreur SQL
     */
    boolean changerRole(String username, String nouveauRole) throws SQLException;

    /**
     * Liste tous les utilisateurs
     * @return Liste de tous les utilisateurs
     * @throws SQLException en cas d'erreur SQL
     */
    List<Utilisateur> listerTous() throws SQLException;

    /**
     * Liste les utilisateurs par rôle
     * @param role Le rôle à filtrer
     * @return Liste des utilisateurs avec ce rôle
     * @throws SQLException en cas d'erreur SQL
     */
    List<Utilisateur> listerParRole(String role) throws SQLException;

    /**
     * Compte le nombre total d'utilisateurs
     * @return Le nombre d'utilisateurs
     * @throws SQLException en cas d'erreur SQL
     */
    int compter() throws SQLException;

    /**
     * Compte le nombre d'utilisateurs par rôle
     * @param role Le rôle à compter
     * @return Le nombre d'utilisateurs avec ce rôle
     * @throws SQLException en cas d'erreur SQL
     */
    int compterParRole(String role) throws SQLException;
}