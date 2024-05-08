Model ID: ibm/granite-20b-code-javaenterprise

@redhat-developer/java-ee-to-quarkus-assistant

---

@redhat.com

# Output Information

## Reasoning

I will replace the `javax.ws` import statement with `jakarta.ws` 

I will replace the `javax.ws` import statement with `jakarta.ws` 

JAX-RS activation is no longer necessary. You can set a root path like this but you don't have to.

JAX-RS activation is no longer necessary. You can set a root path like this but you don't have to.

## Updated File

```java
package com.redhat.coolstore.rest;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;


@ApplicationPath("/services")
public class RestApplication extends Application {

}

```

## Additional Information (optional)

If you have any additonal details or steps that need to be performed, put it here
