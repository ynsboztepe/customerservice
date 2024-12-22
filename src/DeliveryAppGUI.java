import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

public class DeliveryAppGUI {
    private JFrame frame;
    private CustomerManager customerManager;
    private DeliveryTree deliveryTree;

    // Constructor
    public DeliveryAppGUI() {
        customerManager = new CustomerManager(); // Müşteri yöneticisi
        deliveryTree = initializeDeliveryTree(); // Teslimat ağacı
        initialize();
    }

    // GUI başlatma
    private void initialize() {
        frame = new JFrame("Delivery Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        frame.setLayout(new GridLayout(8, 1));

        // Ana menü düğmeleri
        JButton addCustomerButton = new JButton("Add Customer");
        JButton addDeliveryButton = new JButton("Add Delivery");
        JButton viewCustomerButton = new JButton("View Customer");
        JButton viewRecentDeliveriesButton = new JButton("View Recent Deliveries");
        JButton prioritizeDeliveriesButton = new JButton("Prioritize Deliveries");
        JButton queryDeliveryStatusButton = new JButton("Query Delivery Status");
        JButton showDeliveryRoutesButton = new JButton("Show Delivery Routes");
        JButton exitButton = new JButton("Exit");

        // Düğmeleri ana pencereye ekle
        frame.add(addCustomerButton);
        frame.add(addDeliveryButton);
        frame.add(viewCustomerButton);
        frame.add(viewRecentDeliveriesButton);
        frame.add(prioritizeDeliveriesButton);
        frame.add(queryDeliveryStatusButton);
        frame.add(showDeliveryRoutesButton);
        frame.add(exitButton);

        // Müşteri ekleme işlemi
        addCustomerButton.addActionListener(e -> showAddCustomerDialog());

        // Teslimat ekleme işlemi
        addDeliveryButton.addActionListener(e -> showAddDeliveryDialog());

        // Müşteri bilgilerini görüntüleme
        viewCustomerButton.addActionListener(e -> showViewCustomerDialog());

        // Son 5 gönderiyi görüntüleme
        viewRecentDeliveriesButton.addActionListener(e -> showRecentDeliveriesDialog());

        // Teslimatları önceliklendirme işlemi
        prioritizeDeliveriesButton.addActionListener(e -> prioritizeDeliveries());

        // Kargo durum sorgulama işlemi
        queryDeliveryStatusButton.addActionListener(e -> queryDeliveryStatus());

        // Teslimat rotalarını gösterme işlemi
        showDeliveryRoutesButton.addActionListener(e -> showDeliveryRoutes());

        // Çıkış
        exitButton.addActionListener(e -> System.exit(0));

        frame.setVisible(true);
    }

    // Müşteri ekleme
    private void showAddCustomerDialog() {
        String customerId = JOptionPane.showInputDialog(frame, "Enter Customer ID:");
        String firstName = JOptionPane.showInputDialog(frame, "Enter First Name:");
        String lastName = JOptionPane.showInputDialog(frame, "Enter Last Name:");

        if (customerId != null && firstName != null && lastName != null) {
            customerManager.addCustomer(customerId, firstName, lastName);
            JOptionPane.showMessageDialog(frame, "Customer added successfully!");
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid input. Please try again.");
        }
    }

    // Teslimat ekleme
    private void showAddDeliveryDialog() {
        String customerId = JOptionPane.showInputDialog(frame, "Enter Customer ID:");
        String deliveryId = JOptionPane.showInputDialog(frame, "Enter Delivery ID:");
        String deliveryDate = JOptionPane.showInputDialog(frame, "Enter Delivery Date (YYYY-MM-DD):");
        String status = JOptionPane.showInputDialog(frame, "Enter Delivery Status (Delivered/Pending):");
        String durationStr = JOptionPane.showInputDialog(frame, "Enter Delivery Duration (in days):");

        try {
            int duration = Integer.parseInt(durationStr);
            customerManager.addDeliveryToCustomer(customerId, deliveryId, deliveryDate, status, duration);
            JOptionPane.showMessageDialog(frame, "Delivery added successfully!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid duration. Please try again.");
        }
    }

    // Müşteri bilgilerini görüntüleme
    private void showViewCustomerDialog() {
        String customerId = JOptionPane.showInputDialog(frame, "Enter Customer ID:");
        if (customerId != null) {
            Customer customer = customerManager.customers.get(customerId);

            if (customer != null) {
                StringBuilder customerInfo = new StringBuilder();
                customerInfo.append("Customer Details:\n");
                customerInfo.append("ID: ").append(customer.getCustomerId()).append("\n");
                customerInfo.append("Name: ").append(customer.getFirstName()).append(" ").append(customer.getLastName()).append("\n");

                // Teslimat geçmişini ekleyelim
                customerInfo.append("\nDelivery History:\n");
                if (customer.getDeliveryHistory().getDeliveries().isEmpty()) {
                    customerInfo.append("No deliveries found.\n");
                } else {
                    for (Delivery delivery : customer.getDeliveryHistory().getDeliveries()) {
                        customerInfo.append(delivery).append("\n");
                    }
                }

                JOptionPane.showMessageDialog(frame, customerInfo.toString(), "Customer Details", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Customer with ID " + customerId + " not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Son 5 gönderiyi görüntüleme
    private void showRecentDeliveriesDialog() {
        String customerId = JOptionPane.showInputDialog(frame, "Enter Customer ID:");
        if (customerId != null) {
            Customer customer = customerManager.customers.get(customerId);

            if (customer != null) {
                StringBuilder recentDeliveriesInfo = new StringBuilder();
                recentDeliveriesInfo.append("Recent Deliveries for Customer ").append(customerId).append(":\n");

                // Son 5 gönderiyi ekle
                Stack<Delivery> recentDeliveries = customer.getDeliveryHistory().getRecentDeliveries();
                if (recentDeliveries.isEmpty()) {
                    recentDeliveriesInfo.append("No recent deliveries found.\n");
                } else {
                    for (Delivery delivery : recentDeliveries) {
                        recentDeliveriesInfo.append(delivery).append("\n");
                    }
                }

                JOptionPane.showMessageDialog(frame, recentDeliveriesInfo.toString(), "Recent Deliveries", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Customer with ID " + customerId + " not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Teslimatları önceliklendirme (Delivery Duration)
    private void prioritizeDeliveries() {
        List<Delivery> allDeliveries = new ArrayList<>();

        // Tüm kargoları topla
        for (Customer customer : customerManager.customers.values()) {
            allDeliveries.addAll(customer.getDeliveryHistory().getDeliveries());
        }

        // Teslim süresine göre sıralama
        allDeliveries.sort(Comparator.comparingInt(Delivery::getDeliveryDuration));

        // Sıralanmış teslimatları birleştir
        StringBuilder prioritizedDeliveries = new StringBuilder("Prioritized Deliveries (by Delivery Duration):\n");
        for (Delivery delivery : allDeliveries) {
            prioritizedDeliveries.append(delivery).append("\n");
        }

        // GUI'de göster
        JOptionPane.showMessageDialog(frame, prioritizedDeliveries.toString(), "Prioritized Deliveries", JOptionPane.INFORMATION_MESSAGE);
    }

    // Kargo durum sorgulama
    private void queryDeliveryStatus() {
        StringBuilder delivered = new StringBuilder("Delivered Deliveries:\n");
        StringBuilder undelivered = new StringBuilder("Undelivered Deliveries:\n");

        for (Customer customer : customerManager.customers.values()) {
            for (Delivery delivery : customer.getDeliveryHistory().getDeliveries()) {
                if (delivery.getStatus().equalsIgnoreCase("Delivered")) {
                    delivered.append(delivery).append("\n");
                }
            }

            List<Delivery> sortedUndelivered = customer.getDeliveryHistory().sortUnDeliveredByDuration();
            for (Delivery delivery : sortedUndelivered) {
                undelivered.append(delivery).append("\n");
            }
        }

        JOptionPane.showMessageDialog(frame, delivered.toString(), "Delivered Deliveries", JOptionPane.INFORMATION_MESSAGE);
        JOptionPane.showMessageDialog(frame, undelivered.toString(), "Undelivered Deliveries", JOptionPane.INFORMATION_MESSAGE);
    }

    // Teslimat rotalarını gösterme
    private void showDeliveryRoutes() {
        System.out.println("Delivery Routes:");
        deliveryTree.printTree(deliveryTree.getRoot(), "");
        int depth = deliveryTree.calculateDepth(deliveryTree.getRoot());
        JOptionPane.showMessageDialog(frame, "Tree Depth: " + depth, "Delivery Tree", JOptionPane.INFORMATION_MESSAGE);
    }

    // Teslimat ağacını başlatma
    private DeliveryTree initializeDeliveryTree() {
        DeliveryTree tree = new DeliveryTree("Istanbul", "IST");

        CityNode root = tree.getRoot();
        CityNode ankara = new CityNode("Ankara", "ANK");
        CityNode izmir = new CityNode("Izmir", "IZM");
        CityNode bursa = new CityNode("Bursa", "BUR");
        CityNode antalya = new CityNode("Antalya", "ANT");
        CityNode trabzon = new CityNode("Trabzon", "TRB");
        CityNode diyarbakir = new CityNode("Diyarbakir", "DIY");
        CityNode kayseri = new CityNode("Kayseri", "KAY");
        CityNode erzurum = new CityNode("Erzurum", "ERZ");
        CityNode adana = new CityNode("Adana", "ADA");
        CityNode eskisehir = new CityNode("Eskisehir", "ESK");

        root.addChild(ankara);
        root.addChild(izmir);
        root.addChild(bursa);
        root.addChild(antalya);

        ankara.addChild(kayseri);
        ankara.addChild(eskisehir);

        izmir.addChild(adana);

        antalya.addChild(diyarbakir);
        antalya.addChild(trabzon);

        diyarbakir.addChild(erzurum);

        return tree;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DeliveryAppGUI::new);
    }
}
