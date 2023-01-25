package kate.spring.purchase;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ItemDTO {
    private  String itemName;
    private  int quantity;
    private  BigDecimal price;
    private String buyer;

    public ItemDTO(String itemName, int quantity, BigDecimal price, String buyer) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
        this.buyer = buyer;
    }
}
