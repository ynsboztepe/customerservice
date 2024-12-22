import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class DeliveryHistory {
    private LinkedList<Delivery> deliveries; // Tüm gönderimlerin listesi
    private Stack<Delivery> recentDeliveries; // Son 5 gönderim

    // Constructor
    public DeliveryHistory() {
        this.deliveries = new LinkedList<>();
        this.recentDeliveries = new Stack<>();
    }

    // Gönderi ekleme (tarihe göre sıralı)
    public void addDelivery(Delivery delivery) {
        int index = 0;
        for (Delivery d : deliveries) {
            if (delivery.getDeliveryDate().compareTo(d.getDeliveryDate()) <= 0) {
                break;
            }
            index++;
        }
        deliveries.add(index, delivery);

        // Son gönderimler stack'e ekleniyor
        if (recentDeliveries.size() == 5) {
            recentDeliveries.remove(0); // Stack doluysa en eski gönderi çıkarılır
        }
        recentDeliveries.push(delivery);
    }

    // Gönderim geçmişini yazdırma
    public void printDeliveries() {
        if (deliveries.isEmpty()) {
            System.out.println("No deliveries found.");
        } else {
            System.out.println("All Deliveries:");
            for (Delivery delivery : deliveries) {
                System.out.println(delivery);
            }
        }
    }

    // Son 5 gönderiyi yazdırma
    public void printRecentDeliveries() {
        if (recentDeliveries.isEmpty()) {
            System.out.println("No recent deliveries found.");
        } else {
            System.out.println("Last 5 Deliveries:");
            for (Delivery delivery : recentDeliveries) {
                System.out.println(delivery);
            }
        }
    }

    // Teslim edilmemiş kargoları teslim süresine göre sıralama (merge sort)
    public List<Delivery> sortUnDeliveredByDuration() {
        ArrayList<Delivery> unDelivered = new ArrayList<>();

        for (Delivery delivery : deliveries) {
            if (!delivery.getStatus().equalsIgnoreCase("Delivered")) {
                unDelivered.add(delivery);
            }
        }

        return mergeSort(unDelivered);
    }

    private List<Delivery> mergeSort(List<Delivery> list) {
        if (list.size() <= 1) {
            return list;
        }

        int mid = list.size() / 2;
        List<Delivery> left = mergeSort(list.subList(0, mid));
        List<Delivery> right = mergeSort(list.subList(mid, list.size()));

        return merge(left, right);
    }

    private List<Delivery> merge(List<Delivery> left, List<Delivery> right) {
        List<Delivery> merged = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            if (left.get(i).getDeliveryDuration() <= right.get(j).getDeliveryDuration()) {
                merged.add(left.get(i));
                i++;
            } else {
                merged.add(right.get(j));
                j++;
            }
        }

        while (i < left.size()) {
            merged.add(left.get(i));
            i++;
        }

        while (j < right.size()) {
            merged.add(right.get(j));
            j++;
        }

        return merged;
    }

    // Teslim edilmiş kargoları binary search ile bulma
    public Delivery binarySearchDeliveryById(String deliveryId) {
        ArrayList<Delivery> sortedList = new ArrayList<>(deliveries);
        sortedList.sort((d1, d2) -> d1.getDeliveryId().compareTo(d2.getDeliveryId())); // ID'ye göre sıralama

        int left = 0, right = sortedList.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            Delivery midDelivery = sortedList.get(mid);

            if (midDelivery.getDeliveryId().equals(deliveryId)) {
                return midDelivery;
            } else if (midDelivery.getDeliveryId().compareTo(deliveryId) < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return null; // Kargo bulunamazsa
    }

    // Tüm gönderimleri alma
    public LinkedList<Delivery> getDeliveries() {
        return deliveries;
    }

    // Son 5 gönderiyi alma
    public Stack<Delivery> getRecentDeliveries() {
        return recentDeliveries;
    }
}
