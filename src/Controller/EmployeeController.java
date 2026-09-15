package Controller;


import Model.Employee;
import Model.EmployeeManager;
import Model.SessionUtilisateur;
import View.FenetrePrincipale;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Contrôleur principal pour la gestion des employés
 * Gère les interactions avec la fenêtre principale
 */
public class EmployeeController {
    private FenetrePrincipale vue;
    private EmployeeManager manager;
    private Employee employeSelectionne;
    private SimpleDateFormat dateFormat;

    /**
     * Constructeur
     */
    public EmployeeController(FenetrePrincipale vue, EmployeeManager manager) {
        this.vue = vue;
        this.manager = manager;
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        initialiserEcouteurs();
        initialiserInterface();
    }

    /**
     * Initialise l'interface et charge les données initiales
     */
    private void initialiserInterface() {
        // Afficher le nom de l'utilisateur connecté
        String username = SessionUtilisateur.getUtilisateurCourant().getUsername();
        vue.setTitreUtilisateur("Utilisateur: " + username);

        // Charger la liste des employés
        chargerListeEmployes();

        // Initialiser les valeurs par défaut des champs
        initialiserChamps();

        // Mettre à jour l'état des boutons
        mettreAJourEtatBoutons();
    }

    /**
     * Initialise tous les écouteurs d'événements
     */
    private void initialiserEcouteurs() {
        // Écouteurs pour les boutons d'action
        initialiserEcouteursBoutons();

        // Écouteurs pour la table des employés
        initialiserEcouteursTable();

        // Écouteurs pour les menus
        initialiserEcouteursMenus();

        // Écouteurs pour les raccourcis clavier
        initialiserRaccourcisClavier();
    }

    /**
     * Initialise les écouteurs pour les boutons
     */
    private void initialiserEcouteursBoutons() {
        // Bouton Ajouter
        vue.getBoutonAjouter().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterEmploye();
            }
        });

        // Bouton Modifier
        vue.getBoutonModifier().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifierEmploye();
            }
        });

        // Bouton Supprimer
        vue.getBoutonSupprimer().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supprimerEmploye();
            }
        });

        // Bouton Actualiser
        vue.getBoutonActualiser().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chargerListeEmployes();
            }
        });

        // Bouton Rechercher
        vue.getBoutonRechercher().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rechercherEmployes();
            }
        });

        // Bouton Vider
        vue.getBoutonVider().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viderFormulaire();
                mettreAJourEtatBoutons();
            }
        });
    }

    /**
     * Initialise les écouteurs pour la table
     */
    private void initialiserEcouteursTable() {
        vue.getTableEmployes().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    selectionnerEmployeDansTable();
                }
            }
        });
    }

    /**
     * Initialise les écouteurs pour les menus
     */
    private void initialiserEcouteursMenus() {
        // Menu Fichier → Déconnexion
        vue.getMenuItemDeconnexion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deconnecter();
            }
        });

        // Menu Fichier → Quitter
        vue.getMenuItemQuitter().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quitter();
            }
        });

        // Menu Aide → À propos
        vue.getMenuItemAPropos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afficherAPropos();
            }
        });
    }

    /**
     * Initialise les raccourcis clavier
     */
    private void initialiserRaccourcisClavier() {
        // Raccourci Ctrl+N pour Nouveau (Ajouter)
        vue.getRootPane().registerKeyboardAction(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ajouterEmploye();
                    }
                },
                KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );

        // Raccourci Ctrl+F pour Rechercher
        vue.getRootPane().registerKeyboardAction(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        vue.focusRecherche();
                    }
                },
                KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );

        // Raccourci F5 pour Actualiser
        vue.getRootPane().registerKeyboardAction(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        chargerListeEmployes();
                    }
                },
                KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );
    }

    /**
     * Charge la liste complète des employés dans la table
     */
    private void chargerListeEmployes() {
        try {
            vue.setChargementEnCours(true);

            List<Employee> employes = manager.listerTousEmployees();
            vue.afficherEmployes(employes);

            vue.afficherMessageStatut("Chargé " + employes.size() + " employé(s)");

        } catch (Exception e) {
            vue.afficherErreur("Erreur lors du chargement des employés: " + e.getMessage());
            System.err.println("Erreur de chargement: " + e.getMessage());
        } finally {
            vue.setChargementEnCours(false);
        }
    }

    /**
     * Ajoute un nouvel employé
     */
    private void ajouterEmploye() {
        try {
            // Récupérer les données du formulaire
            Employee nouvelEmploye = vue.getDonneesEmploye();

            if (nouvelEmploye == null) {
                return; // Validation a échoué
            }

            // Validation supplémentaire avec EmployeeManager
            String messageValidation = manager.validerEmployee(nouvelEmploye);
            if (messageValidation != null) {
                vue.afficherErreur(messageValidation);
                return;
            }

            // Vérifier que le matricule n'existe pas déjà
            if (manager.employeExiste(nouvelEmploye.getMatricule())) {
                vue.afficherErreur("Un employé avec ce matricule existe déjà");
                return;
            }

            // Ajouter l'employé
            boolean succes = manager.ajouterEmployee(nouvelEmploye);

            if (succes) {
                vue.afficherMessage("Employé ajouté avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
                viderFormulaire();
                chargerListeEmployes();
            } else {
                vue.afficherErreur("Erreur lors de l'ajout de l'employé");
            }

        } catch (Exception e) {
            vue.afficherErreur("Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Modifie l'employé sélectionné
     */
    private void modifierEmploye() {
        if (employeSelectionne == null) {
            vue.afficherErreur("Veuillez sélectionner un employé à modifier");
            return;
        }

        try {
            // Récupérer les données modifiées
            Employee employeModifie = vue.getDonneesEmploye();

            if (employeModifie == null) {
                return; // Validation a échoué
            }

            // Conserver l'ID de l'employé original
            employeModifie.setId(employeSelectionne.getId());

            // Validation supplémentaire avec EmployeeManager
            String messageValidation = manager.validerEmployee(employeModifie);
            if (messageValidation != null) {
                vue.afficherErreur(messageValidation);
                return;
            }

            // Vérifier si le matricule a changé et s'il existe déjà
            if (!employeSelectionne.getMatricule().equals(employeModifie.getMatricule())) {
                if (manager.employeExiste(employeModifie.getMatricule())) {
                    vue.afficherErreur("Un autre employé avec ce matricule existe déjà");
                    return;
                }
            }

            // Mettre à jour l'employé
            boolean succes = manager.mettreAJourEmployee(employeModifie);

            if (succes) {
                vue.afficherMessage("Employé modifié avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
                viderFormulaire();
                chargerListeEmployes();
                employeSelectionne = null;
                mettreAJourEtatBoutons();
            } else {
                vue.afficherErreur("Erreur lors de la modification de l'employé");
            }

        } catch (Exception e) {
            vue.afficherErreur("Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Supprime l'employé sélectionné
     */
    private void supprimerEmploye() {
        if (employeSelectionne == null) {
            vue.afficherErreur("Veuillez sélectionner un employé à supprimer");
            return;
        }

        // Confirmation de suppression
        int confirmation = JOptionPane.showConfirmDialog(
                vue,
                "Êtes-vous sûr de vouloir supprimer l'employé :\n" +
                        employeSelectionne.getNom() + " " + employeSelectionne.getPrenom() +
                        " (" + employeSelectionne.getMatricule() + ")?\n\n" +
                        "Cette action est irréversible.",
                "Confirmation de suppression",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmation == JOptionPane.YES_OPTION) {
            try {
                boolean succes = manager.supprimerEmployee(employeSelectionne.getId());

                if (succes) {
                    vue.afficherMessage("Employé supprimé avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    viderFormulaire();
                    chargerListeEmployes();
                    employeSelectionne = null;
                    mettreAJourEtatBoutons();
                } else {
                    vue.afficherErreur("Erreur lors de la suppression de l'employé");
                }
            } catch (Exception e) {
                vue.afficherErreur("Erreur: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Recherche des employés selon les critères
     */
    private void rechercherEmployes() {
        String critere = vue.getCritereRecherche();
        String typeRecherche = vue.getTypeRecherche();

        if (critere == null || critere.trim().isEmpty()) {
            chargerListeEmployes();
            return;
        }

        try {
            vue.setChargementEnCours(true);

            List<Employee> resultats;

            if ("Matricule".equals(typeRecherche)) {
                Employee employe = manager.trouverEmployeeParMatricule(critere);
                resultats = (employe != null) ? java.util.Arrays.asList(employe) : java.util.Collections.emptyList();
            } else if ("Nom".equals(typeRecherche)) {
                resultats = manager.rechercherParNom(critere);
            } else {
                resultats = manager.listerTousEmployees();
            }

            vue.afficherEmployes(resultats);
            vue.afficherMessageStatut(resultats.size() + " résultat(s) trouvé(s)");

        } catch (Exception e) {
            vue.afficherErreur("Erreur lors de la recherche: " + e.getMessage());
        } finally {
            vue.setChargementEnCours(false);
        }
    }

    /**
     * Gère la sélection d'un employé dans la table
     */
    private void selectionnerEmployeDansTable() {
        int ligneSelectionnee = vue.getTableEmployes().getSelectedRow();

        if (ligneSelectionnee >= 0) {
            try {
                // Récupérer l'ID ou le matricule de l'employé sélectionné
                String matricule = (String) vue.getTableEmployes().getValueAt(ligneSelectionnee, 1);
                employeSelectionne = manager.trouverEmployeeParMatricule(matricule);

                if (employeSelectionne != null) {
                    // Remplir le formulaire avec les données de l'employé
                    vue.remplirFormulaire(employeSelectionne);
                    mettreAJourEtatBoutons();

                    vue.afficherMessageStatut("Employé sélectionné: " +
                            employeSelectionne.getNom() + " " +
                            employeSelectionne.getPrenom());
                }
            } catch (Exception e) {
                vue.afficherErreur("Erreur lors de la sélection: " + e.getMessage());
            }
        }
    }

    /**
     * Vide le formulaire et réinitialise la sélection
     */
    private void viderFormulaire() {
        vue.viderFormulaire();
        employeSelectionne = null;
        vue.getTableEmployes().clearSelection();
    }

    /**
     * Initialise les valeurs par défaut des champs
     */
    private void initialiserChamps() {
        vue.setDepartements(new String[]{
                "Informatique", "Ressources Humaines", "Comptabilité",
                "Marketing", "Production", "Commercial", "Direction", "Autre"
        });

        vue.setPostes(new String[]{
                "Développeur", "Analyste", "Chef de projet", "Responsable RH",
                "Comptable", "Commercial", "Directeur", "Technicien", "Autre"
        });
    }

    /**
     * Met à jour l'état des boutons selon le contexte
     */
    private void mettreAJourEtatBoutons() {
        boolean employeSelectionne = (this.employeSelectionne != null);

        vue.getBoutonModifier().setEnabled(employeSelectionne);
        vue.getBoutonSupprimer().setEnabled(employeSelectionne);
        vue.getBoutonVider().setEnabled(true);

        // Désactiver le bouton modifier si l'utilisateur n'est pas admin
        if (employeSelectionne && !SessionUtilisateur.estAdmin()) {
            vue.getBoutonModifier().setEnabled(false);
            vue.getBoutonSupprimer().setEnabled(false);
        }
    }

    /**
     * Déconnecte l'utilisateur
     */
    private void deconnecter() {
        int confirmation = JOptionPane.showConfirmDialog(
                vue,
                "Voulez-vous vraiment vous déconnecter ?",
                "Déconnexion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirmation == JOptionPane.YES_OPTION) {
            // Fermer la fenêtre
            vue.dispose();

            // Retourner à l'authentification via MainController
            // Cette méthode sera appelée depuis MainController
            // ou nous pouvons laisser MainController gérer cela
        }
    }

    /**
     * Quitte l'application
     */
    private void quitter() {
        int confirmation = JOptionPane.showConfirmDialog(
                vue,
                "Voulez-vous vraiment quitter l'application ?",
                "Quitter",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirmation == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    /**
     * Affiche la boîte de dialogue "À propos"
     */
    private void afficherAPropos() {
        JOptionPane.showMessageDialog(
                vue,
                "Application de Gestion des Employés\n" +
                        "Version 1.0\n\n" +
                        "Développé dans le cadre du mini-projet Java\n" +
                        "Gestion des employés avec interface graphique\n" +
                        "Base de données MySQL - Architecture MVC\n\n" +
                        "© 2024 - Tous droits réservés",
                "À propos",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}