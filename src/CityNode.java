import java.util.ArrayList;
import java.util.List;

public class CityNode {
    private String cityName; // Şehir adı
    private String cityId;   // Şehir ID'si
    private List<CityNode> children; // Alt düğümler

    // Constructor
    public CityNode(String cityName, String cityId) {
        this.cityName = cityName;
        this.cityId = cityId;
        this.children = new ArrayList<>();
    }

    // Çocuk düğüm ekleme
    public void addChild(CityNode child) {
        children.add(child);
    }

    // Getters
    public String getCityName() {
        return cityName;
    }

    public String getCityId() {
        return cityId;
    }

    public List<CityNode> getChildren() {
        return children;
    }
}
