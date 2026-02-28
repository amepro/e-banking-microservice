# E-Banking Microservices

Application bancaire construite avec une architecture microservices en Spring Boot.

## Architecture

```
                    ┌──────────────────┐
                    │   API Gateway    │
                    │   (Spring Cloud) │
                    └────────┬─────────┘
                             │
              ┌──────────────┼──────────────┐
              │              │              │
    ┌─────────▼──────┐  ┌───▼───────────┐  │
    │  Customer      │  │  Account      │  │
    │  Service       │  │  Service      │  │
    │  (port 8081)   │  │  (port 8082)  │  │
    └─────────┬──────┘  └───┬───────────┘  │
              │             │              │
              │    ┌────────▼────────┐     │
              │    │  Feign Client   │     │
              │    │  (inter-service)│     │
              │    └─────────────────┘     │
              │                            │
    ┌─────────▼────────────────────────────▼──┐
    │              Eureka Server               │
    │          (Service Discovery)             │
    └─────────────────────────────────────────┘
```

## Services

### 1. Eureka Server (Discovery Service)

Service de découverte qui permet aux microservices de s'enregistrer et de se localiser mutuellement.

- **Port** : `8761`
- **Dashboard** : `http://localhost:8761`

### 2. API Gateway

Point d'entrée unique pour tous les clients. Route les requêtes vers les microservices appropriés.

- **Port** : `8050`
- **Routes** :
  - `/api/customers/**` → Customer Service
  - `/api/accounts/**` → Account Service

### 3. Customer Service

Gestion des clients de la banque (CRUD complet).

- **Port** : `8056`
- **Base URL** : `/api/customers`

| Méthode | Endpoint              | Description              |
|---------|-----------------------|--------------------------|
| POST    | `/api/customers`      | Créer un client          |
| GET     | `/api/customers`      | Lister tous les clients  |
| GET     | `/api/customers/{id}` | Obtenir un client par ID |
| PUT     | `/api/customers/{id}` | Modifier un client       |
| DELETE  | `/api/customers/{id}` | Supprimer un client      |

**Exemple de requête (POST)** :
```json
{
  "firstName": "Mamadou",
  "lastName": "Diallo",
  "email": "mamadou@email.com"
}
```

### 4. Account Service

Gestion des comptes bancaires. Communique avec le Customer Service via **Feign Client** pour récupérer les informations du client associé.

- **Port** : `8057`
- **Base URL** : `/api/accounts`

| Méthode | Endpoint                            | Description                          |
|---------|-------------------------------------|--------------------------------------|
| POST    | `/api/accounts`                     | Créer un compte                      |
| GET     | `/api/accounts`                     | Lister tous les comptes              |
| GET     | `/api/accounts/{id}`                | Obtenir un compte par ID             |
| GET     | `/api/accounts/customer/{custId}`   | Comptes d'un client spécifique       |
| PUT     | `/api/accounts/{id}`                | Modifier un compte                   |
| DELETE  | `/api/accounts/{id}`                | Supprimer un compte                  |

**Exemple de requête (POST)** :
```json
{
  "accountNumber": "76222",
  "type": "SAVINGS",
  "customerId": 1
}
```

**Types de compte** : `SAVINGS`, `CURRENT`

## Stack Technique

| Technologie                   | Utilisation                          |
|-------------------------------|--------------------------------------|
| Java 17+                      | Langage principal                    |
| Spring Boot 3.5               | Framework backend                    |
| Spring Cloud Gateway           | API Gateway (réactif)                |
| Spring Cloud Netflix Eureka    | Service Discovery                    |
| Spring Cloud OpenFeign         | Communication inter-services         |
| Spring Cloud LoadBalancer      | Load balancing côté client           |
| Spring Data JPA                | Accès aux données                    |
| PostgreSQL                     | Base de données                      |
| Lombok                         | Réduction du boilerplate             |

## Structure du Projet

```
e-banking/
├── eureka-server/            # Service Discovery
├── gateway-service/          # API Gateway
├── customer-service/         # Microservice Client
│   ├── controllers/
│   │   └── CustomerController.java
│   ├── dtos/
│   │   ├── CustomerRequest.java
│   │   └── CustomerResponse.java
│   ├── mappers/
│   │   └── CustomerMapper.java
│   ├── models/
│   │   └── Customer.java
│   ├── repository/
│   │   └── CustomerRepository.java
│   └── services/
│       ├── CustomerService.java
│       └── CustomerServiceImpl.java
└── account-service/          # Microservice Compte
    ├── controllers/
    │   └── AccountController.java
    ├── customer/
    │   ├── Customer.java         # DTO du client (côté account)
    │   └── CustomerClient.java   # Feign Client
    ├── dtos/
    │   ├── AccountRequest.java
    │   └── AccountResponse.java
    ├── mappers/
    │   └── AccountMapper.java
    ├── models/
    │   ├── Account.java
    │   └── AccountType.java
    ├── repository/
    │   └── AccountRepository.java
    └── services/
        ├── AccountService.java
        └── AccountServiceImpl.java
```

## Prérequis

- **Java** 17 ou supérieur
- **Maven** 3.8+
- **PostgreSQL** installé et configuré
- **IDE** : IntelliJ IDEA recommandé

## Installation & Lancement

### 1. Cloner le projet

```bash
git clone https://github.com/votre-repo/e-banking.git
cd e-banking
```

### 2. Configurer les bases de données PostgreSQL

Créer deux bases de données :

```sql
CREATE DATABASE customer_db;
CREATE DATABASE account_db;
```

### 3. Démarrer les services (dans cet ordre)

```bash
# 1. Eureka Server (attendre qu'il démarre complètement)
cd eureka-server
mvn spring-boot:run

# 2. API Gateway
cd gateway-service
mvn spring-boot:run

# 3. Customer Service
cd customer-service
mvn spring-boot:run

# 4. Account Service
cd account-service
mvn spring-boot:run
```

> **Important** : Toujours démarrer Eureka Server en premier, puis attendre que les autres services s'y enregistrent avant de tester.

### 4. Vérifier

- Eureka Dashboard : `http://localhost:8761` — les services doivent apparaître
- Tester via Gateway : `http://localhost:8050/api/customers`

## Configuration

### Customer Service (`application.yml`)

```yaml
server:
  port: 8056

spring:
  application:
    name: customer-service
  datasource:
    url: jdbc:postgresql://localhost:5432/customer_db
    username: postgres
    password: votre_mot_de_passe
  jpa:
    hibernate:
      ddl-auto: update

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
```

### Account Service (`application.yml`)

```yaml
server:
  port: 8057

spring:
  application:
    name: account-service
  datasource:
    url: jdbc:postgresql://localhost:5432/account_db
    username: postgres
    password: votre_mot_de_passe
  jpa:
    hibernate:
      ddl-auto: update

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
```

### Gateway (`application.yml`)

```yaml
server:
  port: 8050

spring:
  application:
    name: gateway-service
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true
          lower-case-service-id: true
      routes:
        - id: customer-service
          uri: lb://CUSTOMER-SERVICE
          predicates:
            - Path=/api/customers/**
        - id: account-service
          uri: lb://ACCOUNT-SERVICE
          predicates:
            - Path=/api/accounts/**

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
```

## Patterns Utilisés

- **DTO Pattern** : Séparation entre les entités JPA et les objets exposés par l'API (`CustomerRequest`/`CustomerResponse`)
- **Mapper Pattern** : Conversion entre entités et DTOs via des classes `@Component` dédiées
- **Service Layer** : Logique métier isolée dans les classes `ServiceImpl`
- **Feign Client** : Communication déclarative entre microservices
- **API Gateway** : Point d'entrée unique avec routage vers les services

## Auteur

**Senyitte** — Projet E-Banking Microservices
