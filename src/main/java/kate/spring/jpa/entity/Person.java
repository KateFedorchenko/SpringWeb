package kate.spring.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Person {
    @Id
    @GeneratedValue
    private Long id;    // mandatory + all anno above
    private String firstName;
    private String lastName;
}
