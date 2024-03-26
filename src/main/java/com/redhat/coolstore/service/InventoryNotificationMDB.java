@Quarkus(
    applications = "coolstore-service",
    mainClass = InventoryNotificationMDB.class
)
public class InventoryNotificationMDB implements MessageListener {

    @Inject
    private CatalogService catalogService;

    @Inject
    @Named("HELLOWORLDMDBTopic")
    Emitter<String> topicEmitter;

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

    public void init() throws NamingException {
        Context ctx = java.naming.Context.getInitialContext();
        TopicConnectionFactory tconFactory = (TopicConnectionFactory) ctx.lookup("quarkus.jms.connection.factories.SmallRyeJmsConnectionFactory");
        tcon = tconFactory.createTopicConnection();
        tsession = tcon.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = (Topic) ctx.lookup("quarkus.jms.topic.InventoryNotificationTopic");
        tsubscriber = tsession.createSubscriber(topic);
        tsubscriber.setMessageListener(this);
        tcon.start();
    }

    public void close() throws JMSException {
        tsubscriber.close();
        tsession.close();
        tcon.close();
    }
}