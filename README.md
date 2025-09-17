# 🚗 Parking Lot System (Spring Boot + Google OAuth2)

Parking Lot System built with **Spring Boot 3**, **Spring Security (OAuth2 with Google)**, **H2 Database**, and **Java 8+**.  
This project demonstrates **clean code, SOLID principles, design patterns (Strategy, Factory), concurrency handling**, and secure REST APIs.

---

## ✨ Features
- Vehicles: Car, Bike, Truck (extensible)
- Nearest slot allocation strategy (Strategy Pattern)
- Multiple entry gates
- Ticket generation (entry time, slot, vehicle info)
- Pricing rules (first 2 hrs free, hourly charges by type)
- Payment on exit (slot freed only after payment)
- Admin management (slots, pricing rules)
- Concurrency safe (DB constraints, locking)
- OAuth2 login with Google for authentication
- Role-based authorization (Admin vs User)

---

## 🏗️ Tech Stack
- Java 17+
- Spring Boot 3.5.5
- Spring Security (OAuth2 Resource Server)
- H2 Database (in-memory)
- Maven
- Postman for API testing

---

## ⚙️ Setup & Run Locally

### 1. Clone Repo

---
git clone https://github.com/kartikayapandey2689/parking-lot-system.git
cd parking-lot-system

## Configure Google OAuth2

- Go to [Google Cloud Console](https://console.cloud.google.com/).
2. Create a new project → Enable **OAuth Consent Screen**.
3. Create **OAuth Client ID** (Web Application):
   - **Authorized Redirect URI** → `https://oauth.pstmn.io/v1/callback`
4. Copy the generated **Client ID** and **Client Secret**.


You will use these in Postman and `application.yml`.

---

## ⚙️ Configure `application.yml`
Configuration
Copy `application-test.yml` to `application.yml` and update with your values:

---
spring:
  h2:
    console:
      enabled: true
      path: /h2-console
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: sa
    password:

  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: https://accounts.google.com

logging:
  level:
    org.springframework.security: DEBUG


Run Application
# Using Maven
App runs at: http://localhost:8080

# Database (H2 Console)

URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:testdb
Username: sa
Password: (leave blank unless configured)


# Security & Roles

Authentication: Google OAuth2 (JWT validation)
Roles:
ROLE_USER → default for all Google users
ROLE_ADMIN → assigned to specific emails (see SecurityConfig)
Access rules:
/auth/me → logged-in user info
/api/parking/** → authenticated users
/api/admin/** → admin users only

# Vehicle Entry

POST /api/parking/entry

{
  "plate": "KA02AB1234",
  "type": "CAR",
  "gateId": 1
}
# Exit Initiate

POST /api/parking/exit/initiate

{
  "plate": "KA02AB1234"
}
# Exit Pay

POST /api/parking/exit/pay

{
  "ticketId": 101,
  "paymentMethod": "CASH"
}

# Add Slot (Admin Only)

POST /api/admin/slot

{
  "level": 1,
  "slotNumber": "A1",
  "vehicleType": "CAR"
}

#Get Logged-in User

GET /auth/me

Postman Collection

We provide a ready-to-use Postman setup.

Import postman/ParkingLot-Collection.json

Set environment variables:

http://localhost:8080

GOOGLE_CLIENT_ID → your OAuth Client ID

GOOGLE_CLIENT_SECRET → your OAuth Client Secret

In Postman, go to Authorization → Get New Access Token and log in with Google.

Test endpoints (/api/parking/entry, /api/parking/exit/initiate, etc.).


Author
Kartikeya Pandey
Senior Java Backend Developer | Spring Boot | Microservices | Cloud