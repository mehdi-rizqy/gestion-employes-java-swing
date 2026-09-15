package Controller;



import Model.EmployeeManager;
import Model.SessionUtilisateur;
import Model.Utilisateur;
import View.FenetreAuthentification;
import View.FenetrePrincipale;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthController {
    private FenetreAuthentification vue;
    private EmployeeManager manager;

    public AuthController(FenetreAuthentification vue, EmployeeManager manager) {
        this.vue = vue;
        this.manager = manager;
        initialiserEcouteurs();
    }

    private void initialiserEcouteurs() {
        // Écouteur pour le bouton de connexion
        vue.getBoutonConnexion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authentifierUtilisateur();
            }
        });

        // Écouteur pour le bouton d'annulation
        vue.getBoutonAnnuler().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Écouteur pour la touche Entrée dans les champs de texte
        vue.getChampMotDePasse().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authentifierUtilisateur();
            }
        });
    }

    private void authentifierUtilisateur() {
        String username = vue.getUsername();
        String password = vue.getPassword();

        // Validation des champs
        if (username.isEmpty() || password.isEmpty()) {
            vue.afficherErreur("Veuillez remplir tous les champs");
            return;
        }

        try {
            // Vérifier les identifiants
            boolean authentifie = manager.verifierIdentifiants(username, password);

            if (authentifie) {
                // Récupérer l'utilisateur et créer la session
                Utilisateur utilisateur = manager.trouverUtilisateur(username);
                if (utilisateur != null) {
                    SessionUtilisateur.connecter(utilisateur);

                    // Afficher un message de succès
                    vue.afficherMessage("Connexion réussie !", "Succès", JOptionPane.INFORMATION_MESSAGE);

                    // Ouvrir la fenêtre principale
                    ouvrirFenetrePrincipale();
                }
            } else {
                vue.afficherErreur("Nom d'utilisateur ou mot de passe incorrect");
            }
        } catch (Exception e) {
            vue.afficherErreur("Erreur d'authentification: " + e.getMessage());
        }
    }

    private void ouvrirFenetrePrincipale() {
        // Créer et afficher la fenêtre principale
        FenetrePrincipale fenetrePrincipale = new FenetrePrincipale();
        new EmployeeController(fenetrePrincipale, manager);
        fenetrePrincipale.setVisible(true);

        // Fermer la fenêtre d'authentification
        vue.dispose();
    }
}