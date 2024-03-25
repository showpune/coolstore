<dependencies>
    <!-- Quarkus BOM to manage versions -->
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>io.quarkus</groupId>
                <artifactId>quarkus-bom</artifactId>
                <version>Insert latest version here</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <!-- Quarkus RESTEasy dependency for JAX-RS -->
    <dependency>
        <groupId>io.quarkus</groupId>
        <artifactId>quarkus-resteasy</artifactId>
    </dependency>

    <!-- Quarkus RESTEasy Jackson for JSON processing -->
    <dependency>
        <groupId>io.quarkus</groupId>
        <artifactId>quarkus-resteasy-jackson</artifactId>
    </dependency>

    <!-- Other necessary dependencies here -->
</dependencies>