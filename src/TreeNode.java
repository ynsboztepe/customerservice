import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    private String cityName;                // Şehir adı
    private String cityId;                  // Şehir ID
    private List<TreeNode> children;        // Alt şehirler (çocuk düğümler)

    // Constructor
    public TreeNode(String cityName, String cityId) {
        this.cityName = cityName;
        this.cityId = cityId;
        this.children = new ArrayList<>();
    }

    // Alt düğüm ekleme
    public void addChild(TreeNode child) {
        this.children.add(child);
    }

    // Getter ve Setter metodları
    public String getCityName() {
        return cityName;
    }

    public String getCityId() {
        return cityId;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    // Düğüm bilgilerini yazdırma
    @Override
    public String toString() {
        return "City: " + cityName + " (ID: " + cityId + ")";
    }
}
