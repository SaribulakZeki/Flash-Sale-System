# Flash Sale System - Distributed Microservices Architecture

A scalable, event-driven microservices application for flash sales built with Spring Boot, React, Kafka, and Kubernetes.

# Flash Sale System (Microservices)

A scalable, event-driven microservices application designed to handle high-concurrency purchasing scenarios, such as limited ticket sales or flash discounts. The architecture ensures data consistency, asynchronous processing, and high availability using modern DevOps practices.

## Technologies Used

- **Frontend:** React.js
- **Backend:** Java, Spring Boot
- **Message Brokers:** Apache Kafka, RabbitMQ
- **Database & Cache:** PostgreSQL, Redis
- **DevOps & Orchestration:** Docker, Kubernetes

## Microservices Architecture

1.  **Product Service:** Manages product listings and stock. Uses Redis for fast data retrieval. Publishes order events to Kafka.
2.  **Order Service:** Consumes events from Kafka, processes the order securely, saves the record to PostgreSQL, and routes a success message to RabbitMQ.
3.  **Notification Service:** Consumes messages from RabbitMQ and simulates sending a confirmation email to the user.
4.  **Frontend Client:** A React-based user interface that communicates with the backend to display products and process purchases.

## How to Run Locally (Kubernetes)

1. Security Setup (Required)

For security reasons, the secrets.yaml file is ignored in this repository. Before deploying, create a secrets.yaml file inside the k8s-files directory with your base credentials:

```
YAML
apiVersion: v1
kind: Secret
metadata:
  name: credentials
type: Opaque
stringData:
  username: postgres
  password: root
```

2. Build Docker Images

Build the updated v4 Docker images for all services:

```
Bash
docker build -t flashsalesystem:v4 ./product-service
docker build -t order-service:v4 ./order-service
docker build -t notification-service:v4 ./notification-service
docker build -t frontend-client:v4 ./frontend-client
```

3. Deploy to Kubernetes

Apply the configuration files in the following order to set up the isolated databases, message brokers, and applications:

```
Bash
kubectl apply -f k8s-files/secrets.yaml
kubectl apply -f k8s-files/infrastructure.yaml
kubectl apply -f k8s-files/apps.yaml
```

4. Access the Application (Port-Forwarding)

Since the application runs inside a closed Kubernetes cluster, open two terminal windows to establish tunnels for the frontend and backend:

```
Bash
kubectl port-forward svc/frontend-service 5173:5173
kubectl port-forward svc/product-service 8081:8081
Open your browser and navigate to: http://localhost:5173
```

(Optional) To view the isolated databases via a DB client like DBeaver:

Product DB: kubectl port-forward svc/postgres-product-service 5431:5432

Order DB: kubectl port-forward svc/postgres-order-service 5433:5432
