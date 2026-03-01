# Spring Boot CRUD API (MySQL in Docker)

This project provides a simple **production-style** CRUD REST API using:
- Spring Boot 3 (Java 17)
- Spring Data JPA (Hibernate)
- MySQL (running in Docker)

## 1) Prerequisites
- Java 17+
- Maven 3.9+
- Docker Desktop
- MySQL container running (example below)

## 2) Ensure your MySQL Docker container is running
If you already have it running, skip this.

Example (PowerShell):
```powershell
docker volume create mysql_data

docker run -d --name mysql-db --restart unless-stopped `
  -p 3306:3306 `
  -e MYSQL_ROOT_PASSWORD="root123!" `
  -e MYSQL_DATABASE="ecommerce" `
  -e MYSQL_USER="app" `
  -e MYSQL_PASSWORD="app123!" `
  -v mysql_data:/var/lib/mysql `
  mysql:8.4
```

> If port 3306 is busy, map to 3307: `-p 3307:3306` and set `DB_PORT=3307` when running the app.

## 3) Configure DB (defaults match your Docker container)
The app reads these environment variables (with defaults):

- `DB_HOST` (default: `localhost`)
- `DB_PORT` (default: `3306`)
- `DB_NAME` (default: `ecommerce`)
- `DB_USER` (default: `app`)
- `DB_PASS` (default: `app123!`)

MySQL 8.4 uses `caching_sha2_password` by default, so the JDBC URL includes:
- `allowPublicKeyRetrieval=true`
- `useSSL=false`

## 4) Run the app
### Option A: Run with Maven
```bash
mvn spring-boot:run
```

### Option B: Run with custom env (PowerShell)
```powershell
$env:DB_HOST="localhost"
$env:DB_PORT="3306"
$env:DB_NAME="ecommerce"
$env:DB_USER="app"
$env:DB_PASS="app123!"
mvn spring-boot:run
```

App runs on: http://localhost:8080

Health: http://localhost:8080/actuator/health

## 5) API Endpoints (Postman)
Base path: `/api/products`

### Create
POST `http://localhost:8080/api/products`
```json
{
  "name": "iPhone 15",
  "sku": "IPHONE-15-128",
  "price": 999.99,
  "quantity": 10
}
```

### List
GET `http://localhost:8080/api/products`

### Get by id
GET `http://localhost:8080/api/products/{id}`

### Update
PUT `http://localhost:8080/api/products/{id}`
```json
{
  "name": "iPhone 15 Pro",
  "sku": "IPHONE-15-PRO-256",
  "price": 1299.99,
  "quantity": 5
}
```

### Delete
DELETE `http://localhost:8080/api/products/{id}`

## 6) Notes
- Uses `spring.jpa.hibernate.ddl-auto=update` for local dev convenience.
- Has structured error responses and validation messages.
## run docker compose
docker compose -p myproject down
docker compose -p myproject up -d
