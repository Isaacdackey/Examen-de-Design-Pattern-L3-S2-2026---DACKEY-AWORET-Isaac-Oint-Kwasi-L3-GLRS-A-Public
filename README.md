# BadWallet API Payment Service

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F)
![MySQL](https://img.shields.io/badge/MySQL-8-4479A1)
![Status](https://img.shields.io/badge/Status-Completed-success)

## Description

BadWallet API Payment Service est une API REST développée avec Spring Boot dans le cadre de l'examen de Design Pattern (Licence 3 GLRS - Semestre 2).

Elle permet la gestion complète de portefeuilles électroniques, des transactions financières ainsi que des paiements de factures via une architecture modulaire respectant les bonnes pratiques de développement.

---

## Fonctionnalités

### Authentification

- Connexion utilisateur
- Inscription utilisateur

### Gestion des portefeuilles

- Création d'un portefeuille
- Consultation d'un portefeuille
- Liste paginée des portefeuilles
- Consultation du solde

### Transactions

- Dépôt
- Retrait
- Transfert d'argent
- Historique des transactions

### Paiement de factures

- Paiement simple
- Paiement multiple
- Consultation des factures via un service externe

---

## Technologies utilisées

| Technologie | Version |
|-------------|---------|
| Java | 17 |
| Spring Boot | 3.x |
| Spring Security | ✔ |
| Spring Data JPA | ✔ |
| Spring WebFlux (WebClient) | ✔ |
| MySQL | 8 |
| Maven | 3.x |
| Lombok | ✔ |

---

## Architecture

```text
src/main/java/
│
├── auth/
│   └── web/
│
├── config/
│
├── external/
│   ├── service/
│   └── web/
│
├── mock/
│
├── shared/
│   ├── exceptions/
│   └── response/
│
├── wallet/
│   ├── data/
│   │   ├── entity/
│   │   └── repository/
│   ├── factory/
│   ├── service/
│   └── web/
│       ├── dto/
│       ├── mapper/
│       └── controller/
│
└── BadWalletApiPaymentServiceApplication.java
```

Le projet suit une architecture en couches garantissant une bonne séparation des responsabilités et une meilleure maintenabilité.

---

## Installation

### Prérequis

- Java 17 ou supérieur
- Maven 3.8+
- MySQL 8+

### Cloner le dépôt

```bash
git clone https://github.com/Isaacdackey/Examen-de-Design-Pattern-L3-S2-2026---DACKEY-AWORET-Isaac-Oint-Kwasi-L3-GLRS-A-Public.git
```

### Accéder au projet

```bash
cd Examen-de-Design-Pattern-L3-S2-2026---DACKEY-AWORET-Isaac-Oint-Kwasi-L3-GLRS-A-Public
```

### Configurer la base de données

Configurer le fichier `application.properties` :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/badwallet
spring.datasource.username=root
spring.datasource.password=root

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

payment.service.url=http://localhost:8081

wallet.withdraw.fee.rate=0.01
wallet.withdraw.fee.max=5000
```

### Lancer l'application

```bash
mvn clean install
mvn spring-boot:run
```

L'API sera disponible à l'adresse :

```text
http://localhost:8080
```

---

## Principaux Endpoints

### Authentification

| Méthode | Endpoint |
|----------|----------|
| POST | `/api/auth/login` |
| POST | `/api/auth/register` |

### Portefeuilles

| Méthode | Endpoint |
|----------|----------|
| POST | `/api/wallets` |
| GET | `/api/wallets` |
| GET | `/api/wallets/{phone}` |
| GET | `/api/wallets/{phone}/balance` |

### Transactions

| Méthode | Endpoint |
|----------|----------|
| POST | `/api/wallets/{id}/deposit` |
| POST | `/api/wallets/withdraw` |
| POST | `/api/wallets/transfer` |
| GET | `/api/wallets/{phone}/transactions` |

### Paiements

| Méthode | Endpoint |
|----------|----------|
| POST | `/api/wallets/pay` |
| POST | `/api/wallets/pay-factures` |

### Factures

| Méthode | Endpoint |
|----------|----------|
| GET | `/api/external/factures/{code}/current` |
| GET | `/api/external/factures/{code}/periode` |

---

## Tests

Les requêtes de test sont disponibles dans le fichier :

```text
badwallet-api.http
```

Vous pouvez les exécuter avec IntelliJ IDEA ou l'extension **REST Client** de Visual Studio Code.

---

## Build

Compiler le projet :

```bash
mvn clean package
```

---

## Gestion des branches

Le projet suit une stratégie **Feature Branching**.

```text
main
│
└── develop
     ├── feature/auth
     ├── feature/wallet
     ├── feature/deposit
     ├── feature/withdraw
     ├── feature/transfer
     ├── feature/payment
     ├── feature/history
     └── feature/external-service
```

Chaque fonctionnalité a été développée dans une branche dédiée avant d'être fusionnée dans `develop`, puis dans `main`.

---

## Auteur

**DACKEY AWORET Isaac Oint Kwasi**

Licence 3 GLRS-A

Année universitaire 2025-2026

---

## Licence

Projet réalisé dans le cadre de l'examen de Design Pattern.
