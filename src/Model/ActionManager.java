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
		this.actions.add(new Action("Pick Grass", new IActionable() {
			@Override
			public void performAction(GameSession gs, Agent agent,
					TileSystem ts, PlayerUI pui) {
				gs.addItem(ItemFactory.createItem(ItemType.GRASS));
				ts.getTileFromWorld(pui.location.x, pui.location.y).id = TileId.DIRT;
				agent.decFood(5);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, TileId tile, List<Item> selectedItems) {
				return (tile == TileId.GRASS);
			}

		}));
		this.actions.add(new Action("Eat Snack", new IActionable() {
			@Override
			public void performAction(GameSession gs, Agent agent,
					TileSystem ts, PlayerUI pui) {
				gs.removeItem(ItemFactory.createItem(ItemType.SNACK));
				agent.incFood(30);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, TileId tile, List<Item> selectedItems) {
				for (Item item : selectedItems) {
					if (item.getType() == ItemType.SNACK) {
						return true;
					}
				}
				return false;
			}

		}));
		this.actions.add(new Action("Drink Water", new IActionable() {
			@Override
			public void performAction(GameSession gs, Agent agent,
					TileSystem ts, PlayerUI pui) {
				agent.incWater(30);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, TileId tile, List<Item> selectedItems) {
				return (tile == TileId.WATER);
			}

		}));
		this.actions.add(new Action("Take Dirt", new IActionable() {
			@Override
			public void performAction(GameSession gs, Agent agent,
					TileSystem ts, PlayerUI pui) {

				gs.addItem(ItemFactory.createItem(ItemType.MUD));
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, TileId tile, List<Item> selectedItems) {
				return (tile == TileId.DIRT);
			}

		}));
		this.actions.add(new Action("Make Mud Brick", new IActionable() {
			@Override
			public void performAction(GameSession gs, Agent agent,
					TileSystem ts, PlayerUI pui) {
				gs.removeItem(ItemFactory.createItem(ItemType.MUD));
				gs.removeItem(ItemFactory.createItem(ItemType.GRASS));
				gs.addItem(ItemFactory.createItem(ItemType.BRICK));
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, TileId tile, List<Item> selectedItems) {
				return (gs.getItemCount(ItemType.MUD) == 1 && gs
						.getItemCount(ItemType.GRASS) == 1);
			}

		}));
	}

	public ArrayList<Action> getValidActions(GameSession gs, TileSystem ts, TileId tile,
			List<Item> selectedItems, Agent agent) {
		ArrayList<Action> validActions = new ArrayList<Action>();
		for (Action action : actions) {
			if (action.getActionable().canPerform(gs, agent, ts, tile, selectedItems)) {
					validActions.add(action);
			}
		}
		return validActions;
	}
}
