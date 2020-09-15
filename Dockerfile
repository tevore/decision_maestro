FROM openjdk:14-alpine
COPY build/libs/DecisionMaestro-*-all.jar DecisionMaestro.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "DecisionMaestro.jar"]