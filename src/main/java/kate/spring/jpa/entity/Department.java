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

    @OneToMany(mappedBy = "department")
    private List<Employee> employees;

    public Department(String departmentName) {
        this.departmentName = departmentName;
    }
}
