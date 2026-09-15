package View;

import javax.swing.*;
import java.awt.*;

/**
 * Fenêtre d'authentification pour l'application de gestion des employés
 */
public class FenetreAuthentification extends JFrame {
    // Composants de l'interface
    private JPanel panelPrincipal;
    private JLabel labelTitre;
    private JLabel labelUsername;
    private JLabel labelPassword;
    private JTextField champUsername;
    private JPasswordField champPassword;
    private JButton boutonConnexion;
    private JButton boutonAnnuler;
    private JProgressBar progressBar;

    /**
     * Constructeur - Initialise la fenêtre d'authentification
     */
    public FenetreAuthentification() {
        initialiserComposants();
        configurerFenetre();
        ajouterComposants();
    }

    /**
     * Initialise tous les composants de l'interface
     */
    private void initialiserComposants() {
        // Panel principal
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridBagLayout());
        panelPrincipal.setBackground(new Color(240, 240, 240));

        // Titre
        labelTitre = new JLabel("Authentification");
        labelTitre.setFont(new Font("Arial", Font.BOLD, 24));
        labelTitre.setForeground(new Color(0, 102, 204));

        // Labels
        labelUsername = new JLabel("Nom d'utilisateur:");
        labelUsername.setFont(new Font("Arial", Font.PLAIN, 14));

        labelPassword = new JLabel("Mot de passe:");
        labelPassword.setFont(new Font("Arial", Font.PLAIN, 14));

        // Champs de saisie
        champUsername = new JTextField(20);
        champUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        champUsername.setToolTipText("Entrez votre nom d'utilisateur");

        champPassword = new JPasswordField(20);
        champPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        champPassword.setToolTipText("Entrez votre mot de passe");

        // Boutons
        boutonConnexion = new JButton("Connexion");
        boutonConnexion.setFont(new Font("Arial", Font.BOLD, 14));
        boutonConnexion.setBackground(new Color(76, 175, 80));
        boutonConnexion.setForeground(Color.BLACK);
        boutonConnexion.setFocusPainted(false);
        boutonConnexion.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boutonAnnuler = new JButton("Annuler");
        boutonAnnuler.setFont(new Font("Arial", Font.PLAIN, 14));
        boutonAnnuler.setBackground(new Color(244, 67, 54));
        boutonAnnuler.setForeground(Color.BLACK);
        boutonAnnuler.setFocusPainted(false);
        boutonAnnuler.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Barre de progression
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(false);
        progressBar.setVisible(false);
        progressBar.setString("Connexion en cours...");
        progressBar.setStringPainted(true);
    }

    /**
     * Configure les propriétés de la fenêtre
     */
    private void configurerFenetre() {
        setTitle("Gestion des Employés - Authentification");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null); // Centrer la fenêtre
        setResizable(false);

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
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Titre
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(labelTitre, gbc);

        // Espace
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)), gbc);

        // Nom d'utilisateur
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        panelPrincipal.add(labelUsername, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        panelPrincipal.add(champUsername, gbc);

        // Mot de passe
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_END;
        panelPrincipal.add(labelPassword, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        panelPrincipal.add(champPassword, gbc);

        // Barre de progression
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(progressBar, gbc);

        // Panel pour les boutons
        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panelBoutons.setBackground(new Color(240, 240, 240));
        panelBoutons.add(boutonConnexion);
        panelBoutons.add(boutonAnnuler);

        gbc.gridy = 5;
        panelPrincipal.add(panelBoutons, gbc);

        // Ajouter le panel principal à la fenêtre
        add(panelPrincipal);
    }

    // Getters pour les composants
    public JButton getBoutonConnexion() {
        return boutonConnexion;
    }

    public JButton getBoutonAnnuler() {
        return boutonAnnuler;
    }

    public JTextField getChampUsername() {
        return champUsername;
    }

    public JPasswordField getChampMotDePasse() {
        return champPassword;
    }

    /**
     * Récupère le nom d'utilisateur saisi
     */
    public String getUsername() {
        return champUsername.getText().trim();
    }

    /**
     * Récupère le mot de passe saisi
     */
    public String getPassword() {
        return new String(champPassword.getPassword());
    }

    /**
     * Efface le champ du mot de passe
     */
    public void effacerMotDePasse() {
        champPassword.setText("");
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

        boutonConnexion.setEnabled(!enCours);
        boutonAnnuler.setEnabled(!enCours);
        champUsername.setEnabled(!enCours);
        champPassword.setEnabled(!enCours);
    }
}