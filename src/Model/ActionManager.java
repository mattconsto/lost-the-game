package Model;

import java.util.ArrayList;
import java.util.Random;

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
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile) {
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

		this.actions.add(new Action("Eat Fish", 10, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.removeItemByType(ItemType.FISH);
			}

			@Override
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile) {
				agent.incFood(30);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (gs.getItemCount(ItemType.FISH) >= 1);
			}

		}));

		this.actions.add(new Action("Eat Cake", 10, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.removeItemByType(ItemType.SNACK);
			}

			@Override
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile) {
				agent.incFood(30);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (gs.getItemCount(ItemType.SNACK) >= 1);
			}

		}));

		this.actions.add(new Action("Eat Berries", 10, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.removeItemByType(ItemType.BERRIES);
			}

			@Override
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile) {
				agent.incFood(15);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (gs.getItemCount(ItemType.BERRIES) >= 1);
			}

		}));

		this.actions.add(new Action("Drink Water", 3, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.removeItemByType(ItemType.WATER);
				gs.addItemByType(ItemType.LEAF);
			}

			@Override
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile) {
				agent.incWater(20);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (gs.getItemCount(ItemType.WATER) >= 1);
			}

		}));

		this.actions.add(new Action("Drink Seawater", 3, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
			}

			@Override
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile) {
				agent.decWater(10);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (tile.id == TileId.WATER);
			}

		}));

		this.actions.add(new Action("Drink Pondwater", 3, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
			}

			@Override
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile) {
				agent.incWater(20);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (tile.id == TileId.POND);
			}

		}));

		this.actions.add(new Action("Take Dirt", 3, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
			}

			@Override
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile) {
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
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile) {
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
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile) {
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
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile) {
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
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile) {

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
				gs.removeItemByType(ItemType.FIRESTICK);
				gs.addItemByType(ItemType.STICK);
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

		this.actions.add(new Action("Salvage Parts", 30, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
			}

			@Override
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile) {
				gs.addItemByType(ItemType.METAL);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (tile.attr == TileAttr.WRECKAGE);
			}
		}));

		this.actions.add(new Action("Light Stick", 20, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.removeItemByType(ItemType.STICK);
				gs.removeItemByType(ItemType.ROCK);
				gs.removeItemByType(ItemType.OIL);
				gs.removeItemByType(ItemType.CLOTH);
			}

			@Override
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile) {
				gs.addItemByType(ItemType.FIRESTICK);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (gs.getItemCount(ItemType.STICK) >= 1
						&& gs.getItemCount(ItemType.ROCK) >= 1
						&& gs.getItemCount(ItemType.CLOTH) >= 1 && gs
						.getItemCount(ItemType.OIL) >= 1);
			}
		}));

		this.actions.add(new Action("Eat Corpsicle", 20, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.removeItemByType(ItemType.MEAT);
			}

			@Override
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile) {
				agent.incFood(40);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (gs.getItemCount(ItemType.MEAT) >= 1);
			}

		}));

		this.actions.add(new Action("Eat Corpse", 60, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.removeItemByType(ItemType.CORPSE);
			}

			@Override
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile) {
				agent.incFood(20);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (gs.getItemCount(ItemType.CORPSE) >= 1);
			}

		}));

		this.actions.add(new Action("Take Corpse", 5, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
			}

			@Override
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile) {
				gs.addItemByType(ItemType.CORPSE);
				tile.attr = TileAttr.SKELETON;
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
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile) {

				tile.attrHealth -= 5;
				if (tile.attrHealth == 0) {
					tile.id = TileId.DIRT;
				}

				gs.addItemByType(ItemType.ROCK);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (tile.id == TileId.ROCK);
			}

		}));

		this.actions.add(new Action("Find Berries", 5, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
			}

			@Override
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile) {
				gs.addItemByType(ItemType.BERRIES);

				tile.attrHealth -= 5;
				if (tile.attrHealth == 0) {
					RandomTileObject(TileId.GRASS, TileAttr.SHRUB, 1, false, ts);
					tile.attr = TileAttr.NONE;
				}
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (tile.attr == TileAttr.SHRUB);
			}

		}));

		this.actions.add(new Action("Make Sail", 30, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.removeItemByType(ItemType.CLOTH, 2);
			}

			@Override
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile) {
				gs.addItemByType(ItemType.SAIL);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (gs.getItemCount(ItemType.CLOTH) >= 5);
			}

		}));

		this.actions.add(new Action("Make Cloth", 30, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.removeItemByType(ItemType.WEB, 5);
			}

			@Override
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile) {
				gs.addItemByType(ItemType.CLOTH);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (gs.getItemCount(ItemType.WEB) >= 5);
			}

		}));

		this.actions.add(new Action("Chop Tree", 30, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
			}

			@Override
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile) {
				gs.addItemByType(ItemType.PLANK, 5);
				
				switch(tile.attr){
					case TREE:
						RandomTileObject(TileId.GRASS, TileAttr.TREE, 1, true, ts);
						break;
					case PINE_TREE:
						RandomTileObject(TileId.ROCK, TileAttr.PINE_TREE, 1, true, ts);
						break;
					case PALM_TREE:
						RandomTileObject(TileId.DIRT, TileAttr.PALM_TREE, 1, true, ts);
						break;
				}
				tile.attr = TileAttr.NONE;
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {

				return ((tile.attr == TileAttr.PALM_TREE
						|| tile.attr == TileAttr.PINE_TREE || tile.attr == TileAttr.TREE) && gs
						.getItemCount(ItemType.AXE) >= 1);
			}

		}));

		this.actions.add(new Action("Find Artifacts", 60, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
			}

			@Override
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile) {
				gs.addItemByType(ItemType.ARTIFACT);
				tile.attr = TileAttr.NONE;
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (tile.attr == TileAttr.ALIEN_ARTIFACT);
			}

		}));
		this.actions.add(new Action("Get Water", 3, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.removeItemByType(ItemType.LEAF);
			}

			@Override
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile) {
				gs.addItemByType(ItemType.WATER);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (gs.getItemCount(ItemType.LEAF) >= 1 && (tile.id == TileId.POND || tile.id == TileId.WATER));
			}

		}));

		this.actions.add(new Action("Get Oil", 3, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.removeItemByType(ItemType.LEAF);
			}

			@Override
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile) {
				gs.addItemByType(ItemType.OIL);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (tile.id == TileId.TARPIT && gs
						.getItemCount(ItemType.LEAF) >= 1);
			}

		}));

		this.actions.add(new Action("Make Axe", 15, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.removeItemByType(ItemType.STICK);
				gs.removeItemByType(ItemType.METAL);
				gs.removeItemByType(ItemType.VINE);
			}

			@Override
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile) {
				gs.addItemByType(ItemType.AXE);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (gs.getItemCount(ItemType.STICK) >= 1
						&& gs.getItemCount(ItemType.METAL) >= 1 && gs
						.getItemCount(ItemType.VINE) >= 1);
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
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile) {
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
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {

			}

			@Override
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile) {
				if (Math.random() < 0.9) {
					gs.addItemByType(ItemType.STICK);
				} else {
					gs.addItemByType(ItemType.LEAF);
				}
				tile.attrHealth--;
				if (tile.attrHealth == 0) {
					switch(tile.attr){
						case TREE:
							RandomTileObject(TileId.GRASS, TileAttr.TREE, 1, true, ts);
							break;
						case PINE_TREE:
							RandomTileObject(TileId.ROCK, TileAttr.PINE_TREE, 1, true, ts);
							break;
						case PALM_TREE:
							RandomTileObject(TileId.DIRT, TileAttr.PALM_TREE, 1, true, ts);
							break;
					}
					tile.attr = TileAttr.NONE;
				}
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return ((tile.attr == TileAttr.PALM_TREE
						|| tile.attr == TileAttr.PINE_TREE || tile.attr == TileAttr.TREE));
			}

		}));

		this.actions.add(new Action("Take Web", 5, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {

			}

			@Override
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile) {
				gs.addItemByType(ItemType.WEB);
				tile.attr = TileAttr.TREE;
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return tile.attr == TileAttr.WEBBED_TREE;
			}

		}));

		this.actions.add(new Action("Take Vine", 10, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {

			}

			@Override
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile) {
				gs.addItemByType(ItemType.VINE);
				tile.attrHealth -= 5;
				if (tile.attrHealth == 0) {
					RandomTileObject(TileId.DIRT, TileAttr.PALM_TREE, 1, true, ts);
					tile.attr = TileAttr.NONE;
				}
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return tile.attr == TileAttr.PALM_TREE;
			}

		}));

		this.actions.add(new Action("Catch Fish", 10, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {

			}

			@Override
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile) {
				gs.addItemByType(ItemType.FISH);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return tile.id == TileId.WATER
						&& gs.getItemCount(ItemType.SPEAR) >= 1;
			}

		}));

		this.actions.add(new Action("Sacrifice Another", 0, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.removeItemByType(ItemType.CORPSE);
			}

			@Override
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile) {
				gs.addItemByType(ItemType.FLIGHT);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (gs.getItemCount(ItemType.CORPSE) >= 1 && tile.attr == TileAttr.ALTAR);
			}

		}));
		
		this.actions.add(new Action("Sacrifice Yourself", 0, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
			}

			@Override
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile) {
				gs.addItemByType(ItemType.FLIGHT);
				agent.setState(AgentState.DEAD);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (tile.attr == TileAttr.ALTAR);
			}

		}));
		
		this.actions.add(new Action("Sleep", 0, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				agent.setState(AgentState.SLEEPING);

			}

			@Override
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile) {
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return tile.attr == TileAttr.HUT;
			}

		}));

		this.actions.add(new Action("Build Raft", 3, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.removeItemByType(ItemType.VINE, 3);
				gs.removeItemByType(ItemType.SAIL, 1);
				gs.removeItemByType(ItemType.STICK, 10);
			}

			@Override
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile) {
				gs.setCompletionType(1);
				gs.setCompleted(true);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (gs.getItemCount(ItemType.VINE) >= 3
						&& gs.getItemCount(ItemType.SAIL) >= 1
						&& gs.getItemCount(ItemType.STICK) >= 10 && tile.id == TileId.WATER);
			}

		}));

		this.actions.add(new Action("Build Boat", 3, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.removeItemByType(ItemType.VINE, 3);
				gs.removeItemByType(ItemType.SAIL, 1);
				gs.removeItemByType(ItemType.STICK, 10);
				gs.removeItemByType(ItemType.METAL, 10);
				gs.removeItemByType(ItemType.MUD, 5);
				gs.removeItemByType(ItemType.PLANK, 25);
			}

			@Override
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile) {
				gs.setCompletionType(2);
				gs.setCompleted(true);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (gs.getItemCount(ItemType.VINE) >= 3
						&& gs.getItemCount(ItemType.SAIL) >= 1
						&& gs.getItemCount(ItemType.STICK) >= 10
						&& gs.getItemCount(ItemType.METAL) >= 10
						&& gs.getItemCount(ItemType.MUD) >= 5
						&& gs.getItemCount(ItemType.PLANK) >= 25

				&& tile.id == TileId.WATER);
			}

		}));

		this.actions.add(new Action("Build Plane", 3, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.removeItemByType(ItemType.VINE, 3);
				gs.removeItemByType(ItemType.SAIL, 1);
				gs.removeItemByType(ItemType.STICK, 10);
				gs.removeItemByType(ItemType.METAL, 10);
				gs.removeItemByType(ItemType.MUD, 5);
				gs.removeItemByType(ItemType.PLANK, 25);
				gs.removeItemByType(ItemType.OIL, 5);
			}

			@Override
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile) {
				gs.setCompletionType(3);
				gs.setCompleted(true);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (gs.getItemCount(ItemType.VINE) >= 3
						&& gs.getItemCount(ItemType.SAIL) >= 1
						&& gs.getItemCount(ItemType.STICK) >= 10
						&& gs.getItemCount(ItemType.METAL) >= 10
						&& gs.getItemCount(ItemType.MUD) >= 5
						&& gs.getItemCount(ItemType.PLANK) >= 25
						&& gs.getItemCount(ItemType.OIL) >= 5
						&& gs.getItemCount(ItemType.FLIGHT) >= 1

				&& tile.id == TileId.SNOW);
			}

		}));

		this.actions.add(new Action("Build Spacecraft", 3, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.removeItemByType(ItemType.VINE, 3);
				gs.removeItemByType(ItemType.SAIL, 1);
				gs.removeItemByType(ItemType.STICK, 10);
				gs.removeItemByType(ItemType.METAL, 10);
				gs.removeItemByType(ItemType.MUD, 5);
				gs.removeItemByType(ItemType.PLANK, 25);
				gs.removeItemByType(ItemType.OIL, 5);
				gs.removeItemByType(ItemType.ARTIFACT, 1);
			}

			@Override
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile) {
				gs.setCompletionType(3);
				gs.setCompleted(true);
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return (gs.getItemCount(ItemType.VINE) >= 3
						&& gs.getItemCount(ItemType.SAIL) >= 1
						&& gs.getItemCount(ItemType.STICK) >= 10
						&& gs.getItemCount(ItemType.METAL) >= 10
						&& gs.getItemCount(ItemType.MUD) >= 5
						&& gs.getItemCount(ItemType.PLANK) >= 25
						&& gs.getItemCount(ItemType.OIL) >= 5
						&& gs.getItemCount(ItemType.ARTIFACT) >= 1
						&& gs.getItemCount(ItemType.FLIGHT) >= 1

				&& tile.id == TileId.SNOW);
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
	
	private void RandomTileObject(TileId tileType, TileAttr tileAtt, int treeCount, boolean preferGroupings, TileSystem ts)
	{
		Random randomGenerator = new Random();
		while(true)
		{
			int x = randomGenerator.nextInt(ts.getSize()-2)+1;
			int y = randomGenerator.nextInt(ts.getSize()-2)+1;
			Tile tile = ts.getTile(x, y);
			if (tile.id== tileType && tile.attr == TileAttr.NONE)
			{
				float surroundTree = 1;
				if (ts.getTile(x+1, y).attr==tileAtt) surroundTree++;
				if (ts.getTile(x-1, y).attr==tileAtt) surroundTree++;
				if (ts.getTile(x, y+1).attr==tileAtt) surroundTree++;
				if (ts.getTile(x, y-1).attr==tileAtt) surroundTree++;
				float num = (float)randomGenerator.nextInt(100) ;
				if (preferGroupings)
					num /= surroundTree; 
				else
					num /=1.25;
					num *= surroundTree; 
				
				if (num > 50)
				{
					treeCount-=1;
					tile.attr= tileAtt;
				}
			}
			
			if (treeCount ==0) return;
		}
		
	}
}
