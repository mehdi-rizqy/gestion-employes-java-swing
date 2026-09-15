package Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class Validator {

    public static boolean validerMatricule(String matricule) {
        return matricule != null && !matricule.trim().isEmpty()
                && matricule.length() >= 3 && matricule.length() <= 20
                && Pattern.matches("^[A-Za-z0-9_-]+$", matricule);
    }

    public static boolean validerNom(String nom) {
        return nom != null && !nom.trim().isEmpty()
                && nom.length() >= 2 && nom.length() <= 50
                && Pattern.matches("^[A-Za-zÀ-ÿ\\s'-]+$", nom);
    }

    public static boolean validerPrenom(String prenom) {
        return validerNom(prenom); // Mêmes critères que le nom
    }

    public static boolean validerPoste(String poste) {
        return poste != null && !poste.trim().isEmpty()
                && poste.length() >= 2 && poste.length() <= 50;
    }

    public static boolean validerSalaire(double salaire) {
        return salaire > 0 && salaire <= 100000;
    }

    public static boolean validerSalaire(String salaireStr) {
        try {
            double salaire = Double.parseDouble(salaireStr);
            return validerSalaire(salaire);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validerDepartement(String departement) {
        return departement != null && !departement.trim().isEmpty()
                && departement.length() >= 2 && departement.length() <= 50;
    }

    public static boolean validerDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);

        try {
            Date date = sdf.parse(dateStr);
            Date aujourdhui = new Date();

            // La date ne doit pas être dans le futur
            return !date.after(aujourdhui);
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean validerUsername(String username) {
        return username != null && !username.trim().isEmpty()
                && username.length() >= 3 && username.length() <= 50
                && Pattern.matches("^[A-Za-z0-9_-]+$", username);
    }

    public static boolean validerPassword(String password) {
        return password != null && !password.trim().isEmpty()
                && password.length() >= 4 && password.length() <= 100;
    }

    public static String nettoyerChaine(String chaine) {
        if (chaine == null) {
            return "";
        }
        return chaine.trim();
    }

    public static Date convertirEnDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String formaterDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}