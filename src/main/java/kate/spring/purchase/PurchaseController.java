package kate.spring.purchase;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class PurchaseController {
    private Map<Buyer, List<Item>> shoppingCart = new ConcurrentHashMap<>();

    @GetMapping("get-shopping-cart")
    public List<Item> getItemListByBuyer(@RequestParam String buyerName) {
        if (shoppingCart.containsKey(buyerName)) {
            return shoppingCart.get(buyerName);
        } else {
            throw new RuntimeException("No such buyer found");
        }
    }

    @PutMapping("add-buyer")
    public String addBuyer(@RequestParam String buyerName){
        if(!shoppingCart.containsKey(buyerName)){
            shoppingCart.put(new Buyer(buyerName),new ArrayList<>());
            return "New buyer added!";
        } else {
            return "Such buyer is already in the list";
        }
    }

    @PutMapping("add-item")
    public void addItem(
            @RequestParam String itemName,
            @RequestParam Integer quantity,
            @RequestParam BigDecimal price,
            @RequestParam String buyerName
    ) {
        List<Item> items = shoppingCart.get(buyerName);
        items.add(new Item(itemName, quantity, price));
        System.out.println("added");
    }

    @PutMapping("remove-item")
    public String removeItemFromList(@RequestParam String itemName, @RequestParam String buyerName) {
        if (!shoppingCart.containsKey(buyerName)) {
            return "No such buyer found";
        }
        List<Item> items = shoppingCart.get(buyerName);
        if (items.isEmpty()) {
            return "No items found";
        }
        for (Item item : items) {
            String name = item.getItemName();
            if (name.equals(itemName)) {
                items.remove(item);
                return itemName + " has been removed successfully!";
            }
        }
        return "Failed. No such item was added to the Cart earlier.";
    }

    @GetMapping("get-all-shopping-cart")
    public void loadPurchaseList() {
        if(shoppingCart.isEmpty()){
            System.out.println("No buyers are found");
            return ;
        }
        for (Map.Entry<Buyer, List<Item>> entry : shoppingCart.entrySet()) {
            System.out.println(entry);
        }
    }
}


// addItem(name, quantity, maxPrice, author), getItemListByAuthor(author), removeItemFromList(name, quantity)
// !!! PUT, POST, GET -> think over
// 3) loadPurchaseList(for all authors!)