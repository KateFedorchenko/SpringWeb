package kate.spring.purchase;

import java.util.Optional;

public interface ItemRepository {
    void save(Item item);
    Optional<Item> findByItemNameAndBuyerName(String itemName, String buyerName);

    void remove(Item item);
}
