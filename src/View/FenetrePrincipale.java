package View;

import Model.Employee;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Fenêtre principale de l'application de gestion des employés
 */
public class FenetrePrincipale extends JFrame {
    // Composants de l'interface
    private JPanel panelPrincipal;
    private JMenuBar menuBar;
    private JMenu menuFichier;
    private JMenu menuAide;
    private JMenuItem menuItemDeconnexion;
    private JMenuItem menuItemQuitter;
    private JMenuItem menuItemAPropos;

    private JLabel labelTitre;
    private JLabel labelStatut;

    // Panel de recherche
    private JPanel panelRecherche;
    private JLabel labelRecherche;
    private JTextField champRecherche;
    private JComboBox<String> comboTypeRecherche;
    private JButton boutonRechercher;
    private JButton boutonActualiser;

    // Panel du formulaire
    private JPanel panelFormulaire;
    private JLabel labelMatricule;
    private JLabel labelNom;
    private JLabel labelPrenom;
    private JLabel labelPoste;
    private JLabel labelSalaire;
    private JLabel labelDepartement;
    private JLabel labelDateEmbauche;

    private JTextField champMatricule;
    private JTextField champNom;
    private JTextField champPrenom;
    private JComboBox<String> comboPoste;
    private JTextField champSalaire;
    private JComboBox<String> comboDepartement;
    private JTextField champDateEmbauche;

    private JButton boutonAjouter;
    private JButton boutonModifier;
    private JButton boutonSupprimer;
    private JButton boutonVider;

    // Panel de la table
    private JPanel panelTable;
    private JScrollPane scrollPaneTable;
    private JTable tableEmployes;
    private DefaultTableModel tableModel;

    private JProgressBar progressBar;
    private SimpleDateFormat dateFormat;

    /**
     * Constructeur - Initialise la fenêtre principale
     */
    public FenetrePrincipale() {
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        initialiserComposants();
        configurerFenetre();
        ajouterComposants();
    }

    /**
     * Initialise tous les composants de l'interface
     */
    private void initialiserComposants() {
        // Panel principal
        panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelPrincipal.setBackground(new Color(240, 240, 240));

        // Barre de menus
        initialiserBarreMenus();

        // Titre et statut
        initialiserTitre();

        // Panel de recherche
        initialiserPanelRecherche();

        // Panel du formulaire
        initialiserPanelFormulaire();

        // Panel de la table
        initialiserPanelTable();

        // Barre de progression
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(false);
        progressBar.setVisible(false);
        progressBar.setString("Chargement en cours...");
        progressBar.setStringPainted(true);
    }

    /**
     * Initialise la barre de menus
     */
    private void initialiserBarreMenus() {
        menuBar = new JMenuBar();

        // Menu Fichier
        menuFichier = new JMenu("Fichier");
        menuFichier.setFont(new Font("Arial", Font.PLAIN, 14));

        menuItemDeconnexion = new JMenuItem("Déconnexion");
        menuItemDeconnexion.setFont(new Font("Arial", Font.PLAIN, 14));
        menuItemDeconnexion.setAccelerator(KeyStroke.getKeyStroke("ctrl D"));

        menuItemQuitter = new JMenuItem("Quitter");
        menuItemQuitter.setFont(new Font("Arial", Font.PLAIN, 14));
        menuItemQuitter.setAccelerator(KeyStroke.getKeyStroke("ctrl Q"));

        menuFichier.add(menuItemDeconnexion);
        menuFichier.addSeparator();
        menuFichier.add(menuItemQuitter);

        // Menu Aide
        menuAide = new JMenu("Aide");
        menuAide.setFont(new Font("Arial", Font.PLAIN, 14));

        menuItemAPropos = new JMenuItem("À propos");
        menuItemAPropos.setFont(new Font("Arial", Font.PLAIN, 14));
        menuItemAPropos.setAccelerator(KeyStroke.getKeyStroke("F1"));

        menuAide.add(menuItemAPropos);

        menuBar.add(menuFichier);
        menuBar.add(menuAide);
    }

    /**
     * Initialise le titre et le statut
     */
    private void initialiserTitre() {
        JPanel panelTitre = new JPanel(new BorderLayout());
        panelTitre.setBackground(new Color(240, 240, 240));

        labelTitre = new JLabel("GESTION DES EMPLOYÉS", SwingConstants.CENTER);
        labelTitre.setFont(new Font("Arial", Font.BOLD, 28));
        labelTitre.setForeground(new Color(0, 102, 204));

        labelStatut = new JLabel("Prêt", SwingConstants.LEFT);
        labelStatut.setFont(new Font("Arial", Font.PLAIN, 12));
        labelStatut.setForeground(Color.DARK_GRAY);

        panelTitre.add(labelTitre, BorderLayout.CENTER);
        panelTitre.add(labelStatut, BorderLayout.SOUTH);

        panelPrincipal.add(panelTitre, BorderLayout.NORTH);
    }

    /**
     * Initialise le panel de recherche
     */
    private void initialiserPanelRecherche() {
        panelRecherche = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelRecherche.setBackground(new Color(220, 230, 240));
        panelRecherche.setBorder(BorderFactory.createTitledBorder("Recherche"));

        labelRecherche = new JLabel("Rechercher:");
        labelRecherche.setFont(new Font("Arial", Font.PLAIN, 14));

        champRecherche = new JTextField(20);
        champRecherche.setFont(new Font("Arial", Font.PLAIN, 14));
        champRecherche.setToolTipText("Entrez un terme de recherche");

        comboTypeRecherche = new JComboBox<>(new String[]{"Matricule", "Nom"});
        comboTypeRecherche.setFont(new Font("Arial", Font.PLAIN, 14));

        boutonRechercher = new JButton("Rechercher");
        boutonRechercher.setFont(new Font("Arial", Font.PLAIN, 14));
        boutonRechercher.setBackground(new Color(33, 150, 243));
        boutonRechercher.setForeground(Color.BLACK);
        boutonRechercher.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boutonActualiser = new JButton("Actualiser");
        boutonActualiser.setFont(new Font("Arial", Font.PLAIN, 14));
        boutonActualiser.setBackground(new Color(76, 175, 80));
        boutonActualiser.setForeground(Color.BLACK);
        boutonActualiser.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelRecherche.add(labelRecherche);
        panelRecherche.add(champRecherche);
        panelRecherche.add(comboTypeRecherche);
        panelRecherche.add(boutonRechercher);
        panelRecherche.add(boutonActualiser);
    }

    /**
     * Initialise le panel du formulaire
     */
    private void initialiserPanelFormulaire() {
        panelFormulaire = new JPanel(new GridLayout(4, 4, 10, 10));
        panelFormulaire.setBackground(new Color(250, 250, 250));
        panelFormulaire.setBorder(BorderFactory.createTitledBorder("Informations de l'employé"));

        // Labels
        labelMatricule = creerLabel("Matricule*:");
        labelNom = creerLabel("Nom*:");
        labelPrenom = creerLabel("Prénom*:");
        labelPoste = creerLabel("Poste:");
        labelSalaire = creerLabel("Salaire:");
        labelDepartement = creerLabel("Département:");
        labelDateEmbauche = creerLabel("Date d'embauche:");

        // Champs de saisie
        champMatricule = creerTextField();
        champNom = creerTextField();
        champPrenom = creerTextField();
        comboPoste = new JComboBox<>();
        comboPoste.setFont(new Font("Arial", Font.PLAIN, 14));

        champSalaire = creerTextField();
        comboDepartement = new JComboBox<>();
        comboDepartement.setFont(new Font("Arial", Font.PLAIN, 14));

        champDateEmbauche = creerTextField();
        champDateEmbauche.setToolTipText("Format: JJ/MM/AAAA");

        // Boutons d'action
        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelBoutons.setBackground(new Color(250, 250, 250));

        boutonAjouter = creerBouton("Ajouter", new Color(76, 175, 80));
        boutonModifier = creerBouton("Modifier", new Color(255, 152, 0));
        boutonSupprimer = creerBouton("Supprimer", new Color(244, 67, 54));
        boutonVider = creerBouton("Vider", new Color(158, 158, 158));

        // Désactiver les boutons Modifier et Supprimer initialement
        boutonModifier.setEnabled(false);
        boutonSupprimer.setEnabled(false);

        panelBoutons.add(boutonAjouter);
        panelBoutons.add(boutonModifier);
        panelBoutons.add(boutonSupprimer);
        panelBoutons.add(boutonVider);

        // Ajouter les composants au panel
        panelFormulaire.add(labelMatricule);
        panelFormulaire.add(champMatricule);
        panelFormulaire.add(labelNom);
        panelFormulaire.add(champNom);
        panelFormulaire.add(labelPrenom);
        panelFormulaire.add(champPrenom);
        panelFormulaire.add(labelPoste);
        panelFormulaire.add(comboPoste);
        panelFormulaire.add(labelSalaire);
        panelFormulaire.add(champSalaire);
        panelFormulaire.add(labelDepartement);
        panelFormulaire.add(comboDepartement);
        panelFormulaire.add(labelDateEmbauche);
        panelFormulaire.add(champDateEmbauche);

        // Panel principal avec formulaire et boutons
        JPanel panelFormulaireComplet = new JPanel(new BorderLayout(0, 10));
        panelFormulaireComplet.setBackground(new Color(240, 240, 240));
        panelFormulaireComplet.add(panelFormulaire, BorderLayout.CENTER);
        panelFormulaireComplet.add(panelBoutons, BorderLayout.SOUTH);

        // Ajouter le panel de recherche et formulaire à un panel nord
        JPanel panelNord = new JPanel(new BorderLayout(0, 10));
        panelNord.setBackground(new Color(240, 240, 240));
        panelNord.add(panelRecherche, BorderLayout.NORTH);
        panelNord.add(panelFormulaireComplet, BorderLayout.CENTER);

        panelPrincipal.add(panelNord, BorderLayout.CENTER);
    }

    /**
     * Initialise le panel de la table
     */
    private void initialiserPanelTable() {
        panelTable = new JPanel(new BorderLayout());
        panelTable.setBackground(Color.WHITE);
        panelTable.setBorder(BorderFactory.createTitledBorder("Liste des employés"));

        // Modèle de table
        String[] colonnes = {"ID", "Matricule", "Nom", "Prénom", "Poste", "Salaire", "Département", "Date d'embauche"};
        tableModel = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Rendre la table non éditable
            }
        };

        // Table
        tableEmployes = new JTable(tableModel);
        tableEmployes.setFont(new Font("Arial", Font.PLAIN, 12));
        tableEmployes.setRowHeight(25);
        tableEmployes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableEmployes.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tableEmployes.getTableHeader().setBackground(new Color(33, 150, 243));
        tableEmployes.getTableHeader().setForeground(Color.WHITE);

        // ScrollPane
        scrollPaneTable = new JScrollPane(tableEmployes);
        scrollPaneTable.setPreferredSize(new Dimension(800, 300));

        panelTable.add(scrollPaneTable, BorderLayout.CENTER);

        // Ajouter la table en bas
        panelPrincipal.add(panelTable, BorderLayout.SOUTH);
    }

    /**
     * Configure les propriétés de la fenêtre
     */
    private void configurerFenetre() {
        setTitle("Application de Gestion des Employés");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null); // Centrer la fenêtre
        setJMenuBar(menuBar);

        // Utiliser un look and feel système
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement du look and feel: " + e.getMessage());
        }
    }

    /**
     * Ajoute les composants au panel principal
     */
    private void ajouterComposants() {
        // Barre de progression en bas
        add(progressBar, BorderLayout.SOUTH);

        // Panel principal
        add(panelPrincipal);
    }

    // Méthodes utilitaires pour créer des composants
    private JLabel creerLabel(String texte) {
        JLabel label = new JLabel(texte);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        return label;
    }

    private JTextField creerTextField() {
        JTextField champ = new JTextField();
        champ.setFont(new Font("Arial", Font.PLAIN, 14));
        return champ;
    }

    private JButton creerBouton(String texte, Color couleur) {
        JButton bouton = new JButton(texte);
        bouton.setFont(new Font("Arial", Font.BOLD, 14));
        bouton.setBackground(couleur);
        bouton.setForeground(Color.BLACK);
        bouton.setFocusPainted(false);
        bouton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return bouton;
    }

    // Getters pour les composants
    public JMenuItem getMenuItemDeconnexion() {
        return menuItemDeconnexion;
    }

    public JMenuItem getMenuItemQuitter() {
        return menuItemQuitter;
    }

    public JMenuItem getMenuItemAPropos() {
        return menuItemAPropos;
    }

    public JButton getBoutonAjouter() {
        return boutonAjouter;
    }

    public JButton getBoutonModifier() {
        return boutonModifier;
    }

    public JButton getBoutonSupprimer() {
        return boutonSupprimer;
    }

    public JButton getBoutonActualiser() {
        return boutonActualiser;
    }

    public JButton getBoutonRechercher() {
        return boutonRechercher;
    }

    public JButton getBoutonVider() {
        return boutonVider;
    }

    public JTable getTableEmployes() {
        return tableEmployes;
    }

    public JTextField getChampRecherche() {
        return champRecherche;
    }

    // Méthodes pour la gestion des données
    /**
     * Affiche la liste des employés dans la table
     */
    public void afficherEmployes(List<Employee> employes) {
        // Vider la table
        tableModel.setRowCount(0);

        // Ajouter les employés
        for (Employee emp : employes) {
            Object[] ligne = {
                    emp.getId(),
                    emp.getMatricule(),
                    emp.getNom(),
                    emp.getPrenom(),
                    emp.getPoste(),
                    String.format("%.2f", emp.getSalaire()) + " €",
                    emp.getDepartement(),
                    emp.getDateEmbauche() != null ? dateFormat.format(emp.getDateEmbauche()) : "N/A"
            };
            tableModel.addRow(ligne);
        }
    }

    /**
     * Récupère les données du formulaire pour créer un nouvel employé
     */
    public Employee getDonneesEmploye() {
        try {
            // Validation des champs obligatoires
            String matricule = champMatricule.getText().trim();
            String nom = champNom.getText().trim();
            String prenom = champPrenom.getText().trim();

            if (matricule.isEmpty() || nom.isEmpty() || prenom.isEmpty()) {
                afficherErreur("Les champs Matricule, Nom et Prénom sont obligatoires");
                return null;
            }

            // Créer l'employé
            Employee employe = new Employee();
            employe.setMatricule(matricule);
            employe.setNom(nom);
            employe.setPrenom(prenom);
            employe.setPoste((String) comboPoste.getSelectedItem());

            // Salaire
            try {
                if (!champSalaire.getText().trim().isEmpty()) {
                    double salaire = Double.parseDouble(champSalaire.getText().trim());
                    employe.setSalaire(salaire);
                }
            } catch (NumberFormatException e) {
                afficherErreur("Le salaire doit être un nombre valide");
                return null;
            }

            employe.setDepartement((String) comboDepartement.getSelectedItem());

            // Date d'embauche
            String dateText = champDateEmbauche.getText().trim();
            if (!dateText.isEmpty()) {
                try {
                    employe.setDateEmbauche(dateFormat.parse(dateText));
                } catch (Exception e) {
                    afficherErreur("Format de date invalide. Utilisez JJ/MM/AAAA");
                    return null;
                }
            }

            return employe;

        } catch (Exception e) {
            afficherErreur("Erreur lors de la récupération des données: " + e.getMessage());
            return null;
        }
    }

    /**
     * Remplit le formulaire avec les données d'un employé
     */
    public void remplirFormulaire(Employee employe) {
        champMatricule.setText(employe.getMatricule());
        champNom.setText(employe.getNom());
        champPrenom.setText(employe.getPrenom());

        if (employe.getPoste() != null) {
            comboPoste.setSelectedItem(employe.getPoste());
        }

        champSalaire.setText(String.format("%.2f", employe.getSalaire()));

        if (employe.getDepartement() != null) {
            comboDepartement.setSelectedItem(employe.getDepartement());
        }

        if (employe.getDateEmbauche() != null) {
            champDateEmbauche.setText(dateFormat.format(employe.getDateEmbauche()));
        }
    }

    /**
     * Vide le formulaire
     */
    public void viderFormulaire() {
        champMatricule.setText("");
        champNom.setText("");
        champPrenom.setText("");
        comboPoste.setSelectedIndex(0);
        champSalaire.setText("");
        comboDepartement.setSelectedIndex(0);
        champDateEmbauche.setText("");
    }

    /**
     * Récupère le critère de recherche
     */
    public String getCritereRecherche() {
        return champRecherche.getText().trim();
    }

    /**
     * Récupère le type de recherche
     */
    public String getTypeRecherche() {
        return (String) comboTypeRecherche.getSelectedItem();
    }

    /**
     * Donne le focus au champ de recherche
     */
    public void focusRecherche() {
        champRecherche.requestFocus();
    }

    /**
     * Affiche un message dans la barre de statut
     */
    public void afficherMessageStatut(String message) {
        labelStatut.setText("Statut: " + message);
    }

    /**
     * Affiche un message d'erreur
     */
    public void afficherErreur(String message) {
        JOptionPane.showMessageDialog(this,
                message,
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Affiche un message d'information
     */
    public void afficherMessage(String message, String titre, int typeMessage) {
        JOptionPane.showMessageDialog(this,
                message,
                titre,
                typeMessage);
    }

    /**
     * Active/Désactive l'indicateur de chargement
     */
    public void setChargementEnCours(boolean enCours) {
        progressBar.setVisible(enCours);
        progressBar.setIndeterminate(enCours);

        // Désactiver les boutons pendant le chargement
        boutonAjouter.setEnabled(!enCours);
        boutonModifier.setEnabled(!enCours && boutonModifier.isEnabled());
        boutonSupprimer.setEnabled(!enCours && boutonSupprimer.isEnabled());
        boutonVider.setEnabled(!enCours);
        boutonRechercher.setEnabled(!enCours);
        boutonActualiser.setEnabled(!enCours);
    }

    /**
     * Définit les départements disponibles
     */
    public void setDepartements(String[] departements) {
        comboDepartement.setModel(new DefaultComboBoxModel<>(departements));
    }

    /**
     * Définit les postes disponibles
     */
    public void setPostes(String[] postes) {
        comboPoste.setModel(new DefaultComboBoxModel<>(postes));
    }

    /**
     * Définit le titre de l'utilisateur
     */
    public void setTitreUtilisateur(String titre) {
        labelTitre.setText("GESTION DES EMPLOYÉS - " + titre.toUpperCase());
    }
}