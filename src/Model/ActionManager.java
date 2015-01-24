package Model;

import java.util.ArrayList;
import java.util.List;

import TileSystem.TileSystem.TileId;

public class ActionManager {
	private ArrayList<Action> actions;

	public ActionManager() {
		this.actions = new ArrayList<Action>();

		// These are all hard-coded for now!
		this.actions.add(new Action("Collect", new TileId[] { TileId.GRASS },
				new ItemType[] {}, new IActionable() {
					@Override
					public void performAction(GameSession gs, Agent agent) {
						gs.addItem(ItemFactory.createItem(ItemType.GRASS));
					}
				}));
	}

	public ArrayList<Action> getValidActions(TileId tile,
			List<Item> selectedItems, Agent agent) {
		ArrayList<Action> validActions = new ArrayList<Action>();
		for (Action action : actions) {
			boolean valid = true;
			if (action.getRequiredTiles().contains(tile)) {
				ArrayList<ItemType> requiredTypes = action.getRequiredItems();
				if(selectedItems.size() != requiredTypes.size()) {
					valid = false;
				}
				else {
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
