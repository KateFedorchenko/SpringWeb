package kate.spring.conversion;

import kate.spring.purchase.Item;
import kate.spring.purchase.ItemDTO;

public class ItemDTOtoEntityConverter implements Converter<ItemDTO, Item> {
    @Override
    public Item convert(ItemDTO itemDTO) {
        return new Item(itemDTO.getItemName(), itemDTO.getQuantity(), itemDTO.getPrice(), null);
    }

}
