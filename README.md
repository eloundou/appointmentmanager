# 📅 AppointmentManager

> Mini projet de gestion des rendez-vous dans une institution, exposant une API REST avec Spring Boot.

---

## 📋 Table des matières

- [Description](#description)
- [Fonctionnalités](#fonctionnalités)
- [Stack technique](#stack-technique)
- [Prérequis](#prérequis)
- [Installation](#installation)
- [Lancement](#lancement)
- [Tests](#tests)
- [Informations complémentaires](#informations-complémentaires)

---

## Description

**AppointmentManager** est une application Spring Boot qui expose une API REST pour la gestion des rendez-vous au sein d'une institution. Elle implémente une suite de tests permettant la validation des différentes fonctionnalités.

---

## Fonctionnalités

### 🕐 Gestion des plages horaires
Les rendez-vous sont planifiés sur des créneaux d'une heure, de **08h à 16h**. Ces plages sont auto-enregistrées en base de données au démarrage de l'application. Un endpoint permet de les consulter.

### 🏢 Gestion des services
Cinq services sont auto-enregistrés en base de données au démarrage. Un endpoint permet de les consulter afin d'en récupérer les références pour d'autres opérations.

### 👤 Gestion des clients
Les clients sont les personnes souhaitant prendre un rendez-vous. Les opérations disponibles sont :
- Ajout
- Modification
- Consultation

> Chaque client possède une **référence unique** et une **adresse email unique**.

### 👔 Gestion des responsables
Les responsables accueillent les clients lors des rendez-vous et sont rattachés à un service. Les opérations disponibles sont :
- Ajout
- Modification
- Consultation

> Chaque responsable possède une **référence unique** et une **adresse email unique**. Un seul responsable est autorisé par service.

### 📆 Gestion des rendez-vous
Le rendez-vous est l'élément central du système. Il associe un client et un responsable dans un service, à une date et une plage horaire données, pour un motif précis.

Les contraintes appliquées sont les suivantes :
- Un rendez-vous ne peut être effectué que dans les plages horaires autorisées
- Un rendez-vous doit être programmé **au minimum 48h à l'avance**
- Un responsable ne peut recevoir un rendez-vous que dans **son propre service**
- Une même plage horaire, à la même date, dans le même service ne peut accueillir **qu'un seul rendez-vous**
- Un client ne peut pas avoir **deux rendez-vous simultanés** sur la même plage à la même date
- Chaque rendez-vous possède une **référence unique**

Les endpoints disponibles permettent la **création** et la **consultation** des rendez-vous.

---

## Stack technique

| Technologie                     | Version |
|---------------------------------|---------|
| Java                            | 21 |
| Spring Boot                     | 3.5.14 |
| PostgreSQL                      | — |
| Flyway                          | — |
| H2 (Pour l'exécution des tests) | — |
| Lombok                          | — |

---

## Prérequis

- JDK 21
- Maven 3.x.x
- PostgreSQL installé et en cours d'exécution

---

## Installation

1. **Cloner le dépôt**
   ```bash
   git clone <url-du-repo>
   cd appointmentmanager
   ```

2. **Configurer la base de données** dans `src/main/resources/application.properties`
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/<nom_de_la_base>
   spring.datasource.username=<utilisateur>
   spring.datasource.password=<mot_de_passe>
   ```

3. **Créer la base de données** si elle n'existe pas encore
   ```sql
   CREATE DATABASE appointmentmanager;
   ```

---

## Lancement

À la racine du projet, exécuter :

```bash
./mvnw spring-boot:run
```

---

## Tests

À la racine du projet, exécuter :

```bash
./mvnw test
```

---

## Informations complémentaires

### Gestion des erreurs
Les exceptions sont gérées selon la spécification **RFC 7807** (*Problem Details for HTTP APIs*). Toutes les erreurs sont renvoyées sous forme d'objet `ProblemDetail`.

### Gestion de la concurrence
Pour garantir la cohérence des données et prévenir les **race conditions** et accès concurrents, la gestion de l'unicité est déléguée à la couche persistance via l'annotation `@UniqueConstraint` de JPA. Toute tentative d'insertion de doublon lève automatiquement une exception, capturée et retournée à l'utilisateur dans le format approprié.
