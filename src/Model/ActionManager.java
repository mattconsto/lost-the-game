package Model;

import java.util.ArrayList;
import java.util.List;

import TileSystem.TileSystem.TileId;

public class ActionManager {
	private ArrayList<Action> actions;

	public ActionManager() {
		this.actions = new ArrayList<Action>();

		// These are all hard-coded for now!
		this.actions.add(new Action("Pick Grass", new TileId[] { TileId.GRASS },
				new ItemType[] {}, new IActionable() {
					@Override
					public void performAction(GameSession gs, Agent agent) {
						gs.addItem(ItemFactory.createItem(ItemType.GRASS));
						agent.decFood(5);
					}
				}));
		this.actions.add(new Action("Eat Snack", new TileId[] {},
				new ItemType[] { ItemType.SNACK }, new IActionable() {
					@Override
					public void performAction(GameSession gs, Agent agent) {
						gs.removeItem(ItemFactory.createItem(ItemType.SNACK));
						agent.incFood(10);
					}
				}));
		this.actions.add(new Action("Drink Water", new TileId[] {TileId.WATER},
				new ItemType[] {}, new IActionable() {
					@Override
					public void performAction(GameSession gs, Agent agent) {
						agent.incWater(10);
					}
				}));
		this.actions.add(new Action("Take Dirt", new TileId[] {TileId.DIRT},
				new ItemType[] {}, new IActionable() {
					@Override
					public void performAction(GameSession gs, Agent agent) {
						gs.addItem(ItemFactory.createItem(ItemType.MUD));
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
