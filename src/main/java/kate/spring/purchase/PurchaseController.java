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
    public void addItem(@RequestBody ItemDTO itemDTO, @RequestParam String buyerName) {
        purchaseService.addItem(buyerName,itemDTO);
    }

    @DeleteMapping("remove-item")
    public void removeItemFromList(@RequestBody ItemDTO itemDTO, @RequestParam String buyerName) {
        purchaseService.removeItem(buyerName,itemDTO);
    }

    @GetMapping("get-all-shopping-cart")
    public Map<String, List<ItemDTO>> loadPurchaseList() {
        return purchaseService.loadPurchaseList();
    }

}
