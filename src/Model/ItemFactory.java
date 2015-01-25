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
		itemMap.put(ItemType.FIRESTICK, new Item("Stick on fire", "firestick"));
		itemMap.put(ItemType.MEAT, new Item("Meat", "meat"));
		itemMap.put(ItemType.ARTIFACT, new Item("Artifact", "artifact"));
		itemMap.put(ItemType.BERRIES, new Item("Berries", "berries"));
		itemMap.put(ItemType.AXE, new Item("Axe", "axe"));
		itemMap.put(ItemType.WEB, new Item("Web", "web"));
		itemMap.put(ItemType.SAIL, new Item("Sail", "sail"));
		itemMap.put(ItemType.OIL, new Item("Oil", "oil"));
		itemMap.put(ItemType.WATER, new Item("Water", "water"));
		itemMap.put(ItemType.METAL, new Item("Metal", "metal"));
		itemMap.put(ItemType.FISH, new Item("Fish", "fish"));
		itemMap.put(ItemType.FLIGHT, new Item("Flight", "flight"));
	}
	
	public static Item createItem(ItemType itemType) {
		return itemMap.get(itemType);
	}
}
