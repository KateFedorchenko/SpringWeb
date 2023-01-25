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
    private PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("get-shopping-cart")
    public List<ItemDTO> getItemListByBuyer(@RequestBody String buyerName) {
        return purchaseService.getItemListByBuyer(buyerName);
    }

    @PutMapping("add-buyer")
    public String addBuyer(@RequestBody String buyerName) {
        return purchaseService.addBuyer(buyerName);
    }

    @PutMapping("add-item")
    public String addItem(@RequestBody ItemDTO itemDTO) {
        return purchaseService.addItem(itemDTO);
    }

    @DeleteMapping("remove-item")
    public void removeItemFromList(@RequestParam String itemName, @RequestParam String buyerName) {
        purchaseService.removeItem(itemName, buyerName);
    }

    @GetMapping("get-all-shopping-cart")
    public Map<String, List<ItemDTO>> loadPurchaseList() {
        return purchaseService.loadPurchaseList();
    }

}
