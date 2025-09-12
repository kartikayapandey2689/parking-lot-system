# Parking Lot System (Spring Boot)

Production-ready Parking Lot Management System implemented with Spring Boot, JPA, and H2 (dev).  
Focus: modular design, transactions, concurrency protection, SOLID design.

## Features
- Park vehicle / generate ticket
- Exit vehicle / pay / generate receipt
- Admin APIs to add parking slots
- Pessimistic locking to avoid double allocation
- Transactional entry/exit flows (atomic)
- Validation and global exception handling

## Tech stack
- Java 17
- Spring Boot 3.x
- Spring Data JPA
- H2 (development)
- Maven

## Quick start (dev)
1. Clone:
```bash
git clone https://github.com/<you>/parking-lot-system.git
cd parking-lot-system
