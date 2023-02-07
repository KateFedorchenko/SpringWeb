package kate.spring.purchase;

import java.util.List;
import java.util.Optional;

public interface BuyerRepository {
    void save(Buyer buyer);

    Optional<Buyer> findBuyerByName(String buyerName);

    List<Buyer> findAllBuyers();
}
