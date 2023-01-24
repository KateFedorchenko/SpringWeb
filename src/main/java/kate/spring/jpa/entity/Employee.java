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
public class Employee {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name="empName",nullable = false)
    private String employeeName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    public Employee(String employeeName, Department department) {
        this.employeeName = employeeName;
        this.department = department;
    }
}
