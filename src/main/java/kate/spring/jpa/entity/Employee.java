package kate.spring.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Data // (noargs+setter+getter+equalsHashcode)
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="sotrudniki")
public class Employee {
    @Id
    @GeneratedValue
    private Long id;
    private String employeeName;
    @ManyToOne
    private Department department;

    public Employee(String employeeName, Department department) {
        this.employeeName = employeeName;
        this.department = department;
    }
}
