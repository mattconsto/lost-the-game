package Model;

import java.util.ArrayList;
import java.util.List;

import Player.PlayerUI;
import TileSystem.TileSystem;
import TileSystem.TileSystem.TileId;

public class ActionManager {
	private ArrayList<Action> actions;

	public ActionManager() {
		this.actions = new ArrayList<Action>();

		// These are all hard-coded for now!
		this.actions.add(new Action("Pick Grass", new TileId[] { TileId.GRASS },
				new ItemType[] {}, new IActionable() {
					@Override
					public void performAction(GameSession gs, Agent agent, TileSystem ts, PlayerUI pui) {
						gs.addItem(ItemFactory.createItem(ItemType.GRASS));
						ts.getTileFromWorld(pui.location.x, pui.location.y).id = TileId.DIRT;
						agent.decFood(5);
					}
				}));
		this.actions.add(new Action("Eat Snack", new TileId[] {},
				new ItemType[] { ItemType.SNACK }, new IActionable() {
					@Override
					public void performAction(GameSession gs, Agent agent, TileSystem ts, PlayerUI pui) {
						gs.removeItem(ItemFactory.createItem(ItemType.SNACK));
						agent.incFood(30);
					}
				}));
		this.actions.add(new Action("Drink Water", new TileId[] {TileId.WATER},
				new ItemType[] {}, new IActionable() {
					@Override
					public void performAction(GameSession gs, Agent agent, TileSystem ts, PlayerUI pui) {
						agent.incWater(30);
					}
				}));
		this.actions.add(new Action("Take Dirt", new TileId[] {TileId.DIRT},
				new ItemType[] {}, new IActionable() {
					@Override
					public void performAction(GameSession gs, Agent agent, TileSystem ts, PlayerUI pui) {
						gs.addItem(ItemFactory.createItem(ItemType.MUD));
						ts.getTileFromWorld(pui.location.x, pui.location.y).id = TileId.WATER;
					}
				}));
		this.actions.add(new Action("Make Mud Brick", new TileId[] {},
				new ItemType[] {ItemType.MUD, ItemType.GRASS}, new IActionable() {
					@Override
					public void performAction(GameSession gs, Agent agent, TileSystem ts, PlayerUI pui) {
						gs.removeItem(ItemFactory.createItem(ItemType.MUD));
						gs.removeItem(ItemFactory.createItem(ItemType.GRASS));
						gs.addItem(ItemFactory.createItem(ItemType.BRICK));
					}
				}));
		this.actions.add(new Action("Build Hut", new TileId[] {TileId.GRASS},
				new ItemType[] {ItemType.BRICK}, new IActionable() {
					@Override
					public void performAction(GameSession gs, Agent agent, TileSystem ts, PlayerUI pui) {
						gs.removeItem(ItemFactory.createItem(ItemType.BRICK));
						//ts.getTileFromWorld(pui.location.x, pui.location.y).id = TileId. ;
					}
				}));
	}

	public ArrayList<Action> getValidActions(TileId tile,
			List<Item> selectedItems, Agent agent) {
		ArrayList<Action> validActions = new ArrayList<Action>();
		for (Action action : actions) {
			boolean valid = true;
			if (action.getRequiredTiles().contains(tile) || action.getRequiredTiles().size() == 0) {
				ArrayList<ItemType> requiredTypes = action.getRequiredItems();
				if (selectedItems.size() != requiredTypes.size()) {
					valid = false;
				} else {
					for (Item selectedItem : selectedItems) {
						if (!requiredTypes.contains(selectedItem.getType())) {
							// TODO: Add health etc checks
							valid = false;
						}
					}
				}
				if (valid) {
					validActions.add(action);
				}
			}
		}
		return validActions;
	}
}
