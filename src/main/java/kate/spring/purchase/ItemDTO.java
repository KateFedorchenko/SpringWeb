package kate.spring.purchase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ItemDTO {
    private  String itemName;
    private  int quantity;
    private  BigDecimal price;
}
