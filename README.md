# CoolStore Monolith

This repository has the complete coolstore monolith built as a Java EE 7 application. To deploy it on OpenShift Container Platform (OCP) follow the instructions below


## Pre requisite

* Access to a OCP cluster using 3.5 or later.
* OpenShift Command Client tool (eg. oc) installed locally
* Authenticated from the command line client to the cluster

        oc login <url>


## Build and deploy using the GitHub repo
To build and deploy using the github repo there is no need to clone this repo locally. All that is required is to create a project and process and create the application according to the template. 

NOTE: A source deployment takes longer than a binary deployment

Create a new project (or use an existing)

    oc new-project coolstore

Deploy and start the build

    oc process -f https://raw.githubusercontent.com/coolstore/monolith/master/src/main/openshift/template.json | oc create -f -

## Build and deploy using the binary deployment

Clone the project to a local directory

    git clone https://github.com/coolstore/monolith.git coolstore-monolith
    cd coolstore-monolith

Build the project using openshift profile 

    mvn -Popenshift package

Create a new project (or use an existing)

    oc new-project coolstore

Create the app

    oc process -f src/main/openshift/template-binary.json | oc create -f -

Start the build

    oc start-build coolstore --from-file=deployments/ROOT.war
    
To deploy the production environment and Jenkins pipeline

    oc process -f src/main/openshift/template-prod.json | oc create -f -
    
Manually start the pipeline

    oc start-build monolith-pipeline



# Run standalone

The application can also be deploy to a local JBoss EAP, which is great for development, but requires JMS Queues etc to be configured. The `pom.xml` does however support adding that through the `maven-wildfly-plugin`.

## First time

1. Download JBoss EAP 7.x from [developers.redhat.com](https://developers.redhat.com/products/eap/download/).
1. Unzip the installation in a suitable locations (e.g. /opt)
1. Set the JBOSS_HOME environment variable (in windows right click on my computer and add system environment variable)

        export JBOSS_HOME=/opt/jboss-eap-7.0

1. Run the following maven command

        mvn clean package wildfly:start wildfly:add-resource wildfly:deploy

1. Test the application by going to http://localhost:8080
1. To stop the application run the following command

        mvn wildfly:shutdown

## Second time

If you want to redeploy the application there is no need to use the `wildfly:add-resource` goal and if the JBoss is running then there is also possible to skip `wildfly:start` and just execute the following command

        mvn clean package wildfly:deploy


 Copy postgres jar file to modules/org/postgresql/main

create module.xml

<?xml version="1.0" encoding="UTF-8"?>
<module xmlns="urn:jboss:module:1.0" name="org.postgresql">
 <resources>
 <resource-root path="postgresql-42.5.4.jar"/>
 </resources>
 <dependencies>
 <module name="javax.api"/>
 <module name="javax.transaction.api"/>
 </dependencies>
</module>

Edit your standalone.xml and add the following code between the tags <datasources><drivers>
 <driver name="postgresql" module="org.postgresql">
 <xa-datasource-class>
 org.postgresql.xa.PGXADataSource
 </xa-datasource-class>
 </driver>

 data-source add --name=CoolstoreDS --jndi-name=java:jboss/datasources/CoolstoreDS --driver-name=postgresql --connection-url=jdbc:postgresql://127.0.0.1:5432/postgresDB --user-name=postgresUser --password=postgresPW

jms-topic add --topic-address=topic.orders --entries=topic/orders




