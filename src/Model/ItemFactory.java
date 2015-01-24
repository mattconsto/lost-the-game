package Model;

import Model.item.Brick;
import Model.item.Cloth;
import Model.item.Grass;
import Model.item.Leaf;
import Model.item.LifeJacket;
import Model.item.Mud;
import Model.item.Plank;
import Model.item.Rock;
import Model.item.Snack;
import Model.item.Spear;
import Model.item.Stick;
import Model.item.Vine;

public class ItemFactory {
	public static Item createItem(ItemType type) {
		switch(type) {
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
		case LIFEJACKET:
			return new LifeJacket();
		case SNACK:
			return new Snack();
		case BRICK:
			return new Brick();
		case SPEAR:
			return new Spear();
		case VINE:
			return new Vine();
		default:
			throw new IllegalArgumentException();
		}
	}
}
