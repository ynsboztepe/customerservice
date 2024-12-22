import java.util.HashMap;

public class CustomerManager {
    public HashMap<String, Customer> customers; // Müşteri bilgileri

    // Constructor
    public CustomerManager() {
        this.customers = new HashMap<>();
    }

    // Yeni müşteri ekleme
    public void addCustomer(String customerId, String firstName, String lastName) {
        if (customers.containsKey(customerId)) {
            System.out.println("Error: Customer with ID " + customerId + " already exists.");
        } else {
            Customer newCustomer = new Customer(customerId, firstName, lastName);
            customers.put(customerId, newCustomer);
            System.out.println("Customer added successfully: " + newCustomer);
        }
    }

    // Müşteri bilgilerini görüntüleme
    public void viewCustomer(String customerId) {
        if (customers.containsKey(customerId)) {
            Customer customer = customers.get(customerId);
            System.out.println("Customer Details:");
            System.out.println(customer);
            System.out.println("Delivery History:");
            customer.getDeliveryHistory().printDeliveries();
        } else {
            System.out.println("Error: Customer with ID " + customerId + " not found.");
        }
    }

    // Müşteri gönderim geçmişine yeni bir gönderi ekleme
    public void addDeliveryToCustomer(String customerId, String deliveryId, String deliveryDate, String status, int deliveryDuration) {
        if (customers.containsKey(customerId)) {
            Customer customer = customers.get(customerId);
            Delivery newDelivery = new Delivery(deliveryId, deliveryDate, status, deliveryDuration);
            customer.getDeliveryHistory().addDelivery(newDelivery);
            System.out.println("Delivery added to customer " + customerId + ": " + newDelivery);
        } else {
            System.out.println("Error: Customer with ID " + customerId + " not found.");
        }
    }

    // Son 5 gönderiyi listeleme
    public void viewRecentDeliveries(String customerId) {
        if (customers.containsKey(customerId)) {
            Customer customer = customers.get(customerId);
            System.out.println("Recent Deliveries for Customer " + customerId + ":");
            customer.getDeliveryHistory().printRecentDeliveries();
        } else {
            System.out.println("Error: Customer with ID " + customerId + " not found.");
        }
    }

    // Teslim edilmemiş gönderileri sıralama ve yazdırma
    public void viewUnDeliveredSorted(String customerId) {
        if (customers.containsKey(customerId)) {
            Customer customer = customers.get(customerId);
            System.out.println("UnDelivered Deliveries for Customer " + customerId + ":");
            customer.getDeliveryHistory().sortUnDeliveredByDuration();
        } else {
            System.out.println("Error: Customer with ID " + customerId + " not found.");
        }
    }

    // Tüm müşterileri yazdırma
    public void printAllCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            System.out.println("All Customers:");
            for (Customer customer : customers.values()) {
                System.out.println(customer);
            }
        }
    }
}
