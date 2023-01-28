package kate.spring.purchase;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ItemRepositoryImpl extends AbstractRepository implements ItemRepository {
    @Override
    public void save(Item item) {
        doWithTransaction(em -> em.persist(item));
    }

    @Override
    public Optional<Item> findByItemNameAndBuyerName(String itemName, String buyerName) {
        Long existItem = em.createQuery("SELECT COUNT(1) FROM Item i WHERE i.buyer.name = :buyerName and i.itemName = :itemName", Long.class)
                .setParameter("buyerName", buyerName)
                .setParameter("itemName", itemName)
                .getSingleResult();
        if (existItem != 0) {
            Item item = em.createQuery("SELECT i FROM Item i WHERE i.buyer.name = :buyerName and i.itemName = :itemName", Item.class)
                    .setParameter("buyerName", buyerName)
                    .setParameter("itemName", itemName)
                    .getSingleResult();
            return Optional.of(item);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void remove(Item item) {
        doWithTransaction(em -> em.remove(item));
    }
}
