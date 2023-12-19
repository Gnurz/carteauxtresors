# Carte aux trésors
## Description
Ce projet est une application Java Spring Boot qui simule le mouvement d'aventuriers sur une carte

## Prérequis
- Java 17 installé sur la machine. 
- Maven (https://maven.apache.org) installé et configuré.
- Les dépendances nécessaires au bon fonctionnement du projet sont indiqués dans le pom.xml
- Le projet utilise lombok. Dans certains cas il peut être nécessaire, après avoir chargé les dépendances, d'installer lombok via le jar associé. (généralement il se situe : .m2/repository/org/projectlombok/lombok/lombok-version.jar)

## Utilisation
- Le programme prend deux paramètres en entrée : Le fichier d'entrée et le fichier de sortie.
- Executer le jar soit en invite de commande en spécifiant le chemin vers le fichier "jeu" (java -jar carteauxtresors-0.0.1-SNAPSHOT.jar path/in/cheminFichierIn path/out/cheminFichierOut)

## Framework de test et technologie de développement
- Java 17 : Le programme a été développé et testé en java 17
- AssertJ : Pour les test unitaires.
- JUnit 5 : Le projet utilise la dernière version de JUnit pour l'écriture des tests unitaires.

## Getting started
1. Cloner le repository git clone https://github.com/Gnurz/carteauxtresors.git
2. Importer le projet dans votre IDE.
3. Lancer un clean install.
4. Un jar est généré dans le repertoire target

NB : Si maven est correctement installé sur votre machine vous pouvez, directement après l'option 1 : 
.Vous déplacer dans le répertoire créé
. lancer la commande mvn clean install (un exécutable sera créé dans le repértoire target du programme)

