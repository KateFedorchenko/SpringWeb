package kate.spring.purchase;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Component
public class BuyerRepositoryImpl extends AbstractRepository implements BuyerRepository {

    @Override
    public void save(Buyer buyer) {
        doWithTransaction(em -> em.persist(buyer));
    }

    @Override
    public Optional<Buyer> findBuyerByName(String buyerName) {
        Long existBuyer = em.createQuery("SELECT COUNT(1) FROM Buyer b WHERE b.name = :buyerName", Long.class)
                .setParameter("buyerName", buyerName)
                .getSingleResult();
        if (existBuyer != 0) {
            Buyer buyer = em.createQuery("SELECT b FROM Buyer b WHERE b.name = :buyerName", Buyer.class)
                    .setParameter("buyerName", buyerName)
                    .getSingleResult();
            return Optional.of(buyer);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Buyer> findAllBuyers() {
        Long anyBuyerExist = em.createQuery("SELECT COUNT(1) FROM Buyer b", Long.class)
                .getSingleResult();

        if (anyBuyerExist != 0) {
            List<Buyer> buyerList = em.createQuery("SELECT b FROM Buyer b", Buyer.class)
                    .getResultList();

            return buyerList;
        } else {
            return Collections.emptyList();
        }
    }
}
