package Model.item;

import Model.Item;
import Model.ItemType;

public class Cloth extends Item {

	@Override
	public String getImageName() {
		return "cloth";
	}

	@Override
	public String getItemName() {
		return "Cloth";
	}

	@Override
	public ItemType getType() {
		return ItemType.CLOTH;
	}

}
