package Model.item;

import Model.Item;
import Model.ItemType;

public class Mud extends Item {

	@Override
	public String getImageName() {
		return "mud";
	}

	@Override
	public String getItemName() {
		return "Mud";
	}

	@Override
	public ItemType getType() {
		return ItemType.MUD;
	}
}
