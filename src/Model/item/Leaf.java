package Model.item;

import Model.Item;
import Model.ItemType;

public class Leaf extends Item {

	@Override
	public String getImageName() {
		return "leaf";
	}

	@Override
	public String getItemName() {
		return "Leaf";
	}
	
	@Override
	public ItemType getType() {
		return ItemType.LEAF;
	}

}
