package deserted.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import deserted.model.action.BaseAction;
import deserted.model.item.EdibleItem;
import deserted.model.item.ItemType;
import deserted.tilesystem.Tile;

public class Agent {
	private float food;
	private float water;
	private float health;
	private String name;
	private AgentState state;
	private double expiredTime;

	public float healthDelta = 0;
	public float currentFlashAlpha = 0.2f;
	public float flashDelta = 0.02f;

	private static int namePos = 0;
	private static boolean shuffled = false;

	private String[] names = { "Bill", "Joe", "Jane", "Jet", "Andy", "Ollie",
			"Mike", "Jonah", "Sam", "Joanne", "Suzy", "Lucy", "Mary", "Betsy" };
	private boolean placedCorpse;
	private BaseAction action;
	private double actionStartTime;
	private Tile tile;

	public Agent(float food, float water, float health) {
		this();
		this.setFood(food);
		this.setWater(water);
		this.setHealth(health);
	}

	public Tile getTile() {
		return this.tile;
	}

	private void generateName() {
		if (!shuffled) {
			List<String> nameList = Arrays.asList(names);
			Collections.shuffle(nameList);
			names = nameList.toArray(new String[nameList.size()]);
			shuffled = true;
		}
		this.setName(names[namePos++ % names.length]);
	}

	public Agent() {
		this.setFood(100);
		this.setWater(100);
		this.setHealth(100);
		this.generateName();
		this.setState(AgentState.STANDING);
		generateInventory();
	}

	private void generateInventory() {
		ItemType[] itemTypes = { ItemType.LIFEJACKET, ItemType.SNACK };
		for (ItemType itemType : itemTypes) {
			if (Math.random() > 0.8) {
				getInventory().addItem(itemType);
			}
		}
	}

	public float getFood() {
		return food;
	}

	public void setFood(float food) {
		this.food = food;
	}

	public float getWater() {
		return water;
	}

	public void setWater(float water) {
		this.water = water;
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void decWater(float w) {
		this.water -= w;
		if (this.water < 0)
			this.water = 0;
	}

	public void incWater(float w) {
		this.water += w;
		if (this.water > 100) {
			this.water = 100;
		}
		if (this.water < 0) {
			this.water = 0;
		}
	}

	public void decFood(float f) {
		this.food -= f;
		if (this.food < 0)
			this.food = 0;
	}

	public void incFood(float f) {
		this.food += f;
		if (this.food > 100) {
			this.food = 100;
		}
		if (this.food < 0) {
			this.food = 0;
		}
	}

	public void decHealth(float h) {
		this.health -= h;
		if (this.health < 0)
			this.health = 0;
	}

	public void incHealth(float h) {
		this.health += h;
		if (this.health > 100) {
			this.health = 100;
		}
		if (this.health < 0) {
			this.health = 0;
		}
	}

	public Inventory getInventory() {
		return GameSession.getInstance().getInventory();
	}

	public AgentState getState() {
		return state;
	}

	public void setState(AgentState state) {
		this.state = state;
	}

	public void setExpiredTime(double expiredTime) {
		this.expiredTime = expiredTime;
	}

	public double getExpiredTime() {
		return expiredTime;
	}

	public boolean hasPlacedCorpse() {
		return this.placedCorpse;
	}

	public void setPlacedCorpse(boolean placedCorpse) {
		this.placedCorpse = placedCorpse;
	}

	public boolean hasAction() {
		return (this.getAction() != null);
	}

	public void stopAction() {
		this.setAction(null);
	}

	public void startAction(BaseAction action) {
		this.setAction(action);
		this.actionStartTime = GameSession.getInstance().getTimeSurvived();
	}

	public boolean haveFinishedAction() {
		GameSession gs = GameSession.getInstance();
		if ((gs.getTimeSurvived() - this.actionStartTime) >= this.getAction()
				.getDuration()) {
			return true;
		}
		return false;
	}

	public BaseAction getAction() {
		return action;
	}

	public void setAction(BaseAction action) {
		this.action = action;
	}

	public float getRatioOfActionRemaining() {
		GameSession gs = GameSession.getInstance();
		float duration = this.getAction().getDuration();
		float elapsed = (float) gs.getTimeSurvived()
				- (float) this.actionStartTime;
		float ratio = elapsed / duration;
		if (ratio > 1)
			ratio = 1;
		return ratio;
	}

	public void consume(EdibleItem edibleItem) {

		this.incFood(edibleItem.getFood());
		this.incWater(edibleItem.getWater());
		this.incHealth(edibleItem.getHealth());
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}
	
	public void update(float delta) {
		if (getState() == AgentState.WALKING) {
			decFood(GameConfig.FOOD_PER_SEC_WALK * delta);
			decWater(GameConfig.WATER_PER_SEC_WALK * delta);
		} else if (getState() == AgentState.STANDING) {
			decFood(GameConfig.FOOD_PER_SEC_STAND * delta);
			decWater(GameConfig.WATER_PER_SEC_STAND * delta);
		} else if (getState() == AgentState.SLEEPING) {
			// Only gain health if not thirsty or hungry
			if (getFood() > 0 && getWater() > 0) {
				if (getHealth() < 90) {
					incHealth(0.1f);
				}
			}
			decFood(GameConfig.FOOD_PER_SEC_SLEEP * delta);
			decWater(GameConfig.WATER_PER_SEC_SLEEP * delta);
		}

		if (getFood() == 0 && getWater() == 0) {
			decHealth(GameConfig.HEALTH_PER_SEC * delta);
		}

		if (getHealth() <= 0) {

			if (getState() != AgentState.DEAD)
				setExpiredTime(GameSession.getInstance().getTimeSurvived());
			setState(AgentState.DEAD);

		}
	}

}
