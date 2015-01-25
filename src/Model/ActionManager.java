package Model;

import java.util.ArrayList;

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
				gs.addItemByType(ItemType.GRASS);
				ts.setTileID(tile.x, tile.y, TileId.DIRT);
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
				tile.attrHealth = 10;
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				if (gs.getItemCount(ItemType.BRICK) >= 5
						&& tile.id != TileId.WATER) {
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
				tile.attrHealth = 10;
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return true;
			}

		}));

		this.actions.add(new Action("Burn Corpse", new IActionable() {
			@Override
			public void performAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.removeItemByType(ItemType.CORPSE);
				gs.addItemByType(ItemType.MEAT);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (gs.getItemCount(ItemType.CORPSE) > 0 && tile.attr == TileAttr.FIRE);
			}
		}));

		this.actions.add(new Action("Start Fire", new IActionable() {
			@Override
			public void performAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {

				switch (tile.attr) {
				case CORPSE:
					gs.addItemByType(ItemType.MEAT);
					break;
				case HUT:
					gs.addItemByType(ItemType.MUD);
					break;
				default:
					// Do nothing
				}
				tile.attr = TileAttr.FIRE;
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				if (gs.getItemCount(ItemType.FIRESTICK) == 0) {
					return false;
				}
				return (tile.id != TileId.SNOW && tile.id != TileId.WATER && tile.id != TileId.WALL);
			}
		}));

		this.actions.add(new Action("Light Stick", new IActionable() {
			@Override
			public void performAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.addItemByType(ItemType.FIRESTICK);
				gs.removeItemByType(ItemType.STICK);
				gs.removeItemByType(ItemType.ROCK);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (gs.getItemCount(ItemType.STICK) >= 1 && gs
						.getItemCount(ItemType.ROCK) >= 1);
			}
		}));

		this.actions.add(new Action("Take Corpse", new IActionable() {
			@Override
			public void performAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.addItemByType(ItemType.CORPSE);
				tile.attr = TileAttr.NONE;
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (tile.attr == TileAttr.CORPSE);
			}

		}));

		this.actions.add(new Action("Get Rock", new IActionable() {
			@Override
			public void performAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.addItemByType(ItemType.ROCK);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return true;
			}

		}));

		this.actions.add(new Action("Build Palm Tree", new IActionable() {
			@Override
			public void performAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				tile.attr = TileAttr.PALM_TREE;
				tile.attrHealth = 10;
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return true;
			}

		}));

		this.actions.add(new Action("Make Spear", new IActionable() {
			@Override
			public void performAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.addItemByType(ItemType.SPEAR);
				gs.removeItemByType(ItemType.STICK);
				gs.removeItemByType(ItemType.ROCK);
				gs.removeItemByType(ItemType.VINE);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (gs.getItemCount(ItemType.STICK) >= 1
						&& gs.getItemCount(ItemType.ROCK) >= 1 && gs
						.getItemCount(ItemType.VINE) >= 1);
			}

		}));

		this.actions.add(new Action("Take Stick", new IActionable() {
			@Override
			public void performAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				if (Math.random() < 0.9) {
					gs.addItemByType(ItemType.STICK);
				} else {
					gs.addItemByType(ItemType.LEAF);
				}
				tile.attrHealth--;
				if (tile.attrHealth == 0) {
					tile.attr = TileAttr.NONE;
				}
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return tile.attr == TileAttr.TREE;
			}

		}));

		this.actions.add(new Action("Take Vine", new IActionable() {
			@Override
			public void performAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.addItemByType(ItemType.VINE);
				tile.attrHealth -= 5;
				if (tile.attrHealth == 0) {
					tile.attr = TileAttr.NONE;
				}
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return tile.attr == TileAttr.PALM_TREE;
			}

		}));

		this.actions.add(new Action("Sleep", new IActionable() {
			@Override
			public void performAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				agent.setState(AgentState.SLEEPING);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return tile.attr == TileAttr.HUT;
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
