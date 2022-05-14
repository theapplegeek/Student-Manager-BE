# STUDENT MANAGER

## Docker
- Start all microservices
  ```bash
  docker compose up -d --build
  ```
- Start all microservices with 3 instance for each application
  ```bash
  docker compose up -d --build \
  --scale student=3 \
  --scale student-card=3
  ```
