package deserted.model.item;

public class ItemStack {
	private ItemType itemType;
	private int quantity;
	
	public ItemStack(ItemType itemType, int quantity) {
		this.setItemType(itemType);
		this.setQuantity(quantity);
	}
	

	public ItemStack(ItemType itemType) {
		this(itemType, 1);
	}

	public ItemType getItemType() {
		return itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
