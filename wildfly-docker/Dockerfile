FROM quay.io/wildfly/wildfly:latest-jdk21

# Installation du module JDBC PostgreSQL
COPY postgresql-module/postgresql-42.7.3.jar /opt/jboss/wildfly/modules/system/layers/base/org/postgresql/main/postgresql-42.7.3.jar
COPY postgresql-module/module.xml /opt/jboss/wildfly/modules/system/layers/base/org/postgresql/main/module.xml

# Configuration WildFly (driver + datasource)
COPY standalone.xml /opt/jboss/wildfly/standalone/configuration/standalone.xml

# Déploiement de l'application
COPY mediatheque-api.war /opt/jboss/wildfly/standalone/deployments/

EXPOSE 8080

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]