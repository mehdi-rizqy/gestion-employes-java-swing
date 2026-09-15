package Model;

import java.util.Date;

/**
 * Classe métier représentant un employé
 */
public class Employee {
    private int id;
    private String matricule;
    private String nom;
    private String prenom;
    private String poste;
    private double salaire;
    private String departement;
    private Date dateEmbauche;

    /**
     * Constructeur par défaut
     */
    public Employee() {
        this.dateEmbauche = new Date(); // Date actuelle par défaut
    }

    /**
     * Constructeur avec paramètres || Le constructeur sans ID est destiné à créer un nouvel employé avant son insertion en base de données, lorsque l'identifiant n'est pas encore connu (il sera généré automatiquement par la base)
     */
    public Employee(String matricule, String nom, String prenom, String poste,
                    double salaire, String departement, Date dateEmbauche) {
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.poste = poste;
        this.salaire = salaire;
        this.departement = departement;
        this.dateEmbauche = dateEmbauche;
    }

    /**
     * Constructeur avec ID (pour les mises à jour) || Le constructeur avec ID est utilisé lorsqu'on récupère un employé existant depuis la base
     */
    public Employee(int id, String matricule, String nom, String prenom, String poste,
                    double salaire, String departement, Date dateEmbauche) {
        this.id = id;
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.poste = poste;
        this.salaire = salaire;
        this.departement = departement;
        this.dateEmbauche = dateEmbauche;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public double getSalaire() {
        return salaire;
    }

    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public Date getDateEmbauche() {
        return dateEmbauche;
    }

    public void setDateEmbauche(Date dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", matricule='" + matricule + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", poste='" + poste + '\'' +
                ", salaire=" + salaire +
                ", departement='" + departement + '\'' +
                ", dateEmbauche=" + dateEmbauche +
                '}';
    }

    /**
     * Vérifie si l'employé est valide (champs obligatoires remplis)
     */
    public boolean estValide() {
        return matricule != null && !matricule.trim().isEmpty() &&  //Enlève les espaces en début et fin, puis vérifie si la chaîne est vide.
                nom != null && !nom.trim().isEmpty() &&
                prenom != null && !prenom.trim().isEmpty();
    }

    /**
     * Vérifie si deux employés ont le même matricule
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Employee employee = (Employee) obj;
        return matricule != null && matricule.equals(employee.matricule);
    }

}