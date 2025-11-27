Projet : Application RESTful Spring Boot avec Talend et Prometheus

1. Prérequis

Avant de commencer, assurez-vous d’avoir installé :

Java JDK 17+
Vérification :

java -version


Maven 3+
Vérification :

mvn -v


MySQL ou PostgreSQL

Base de données vide pour les produits

Postman pour tester l’API

2. Installation de Talend Open Studio

Télécharger Talend Open Studio pour Data Integration :
https://www.talend.com/products/talend-open-studio/

Extraire l’archive dans un dossier de votre choix.

Pour notre travail nous avons utiliser talend: TOS_DI-20211109_1610-V8.0.1


Lancer Talend :

Windows : double-cliquer sur TOS_DI-win-x86_64.exe

Linux : lancer le binaire TOS_DI-linux-gtk-x86_64

Créer un workspace pour stocker vos projets ETL.

Importer les jobs Talend fournis dans le dépôt ou créer votre job :

Extraire les données de la base source (PostgreSQL)

Ici nous avons utiliser des fichier CSV pour charger les donnes dans la base de donnees bd_alimentation

Transformer si nécessaire

Charger les données dans la base de destination (utilisée par Spring Boot)


Installation de Spring Boot

- Installer Java JDK 17+ et Maven 3+ :
  Vérification : java -version et mvn -v
- Le projet a executer est Alimentation avec toutes les dependances necessaires :
  Spring Web, Spring Data JPA, MySQL/PostgreSQL Driver, Actuator, Micrometer Prometheus
- Compiler et lancer le projet :
  mvn clean install
  mvn spring-boot:run
- Vérifier les endpoints REST et les métriques Prometheus exposées

Liste des API de l’application

Authentification : /api/auth/**

Catégories :

api/v1/categorie/liste

api/v1/categorie/creer

api/v1/categorie/delete/{id}

api/v1/categorie/update/{id}

Produits :

api/v1/produit/creer

api/v1/idProduit/{idProduit}/idCategorie/{idCategorie}

api/v1/produit/liste

api/v1/produit/update/{id}

api/v1/produit/consulter/{id}

api/v1/produit/delete/{id}

Logs : api/v1/logs

Actuator : /actuator/**

Installation de Prometheus

- Télécharger Prometheus depuis https://prometheus.io/download/
- Extraire l’archive dans un dossier de votre choix
- Modifier le fichier prometheus.yml pour scrapper Spring Boot :
  scrape_configs:
    - job_name: 'spring-boot-app'
      static_configs:
        - targets: ['localhost:8080']
- Lancer Prometheus :
  ./prometheus --config.file=prometheus.yml
- Vérifier via l'interface web http://localhost:9090 que les métriques apparaissent


Résumé des outils


| Outil                | Version recommandée      | Vérification                           |
|----------------------|------------------------|----------------------------------------|
| Talend Open Studio    | 8.0+                   | Lancer l’interface graphique           |
| Java JDK             | 17+                    | java -version                           |
| Maven                | 3+                     | mvn -v                                  |
| Spring Boot          | 3.2.x                  | mvn spring-boot:run et tester API       |
| Prometheus           | Dernière stable         | http://localhost:9090 et tester métriques |
