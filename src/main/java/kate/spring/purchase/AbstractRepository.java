package kate.spring.purchase;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.function.Consumer;

public abstract class AbstractRepository {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    EntityManager em = emf.createEntityManager();

    void doWithTransaction(Consumer<EntityManager> code) {
        em.getTransaction().begin();
        code.accept(em);
        em.getTransaction().commit();
    }

}
