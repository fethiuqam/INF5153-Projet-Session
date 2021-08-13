# CentRAMQ
***
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![SQLite](https://img.shields.io/badge/sqlite-%2307405e.svg?style=for-the-badge&logo=sqlite&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)

Projet de session dans la cadre du cours de conception INF5153

C'est un logiciel permettant aux utilisateurs du système de santé québécois d'avoir un dossier médical centralisé, peu importe le médecin ou l'établissement qu'il fréquente.

## Usage

En premier, il faut s'assurer d'avoir installé au préalables les prérequis sur sa machine :

### Pré-requis

- [Java 13](https://www.oracle.com/java/technologies/javase/jdk13-archive-downloads.html)
- [Sqlite3 version 3.36.0](https://www.sqlite.org/download.html)
- [Apache Maven version 3.8.1](https://maven.apache.org/download.cgi)
- [Git version 2.32.0](https://git-scm.com/downloads)

### Installation

Après avoir installé les pré-requis ci-dessus, on commence par télécharger le depot du projet sur sa machine :

~~~sh
$ git clone https://gitlab.info.uqam.ca/abi_ayad.fethi_bey/inf5153-ete2021-projet-e7.git
~~~

On se positionne à la racine du projet :

~~~sh
$ cd inf5153-ete2021-projet-e7
~~~

On initialise la base de données de l'application par la suite des commandes suivantes :

~~~sh
$ sqlite3
> .open database/database.db
> .read database/create.sql
~~~

Cela va créer notre base de données et la peupler par des données qu'on va utiliser pour les tests

### compilation

À la racine du projet on exécute la commande maven :

~~~sh
$ mvn clean compile
~~~

### tests

À la racine du projet on exécute la commande maven :

~~~sh
$ mvn clean test
~~~

### Empaquetage

À la racine du projet on exécute la commande maven :

~~~sh
$ mvn clean package
~~~

### Execution du programme

À la racine du projet on exécute la commande maven :

~~~sh
$ mvn javafx:run
~~~


## Téchnologies

- [Java 13](https://www.oracle.com/java/technologies/javase/jdk13-archive-downloads.html)
- [Sqlite3 version 3.36.0](https://www.sqlite.org/download.html)
- [JavaFX 11](https://openjfx.io/openjfx-docs/)
- [Hibernate 5.5](https://hibernate.org/orm/releases/5.5/)
- [Mockito 3.7](https://site.mockito.org/)
- [JUnit 5.5](https://junit.org/junit5/docs/current/user-guide/)

## Auteurs

- [Fethi bey Abi ayad](abi_ayad.fethi_bey@courrier.uqam.ca)

- [Hamza Yahi](af491086@ens.uqam.ca)

- [Mohamed Hocine Rehouma](rehouma.mohamed_hocine@courrier.uqam.ca)
