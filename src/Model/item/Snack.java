package Model.item;

import Model.Item;
import Model.ItemType;

public class Snack extends Item {

	@Override
	public String getImageName() {
		return "cake";
	}

	@Override
	public String getItemName() {
		return "A snack";
	}

	@Override
	public ItemType getType() {
		return ItemType.SNACK;
	}
}
