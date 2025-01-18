FROM tomcat:10.1-jdk21-temurin
LABEL authors="podvo"

# Удаляем дефолтный ROOT Tomcat'а, чтобы наше приложение открывалось через /Tennis
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# Копируем ваш war-файл в webapps/
COPY target/Tennis-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/Tennis.war
