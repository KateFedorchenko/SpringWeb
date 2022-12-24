package kate.spring.purchase;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ShoppingCart {
    private Map<Buyer, List<Item>> map = new ConcurrentHashMap<>();

    public Map<Buyer, List<Item>> getMap() {
        return map;
    }
}
