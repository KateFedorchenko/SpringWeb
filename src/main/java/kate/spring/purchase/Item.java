package kate.spring.purchase;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class Item {
    private final String itemName;
    private final int quantity;
    private final BigDecimal price;
}
