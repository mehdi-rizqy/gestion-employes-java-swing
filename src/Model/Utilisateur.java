package Model;


public class Utilisateur {
    private int id;
    private String username;
    private String password;
    private String role;

    /**
     * Constructeur par défaut
     */
    public Utilisateur() {
    }

    /**
     * Constructeur avec paramètres
     */
    public Utilisateur(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Constructeur complet avec ID
     */
    public Utilisateur(int id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    /**
     * Vérifie si l'utilisateur a le rôle administrateur
     */
    public boolean estAdmin() {
        return "admin".equalsIgnoreCase(role);
    }

    /**
     * Vérifie si l'utilisateur a un rôle spécifique
     */
    public boolean aRole(String role) {
        return this.role != null && this.role.equalsIgnoreCase(role);
    }

    /**
     * Vérifie si les données de l'utilisateur sont valides
     */
    public boolean estValide() {
        return username != null && !username.trim().isEmpty() &&
                password != null && !password.trim().isEmpty() &&
                role != null && !role.trim().isEmpty();
    }

    /**
     * Vérifie si deux utilisateurs ont le même username
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Utilisateur that = (Utilisateur) obj;
        return username != null && username.equals(that.username);
    }

    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }
}