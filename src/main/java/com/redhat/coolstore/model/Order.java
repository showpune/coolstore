// Update the `Order` class to use `jakarta.persistence`
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

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
            <groupId>org.jboss.spec.javax.persistence</groupId>
            <artifactId>jboss-persistence-api_2.1_spec</artifactId>
            <version>2.1.1.Final</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>org.quarkus</groupId>
            <artifactId>quarkus-hibernate-orm</artifactId>
            <version>${quarkus.version}</version>
        </dependency>
        <dependency>
            <groupId>org.hibernate.javax.persistence</groupId>
            <artifactId>hibernate-jpa-2.1-api</artifactId>
            <version>1.0.2.Final</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>${java.version}</source>
                    <target>${java.version}</target>
                </configuration>
            </plugin>
            <plugin>
                <groupId>io.quarkus</groupId>
                <artifactId>quarkus-maven-plugin</artifactId>
                <version>${quarkus.version}</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>build</goal>
                            <goal>native-image</goal>
                         </goals>
                        <configuration>
                            <additionalClasspath>
                                <mainClass>com.redhat.coolstore.Main</mainClass>
                            </additionalClasspath>
                            <java-options>
                                <java-option>-Xbootclasspath/p:<path-to-your-jboss-server-lib></java-option>
                            </java-options>
                            <additionalProperties>
                                <quarkus.native.image.additional-classpath>
                                    <classpath>
                                        <pathelement>${project.build.directory}/classes</pathelement>
                                        <pathelement>${project.build.directory}/native-image</pathelement>
                                    </classpath>
                                </quarkus.native.image.additional-classpath>
                            </additionalProperties>
                         </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>