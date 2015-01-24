package Model;

import java.util.ArrayList;
import java.util.List;

import Player.PlayerUI;
import TileSystem.Tile;
import TileSystem.TileAttr;
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
					TileSystem ts, Tile tile) {
				gs.addItem(ItemFactory.createItem(ItemType.GRASS));
				tile.id = TileId.DIRT;
				agent.decFood(5);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (tile.id == TileId.GRASS);
			}

		}));
		this.actions.add(new Action("Eat Snack", new IActionable() {
			@Override
			public void performAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.removeItemByType(ItemType.SNACK);
				agent.incFood(30);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (gs.getItemCount(ItemType.SNACK) >= 1);
			}

		}));
		this.actions.add(new Action("Drink Water", new IActionable() {
			@Override
			public void performAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				agent.incWater(30);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (tile.id == TileId.WATER);
			}

		}));
		this.actions.add(new Action("Take Dirt", new IActionable() {
			@Override
			public void performAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.addItemByType(ItemType.MUD);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (tile.id == TileId.DIRT);
			}

		}));
		this.actions.add(new Action("Make Mud Brick", new IActionable() {
			@Override
			public void performAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.removeItemByType(ItemType.MUD);
				gs.removeItemByType(ItemType.GRASS);
				gs.addItemByType(ItemType.BRICK);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (gs.getItemCount(ItemType.MUD) >= 1 && gs
						.getItemCount(ItemType.GRASS) >= 1);
			}

		}));
		this.actions.add(new Action("Build Hut", new IActionable() {
			@Override
			public void performAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.removeItemByType(ItemType.BRICK, 5);
				tile.attr = TileAttr.HUT;
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				if (gs.getItemCount(ItemType.BRICK) >= 5) {
					return true;
				}
				return false;
			}

		}));

		this.actions.add(new Action("Build Tree", new IActionable() {
			@Override
			public void performAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				tile.attr = TileAttr.TREE;
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return true;
			}

		}));

		this.actions.add(new Action("Take Stick", new IActionable() {
			@Override
			public void performAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {

			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return tile.attr == TileAttr.TREE;
			}

		}));
	}

	public ArrayList<Action> getValidActions(GameSession gs, TileSystem ts,
			Tile tile, Agent agent) {
		ArrayList<Action> validActions = new ArrayList<Action>();
		for (Action action : actions) {
			if (action.getActionable().canPerform(gs, agent, ts, tile)) {
				validActions.add(action);
			}
		}
		return validActions;
	}
}
