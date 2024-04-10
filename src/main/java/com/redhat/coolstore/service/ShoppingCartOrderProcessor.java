// Add the following to the top of the file
import jakarta.annotation.Priority;
import jakarta.ejb.ConcurrencyManagement;
import jakarta.ejb.EJB;
import jakarta.ejb.EntityBean;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;
import jakarta.jms.JMSContext;
import jakarta.jms.Topic;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.jms.Message;
import reactor.jms.MessageListener;
import reactor.jms.TopicSubscriber;
import reactor.jms.TopicSubscription;
import static com.redhat.coolstore.service.ShoppingCartOrderProcessor.class;

@Singleton
@ConcurrencyManagement(ConcurrencyManagement.MBeanConcurrencyManagement.class)
@EntityBean
@Priority(10)
public class ShoppingCartOrderProcessor {

    @Inject
    private Logger log;

    @Inject
    private JMSContext context;

    @Inject
    @Channel("orders")
    private Publisher<Message<String>> ordersTopic;

    @Inject
    private Subscriber<String> subscriber;

    public void process(ShoppingCart cart) {
        log.info("Sending order from processor: ");
        subscriber.onNext(Transformers.shoppingCartToJson(cart));
        subscriber.onComplete();
    }
}
