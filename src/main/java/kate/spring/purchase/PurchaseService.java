package kate.spring.purchase;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import kate.spring.conversion.ItemDTOtoEntityConverter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Service
public class PurchaseService {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    private EntityManager em = emf.createEntityManager();
    private Map<String, List<ItemDTO>> shoppingCart = new ConcurrentHashMap<>();

    public List<ItemDTO> getItemListByBuyer(String buyerName) {
        if (!buyerExist(buyerName)) {
            throw new RuntimeException("No such buyer found");
        }

        List<Item> itemsByBuyer = em.createQuery("SELECT i FROM Item i WHERE i.buyer.name = :buyer", Item.class)
                .setParameter("buyer", buyerName)
                .getResultList();

        return itemsByBuyer.stream()
                .map(x -> new ItemDTO(x.getItemName(), x.getQuantity(), x.getPrice(), x.getBuyer().getName()))
                .toList();
    }

    public String addBuyer(String buyerName) {
        doWithTransaction(em -> {
            if (buyerExist(buyerName)) {
                throw new RuntimeException("Such buyer is already in the list");
            }
            em.persist(new Buyer(buyerName));
        });
        return "New buyer added!";
    }

    private Buyer findBuyerByName(String buyerName) {
        return em.createQuery("SELECT b FROM Buyer b WHERE b.name = :buyerName", Buyer.class)
                .setParameter("buyerName", buyerName)
                .getSingleResult();
    }

    public String addItem(ItemDTO itemDTO) {
        if (!buyerExist(itemDTO.getBuyer())) {
            throw new RuntimeException("Cannot add item as no such buyer exists");
        }
        Item item = getItemIfExist(itemDTO.getBuyer(), itemDTO.getItemName());
        Buyer buyer = findBuyerByName(itemDTO.getBuyer());
        doWithTransaction(em -> {
            if (item == null) {
                em.persist(new Item(itemDTO.getItemName(), itemDTO.getQuantity(), itemDTO.getPrice(), buyer));
            } else {
                int newQuantity = item.getQuantity() + itemDTO.getQuantity();

                BigDecimal oldCost = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                BigDecimal addedCost = itemDTO.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity()));
                BigDecimal newPrice = oldCost.add(addedCost).divide(BigDecimal.valueOf(newQuantity), RoundingMode.HALF_UP);

                item.setQuantity(newQuantity);
                item.setPrice(newPrice);

                em.persist(item);
            }
        });
        return "New item added";

//
//        List<ItemDTO> itemList = new CopyOnWriteArrayList<>(shoppingCart.get(buyerName));
//        int indexOfItem = getIndexOfItem(itemList, itemDTO.getItemName());
//
//        if (indexOfItem != -1) {
//            ItemDTO curItem = itemList.get(indexOfItem);
//
//            int newQuantity = curItem.getQuantity() + itemDTO.getQuantity();
//            BigDecimal newPrice = curItem.getPrice().add(itemDTO.getPrice()).divide(BigDecimal.valueOf(newQuantity));
//
//            itemList.get(indexOfItem).setQuantity(newQuantity);
//            itemList.get(indexOfItem).setPrice(newPrice);
//        } else {
//            itemList.add(itemDTO);
//        }
//        shoppingCart.put(buyerName, itemList);
    }

    public void removeItem(String itemName, String buyerName) {
        if (!buyerExist(buyerName)) {
            throw new RuntimeException("No such buyer found");
        }
        Item item = getItemIfExist(buyerName, itemName);

        doWithTransaction(em -> {
            if (item == null) {
                throw new RuntimeException("The item does not exist thus cannot be deleted");
            } else {
                em.remove(item);
            }
        });
    }

    public Map<String, List<ItemDTO>> loadPurchaseList() {
        return shoppingCart;
    }

    private void doWithTransaction(Consumer<EntityManager> code) {
        em.getTransaction().begin();
        code.accept(em);
        em.getTransaction().commit();
    }

    private boolean buyerExist(String buyerName) {
        Long singleResult = em.createQuery("SELECT COUNT(1) FROM Buyer b WHERE b.name = :name", Long.class)
                .setParameter("name", buyerName)
                .getSingleResult();

        return singleResult == 1;
    }

    // TODO
    private Item getItemIfExist(String buyerName, String itemName) {
        Long existItem = em.createQuery("SELECT COUNT(1) FROM Item i WHERE i.buyer.name = :buyerName and i.itemName = :itemName", Long.class)
                .setParameter("buyerName", buyerName)
                .setParameter("itemName", itemName)
                .getSingleResult();
        if (existItem != 0) {
            Item item = em.createQuery("SELECT i FROM Item i WHERE i.buyer.name = :buyerName and i.itemName = :itemName", Item.class)
                    .setParameter("buyerName", buyerName)
                    .setParameter("itemName", itemName)
                    .getSingleResult();
            return item;
        } else {
            return null;
        }
    }
}


// continue doing like this
