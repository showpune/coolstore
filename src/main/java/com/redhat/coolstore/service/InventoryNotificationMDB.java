@Quarkus(mainClass = InventoryNotificationMDB.class)
@QuarkusModule(name = "coolstore-service")
public class InventoryNotificationMDB implements MessageListener {

    @Inject
    private CatalogService catalogService;

    @Inject
    private CamelClient tcon;

    @Inject
    private MessageSession tsession;

    @Inject
    private Emitter<String> topicEmitter;

    public void onMessage(Message rcvMessage) {
        TextMessage msg;
        {
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


            } catch (Exception e) {
                System.err.println("An exception occurred: " + e.getMessage());
            }
        }
    }

    public void init() throws Exception {
        topicEmitter = Emitter.create("HELLOWORLDMDBTopic");
        tsession.subscribe("HELLOWORLDMDBTopic", topicEmitter);
    }

    public void close() {
        topicEmitter.close();
    }
}