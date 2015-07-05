# Base image with java 8
FROM dockerfile/java:oracle-java8

# we need this because the workdir is modified in dockerfile/java
WORKDIR /

# run the (java) server as the daemon user
USER daemon

# copy the locally built fat-jar to the image
ADD service/target/service-0.1.0-SNAPSHOT.jar /app/server.jar

# copy the locally built static resources to the image
ADD client/build /app/client

# the server binds to 8090 - expose that port
EXPOSE 8090

# run the server when a container based on this image is being run
CMD [ "java", "-Dcrypto.password=password@aws", "-Dapp.env=aws", "-jar", "/app/server.jar" ]