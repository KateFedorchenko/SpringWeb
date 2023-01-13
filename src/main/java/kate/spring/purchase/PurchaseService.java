package kate.spring.purchase;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class PurchaseService {
    private Map<String, List<ItemDTO>> shoppingCart = new ConcurrentHashMap<>();


    public void removeItem(String buyerName, ItemDTO itemDTO){
        if (!shoppingCart.containsKey(buyerName)) {
            throw new RuntimeException("No such buyer found");
        }

        List<ItemDTO> itemList = new CopyOnWriteArrayList<>(shoppingCart.get(buyerName));
        int indexOfItem = getIndexOfItem(itemList, itemDTO.getItemName());
        if (indexOfItem != -1) {
            ItemDTO currItem = itemList.get(indexOfItem);

            if (itemDTO.getQuantity() > currItem.getQuantity()) {
                throw new RuntimeException("Failed! The inserted quantity is more than the item has now.");
            } else if (itemDTO.getQuantity() == currItem.getQuantity()) {
                itemList.remove(currItem);
            }

            int newQuantity = currItem.getQuantity() - itemDTO.getQuantity();
            BigDecimal newPrice = currItem.getPrice().divide(BigDecimal.valueOf(newQuantity));
            itemList.get(indexOfItem).setQuantity(newQuantity);
            itemList.get(indexOfItem).setPrice(newPrice);
        } else {
            throw new RuntimeException("Failed! No such item was added to the Cart earlier.");
        }
    }

    private int getIndexOfItem(List<ItemDTO> itemList, String itemName) {
        for (ItemDTO item : itemList) {
            if (item.getItemName().equals(itemName)) {
                return itemList.indexOf(item);
            }
        }
        return -1;
    }


}
