package kate.spring.purchase;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
public class PurchaseController {
    private Map<String, List<ItemDTO>> shoppingCart = new ConcurrentHashMap<>();
    private PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("get-shopping-cart")
    public List<ItemDTO> getItemListByBuyer(@RequestBody String buyerName) {
        if (!shoppingCart.containsKey(buyerName)) {
            throw new RuntimeException("No such buyer found");
        }
        return shoppingCart.get(buyerName);
    }

    @PutMapping("add-buyer")
    public String addBuyer(@RequestBody String buyerName) {
        if (shoppingCart.containsKey(buyerName)) {
            throw new RuntimeException("Such buyer is already in the list");
        }

        shoppingCart.put(buyerName, new ArrayList<>());
        return "New buyer added!";
    }

    @PutMapping("add-item")
    public void addItem(
            @RequestBody ItemDTO itemDTO,
            @RequestParam String buyerName
    ) {
        if (!shoppingCart.containsKey(buyerName)) {
            throw new RuntimeException("Cannot add item as no such buyer exists");
        }
        List<ItemDTO> itemList = new CopyOnWriteArrayList<>(shoppingCart.get(buyerName));
        int indexOfItem = getIndexOfItem(itemList, itemDTO.getItemName());

        if (indexOfItem != -1) {
            ItemDTO curItem = itemList.get(indexOfItem);

            int newQuantity = curItem.getQuantity() + itemDTO.getQuantity();
            BigDecimal newPrice = curItem.getPrice().add(itemDTO.getPrice()).divide(BigDecimal.valueOf(newQuantity));

            itemList.get(indexOfItem).setQuantity(newQuantity);
            itemList.get(indexOfItem).setPrice(newPrice);
        } else {
            itemList.add(itemDTO);
        }
    }

    @DeleteMapping("remove-item")
    public void removeItemFromList(@RequestBody ItemDTO itemDTO, @RequestParam String buyerName) {
        purchaseService.removeItem(buyerName,itemDTO);
    }

    @GetMapping("get-all-shopping-cart")
    public Map<String, List<ItemDTO>> loadPurchaseList() {
        return shoppingCart;
    }

    public int getIndexOfItem(List<ItemDTO> itemList, String itemName) {
        for (ItemDTO item : itemList) {
            if (item.getItemName().equals(itemName)) {
                return itemList.indexOf(item);
            }
        }
        return -1;
    }
}


// 1) Controller only for Service request
// 3)