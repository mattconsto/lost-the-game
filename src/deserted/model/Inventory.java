package deserted.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import deserted.model.item.ItemType;

public class Inventory {

	// Maintains the order of items in the inventory
	private ArrayList<ItemType> itemOrder;
	// Maintains the number of each item
	private Map<ItemType, Integer> itemCounts;
	
	public Inventory() {
		this.itemOrder = new ArrayList<ItemType>();
		this.itemCounts = new HashMap<ItemType, Integer>();
	}
	
	public ArrayList<ItemType> getItems() {
		return itemOrder;
	}

	public void addItem(ItemType itemType, int count) {
		for (int i = 0; i < count; i++) {
			addItem(itemType);
		}
	}

	public void addItem(ItemType itemType) {
		if (!itemCounts.containsKey(itemType)) {
			itemOrder.add(itemType);
			itemCounts.put(itemType, 1);
		} else {
			itemCounts.put(itemType, itemCounts.get(itemType) + 1);
		}
	}

	public void removeItem(ItemType itemType) {
		if (getItemCount(itemType) == 0) {
			return;
		}
		int updatedCount = itemCounts.get(itemType) - 1;
		itemCounts.put(itemType, updatedCount);
		if (updatedCount == 0) {
			for (ItemType currentType : itemOrder) {
				if (currentType == itemType) {
					itemOrder.remove(currentType);
					break;
				}
			}
			itemCounts.remove(itemType);
		}
	}

	public void removeItem(ItemType itemType, int count) {
		if (getItemCount(itemType) >= count) {
			for (int i = 0; i < count; i++) {
				removeItem(itemType);
			}
		}
	}

	public int getItemCount(ItemType itemType) {
		if (!itemCounts.containsKey(itemType)) {
			return 0;
		} else {
			return itemCounts.get(itemType);
		}
	}
	
	public boolean hasItem(ItemType itemType) {
		return getItemCount(itemType) > 0;
	}
}
