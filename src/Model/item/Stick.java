package Model.item;

import Model.Item;
import Model.ItemType;

public class Stick extends Item {

	@Override
	public String getImageName() {
		return "branch";
	}

	@Override
	public String getItemName() {
		return "Branch";
	}

	@Override
	public ItemType getType() {
		return ItemType.STICK;
	}
}
