package deserted.model.item;

import java.util.HashMap;
import java.util.Map;

public class ItemFactory {
	
	private static Map<ItemType,Item> itemMap;
	
	public static void init() {
		itemMap = new HashMap<ItemType,Item>();

		itemMap.put(ItemType.SNACK, new EdibleItem("A snack", "cake", 0, 30));
		itemMap.put(ItemType.CORPSE, new EdibleItem("Corpse", "body", 0, 20));
		itemMap.put(ItemType.BERRIES, new EdibleItem("Berries", "berries", 0, 15));
		itemMap.put(ItemType.FISH, new EdibleItem("Fish", "fish", 0, 30));
		itemMap.put(ItemType.MEAT, new EdibleItem("Meat", "meat", 0, 40));
		itemMap.put(ItemType.WATER, new EdibleItem("Water", "water", 20, 0));
		itemMap.put(ItemType.SEAWATER, new EdibleItem("Seawater", -20, 0));
		
		itemMap.put(ItemType.STICK, new Item("Branch", "branch"));
		itemMap.put(ItemType.ROCK, new Item("Small rock", "stones"));
		itemMap.put(ItemType.PLANK, new Item("Plank", "plank"));
		itemMap.put(ItemType.LEAF, new Item("Leaf", "leaf"));
		itemMap.put(ItemType.MUD, new Item("Mud", "mud"));
		itemMap.put(ItemType.GRASS, new Item("Clump of grass", "grass"));
		itemMap.put(ItemType.CLOTH, new Item("Cloth", "cloth"));
		itemMap.put(ItemType.LIFEJACKET, new Item("Lifejacket", "lifejacket"));
		itemMap.put(ItemType.BRICK, new Item("Brick", "brick"));
		itemMap.put(ItemType.SPEAR, new Item("Spear", "spear"));
		itemMap.put(ItemType.VINE, new Item("Vine", "vine"));
		itemMap.put(ItemType.FIRESTICK, new Item("Stick on fire", "firestick"));
		itemMap.put(ItemType.ARTIFACT, new Item("Artifact", "artifact"));
		itemMap.put(ItemType.AXE, new Item("Axe", "axe"));
		itemMap.put(ItemType.WEB, new Item("Web", "web"));
		itemMap.put(ItemType.SAIL, new Item("Sail", "sail"));
		itemMap.put(ItemType.OIL, new Item("Oil", "oil"));
		itemMap.put(ItemType.METAL, new Item("Metal", "metal"));
		itemMap.put(ItemType.FLIGHT, new Item("Flight", "flight"));
		itemMap.put(ItemType.RAFT, new Item("Raft"));
		itemMap.put(ItemType.BOAT, new Item("Boat"));
		itemMap.put(ItemType.PLANE, new Item("Plane"));
		itemMap.put(ItemType.SPACESHIP, new Item("Spaceship"));
	}
	
	public static Item createItem(ItemType itemType) {
		return itemMap.get(itemType);
	}
}
