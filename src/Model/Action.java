package Model;

import java.util.ArrayList;
import java.util.Arrays;

import Player.PlayerUI;
import TileSystem.TileSystem;
import TileSystem.TileSystem.TileId;

public class Action {
	private ArrayList<TileId> requiredTiles;
	private ArrayList<ItemType> requiredItems;
	private int minHealth;
	private int maxHealth;
	private int minHunger;
	private int maxHunger;
	private int minThirst;
	private int maxThirst;
	private IActionable actionable;
	private String name;
	
	public Action(String name, ArrayList<TileId> requiredTiles, ArrayList<ItemType>requiredItems, IActionable actionable) {
		this.setRequiredTiles(requiredTiles);
		this.setRequiredItems(requiredItems);
		this.actionable = actionable;
		this.name = name;
	}
	
	public Action(String name, TileId[] requiredTiles, ItemType[] requiredItems, IActionable actionable) {
		this.setRequiredTiles(new ArrayList<TileId>(Arrays.asList(requiredTiles)));
		this.setRequiredItems(new ArrayList<ItemType>(Arrays.asList(requiredItems)));
		this.actionable = actionable;
		this.name = name;
	}
	
	public void perform(GameSession gs, Agent ag, TileSystem ts, PlayerUI pui) {
		this.actionable.performAction(gs, ag, ts, pui);
	}
	
	public String getName() {
		return this.name;
	}

	public ArrayList<TileId> getRequiredTiles() {
		return requiredTiles;
	}

	public void setRequiredTiles(ArrayList<TileId> requiredTiles) {
		this.requiredTiles = requiredTiles;
	}

	public ArrayList<ItemType> getRequiredItems() {
		return requiredItems;
	}

	public void setRequiredItems(ArrayList<ItemType> requiredItems) {
		this.requiredItems = requiredItems;
	}

	public int getMinHealth() {
		return minHealth;
	}

	public void setMinHealth(int minHealth) {
		this.minHealth = minHealth;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public int getMinHunger() {
		return minHunger;
	}

	public void setMinHunger(int minHunger) {
		this.minHunger = minHunger;
	}

	public int getMaxHunger() {
		return maxHunger;
	}

	public void setMaxHunger(int maxHunger) {
		this.maxHunger = maxHunger;
	}

	public int getMinThirst() {
		return minThirst;
	}

	public void setMinThirst(int minThirst) {
		this.minThirst = minThirst;
	}

	public int getMaxThirst() {
		return maxThirst;
	}

	public void setMaxThirst(int maxThirst) {
		this.maxThirst = maxThirst;
	}
	
	
}