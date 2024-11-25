# event-locator--java-spring

## Table of Contents

1. [Introduction](#introduction)
2. [Features](#features)
3. [Tech Stack](#tech-stack)
4. [Installation and Usage](#installation-and-usage)
5. [API Endpoints](#api-endpoints)

## Introduction

The Event Locator App is a backend application designed for managing events and user interactions with them. It allows users to discover, register for, and engage with events while providing administrators with tools to organize and maintain event data. The app features a secure role-based access control system (RBAC), enabling admins to create and assign roles to users.

Users can manage their accounts, register for events, leave reviews, and handle favorites. Admins can create, update, and categorize events, ensuring organized event management. The app also includes a notification system to inform users about upcoming and registered events via SMS, email, and mobile notifications. Built using Java Spring, PostgreSQL, and Firebase, it is designed for reliability and scalability.

## Features

- **Role-Based Access Control (RBAC)**

  - Admins can create, update, and assign roles to users.
  - Different levels of access for admins and regular users.

- **Event Management**

  - Admins can create, update, delete, and manage events.
  - Events are categorized, and each event is linked to a category.

- **Category Management**

  - Admins can perform CRUD operations on categories.

- **User Registration and Participation**

  - Users can register for events and cancel their registrations.
  - Users can view their upcoming and past event registrations.

- **Event Reviews and Ratings**

  - Users can leave reviews, ratings, and comments for events.
  - Reviews and ratings are publicly viewable.

- **Favorites Management**

  - Users can add events to their favorites.
  - Users can remove events from their favorites list.

- **Notifications**

  - Users receive SMS, email, and mobile notifications for upcoming and registered events.

- **User Management**

  - Users can update profiles, handle images, and reset or update their passwords.

## Tech Stack

The app is built with **Spring Boot**, using the following key dependencies:

- **Spring Boot Starter Data JPA**: For database operations.
- **Spring Boot Starter Security**: For authentication and authorization.
- **Spring Boot Starter Web**: For building REST APIs.
- **PostgreSQL**: As the database.
- **Lombok**: To reduce boilerplate code.
- **Twilio SDK**: For SMS notifications.
- **Firebase**: For mobile notifications.
- **JSON Web Tokens (JJWT)**: For JWT-based authentication.

## Installation and Usage

### Prerequisites

Before setting up the project, ensure you have the following installed:

- **Java 17 or higher**
- **Maven**
- **PostgreSQL**

### Setup

1. Clone the repository and navigate to the directory:

   ```bash
   git clone https://github.com/omerfarukgulhan/event-locator--java-spring.git
   cd event-locator--java-spring
   ```

2. Set up the backend environment variables

   ```bash
   DB_URL=<your-database-url>
   DB_USERNAME=<your-database-username>
   DB_PASSWORD=<your-database-password>
   SECURITY_USER_PASSWORD=<default-admin-password>
   EMAIL_USERNAME=<your-email-username>
   EMAIL_PASSWORD=<your-email-password>
   JWT_SECRET_KEY=<your-jwt-secret-key>
   TWILIO_ACCOUNT_SID=<your-twilio-account-sid>
   TWILIO_AUTH_TOKEN=<your-twilio-auth-token>
   TWILIO_PHONE_NUMBER=<your-twilio-phone-number>
   CLIENT_HOST=<frontend-client-url>
   FIREBASE_TYPE=<your-firebase-type>
   FIREBASE_PROJECT_ID=<your-firebase-project-id>
   FIREBASE_PRIVATE_KEY_ID=<your-firebase-private-key-id>
   FIREBASE_PRIVATE_KEY=<your-firebase-private-key>
   FIREBASE_CLIENT_EMAIL=<your-firebase-client-email>
   FIREBASE_CLIENT_ID=<your-firebase-client-id>
   FIREBASE_AUTH_URI=<your-firebase-auth-uri>
   FIREBASE_TOKEN_URI=<your-firebase-token-uri>
   FIREBASE_AUTH_PROVIDER_X509_CERT_URL=<your-firebase-auth-provider-cert-url>
   FIREBASE_CLIENT_X509_CERT_URL=<your-firebase-client-cert-url>
   ```

3. Build and run the server:

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

## API Endpoints

### Favorites

- `GET /favorites`  
  **Access**: `ROLE_USER`  
  **Description**: Get all favorites for the current user.
- `POST /favorites/`  
  **Access**: `ROLE_USER`  
  **Description**: Add an event to the favorites list.
- `DELETE /favorites/{eventId}`  
  **Access**: `ROLE_USER`  
  **Description**: Remove an event from the favorites list.

### Reviews

- `GET /reviews/{eventId}`  
  **Access**: `permitAll`  
  **Description**: Get all reviews for a specific event.
- `POST /reviews`  
  **Access**: `ROLE_USER`  
  **Description**: Add a review for an event.
- `PUT /reviews/{reviewId}`  
  **Access**: `ROLE_USER`  
  **Description**: Update an existing review.
- `DELETE /reviews/{reviewId}`  
  **Access**: `ROLE_USER`  
  **Description**: Delete a review.

### Registrations

- `GET /registrations/future`  
  **Access**: `ROLE_USER`  
  **Description**: Get all upcoming event registrations for the user.
- `GET /registrations/past`  
  **Access**: `ROLE_USER`  
  **Description**: Get all past event registrations for the user.
- `POST /registrations`  
  **Access**: `ROLE_USER`  
  **Description**: Register for an event.
- `DELETE /registrations/{eventId}`  
  **Access**: `ROLE_USER`  
  **Description**: Cancel an event registration.

### Events

- `GET /events`  
  **Access**: `permitAll`  
  **Description**: Get a list of all events.
- `GET /events/{eventId}`  
  **Access**: `permitAll`  
  **Description**: Get details of a specific event.
- `POST /events`  
  **Access**: `ROLE_ADMIN`  
  **Description**: Create a new event.
- `PUT /events/{eventId}`  
  **Access**: `ROLE_ADMIN`  
  **Description**: Update an existing event.
- `DELETE /events/{eventId}`  
  **Access**: `ROLE_ADMIN`  
  **Description**: Delete an event.

### Categories

- `GET /categories`  
  **Access**: `permitAll`  
  **Description**: Get a list of all categories.
- `GET /categories/{categoryId}`  
  **Access**: `permitAll`  
  **Description**: Get details of a specific category.
- `GET /categories/name/{name}`  
  **Access**: `permitAll`  
  **Description**: Get a category by name.
- `POST /categories`  
  **Access**: `ROLE_ADMIN`  
  **Description**: Create a new category.
- `PUT /categories/{categoryId}`  
  **Access**: `ROLE_ADMIN`  
  **Description**: Update an existing category.
- `DELETE /categories/{categoryId}`  
  **Access**: `ROLE_ADMIN`  
  **Description**: Delete a category.

### Roles

- `GET /roles`  
  **Access**: `ROLE_ADMIN`  
  **Description**: Get a list of all roles.
- `GET /roles/{roleId}`  
  **Access**: `ROLE_ADMIN`  
  **Description**: Get details of a specific role.
- `POST /roles`  
  **Access**: `ROLE_ADMIN`  
  **Description**: Create a new role.
- `PUT /roles/{roleId}`  
  **Access**: `ROLE_ADMIN`  
  **Description**: Update an existing role.
- `DELETE /roles/{roleId}`  
  **Access**: `ROLE_ADMIN`  
  **Description**: Delete a role.

### Users

- `GET /users`  
  **Access**: `ROLE_ADMIN`  
  **Description**: Get a list of all users.
- `GET /users/{userId}`  
  **Access**: `ROLE_ADMIN`  
  **Description**: Get details of a specific user.
- `POST /users`  
  **Access**: `ROLE_ADMIN`  
  **Description**: Create a new user.
- `PUT /users/{userId}`  
  **Access**: `ROLE_USER`  
  **Description**: Update user information.
- `PUT /users/activate/{token}`  
  **Access**: `permitAll`  
  **Description**: Activate a user account using a token.
- `PUT /users/update-password/{userId}`  
  **Access**: `ROLE_USER`  
  **Description**: Update the password for a user.
- `PUT /users/reset-password`  
  **Access**: `permitAll`  
  **Description**: Request a password reset.
- `PUT /users/reset-password/verify/{token}`  
  **Access**: `permitAll`  
  **Description**: Verify the password reset token.
- `DELETE /users/{userId}`  
  **Access**: `ROLE_USER`  
  **Description**: Delete a user.
