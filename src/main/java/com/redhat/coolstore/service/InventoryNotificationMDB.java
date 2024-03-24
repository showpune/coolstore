// Update the InventoryNotificationMDB class to use Quarkus JMS and SmallRye
@Quarkus(
    applications = "coolstore-service",
    mainClass = InventoryNotificationMDB.class
)
@QuarkusApplicationClient("inventory-notification-mdb")
public class InventoryNotificationMDB implements MessageListener {

    @Inject
    private CatalogService catalogService;

    @Inject
    private Context context;

    @Inject
    private Emitter<String> topicEmitter;

    @Inject
    private MessageEndpoint<String> messageEndpoint;

    @Inject
    private MessageSession messageSession;

    @Inject
    private MessageListenerContainer<String> messageListenerContainer;

    @Override
    public void onMessage(Message rcvMessage) {
        TextMessage msg;
        try {
            System.out.println("received message inventory");
            if (rcvMessage instanceof TextMessage) {
                msg = (TextMessage) rcvMessage;
                String orderStr = msg.getBody(String.class);
                Order order = Transformers.jsonToOrder(orderStr);
                order.getItemList().forEach(orderItem -> {
                    int old_quantity = catalogService.getCatalogItemById(orderItem.getProductId()).getInventory().getQuantity();
                    int new_quantity = old_quantity - orderItem.getQuantity();
                    if (new_quantity < LOW_THRESHOLD) {
                        System.out.println("Inventory for item " + orderItem.getProductId() + " is below threshold (" + LOW_THRESHOLD + "), contact supplier!");
                    } else {
                        orderItem.setQuantity(new_quantity);
                    }
                });
            }
        } catch (JMSException jmse) {
            System.err.println("An exception occurred: " + jmse.getMessage());
        }
    }

    public void init() {
        // No-op
    }

    public void close() {
        // No-op
    }
}