package Controller;


import dao.EmployeeDAO;
import dao.UtilisateurDAO;
import Model.EmployeeManager;
import Model.SessionUtilisateur;
import Util.DatabaseConnection;
import View.FenetreAuthentification;
import View.FenetrePrincipale;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Contrôleur principal de l'application
 * Gère le démarrage et la navigation entre les différentes vues
 */
public class MainController {
    private EmployeeManager employeeManager;
    private FenetreAuthentification fenetreAuth;
    private FenetrePrincipale fenetrePrincipale;

    /**
     * Constructeur principal - initialise l'application
     */
    public MainController() {
        initialiserApplication();
    }

    /**
     * Initialise tous les composants de l'application
     */
    private void initialiserApplication() {
        try {
            // 1. Vérifier et établir la connexion à la base de données
            System.out.println("Initialisation de la base de données...");
            Connection connection = DatabaseConnection.getInstance().getConnection();

            if (connection == null || connection.isClosed()) {
                throw new SQLException("Impossible d'établir la connexion à la base de données");
            }

            // 2. Initialiser les DAO (Data Access Objects)
            System.out.println("Initialisation des DAO...");
            EmployeeDAO employeeDAO = new EmployeeDAO(connection);
            UtilisateurDAO utilisateurDAO = new UtilisateurDAO(connection);

            // 3. Initialiser le gestionnaire d'employés
            System.out.println("Initialisation du gestionnaire d'employés...");
            employeeManager = new EmployeeManager(employeeDAO, utilisateurDAO);



            // 5. Afficher la fenêtre d'authentification
            System.out.println("Affichage de la fenêtre d'authentification...");
            afficherFenetreAuthentification();

            System.out.println("Application initialisée avec succès !");

        } catch (SQLException e) {
            afficherErreurCritique("Erreur de base de données: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            afficherErreurCritique("Erreur lors de l'initialisation: " + e.getMessage());
            System.err.println("Erreur détaillée: " + e.getMessage());
            System.exit(1);
        }
    }


    /**
     * Affiche la fenêtre d'authentification
     */
    private void afficherFenetreAuthentification() {
        SwingUtilities.invokeLater(() -> {
            fenetreAuth = new FenetreAuthentification();
            new AuthController(fenetreAuth, employeeManager);
            fenetreAuth.setVisible(true);
        });
    }

    /**
     * Ouvre la fenêtre principale après authentification réussie
     */
    public void ouvrirFenetrePrincipale() {
        SwingUtilities.invokeLater(() -> {
            // Fermer la fenêtre d'authentification
            if (fenetreAuth != null) {
                fenetreAuth.dispose();
                fenetreAuth = null;
            }

            // Créer et afficher la fenêtre principale
            fenetrePrincipale = new FenetrePrincipale();
            new EmployeeController(fenetrePrincipale, employeeManager);
            fenetrePrincipale.setVisible(true);
        });
    }

    /**
     * Déconnecte l'utilisateur et retourne à l'écran d'authentification
     */
    public void deconnecter() {
        SwingUtilities.invokeLater(() -> {
            // Fermer la fenêtre principale si elle existe
            if (fenetrePrincipale != null) {
                fenetrePrincipale.dispose();
                fenetrePrincipale = null;
            }

            // Déconnecter la session
            SessionUtilisateur.deconnecter();

            // Réafficher la fenêtre d'authentification
            afficherFenetreAuthentification();
        });
    }

    /**
     * Ferme proprement l'application
     */
    public void quitterApplication() {
        try {
            // Fermer la connexion à la base de données
            DatabaseConnection.getInstance().closeConnection();
            System.out.println("Connexion à la base de données fermée.");
        } catch (Exception e) {
            System.err.println("Erreur lors de la fermeture de la connexion: " + e.getMessage());
        }

        // Fermer toutes les fenêtres
        if (fenetreAuth != null) {
            fenetreAuth.dispose();
            fenetreAuth = null;
        }
        if (fenetrePrincipale != null) {
            fenetrePrincipale.dispose();
            fenetrePrincipale = null;
        }

        System.out.println("Application fermée.");
        System.exit(0);
    }

    /**
     * Affiche une erreur critique et quitte l'application
     */
    private void afficherErreurCritique(String message) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(
                    null,
                    message + "\nL'application va se fermer.",
                    "Erreur Critique",
                    JOptionPane.ERROR_MESSAGE
            );
        });
    }
}