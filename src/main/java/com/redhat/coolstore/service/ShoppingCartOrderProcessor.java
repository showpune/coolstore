@Quarkus
@ApplicationScoped
public class ShoppingCartOrderProcessor {

    private final Logger log;
    private final JMSContext context;
    private final Emitter<String> ordersTopic;

    @Inject
    public ShoppingCartOrderProcessor(
            Logger log,
            JMSContext context,
            @Named("orders") Emitter<String> ordersTopic
    ) {
        this.log = log;
        this.context = context;
        this.ordersTopic = ordersTopic;
    }

    public void process(ShoppingCart cart) {
        log.info("Sending order from processor: ");
        ordersTopic.emit("{\"cart\": " + Transformers.shoppingCartToJson(cart) + "}");
    }
}