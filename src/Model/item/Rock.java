package Model.item;

import Model.Item;
import Model.ItemType;

public class Rock extends Item {

	@Override
	public String getImageName() {
		return "stones";
	}

	@Override
	public String getItemName() {
		return "Small rock";
	}

	@Override
	public ItemType getType() {
		return ItemType.ROCK;
	}
}
