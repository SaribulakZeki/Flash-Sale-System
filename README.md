# Flash-Sale-System

A scalable, event-driven microservices application for flash sales built with Spring Boot, React, Kafka, and Kubernetes.

# Flash Sale System (Microservices)

This is a scalable, event-driven microservices application designed to handle high-concurrency purchasing scenarios, such as limited ticket sales or flash discounts. The architecture ensures data consistency, asynchronous processing, and high availability.

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

## How to Run

1. Build the Docker images for all services:

   ```bash
   docker build -t product-service:v3 ./product-service
   docker build -t order-service:v3 ./order-service
   docker build -t notification-service:v3 ./notification-service
   docker build -t frontend-client:v3 ./frontend-client

   ```

2. Deploy the infrastructure and applications to Kubernetes:

   kubectl apply -f k8s-files/infrastructure.yaml
   kubectl apply -f k8s-files/apps.yaml

3. Open the frontend application in your browser:
   http://localhost:30000
