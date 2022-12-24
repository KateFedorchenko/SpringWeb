package kate.spring.purchase;

import java.math.BigDecimal;

public class Item {
    private String itemName;

    public String getItemName() {
        return itemName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    private Integer quantity;
    private BigDecimal price;

    public Item(String itemName, Integer quantity, BigDecimal price) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }
}
