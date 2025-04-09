DROP DATABASE IF EXISTS EXAMEN_SERVLET;

CREATE DATABASE EXAMEN_SERVLET;

USE EXAMEN_SERVLET;

CREATE TABLE Credit (
    id int primary key auto_increment,
    libelle VARCHAR(100),
    montant int
);

CREATE TABLE Depense (
    id int primary key auto_increment,
    id_credit int,
    nom_depense VARCHAR(100),
    montant int
);

CREATE TABLE Utilisateur(
    id int primary key auto_increment,
    nom VARCHAR(20),
    email VARCHAR(20),
    mot_de_passe VARCHAR(20)
);

INSERT INTO Utilisateur VALUES (1,'rojo','rojo@gmail.com','rojo');
