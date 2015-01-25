package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.LocalDateTime;

public class GameSession {
	// 30 seconds = 1 hour
	// 1 second = 2 minutes
	private static final int MINS_PER_SEC = 2;
	private static final int NUMBER_AGENTS = 10;

	private static final float FOOD_PER_SEC_WALK = 0.5f;
	private static final float FOOD_PER_SEC_STAND = 0.25f;
	private static final float FOOD_PER_SEC_SLEEP = 0.2f;

	private static final float WATER_PER_SEC_WALK = 0.5f;
	private static final float WATER_PER_SEC_STAND = 0.25f;
	private static final float WATER_PER_SEC_SLEEP = 0.2f;

	private static final float HEALTH_PER_SEC = 0.5f;

	private boolean walking = false;
	// Play time in seconds
	private double gameTimer;
	// Game time in minutes
	private int timeSurvived;
	// When we 'crashed'
	private LocalDateTime crashDate;

	private ArrayList<ItemType> items;
	private Map<ItemType, Integer> itemCounts;
	private ArrayList<Agent> agents;

	public GameSession() {
		this.gameTimer = 0;
		this.timeSurvived = 0;
		this.items = new ArrayList<ItemType>();
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
		// Update agent stats
		for (Agent agent : agents) {
			if (agent.getState() == AgentState.WALKING) {
				agent.decFood(FOOD_PER_SEC_WALK * delta);
				agent.decWater(WATER_PER_SEC_WALK * delta);
			} else if (agent.getState() == AgentState.STANDING) {
				agent.decFood(FOOD_PER_SEC_STAND * delta);
				agent.decWater(WATER_PER_SEC_STAND * delta);
			} else if (agent.getState() == AgentState.SLEEPING) {
				if (agent.getHealth() < 90) {
					agent.incHealth(0.1f);
				}
				agent.decFood(FOOD_PER_SEC_SLEEP * delta);
				agent.decWater(WATER_PER_SEC_SLEEP * delta);
			}

			if (agent.getFood() == 0 || agent.getWater() == 0) {
				agent.decHealth(HEALTH_PER_SEC);
			}
			
			if (agent.getHealth() <= 0)			{
				
				if (agent.getState() != AgentState.DEAD) agent.setExpiredTime(this.timeSurvived);
				agent.setState(AgentState.DEAD);
				
			}
		}

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

	public ArrayList<ItemType> getItems() {
		return items;
	}

	public void addItemByType(ItemType itemType) {
		if (!itemCounts.containsKey(itemType)) {
			items.add(itemType);
			itemCounts.put(itemType, 1);
		} else {
			itemCounts.put(itemType, itemCounts.get(itemType) + 1);
		}
	}

	public void addItemByType(ItemType itemType, int count) {
		for (int i = 0; i < count; i++) {
			addItemByType(itemType);
		}
	}

	public void addItem(ItemType itemType) {
		if (!itemCounts.containsKey(itemType)) {
			items.add(itemType);
			itemCounts.put(itemType, 1);
		} else {
			itemCounts.put(itemType, itemCounts.get(itemType) + 1);
		}
	}

	public void removeItemByType(ItemType itemType) {
		if (getItemCount(itemType) == 0) {
			return;
		}
		int updatedCount = itemCounts.get(itemType) - 1;
		itemCounts.put(itemType, updatedCount);
		if (updatedCount == 0) {
			for (ItemType currentType : items) {
				if (currentType == itemType) {
					items.remove(currentType);
					break;
				}
			}
			itemCounts.remove(itemType);
		}
	}

	public void removeItemByType(ItemType itemType, int count) {
		if (getItemCount(itemType) >= count) {
			for (int i = 0; i < count; i++) {
				removeItemByType(itemType);
			}
		}
	}

	public void removeItem(ItemType itemType) {
		removeItemByType(itemType);
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
		for (int i = 0; i < NUMBER_AGENTS; i++) {
			for (ItemType itemType : itemTypes) {
				// if (Math.random() > 0.8) {
				addItem(itemType);
				// }
			}
		}
	}
}
