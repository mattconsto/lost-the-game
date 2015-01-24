package Model.item;

import Model.Item;
import Model.ItemType;

public class LifeJacket extends Item {

	@Override
	public String getImageName() {
		return "lifejacket";
	}

	@Override
	public String getItemName() {
		return "Lifejacket";
	}

	@Override
	public ItemType getType() {
		return ItemType.LIFEJACKET;
	}
}
