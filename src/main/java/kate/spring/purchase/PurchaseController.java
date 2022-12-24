package kate.spring.purchase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class PurchaseController {
    private Map<Buyer, List<Item>> shoppingCart = new ConcurrentHashMap<>();

    @GetMapping("get-shopping-cart")
    public List<Item> getItemListByBuyer(@RequestBody String buyerName){
        for (Map.Entry<Buyer, List<Item>> entry : shoppingCart.entrySet()) {
            if (entry.getKey().getName().equals(buyerName)) {
                return entry.getValue();
            }
        }
        throw new RuntimeException("No such buyer found");
    }

    @PutMapping("add-buyer")
    public String addBuyer(@RequestBody String buyerName) {
        for (Buyer buyer : shoppingCart.keySet()) {
            if (buyer.getName().equals(buyerName)) {
                return "Such buyer is already in the list";
            }
        }
        shoppingCart.put(new Buyer(buyerName), new ArrayList<>());
        return "New buyer added!";
    }

    @PutMapping("add-item")
    public void addItem(
            @RequestBody Item item,
            @RequestParam String buyerName
    ) {
        for (Map.Entry<Buyer, List<Item>> entry : shoppingCart.entrySet()) {
            if (entry.getKey().getName().equals(buyerName)) {
                entry.getValue().add(item);
                System.out.println("added");
                return;
            }
        }
        System.out.println("Cannot add item as no such buyer exists");
    }

    @DeleteMapping("remove-item")
    public String removeItemFromList(@RequestParam String itemName, @RequestParam String buyerName) {
        for (Buyer buyer : shoppingCart.keySet()) {
            if (shoppingCart.containsKey(buyer)) {
                List<Item> items = shoppingCart.get(buyer);
                for (Item item : items) {
                    String name = item.getItemName();
                    if (name.equals(itemName)) {
                        items.remove(item);
                        return itemName + " has been removed successfully!";
                    }
                }
                return "Failed. No such item was added to the Cart earlier.";
            }
        }
        return "No such buyer found";
    }

    @GetMapping("get-all-shopping-cart")
    public Map<Buyer,List<Item>> loadPurchaseList(){
        return shoppingCart;
    }
}


// addItem(name, quantity, maxPrice, author), getItemListByAuthor(author), removeItemFromList(name, quantity)
// !!! PUT, POST, GET -> think over
// 3) loadPurchaseList(for all authors!)