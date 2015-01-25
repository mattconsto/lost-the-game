package Model;

import java.util.HashMap;
import java.util.Map;

public class ItemFactory {
	
	private static Map<ItemType,Item> itemMap;
	
	public static void init() {
		itemMap = new HashMap<ItemType,Item>();
		
		itemMap.put(ItemType.STICK, new Item("Branch", "branch"));
		itemMap.put(ItemType.ROCK, new Item("Small rock", "stones"));
		itemMap.put(ItemType.PLANK, new Item("Plank", "plank"));
		itemMap.put(ItemType.LEAF, new Item("Leaf", "leaf"));
		itemMap.put(ItemType.MUD, new Item("Mud", "mud"));
		itemMap.put(ItemType.GRASS, new Item("Clump of grass", "grass"));
		itemMap.put(ItemType.CLOTH, new Item("Cloth", "cloth"));
		itemMap.put(ItemType.LIFEJACKET, new Item("Lifejacket", "lifejacket"));
		itemMap.put(ItemType.SNACK, new Item("A snack", "cake"));
		itemMap.put(ItemType.BRICK, new Item("Brick", "brick"));
		itemMap.put(ItemType.SPEAR, new Item("Spear", "spear"));
		itemMap.put(ItemType.VINE, new Item("Vine", "vine"));
		itemMap.put(ItemType.CORPSE, new Item("Corpse", "body"));
	}
	
	public static Item createItem(ItemType itemType) {
		return itemMap.get(itemType);
	}
}
