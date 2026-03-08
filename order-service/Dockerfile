# 1. Aşama: Maven ile Build al (Güncel Temurin imajı)
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
# Testleri atlayarak .jar dosyasını oluştur
RUN mvn clean package -DskipTests

# 2. Aşama: Çalıştırma Aşaması (Güncel Temurin imajı)
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
# İlk aşamada oluşan .jar dosyasını buraya kopyala
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]