// Elle centralise l'état de connexion et fournit des méthodes pour vérifier les rôles et les droits d'accès d'un utilisateur

package Model;


public class SessionUtilisateur {
    private static SessionUtilisateur instance;
    private Utilisateur utilisateurConnecte;

    /**
     * Constructeur privé pour empêcher l'instanciation directe (empêche la création d'objets en dehors de la classe.)
     */
    private SessionUtilisateur() {
        this.utilisateurConnecte = null;
    }

    /**
     * Retourne l'instance unique de SessionUtilisateur
     */
    public static synchronized SessionUtilisateur getInstance() {
        if (instance == null) {
            instance = new SessionUtilisateur();
        }
        return instance;
    }

    /**
     * Connecte un utilisateur
     */
    public static void connecter(Utilisateur utilisateur) {
        getInstance().utilisateurConnecte = utilisateur;
        System.out.println("Utilisateur connecté: " + utilisateur.getUsername());
    }

    /**
     * Déconnecte l'utilisateur courant
     */
    public static void deconnecter() {
        if (getInstance().utilisateurConnecte != null) {
            System.out.println("Utilisateur déconnecté: " + getInstance().utilisateurConnecte.getUsername());
            getInstance().utilisateurConnecte = null;
        }
    }

    /**
     * Vérifie si un utilisateur est connecté
     */
    public static boolean estConnecte() {
        return getInstance().utilisateurConnecte != null;
    }

    /**
     * Retourne le rôle de l'utilisateur connecté
     */
    public static String getRoleUtilisateur() {
        if (estConnecte()) {
            return getInstance().utilisateurConnecte.getRole();
        }
        return null;
    }

    /**
     * Retourne l'utilisateur courant (Retourne directement l'objet Utilisateur connecté (peut être null).)
     */
    public static Utilisateur getUtilisateurCourant() {
        return getInstance().utilisateurConnecte;
    }

    /**
     * Vérifie si l'utilisateur connecté est administrateur
     */
    public static boolean estAdmin() {
        return estConnecte() && getInstance().utilisateurConnecte.estAdmin();
    }

    /**
     * Retourne le nom d'utilisateur de la session courante
     */
    public static String getUsername() {
        if (estConnecte()) {
            return getInstance().utilisateurConnecte.getUsername();
        }
        return null;
    }

    /**
     * Vérifie si l'utilisateur courant a un rôle spécifique
     */
    public static boolean aRole(String role) {
        return estConnecte() && getInstance().utilisateurConnecte.aRole(role);
    }

    /**
     * Vérifie si l'utilisateur courant a accès à une fonctionnalité
     */
    public static boolean aAcces(String fonctionnalite) {
        if (!estConnecte()) return false;

        switch (fonctionnalite.toLowerCase()) {
            case "ajouter_employe":
            case "modifier_employe":
            case "supprimer_employe":
            case "ajouter_utilisateur":
            case "supprimer_utilisateur":
                return estAdmin();
            case "consulter_employes":
            case "rechercher_employes":
                return true; // Tous les utilisateurs connectés peuvent consulter
            default:
                return false;
        }
    }

    /**
     * Vide la session (pour les tests) qui réinitialise l'instance Singleton, permettant de repartir avec une session vierge.
     */
    public static void clear() {
        instance = null;
    }
}