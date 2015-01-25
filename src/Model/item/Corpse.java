package Model.item;

import Model.Item;
import Model.ItemType;

public class Corpse extends Item {

	@Override
	public String getImageName() {
		return "body";
	}

	@Override
	public String getItemName() {
		return "Corpse";
	}

	@Override
	public ItemType getType() {
		return ItemType.CORPSE;
	}

}
