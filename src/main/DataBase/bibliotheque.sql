DROP DATABASE IF EXISTS bibliotheque;

CREATE DATABASE bibliotheque;

USE bibliotheque;

CREATE TABLE bibusers (
id INT(15) PRIMARY KEY AUTO_INCREMENT ,
name VARCHAR(30) ,
Password VARCHAR(30)
);
INSERT INTO bibusers(id, name, Password) VALUES ('1','moi','0000');
INSERT INTO bibusers(id, name, Password) VALUES ('2','toi','9999');

CREATE TABLE adherent (
id INT(15) PRIMARY KEY AUTO_INCREMENT ,
name VARCHAR(30) ,
lastname VARCHAR(30),
adresse VARCHAR(30)
) ;

INSERT INTO adherent(id, name, lastname,adresse) VALUES ('1','youssef','elomari','Tadla');
INSERT INTO adherent(id, name, lastname,adresse) VALUES ('2','hadil','fdilat','Essaouira');
INSERT INTO adherent(id, name, lastname,adresse) VALUES ('3','marwan','merbouh','Fes');
INSERT INTO adherent(id, name, lastname,adresse) VALUES ('4','ikram','mernissi','Fes');

CREATE TABLE livre (
numeroLivre INT(15) PRIMARY KEY AUTO_INCREMENT ,
titre VARCHAR(30),
auteurs VARCHAR(30),
maisonEdition VARCHAR(30),
nombrePages INT(30),
prix FLOAT(30)
) ;

INSERT INTO livre(numeroLivre, titre, auteurs, maisonEdition, nombrePages, prix) VALUES ('1','Hope','alberte','agahome','123','45.0');
INSERT INTO livre(numeroLivre, titre, auteurs, maisonEdition, nombrePages, prix) VALUES ('2','Happiness','moi','casahome','143','43.3');

CREATE TABLE dvd (
numDvd INT(15) PRIMARY KEY AUTO_INCREMENT ,
nomFilm VARCHAR(30),
nomDocumentaire VARCHAR(30),
nomRealisateur VARCHAR(30),
nomActeur VARCHAR(30),
nomEditeurdvd VARCHAR(30),
nomProducteur VARCHAR(30)
) ;

INSERT INTO dvd(numDvd, nomFilm, nomDocumentaire, nomRealisateur, nomActeur, nomEditeurdvd, nomProducteur) VALUES ('1','Hope','alberte','agahome','asd','asasd','asda');
INSERT INTO dvd(numDvd, nomFilm, nomDocumentaire, nomRealisateur, nomActeur, nomEditeurdvd, nomProducteur) VALUES ('2','asd','dad','agahome','asda','asasd','asda');

CREATE TABLE cd (
numCd INT(15) PRIMARY KEY AUTO_INCREMENT ,
nomAlbum VARCHAR(30),
nomInterprete VARCHAR(30),
nomEditeur VARCHAR(30)
) ;

INSERT INTO cd(numCd, nomAlbum, nomInterprete, nomEditeur) VALUES ('1','Hope','alberte','agahome');
INSERT INTO cd(numCd, nomAlbum, nomInterprete, nomEditeur) VALUES ('2','dd','ha','ff');


CREATE TABLE Emprunte (
idEmprunt INT(15) PRIMARY KEY AUTO_INCREMENT ,
idAdh INT(15),
idDocum INT(15),
type VARCHAR(30),
dateEmprunt DATE,
datelimite DATE
) ;
INSERT INTO `emprunte` (`idEmprunt`, `idAdh`, `idDocum`, `type`, `dateEmprunt`, `datelimite`) VALUES
(1, 1, 2, 'Livre', '2022-06-09', '2022-06-08'),
(2, 2, 1, 'Livre', '2022-06-09', '2022-06-29'),
(3, 1, 1, 'Livre', '2022-06-09', '2022-06-06'),
(4, 2, 1, 'Livre', '2022-06-09', '2022-06-08'),
(5, 2, 2, 'Livre', '2022-06-09', '2022-06-29'),
(6, 1, 2, 'DVD', '2022-06-09', '2022-06-24'),
(7, 1, 2, 'DVD', '2022-06-09', '2022-06-24'),
(8, 1, 1, 'DVD', '2022-06-09', '2022-06-07'),
(9, 2, 1, 'DVD', '2022-06-09', '2022-06-24'),
(10, 1, 1, 'CD', '2022-06-09', '2022-06-24'),
(11, 1, 2, 'CD', '2022-06-09', '2022-06-04'),
(12, 1, 1, 'CD', '2022-06-09', '2022-06-04'),
(13, 3, 2, 'Livre', '2022-06-09', '2021-06-29'),
(14, 4, 2, 'Livre', '2022-06-09', '2021-05-29');

CREATE TABLE rendre (
idrendre INT(15) PRIMARY KEY AUTO_INCREMENT ,
idAdh INT(15),
idDocum INT(15),
type VARCHAR(30),
daterendre DATE
) ;

