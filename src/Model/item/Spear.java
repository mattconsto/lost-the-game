package Model.item;

import Model.Item;
import Model.ItemType;

public class Spear extends Item {

	@Override
	public String getImageName() {
		return "spear";
	}

	@Override
	public String getItemName() {
		return "Spear";
	}

	@Override
	public ItemType getType() {
		return ItemType.SPEAR;
	}

}
