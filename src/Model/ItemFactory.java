package Model;

import Model.item.Cloth;
import Model.item.Grass;
import Model.item.Leaf;
import Model.item.Mud;
import Model.item.Phone;
import Model.item.Plank;
import Model.item.Rock;
import Model.item.Stick;

public class ItemFactory {
	public static Item createItem(ItemType type) {
		switch(type) {
		case PHONE:
			return new Phone();
		case STICK:
			return new Stick();
		case ROCK:
			return new Rock();
		case PLANK:
			return new Plank();
		case LEAF:
			return new Leaf();
		case MUD:
			return new Mud();
		case GRASS:
			return new Grass();
		case CLOTH:
			return new Cloth();
		default:
			throw new IllegalArgumentException();
		}
	}
}
