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
