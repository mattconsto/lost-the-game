package Model.item;

import Model.Item;
import Model.ItemType;

public class Grass extends Item {

	@Override
	public String getImageName() {
		return "grass";
	}

	@Override
	public String getItemName() {
		return "Clump of grass";
	}
	
	@Override
	public ItemType getType() {
		return ItemType.GRASS;
	}

}
