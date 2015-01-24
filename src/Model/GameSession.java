package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.LocalDateTime;

public class GameSession {
	// 30 seconds = 1 hour
	// 1 second = 2 minutes
	private static final int MINS_PER_SEC = 2;
	private static final int NUMBER_AGENTS = 2;

	// Play time in seconds
	private double gameTimer;
	// Game time in minutes
	private int timeSurvived;
	// When we 'crashed'
	private LocalDateTime crashDate;

	private ArrayList<Item> items;
	private Map<ItemType, Integer> itemCounts;
	private ArrayList<Agent> agents;

	public GameSession() {
		this.gameTimer = 0;
		this.timeSurvived = 0;
		this.items = new ArrayList<Item>();
		this.itemCounts = new HashMap<ItemType, Integer>();
		this.agents = new ArrayList<Agent>();
		int year = (int) (2010 + (Math.round(Math.random() * 5) - 10));
		int month = (int) Math.ceil(Math.random() * 12);
		int day = (int) Math.ceil(Math.random() * 27);
		int hour = (int) (Math.random() * 24);
		int minute = (int) (Math.random() * 60);
		this.crashDate = new LocalDateTime(year, month, day, hour, minute);

		for (int i = 0; i < NUMBER_AGENTS; i++) {
			getAgents().add(new Agent());
		}
		this.generateInventory();

	}

	public void update(float delta) {
		this.gameTimer += delta;
		this.timeSurvived = (int) Math.floor(gameTimer * MINS_PER_SEC);
	}

	public double getTimeSurvived() {
		return this.timeSurvived;
	}

	public LocalDateTime getDate() {
		return this.crashDate.plusMinutes(this.timeSurvived);
	}

	public ArrayList<Agent> getAgents() {
		return agents;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void addItem(Item item) {
		ItemType type = item.getType();
		if (!itemCounts.containsKey(type)) {
			items.add(item);
			itemCounts.put(type, 1);
		} else {
			itemCounts.put(type, itemCounts.get(type) + 1);
		}
	}

	public void removeItem(Item item) {
		ItemType type = item.getType();
		itemCounts.put(type, itemCounts.get(type) - 1);
		if (itemCounts.get(type) == 0) {
			items.remove(item);
		}
	}

	public int getItemCount(ItemType itemType) {
		if (!itemCounts.containsKey(itemType)) {
			return 0;
		} else {
			return itemCounts.get(itemType);
		}
	}

	private void generateInventory() {
		ItemType[] itemTypes = { ItemType.CLOTH, ItemType.LIFEJACKET,
				ItemType.SNACK };
		for (ItemType itemType : itemTypes) {
			if (Math.random() > 0.8) {
				addItem(ItemFactory.createItem(itemType));
			}
		}
	}
}
