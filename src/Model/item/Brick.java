package Model.item;

import Model.Item;
import Model.ItemType;

public class Brick extends Item {

	@Override
	public String getImageName() {
		return "brick";
	}

	@Override
	public String getItemName() {
		return "Brick";
	}

	@Override
	public ItemType getType() {
		return ItemType.BRICK;
	}

}
