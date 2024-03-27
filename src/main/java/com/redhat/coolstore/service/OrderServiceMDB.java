import io.smallrye.reactive.messaging.annotations.Incoming;
import io.smallrye.reactive.messaging.annotations.Outgoing;
import io.smallrye.reactive.messaging.connectors.Incoming;
import io.smallrye.reactive.messaging.connectors.Outgoing;
import io.smallrye.reactive.messaging.providers.connectors.Message;
import io.quarkus.runtime.Startup;
import io.quarkus.runtime.Shutdown;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.InjectResource;
import io.smallrye.reactive.messaging.impl.ReactiveMessage;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.reactivestreams.Publisher;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Startup
@Shutdown
@ApplicationScoped
public class OrderServiceMDB implements QuarkusApplication {

    @Inject
    @Incoming("order-in")
    private Publisher<ReactiveMessage<Order>> incomingOrder;

    @Outgoing("order-out")
    private Publisher<ReactiveMessage<Order>> outgoingOrder;

    @Override
    public int run(String... args) {
        // Process the order here
        incomingOrder.subscribe().with(order -> outgoingOrder.subscribe().with(outgoingOrder -> outgoingOrder.send(order)));
        return 0;
    }

    @Override
    public void close() {
        // Perform any necessary cleanup here
    }
}