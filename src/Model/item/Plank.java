package Model.item;

import Model.Item;
import Model.ItemType;

public class Plank extends Item {

	@Override
	public String getImageName() {
		return "plank";
	}

	@Override
	public String getItemName() {
		return "Plank";
	}

	@Override
	public ItemType getType() {
		return ItemType.PLANK;
	}
}
