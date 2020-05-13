FROM openjdk:8-jdk-alpine
COPY extras/images /app/images
COPY app/instagramclone-0.0.1-SNAPSHOT.jar /app
ENTRYPOINT ["java","-Dapp.extras.path=/app","-Dspring.datasource.url=jdbc:mysql://instagramclone-mysql:3306/instagramdb","-Dapp.extras.ip=${EXTRAS_IP}","-jar","/app/instagramclone-0.0.1-SNAPSHOT.jar","--envname=prod"]