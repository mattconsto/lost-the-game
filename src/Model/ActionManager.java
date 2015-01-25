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
		this.actions.add(new Action("Pick Grass", 6, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
			}
			
			@Override
			public void afterAction(GameSession gs, Agent agent,
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
		this.actions.add(new Action("Eat Cake", 10, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.removeItemByType(ItemType.SNACK);
			}
			
			@Override
			public void afterAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				agent.incFood(30);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (gs.getItemCount(ItemType.SNACK) >= 1);
			}

		}));
		this.actions.add(new Action("Drink Water", 3, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
			}
			
			@Override
			public void afterAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				agent.incWater(10);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (tile.id == TileId.WATER);
			}

		}));
		this.actions.add(new Action("Take Dirt", 3, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
			}
			
			@Override
			public void afterAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.addItemByType(ItemType.MUD);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (tile.id == TileId.DIRT);
			}

		}));
		this.actions.add(new Action("Make Mud Brick", 10, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.removeItemByType(ItemType.MUD);
				gs.removeItemByType(ItemType.GRASS);
			}
			@Override
			public void afterAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.addItemByType(ItemType.BRICK);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (gs.getItemCount(ItemType.MUD) >= 1 && gs
						.getItemCount(ItemType.GRASS) >= 1);
			}

		}));
		this.actions.add(new Action("Build Hut", 60, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.removeItemByType(ItemType.BRICK, 5);
			}
			
			@Override
			public void afterAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
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


		this.actions.add(new Action("Burn Corpse", 15, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.removeItemByType(ItemType.CORPSE);
			}
			
			@Override
			public void afterAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.addItemByType(ItemType.MEAT);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (gs.getItemCount(ItemType.CORPSE) > 0 && tile.attr == TileAttr.FIRE);
			}
		}));

		this.actions.add(new Action("Start Fire", 10, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
			}
			
			@Override
			public void afterAction(GameSession gs, Agent agent,
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

		this.actions.add(new Action("Light Stick", 20, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.removeItemByType(ItemType.STICK);
				gs.removeItemByType(ItemType.ROCK);
			}
			
			@Override
			public void afterAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.addItemByType(ItemType.FIRESTICK);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (gs.getItemCount(ItemType.STICK) >= 1 && gs
						.getItemCount(ItemType.ROCK) >= 1);
			}
		}));

		this.actions.add(new Action("Take Corpse", 5, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
			}
			
			@Override
			public void afterAction(GameSession gs, Agent agent,
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

		this.actions.add(new Action("Get Rock", 3, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
			}
			
			@Override
			public void afterAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.addItemByType(ItemType.ROCK);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return(tile.id == TileId.ROCK);
			}

		}));

		this.actions.add(new Action("Make Spear", 15, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.removeItemByType(ItemType.STICK);
				gs.removeItemByType(ItemType.ROCK);
				gs.removeItemByType(ItemType.VINE);
			}
			@Override
			public void afterAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.addItemByType(ItemType.SPEAR);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (gs.getItemCount(ItemType.STICK) >= 1
						&& gs.getItemCount(ItemType.ROCK) >= 1 && gs
						.getItemCount(ItemType.VINE) >= 1);
			}

		}));

		this.actions.add(new Action("Take Stick", 3, new IActionable() {
			
			@Override
			public void beforeAction(GameSession gs, Agent agent, TileSystem ts, Tile tile) {
				
			}
			
			@Override
			public void afterAction(GameSession gs, Agent agent,
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

		this.actions.add(new Action("Take Vine", 10, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent, TileSystem ts, Tile tile) {
				
			}
			@Override
			public void afterAction(GameSession gs, Agent agent,
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

		this.actions.add(new Action("Sleep", 0, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent, TileSystem ts, Tile tile) {
				agent.setState(AgentState.SLEEPING);
				
			}
			@Override
			public void afterAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
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
