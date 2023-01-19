package kate.spring.jpa;

import jakarta.persistence.*;
import kate.spring.jpa.entity.Department;
import kate.spring.jpa.entity.Employee;
import kate.spring.jpa.entity.Person;

public class JPADemo {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        Department office = new Department("office");
        Employee employee = new Employee("Jenny", office);
        Employee employee2 = new Employee("Bob", office);
        Employee employee3 = new Employee("John", office);
        em.getTransaction().begin();
        em.persist(office);
        em.persist(employee);
        em.persist(employee2);
        em.persist(employee3);
        em.flush();
        em.getTransaction().commit();

        Department department = em.find(Department.class, 1L);


        System.out.println(department);

    }
}

// JQL
