public class DeliveryTree {
    private CityNode root; // Ağacın kök düğümü

    // Constructor
    public DeliveryTree(String rootCityName, String rootCityId) {
        this.root = new CityNode(rootCityName, rootCityId);
    }

    // Kök düğümü alma
    public CityNode getRoot() {
        return root;
    }

    // Ağacın derinliğini hesaplama
    public int calculateDepth(CityNode node) {
        if (node.getChildren().isEmpty()) {
            return 1; // Yaprak düğüm
        }
        int maxDepth = 0;
        for (CityNode child : node.getChildren()) {
            maxDepth = Math.max(maxDepth, calculateDepth(child));
        }
        return maxDepth + 1; // Bir üst seviyeyi ekle
    }

    // Ağacı yazdırma
    public void printTree(CityNode node, String indent) {
        System.out.println(indent + node.getCityName() + " (" + node.getCityId() + ")");
        for (CityNode child : node.getChildren()) {
            printTree(child, indent + "  ");
        }
    }
}
