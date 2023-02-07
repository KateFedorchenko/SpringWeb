package kate.spring.purchase;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PurchaseService {
    private BuyerRepository buyerRepository;
    private ItemRepository itemRepository;

    public PurchaseService(BuyerRepository buyerRepository, ItemRepository itemRepository) {
        this.buyerRepository = buyerRepository;
        this.itemRepository = itemRepository;
    }


    public String addBuyer(String buyerName) {
        buyerRepository.findBuyerByName(buyerName).ifPresent(buyer -> {
            throw new RuntimeException("Such buyer is already in the list");
        });
        buyerRepository.save(new Buyer(buyerName));

        return "New buyer added!";
    }

    public String addItem(ItemDTO itemDTO) {
        Buyer buyer = buyerRepository.findBuyerByName(itemDTO.getBuyer())
                .orElseThrow(() -> new RuntimeException("Cannot add item as no such buyer exists"));

        Optional<Item> itemOpt = itemRepository.findByItemNameAndBuyerName(itemDTO.getItemName(), itemDTO.getBuyer());
        Item item;
        if (itemOpt.isPresent()) {
            item = itemOpt.get();
            int newQuantity = item.getQuantity() + itemDTO.getQuantity();

            BigDecimal oldCost = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            BigDecimal addedCost = itemDTO.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity()));
            BigDecimal newPrice = oldCost.add(addedCost).divide(BigDecimal.valueOf(newQuantity), RoundingMode.HALF_UP);

            item.setQuantity(newQuantity);
            item.setPrice(newPrice);
        } else {
            item = new Item(itemDTO.getItemName(), itemDTO.getQuantity(), itemDTO.getPrice(), buyer);
        }
        buyer.addItem(item);
        itemRepository.save(item);

        return "New item added";
    }

    public void removeItem(String itemName, String buyerName) {
        Buyer buyer = buyerRepository.findBuyerByName(buyerName).orElseThrow(() -> new RuntimeException("No such buyer found"));

        Item item = itemRepository.findByItemNameAndBuyerName(itemName, buyerName)
                .orElseThrow(() -> new RuntimeException("The item does not exist thus cannot be deleted"));

        buyer.removeItem(item);
        itemRepository.remove(item);
    }

    public List<ItemDTO> getItemListByBuyer(String buyerName) {
        buyerRepository.findBuyerByName(buyerName).orElseThrow(() -> new RuntimeException("No such buyer found"));

        return itemRepository.findItemsByBuyerName(buyerName).stream()
                .map(x -> new ItemDTO(x.getItemName(), x.getQuantity(), x.getPrice(), x.getBuyer().getName()))
                .toList();
    }

    public Map<String, List<ItemDTO>> loadPurchaseList() {
        List<Buyer> buyers = buyerRepository.findAllBuyers();

        Map<String, List<ItemDTO>> newMap = new HashMap<>();

        for (Buyer buyer : buyers) {
            newMap.put(
                    buyer.getName(),
                    buyer.getItems().stream()
                            .map(x -> new ItemDTO(x.getItemName(), x.getQuantity(), x.getPrice(), x.getBuyer().getName()))
                            .toList()
            );
        }
        return newMap;
    }

}
