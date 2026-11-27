# Aplicação Web MVC (JSP) — imagem independente
# ---------- Etapa 1: build com Maven (Java 17) ----------
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -B -q dependency:go-offline
COPY src ./src
# Os testes do MVC usam o banco e a API; por isso não rodam no build da imagem
RUN mvn -B -q clean package -DskipTests

# ---------- Etapa 2: execução no Tomcat 10.1 (Jakarta Servlet 6) ----------
FROM tomcat:10.1-jdk17-temurin
RUN rm -rf /usr/local/tomcat/webapps/*
# Publicada na raiz: http://<host>:8080/
COPY --from=build /app/target/mvc.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
