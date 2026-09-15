
DROP DATABASE IF EXISTS gestion_employes;
CREATE DATABASE gestion_employes;
USE gestion_employes;

-- Table des employés
CREATE TABLE employe (
    id INT PRIMARY KEY AUTO_INCREMENT,
    matricule VARCHAR(20) UNIQUE NOT NULL,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    poste VARCHAR(50) NOT NULL,
    salaire DECIMAL(10,2) NOT NULL,
    departement VARCHAR(50) NOT NULL,
    date_embauche DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Création de la table utilisateurs
CREATE TABLE IF NOT EXISTS utilisateurs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'utilisateur',
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    derniere_connexion TIMESTAMP NULL,
    INDEX idx_username (username),
    INDEX idx_role (role)
);

-- Insertion de l'administrateur par défaut (mot de passe: admin123)
INSERT INTO utilisateurs (username, password, role)
VALUES ('admin', 'admin123', 'admin')
ON DUPLICATE KEY UPDATE username = username;

-- Insertion d'un utilisateur de test (mot de passe: user123)
INSERT INTO utilisateurs (username, password, role)
VALUES ('user', 'user123', 'utilisateur')
ON DUPLICATE KEY UPDATE username = username;

INSERT INTO utilisateurs (username, password, role)
VALUES ('emp', 'emp123', 'employee')
ON DUPLICATE KEY UPDATE username = username;


-- Données initiales
INSERT INTO utilisateur (username, password, role) VALUES
('admin', 'admin123', 'admin'),
('rh', 'rh123', 'rh'),
('user', 'user123', 'user');

INSERT INTO employe (matricule, nom, prenom, poste, salaire, departement, date_embauche) VALUES
('EMP001', 'Dupont', 'Jean', 'Développeur', 3500.00, 'IT', '2022-01-15'),
('EMP002', 'Martin', 'Marie', 'Chef de projet', 4500.00, 'Management', '2021-03-20'),
('EMP003', 'Bernard', 'Pierre', 'Analyste', 3200.00, 'IT', '2023-05-10'),
('EMP004', 'Dubois', 'Sophie', 'Comptable', 2800.00, 'Finance', '2020-11-05'),
('EMP005', 'Leroy', 'Paul', 'RH', 3000.00, 'Ressources Humaines', '2022-09-12');

-- Index pour améliorer les performances
CREATE INDEX idx_employe_nom ON employe(nom);
CREATE INDEX idx_employe_departement ON employe(departement);
CREATE INDEX idx_utilisateur_role ON utilisateur(role);

-- Vérification
SELECT '=== BASE DE DONNÉES CRÉÉE AVEC SUCCÈS ===' as '';
SELECT '=== TABLES ===' as '';
SHOW TABLES;

SELECT '\n=== EMPLOYÉS (5 premiers) ===' as '';
SELECT * FROM employe LIMIT 5;

SELECT '\n=== UTILISATEURS ===' as '';
SELECT username, role, created_at FROM utilisateur;

SELECT '\n=== STATISTIQUES ===' as '';
SELECT
    COUNT(*) as nombre_employes,
    SUM(salaire) as masse_salariale,
    AVG(salaire) as salaire_moyen
FROM employe;