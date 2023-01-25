package kate.spring.purchase;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue
    private Long id;
    private String itemName;
    private int quantity;
    private BigDecimal price;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "buyer_id", nullable = false)
    private Buyer buyer;

    public Item(String itemName, int quantity, BigDecimal price, Buyer buyer) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
        this.buyer = buyer;
    }
}
