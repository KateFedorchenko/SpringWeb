package kate.spring.purchase;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.stereotype.Component;

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
        Buyer buyer = em.createQuery("SELECT b FROM Buyer b WHERE b.name = :buyerName", Buyer.class)
                .setParameter("buyerName", buyerName)
                .getSingleResult();

       return Optional.ofNullable(buyer);
    }


}
