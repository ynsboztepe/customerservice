import java.util.PriorityQueue;

public class PriorityQueueDelivery {
    private PriorityQueue<Delivery> deliveryQueue;

    // Constructor
    public PriorityQueueDelivery() {
        // Teslim süresine göre sıralama (küçükten büyüğe)
        this.deliveryQueue = new PriorityQueue<>((d1, d2) -> Integer.compare(d1.getDeliveryDuration(), d2.getDeliveryDuration()));
    }

    // Teslimat ekleme
    public void addDelivery(Delivery delivery) {
        deliveryQueue.offer(delivery);
        System.out.println("Delivery added to priority queue: " + delivery);
    }

    // En öncelikli teslimatı alma ve işleme
    public Delivery processNextDelivery() {
        if (deliveryQueue.isEmpty()) {
            System.out.println("No deliveries to process.");
            return null;
        }
        Delivery nextDelivery = deliveryQueue.poll();
        System.out.println("Processing delivery: " + nextDelivery);
        return nextDelivery;
    }

    // Tüm teslimatları listeleme
    public void listAllDeliveries() {
        if (deliveryQueue.isEmpty()) {
            System.out.println("No deliveries in the queue.");
        } else {
            System.out.println("All deliveries in the priority queue:");
            for (Delivery delivery : deliveryQueue) {
                System.out.println(delivery);
            }
        }
    }

    // Teslimat sayısını alma
    public int getDeliveryCount() {
        return deliveryQueue.size();
    }
}
