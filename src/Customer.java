public class Customer {
    private String customerId;  // Müşteri ID (benzersiz)
    private String firstName;   // Müşteri adı
    private String lastName;    // Müşteri soyadı
    private DeliveryHistory deliveryHistory;  // Gönderim geçmişi (Linked List)

    // Constructor
    public Customer(String customerId, String firstName, String lastName) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.deliveryHistory = new DeliveryHistory();  // Yeni gönderim geçmişi oluşturuluyor
    }

    // Getter ve Setter metodları
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public DeliveryHistory getDeliveryHistory() {
        return deliveryHistory;
    }

    // Müşteri bilgilerini yazdırma
    @Override
    public String toString() {
        return "Customer ID: " + customerId +
                ", Name: " + firstName + " " + lastName;
    }
}
