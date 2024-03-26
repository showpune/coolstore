// Update the `Order` and `OrderItem` classes to use `jakarta.persistence`
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ORDERS")
public class Order implements Serializable {

    // ... (omitted for brevity)
}

// Update the `OrderItem` class to use `jakarta.persistence`
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ORDER_ITEMS")
public class OrderItem implements Serializable {

    // ... (omitted for brevity)
}

// Update the `pom.xml` file to include the `jakarta.persistence` dependency
<project xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.redhat.coolstore</groupId>
    <artifactId>coolstore</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>coolstore</name>
    <url>https://example.com</url>

    <properties>
        <java.version>11</java.version>
        <quarkus.version>1.13.0</quarkus.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.jboss.spec.javax.servlet</groupId>
            <artifactId>jboss-servlet-api_3.1_spec</artifactId>
            <version>1.0.2.Final</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>org.jboss.shrinkwrap.api</groupId>
            <artifactId>shrinkwrap-api-artifacts</artifactId>
            <version>2.9.1</version>
        </dependency>
        <dependency>
            <groupId>org.jboss.shrinkwrap.impl</groupId>
            <artifactId>shrinkwrap-impl</artifactId>
            <version>2.9.1</version>
        </dependency>
        <dependency>
            <groupId>org.jboss.spec.javax.transaction</groupId>
            <artifactId>jboss-transaction-api_1.2_spec</artifactId>
            <version>1.0.2.Final</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>io.quarkus</groupId>
            <artifactId>quarkus-hibernate-orm</artifactId>
            <version>${quarkus.version}</version>
        </dependency>
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <version>1.4.197</version>
        </dependency>
        <dependency>
            <groupId>jakarta.persistence</groupId>
            <artifactId>jakarta.persistence-api</artifactId>
            <version>2.2.3</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.jboss.msbuild</groupId>
                <artifactId>jboss-msbuild-plugin</artifactId>
                <version>4.2.3.Final</version>
                <configuration>
                    <mainClass>com.redhat.coolstore.Coolstore</mainClass>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>${java.version}</source>
                    <target>${java.version}</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>