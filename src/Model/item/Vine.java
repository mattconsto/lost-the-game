package Model.item;

import Model.Item;
import Model.ItemType;

public class Vine extends Item {

	@Override
	public String getImageName() {
		return "vine";
	}

	@Override
	public String getItemName() {
		return "Vine";
	}

	@Override
	public ItemType getType() {
		return ItemType.VINE;
	}
}
