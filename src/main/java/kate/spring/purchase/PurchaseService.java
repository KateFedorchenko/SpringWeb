package kate.spring.purchase;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import kate.spring.conversion.ItemDTOtoEntityConverter;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

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
        itemRepository.save(item);

        return "New item added";
    }

    public void removeItem(String itemName, String buyerName) {
        buyerRepository.findBuyerByName(buyerName).orElseThrow(() -> new RuntimeException("No such buyer found"));

        Item item = itemRepository.findByItemNameAndBuyerName(itemName, buyerName)
                .orElseThrow(() -> new RuntimeException("The item does not exist thus cannot be deleted"));

        itemRepository.remove(item);
    }

    public List<ItemDTO> getItemListByBuyer(String buyerName) {
//        if (!buyerExist(buyerName)) {
//            throw new RuntimeException("No such buyer found");
//        }
//
//        List<Item> itemsByBuyer = em.createQuery("SELECT i FROM Item i WHERE i.buyer.name = :buyer", Item.class)
//                .setParameter("buyer", buyerName)
//                .getResultList();
//
//        return itemsByBuyer.stream()
//                .map(x -> new ItemDTO(x.getItemName(), x.getQuantity(), x.getPrice(), x.getBuyer().getName()))
//                .toList();
        return null;
    }

    public Map<String, List<ItemDTO>> loadPurchaseList() {
//        List<Buyer> buyerList = em.createQuery("SELECT b FROM Buyer b", Buyer.class)
//                .getResultList();
//
//        Map<String, List<ItemDTO>> newMap = new HashMap<>();
//
//        for (Buyer buyer : buyerList) {
//            List<Item> itemList = em.createQuery("SELECT b.items FROM Buyer b WHERE b.name = :buyerName", Item.class)
//                    .setParameter("buyerName", buyer.getName())
//                    .getResultList();
//
//            List<ItemDTO> itemDTOS = itemList.stream()
//                    .map(x -> new ItemDTO(x.getItemName(), x.getQuantity(), x.getPrice(), x.getBuyer().getName()))
//                    .toList();
//
//            newMap.put(buyer.getName(), itemDTOS);
//        }
//        return newMap;
        return null;
    }

}
