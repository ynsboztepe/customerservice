public class Delivery {
    private String deliveryId;       // Gönderi ID (benzersiz)
    private String deliveryDate;     // Gönderi tarihi
    private String status;           // Teslim durumu (Teslim Edildi / Teslim Edilmedi)
    private int deliveryDuration;    // Teslim süresi (gün cinsinden)

    // Constructor
    public Delivery(String deliveryId, String deliveryDate, String status, int deliveryDuration) {
        this.deliveryId = deliveryId;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.deliveryDuration = deliveryDuration;
    }

    // Getter ve Setter metodları
    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDeliveryDuration() {
        return deliveryDuration;
    }

    public void setDeliveryDuration(int deliveryDuration) {
        this.deliveryDuration = deliveryDuration;
    }

    // Gönderi bilgilerini yazdırma
    @Override
    public String toString() {
        return "Delivery ID: " + deliveryId +
                ", Date: " + deliveryDate +
                ", Status: " + status +
                ", Duration: " + deliveryDuration + " days";
    }
}
