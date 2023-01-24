package kate.spring.jpa;

import jakarta.persistence.*;
import kate.spring.jpa.entity.Department;
import kate.spring.jpa.entity.Employee;
import kate.spring.jpa.entity.Person;

import java.util.List;

public class JPADemo {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        Person person1 = new Person("Bob","Smith");
        Person person2 = new Person("John","Smith");
        Person person3 = new Person("Clara","Smith");
        Person person4 = new Person("Steve","Smith");


        em.getTransaction().begin();
        em.persist(person1);
        em.persist(person2);
        em.persist(person3);
        em.persist(person4);

        em.getTransaction().commit();
        em.refresh(person3);
        System.out.println(person3);

    }

    public void employeesAndDepartment(){
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
        em.getTransaction().commit();

        em.close();
        em = emf.createEntityManager();
        Department department = em.find(Department.class, 1L);

        System.out.println(department);
    }
}

