package kate.spring.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Department {
    @Id
    @GeneratedValue
    private Long id;
    private String departmentName;

    @OneToMany
    private List<Employee> employeeList;

    public Department(String departmentName) {
        this.departmentName = departmentName;
    }
}
