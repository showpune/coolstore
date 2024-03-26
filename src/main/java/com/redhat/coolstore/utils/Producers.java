import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class Producers {

    // Remove the final modifier and the static keyword
    private String inventoryServiceUrl = "http://inventory-service:8080/inventory";

    // Remove the @Produces annotation
    @Inject
    InventoryService inventoryService;

    // Remove the @Produces annotation and use @InjectInstance instead
    @InjectInstance
    Supplier<InventoryService> inventoryServiceSupplier;

    // Remove the Producers class and move the functionality to the class that needs the InventoryService
    public void doSomethingWithInventoryService() {
        InventoryService inventoryService = inventoryServiceSupplier.get();
        // Use the inventoryService as needed
    }
}