<<<<<<< HEAD
# Pigeon Racing Competition Management System 🐦🏁

## Contexte du projet

Dans le cadre d’une compétition annuelle organisée par la **Fédération Marocaine des Pigeons Voyageurs**, plusieurs éleveurs de pigeons participent durant la saison à plusieurs compétitions. Les compétitions sont des courses réparties sur trois étapes : vitesse, demi-fond et fond (si les conditions climatiques le permettent).

L’événement est très attendu par la communauté et implique des règles strictes pour garantir l’intégrité de la compétition, d'où la nécessité de créer une application pour la gérer.

## Fonctionnalités du Système

### Enregistrement des Éleveurs

Les éleveurs possèdent des pigeons qui participeront à la saison active. Le système doit permettre aux éleveurs de s’enregistrer en fournissant :
- Un nom de colombier (unique)
- Un nom d’utilisateur
- Un mot de passe
- Les coordonnées GPS de son colombier

L’éleveur pourra ensuite s’authentifier pour ajouter des pigeons pour la saison active.

### Gestion des Pigeons

Un pigeon est caractérisé par :
- Un numéro de bague unique
- Sexe
- Âge
- Couleur
- Image (optionnel)

### Gestion des Compétitions

L’organisateur de la saison doit définir des compétitions pour chaque étape de la saison. Chaque compétition inclut :
- Nom de la course
- Coordonnées GPS du point de lâcher
- Date et heure de départ
- Distance prévisionnelle

Avant chaque compétition, l’organisateur doit pouvoir ajouter les pigeons participants via les numéros de bague.

### Clôture des Compétitions et Calcul des Résultats

Après la fin de la compétition, l’organisateur peut clôturer la compétition via l’API, ce qui déclenche le calcul des résultats précédé par l’upload des données collectées (heure d’arrivée, numéro de bague des pigeons).

Les résultats seront immédiatement disponibles pour les éleveurs pour consultation ou export en format PDF.

### Règles de Calcul des Résultats

#### Collecte des Données
L’organisateur peut uploader les données (heure d’arrivée, numéro de bague) des pigeons récupérées des éleveurs après la clôture de la compétition.

#### Calcul de la Distance
La formule de Haversine est utilisée pour calculer la distance entre deux points GPS : le point de lâcher et le colombier de chaque participant.

#### Calcul du Temps de Vol
La différence entre l’heure d’arrivée et l’heure de lâcher est calculée pour obtenir le temps total de vol.

#### Calcul de la Vitesse
La vitesse est calculée en divisant la distance parcourue par le temps de vol (en m/min). Un coefficient d’ajustement est utilisé pour prendre en compte les différences de distance entre les pigeons.

- **Coefficient d’Ajustement** = Distance moyenne / Distance parcourue par le pigeon
- **Distance Moyenne** = Moyenne des distances de tous les pigeons participant à la course.

#### Application du Coefficient d’Ajustement
Le coefficient d’ajustement est appliqué à la vitesse de chaque pigeon.

#### Classement
Les pigeons sont classés en fonction de leur vitesse, du plus rapide au plus lent.

#### Calcul des Points de Performance
Les points sont attribués en fonction du classement et du nombre de pigeons admis, en fonction du pourcentage d’admission de chaque course. Les points peuvent être cumulés sur plusieurs courses pour déterminer un classement général.

#### Affichage des Résultats
Les résultats sont affichés sous forme de tableau pour chaque course et partagés avec tous les participants. Le classement général est également calculé automatiquement et mis à disposition des utilisateurs pour consultation.

### Exigences Techniques

- **Framework** : Spring Boot pour le développement de l'API.
- **Base de données** : MongoDB.
- **Structure** : Application organisée en couches (Controller, Service, Repository, Model).
- **Validation des données** : Obligatoire.
- **Gestion des exceptions** : Centralisée.
- **Tests unitaires** : Obligatoires.
- **Format de fichier de configuration** : YAML.

## Installation et Configuration

### Prérequis
- **Java** 17 ou supérieur
- **MongoDB** installé et en cours d'exécution
- **Maven** pour la gestion des dépendances

### Installation

1. Clonez ce projet :
    ```bash
    git clone https://github.com/username/pigeon-racing-competition.git
    cd pigeon-racing-competition
    ```

2. Installez les dépendances avec Maven :
    ```bash
    mvn install
    ```

3. Configurez votre application en modifiant le fichier `application.yml` :
    ```yaml
    spring:
      data:
        mongodb:
          uri: mongodb://localhost:27017/pigeon_racing_db
    ```

4. Lancez l’application avec la commande suivante :
    ```bash
    mvn spring-boot:run
    ```

### Utilisation de l'API

1. **Enregistrement des éleveurs** : L’éleveur peut s’inscrire via l'endpoint `POST /api/eleveurs`.
2. **Ajout de pigeons** : Après l'authentification, les pigeons peuvent être ajoutés via l'endpoint `POST /api/eleveurs/{eleveurId}/pigeons`.
3. **Création des compétitions** : Les organisateurs peuvent créer des compétitions avec `POST /api/competitions`.
4. **Calcul des résultats** : Les résultats peuvent être calculés avec `POST /api/competitions/{compId}/cloturer`.
5. **Consulter les résultats** : Les éleveurs peuvent consulter les résultats via `GET /api/competitions/{compId}/resultats`.

## Tests

Les tests unitaires sont implémentés pour chaque couche de l’application. Vous pouvez exécuter les tests avec la commande suivante :

```bash
mvn test
=======
# 🕊️ PigeonSkyRace - Gestion des Courses de Pigeons Voyageurs

PigeonSkyRace est une application conçue pour automatiser et gérer les compétitions annuelles organisées par la Fédération Marocaine des Pigeons Voyageurs.  
Ce système assure une organisation équitable, fiable et transparente des courses, tout en fournissant des résultats justes et consultables à tout moment.

---

## 🚀 Objectifs

- Digitaliser la gestion des compétitions (vitesse, demi-fond, fond).
- Permettre aux éleveurs de gérer leurs pigeons et participer aux courses.
- Calculer automatiquement les résultats à partir des données GPS et horaires.
- Fournir un classement et un cumul de points équitables et exportables.

---

## 🧑‍🌾 Fonctionnalités principales

### 👤 Gestion des Éleveurs
- Enregistrement avec nom de colombier unique, identifiants et coordonnées GPS.
- Authentification sécurisée.
- Ajout de pigeons à la saison active.

### 🐦 Gestion des Pigeons
- Caractéristiques : Numéro de bague unique, sexe, âge, couleur, image (optionnelle).

### 🏁 Gestion des Compétitions
- Définition des courses par l’organisateur : nom, coordonnées GPS du point de lâcher, date/heure, distance estimée.
- Sélection des pigeons participants à chaque course.

### ⏱️ Calcul Automatique des Résultats
- Upload des données d’arrivée après la fin de la course.
- Calcul de la distance (formule de Haversine).
- Calcul du temps de vol et de la vitesse (m/min).
- Application d’un coefficient d’ajustement pour l’équité.
- Classement par vitesse ajustée.
- Attribution de points selon classement et taux d’admission.
- Classement général par cumul des 5 meilleurs pigeons par colombier.

### 📊 Résultats et Export
- Affichage des résultats : Colombier, Bague, Heure d’arrivée, Distance, Vitesse, Points.
- Export PDF disponible pour chaque course.
- Classement général sauvegardé pour consultation future.

---

## 🧰 Stack Technique

- **🧠 Backend** : Java + Spring Boot  
- **📦 Base de données** : MongoDB  
- **⚙️ Architecture** : Clean layered (Controller, Service, Repository)  
- **✅ Validation** : Spring Validation  
- **🛡️ Gestion des exceptions** : Centralisée  
- **🧪 Tests** : Tests unitaires obligatoires  
- **🧾 Configuration** : Fichier `application.yaml`

---

## 📌 Points Importants

- Les calculs sont effectués une seule fois et les résultats sont sauvegardés.
- L’application suit une logique métier claire et robuste, respectant les règles de la fédération.
- Chaque donnée est soigneusement validée avant traitement.

---

## 📁 Installation (exemple si applicable)

```bash
git clone https://github.com/hafssa123labhouj/PigeonSkyRace.git
cd PigeonSkyRace
# configurer MongoDB et application.yaml
mvn spring-boot:run
>>>>>>> c4770baf424c7e664e05901225e00470f02948d3
