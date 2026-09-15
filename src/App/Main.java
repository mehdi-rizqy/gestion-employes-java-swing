package App;

import Controller.MainController;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Utiliser SwingUtilities pour assurer le bon démarrage du thread EDT
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Démarrage de l'application
                    System.out.println("=== Application de Gestion des Employés ===");
                    System.out.println("Démarrage en cours...");

                    // Désactiver le look and feel par défaut pour plus de stabilité
                    try {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    } catch (Exception e) {
                        System.err.println("Erreur lors du chargement du look and feel: " + e.getMessage());
                    }

                    // Créer et démarrer le contrôleur principal
                    new MainController();

                    System.out.println("Application démarrée avec succès !");

                } catch (Exception e) {
                    // Journalisation de l'erreur
                    System.err.println("ERREUR FATALE: " + e.getMessage());
                    e.printStackTrace();

                    // Message utilisateur
                    JOptionPane.showMessageDialog(
                            null,
                            "Une erreur critique est survenue lors du démarrage:\n" +
                                    e.getMessage() + "\n\n" +
                                    "L'application va se fermer.",
                            "Erreur de démarrage",
                            JOptionPane.ERROR_MESSAGE
                    );

                    System.exit(1);
                }
            }
        });
    }
}