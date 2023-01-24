package kate.spring.purchase;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

@Service
public class PurchaseService {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    private EntityManager em = emf.createEntityManager();
    private Map<String, List<ItemDTO>> shoppingCart = new ConcurrentHashMap<>();

    public List<ItemDTO> getItemListByBuyer(String buyerName) {
        doWithTransaction(em -> {
            if (!buyerExist(buyerName)) {
                throw new RuntimeException("No such buyer found");
            }
            if (!buyerHasItems(buyerName)) {
                throw new RuntimeException("This buyer has no items");
            }
//            List itemsByBuyer = em.createQuery("SELECT * FROM Item WHERE buyer = :buyer", List.class)
//                    .setParameter("buyer", buyerName)
//                    .getSingleResult();
        });

        return shoppingCart.get(buyerName);
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

    public String addItem(Item item) {
        doWithTransaction(em -> {
            if (!buyerExist(item.getBuyer().getName())) {
                throw new RuntimeException("Cannot add item as no such buyer exists");
            }
            if (buyerHasItems(item.getBuyer().getName())) {
                Integer newQuantity = oldItemQuantityByBuyer(item) + item.getQuantity();
                BigDecimal newPrice = (oldItemPriceByBuyer(item).add(item.getPrice())).divide(BigDecimal.valueOf(newQuantity));

//                em.createQuery("UPDATE Item SET quantity = :quantity, price = :price")
//                        .setParameter("quantity",newQuantity)
//                        .setParameter("price",newPrice);

                /* ?????? */
//                Long idItem = em.createQuery("SELECT id FROM Item WHERE buyer_id = :buyer_id and name = :name", Long.class)
//                        .setParameter("buyer_id", item.getBuyer().getId())
//                        .setParameter("name", item.getItemName())
//                        .getSingleResult();
//
//                Item itemByBuyerDelete = em.find(Item.class, idItem);
//                em.remove(itemByBuyerDelete);
//                em.persist(new Item(item.getItemName(),newQuantity,newPrice,item.getBuyer()));
            } else {
                em.persist(new Item(item.getItemName(), item.getQuantity(), item.getPrice(), item.getBuyer()));
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

    public void removeItem(String buyerName, Item item) {
        doWithTransaction(em -> {
            if (!buyerExist(item.getBuyer().getName())) {
                throw new RuntimeException("No such buyer found");
            }
            if (!buyerHasItems(item.getBuyer().getName())) {
                throw new RuntimeException("This buyer has no items");
            }


//            List<ItemDTO> itemList = new CopyOnWriteArrayList<>(shoppingCart.get(buyerName));
//            int indexOfItem = getIndexOfItem(itemList, itemDTO.getItemName());
//            if (indexOfItem != -1) {
//                ItemDTO currItem = itemList.get(indexOfItem);
//
//                if (itemDTO.getQuantity() > currItem.getQuantity()) {
//                    throw new RuntimeException("Failed! The inserted quantity is more than the item has now.");
//                } else if (itemDTO.getQuantity() == currItem.getQuantity()) {
//                    itemList.remove(currItem);
//                } else {
//                    int newQuantity = currItem.getQuantity() - itemDTO.getQuantity();
//                    BigDecimal newPrice = currItem.getPrice().divide(BigDecimal.valueOf(newQuantity));
//                    itemList.get(indexOfItem).setQuantity(newQuantity);
//                    itemList.get(indexOfItem).setPrice(newPrice);
//                }
//            } else {
//                throw new RuntimeException("Failed! No such item was added to the Cart earlier.");
//            }
//            shoppingCart.put(buyerName, itemList);
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

    private boolean buyerHasItems(String buyerName) {
        Long countItemsByBuyer = em.createQuery("SELECT COUNT(*) FROM Item i WHERE i.buyer = :buyer", Long.class)
                .setParameter("buyer", buyerName)
                .getSingleResult();
        return countItemsByBuyer != 0;
    }

    private Integer oldItemQuantityByBuyer(Item item) {
        return em.createQuery("SELECT i.quantity FROM Item i WHERE i.name = :name and i.buyer = :buyer", Integer.class)
                .setParameter("name", item.getItemName())
                .setParameter("buyer", item.getBuyer())
                .getSingleResult();
    }

    private BigDecimal oldItemPriceByBuyer(Item item) {
        return em.createQuery("SELECT i.price FROM Item i WHERE i.name = :name and i.buyer = :buyer", BigDecimal.class)
                .setParameter("name", item.getItemName())
                .setParameter("buyer", item.getBuyer())
                .getSingleResult();
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


// continue doing like this
