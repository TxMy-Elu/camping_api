# Camping API

## Description
Camping API est une application Spring Boot qui fournit une API RESTful pour gérer les données liées au camping. Elle inclut des fonctionnalités telles que l'authentification des utilisateurs, la gestion des comptes et la persistance des données en utilisant JPA et MySQL.

## Technologies
- Java 17
- Spring Boot 3.3.5
- Maven
- Spring Security
- JWT (JSON Web Token)
- MySQL
- JPA (Java Persistence API)

## Prérequis
- Java 17
- Maven
- MySQL

## Installation

1. Clonez le dépôt :
    ```sh
    git clone https://github.com/your-username/camping_api.git
    cd camping_api
    ```

2. Configurez la base de données MySQL :
    - Créez une base de données nommée `camping_db`.
    - Mettez à jour le fichier `application.properties` avec vos identifiants de base de données MySQL.

3. Construisez le projet :
    ```sh
    mvn clean install
    ```

4. Exécutez l'application :
    ```sh
    mvn spring-boot:run
    ```

## Configuration
Mettez à jour le fichier `src/main/resources/application.properties` avec votre configuration de base de données :
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/camping_db
spring.datasource.username=your-username
spring.datasource.password=your-password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

## Points de terminaison de l'API

### Authentification
- `POST /auth/login` - Authentifie un utilisateur et retourne un jeton JWT.

### Gestion des comptes
- `GET /compte/allCompte` - Récupère tous les comptes (nécessite le rôle `client` ou `admin`).
- `GET /compte/{id}` - Récupère les détails d'un compte par ID (nécessite le rôle `client`).
- `GET /compte/compteBloque` - Récupère les comptes bloqués (nécessite le rôle `admin`).

### Gestion des créneaux
- `GET /creneaux/allCreneaux` - Récupère tous les créneaux.

### Gestion des inscriptions
- `POST /inscription/insertOrUpdateInscription` - Insère ou met à jour une inscription.
- `DELETE /inscription/deleteInscription` - Supprime une inscription.
- `GET /inscription/getRegisteredUsers/{activiteId}` - Récupère les utilisateurs inscrits pour une activité donnée.

## Sécurité
L'application utilise JWT pour sécuriser les points de terminaison. La classe `SecurityConfig` configure les paramètres de sécurité, y compris les points de terminaison qui nécessitent une authentification et les rôles requis pour accéder à des points de terminaison spécifiques.

## Documentation des Routes

### Authentification
- **POST /auth/login**
  - **Description**: Authentifie un utilisateur et retourne un jeton JWT.
  - **Corps de la requête**:
    ```json
    {
      "username": "string",
      "password": "string"
    }
    ```
  - **Réponse**:
    ```json
    {
      "token": "string"
    }
    ```

### Gestion des comptes
- **GET /compte/allCompte**
  - **Description**: Récupère tous les comptes.
  - **Rôles requis**: `client`, `admin`
  - **Réponse**:
    ```json
    [
      {
        "id": "integer",
        "username": "string",
        "email": "string",
        ...
      }
    ]
    ```

- **GET /compte/{id}**
  - **Description**: Récupère les détails d'un compte par ID.
  - **Rôles requis**: `client`
  - **Réponse**:
    ```json
    {
      "id": "integer",
      "username": "string",
      "email": "string",
      ...
    }
    ```

- **GET /compte/compteBloque**
  - **Description**: Récupère les comptes bloqués.
  - **Rôles requis**: `admin`
  - **Réponse**:
    ```json
    [
      {
        "id": "integer",
        "username": "string",
        "email": "string",
        ...
      }
    ]
    ```

### Gestion des créneaux
- **GET /creneaux/allCreneaux**
  - **Description**: Récupère tous les créneaux.
  - **Réponse**:
    ```json
    [
      {
        "id": "integer",
        "startTime": "string",
        "endTime": "string",
        ...
      }
    ]
    ```

### Gestion des inscriptions
- **POST /inscription/insertOrUpdateInscription**
  - **Description**: Insère ou met à jour une inscription.
  - **Corps de la requête**:
    ```json
    {
      "id_compte": "integer",
      "id_creneaux": "integer",
      "date_inscription": "string"
    }
    ```

- **DELETE /inscription/deleteInscription**
  - **Description**: Supprime une inscription.
  - **Corps de la requête**:
    ```json
    {
      "id_compte": "integer",
      "id_creneaux": "integer"
    }
    ```

- **GET /inscription/getRegisteredUsers/{activiteId}**
  - **Description**: Récupère les utilisateurs inscrits pour une activité donnée.
  - **Réponse**:
    ```json
    [
      {
        "id": "integer",
        "username": "string",
        "email": "string",
        ...
      }
    ]
    ```
re to replace the placeholder information with your actual project details and author information.