package deserted.model.action;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.SlickException;

import deserted.model.Agent;
import deserted.model.AgentState;
import deserted.model.GameSession;
import deserted.model.ItemType;
import deserted.player.MonsterManager;
import deserted.sound.SoundManager;
import deserted.sprite.SpriteType;
import deserted.tilesystem.Tile;
import deserted.tilesystem.TileSystem;
import deserted.tilesystem.TileSystem.TileId;

public class ActionManager {
	private ArrayList<Action> inventoryActions;
	private ArrayList<Action> tileActions;

	private static IActionable createConsumeAction(ItemType itemType,
			int health, int water, int food) {
		return createConsumeAction(itemType, health, water, food, null);
	}

	private static IActionable createConsumeAction(ItemType itemType,
			int health, int water, int food, IActionable hooks) {
		final ItemType _itemType = itemType;
		final int _health = health;
		final int _water = water;
		final int _food = food;
		final IActionable _hooks = hooks;

		return new IActionable() {

			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				gs.getInventory().removeItem(_itemType);
				if (_hooks != null) {
					_hooks.beforeAction(gs, agent, ts, tile);
				}
			}

			@Override
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile, MonsterManager monsterManager) {
				if (_health != 0) {
					agent.incFood(_health);
				}
				if (_food != 0) {
					agent.incFood(_food);
				}
				if (_water != 0) {
					agent.incWater(_water);
				}
				if (_hooks != null) {
					_hooks.afterAction(gs, agent, ts, tile, monsterManager);
				}
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {

				if (_hooks != null) {
					if (!_hooks.canPerform(gs, agent, ts, tile)) {
						return false;
					}
				}

				return (gs.getInventory().getItemCount(_itemType) >= 1);
			}
		};
	}

	private static IActionable createCraftAction(ItemType[] consumesTypes,
			int[] consumesQuantities, ItemType produces) {
		return createCraftAction(consumesTypes, consumesQuantities, produces,
				null);
	}

	private static IActionable createCraftAction(ItemType[] consumesTypes,
			int[] consumesQuantities, ItemType produces, IActionable hooks) {
		final ItemType[] _consumesTypes = consumesTypes;
		final int[] _consumesQuantities = consumesQuantities;
		final ItemType _produces = produces;
		final IActionable _hooks = hooks;
		return new IActionable() {

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				for (int i = 0; i < _consumesTypes.length; i++) {
					if (gs.getInventory().getItemCount(_consumesTypes[i]) < _consumesQuantities[i]) {
						return false;
					}
				}

				if (_hooks != null) {
					return _hooks.canPerform(gs, agent, ts, tile);
				}
				return true;
			}

			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				for (int i = 0; i < _consumesTypes.length; i++) {
					gs.getInventory().removeItem(_consumesTypes[i],
							_consumesQuantities[i]);
				}

				if (_hooks != null) {
					_hooks.beforeAction(gs, agent, ts, tile);
				}
			}

			@Override
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile, MonsterManager monsterManager) {
				gs.getInventory().addItem(_produces);

				if (_hooks != null) {
					_hooks.afterAction(gs, agent, ts, tile, monsterManager);
				}
			}
		};
	}

	public ActionManager() {
		this.inventoryActions = new ArrayList<Action>();
		this.tileActions = new ArrayList<Action>();

		/** Inventory-based. These should appear near the inventory in the UI. **/

		this.inventoryActions.add(new Action("Eat Fish", "eating a fish", 10,
				createConsumeAction(ItemType.FISH, 0, 0, 30)));
		this.inventoryActions.add(new Action("Eat Cake", "eating cake", 10,
				createConsumeAction(ItemType.SNACK, 0, 0, 30)));
		this.inventoryActions.add(new Action("Eat Berries", "eating berries",
				10, createConsumeAction(ItemType.BERRIES, 0, 0, 15)));
		this.inventoryActions.add(new Action("Drink Water", "drinking water",
				3, createConsumeAction(ItemType.WATER, 0, 20, 0,
						new BaseActionable() {
							@Override
							public void beforeAction(GameSession gs,
									Agent agent, TileSystem ts, Tile tile) {
								gs.getInventory().addItem(ItemType.LEAF);
							}
						})));

		this.inventoryActions.add(new Action("Make Mud Brick",
				"making a mud brick", 10, createCraftAction(new ItemType[] {
						ItemType.MUD, ItemType.GRASS }, new int[] { 1, 1 },
						ItemType.BRICK, new BaseActionable() {
							@Override
							public void afterAction(GameSession gs,
									Agent agent, TileSystem ts, Tile tile,
									MonsterManager monsterManager) {
								SoundManager.playSound("squelch");
							}
						})));

		this.inventoryActions.add(new Action("Build Hut", "building a hut", 60,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						gs.getInventory().removeItem(ItemType.BRICK, 5);
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						tile.addSprite(SpriteType.HUT);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						if (gs.getInventory().getItemCount(ItemType.BRICK) >= 5
								&& tile.id != TileId.WATER) {
							return true;
						}
						return false;
					}

				}));

		this.inventoryActions.add(new Action("Light Stick", "lighting a stick",
				20, createCraftAction(new ItemType[] { ItemType.STICK,
						ItemType.ROCK, ItemType.OIL, ItemType.CLOTH },
						new int[] { 1, 1, 1, 1, }, ItemType.FIRESTICK)));

		this.inventoryActions.add(new Action("Eat Corpsicle",
				"eating a corpsicle", 20, createConsumeAction(ItemType.MEAT, 0,
						0, 40)));

		this.inventoryActions.add(new Action("Eat Corpse", "eating a corpse",
				60, createConsumeAction(ItemType.CORPSE, 0, 0, 20)));

		this.inventoryActions.add(new Action("Make Sail", "making a sail", 20,
				createCraftAction(new ItemType[] { ItemType.CLOTH },
						new int[] { 2 }, ItemType.SAIL)));

		this.inventoryActions.add(new Action("Make Cloth", "making some cloth",
				30, createCraftAction(new ItemType[] { ItemType.WEB },
						new int[] { 5 }, ItemType.CLOTH)));

		this.inventoryActions.add(new Action("Make Axe", "making an axe", 15,
				createCraftAction(new ItemType[] { ItemType.STICK,
						ItemType.METAL, ItemType.VINE }, new int[] { 1, 1, 1 },
						ItemType.AXE)));

		this.inventoryActions.add(new Action("Make Spear", "making a spear",
				15, createCraftAction(new ItemType[] { ItemType.STICK,
						ItemType.ROCK, ItemType.VINE }, new int[] { 1, 1, 1 },
						ItemType.SPEAR)));

		this.inventoryActions.add(new Action("Build Raft", "building a raft",
				180, new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						gs.getInventory().removeItem(ItemType.VINE, 3);
						gs.getInventory().removeItem(ItemType.SAIL, 1);
						gs.getInventory().removeItem(ItemType.STICK, 10);
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						gs.setCompletionType(1);
						gs.setCompleted(true);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (gs.getInventory().getItemCount(ItemType.VINE) >= 3
								&& gs.getInventory()
										.getItemCount(ItemType.SAIL) >= 1
								&& gs.getInventory().getItemCount(
										ItemType.STICK) >= 10 && tile.id == TileId.WATER);
					}

				}));

		this.inventoryActions.add(new Action("Build Boat", "building a boat",
				240, new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						gs.getInventory().removeItem(ItemType.VINE, 3);
						gs.getInventory().removeItem(ItemType.SAIL, 1);
						gs.getInventory().removeItem(ItemType.STICK, 10);
						gs.getInventory().removeItem(ItemType.METAL, 10);
						gs.getInventory().removeItem(ItemType.MUD, 5);
						gs.getInventory().removeItem(ItemType.PLANK, 25);
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						gs.setCompletionType(2);
						gs.setCompleted(true);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (gs.getInventory().getItemCount(ItemType.VINE) >= 3
								&& gs.getInventory()
										.getItemCount(ItemType.SAIL) >= 1
								&& gs.getInventory().getItemCount(
										ItemType.STICK) >= 10
								&& gs.getInventory().getItemCount(
										ItemType.METAL) >= 10
								&& gs.getInventory().getItemCount(ItemType.MUD) >= 5
								&& gs.getInventory().getItemCount(
										ItemType.PLANK) >= 25

						&& tile.id == TileId.WATER);
					}

				}));

		this.inventoryActions.add(new Action("Build Plane", "building a plane",
				300, new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						gs.getInventory().removeItem(ItemType.VINE, 3);
						gs.getInventory().removeItem(ItemType.SAIL, 1);
						gs.getInventory().removeItem(ItemType.STICK, 10);
						gs.getInventory().removeItem(ItemType.METAL, 10);
						gs.getInventory().removeItem(ItemType.MUD, 5);
						gs.getInventory().removeItem(ItemType.PLANK, 25);
						gs.getInventory().removeItem(ItemType.OIL, 5);
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						gs.setCompletionType(3);
						gs.setCompleted(true);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (gs.getInventory().getItemCount(ItemType.VINE) >= 3
								&& gs.getInventory()
										.getItemCount(ItemType.SAIL) >= 1
								&& gs.getInventory().getItemCount(
										ItemType.STICK) >= 10
								&& gs.getInventory().getItemCount(
										ItemType.METAL) >= 10
								&& gs.getInventory().getItemCount(ItemType.MUD) >= 5
								&& gs.getInventory().getItemCount(
										ItemType.PLANK) >= 25
								&& gs.getInventory().getItemCount(ItemType.OIL) >= 5
								&& gs.getInventory().getItemCount(
										ItemType.FLIGHT) >= 1

						&& tile.id == TileId.SNOW);
					}

				}));

		this.inventoryActions.add(new Action("Build Spacecraft",
				"building a spacecraft", 360, new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						gs.getInventory().removeItem(ItemType.VINE, 3);
						gs.getInventory().removeItem(ItemType.SAIL, 1);
						gs.getInventory().removeItem(ItemType.STICK, 10);
						gs.getInventory().removeItem(ItemType.METAL, 10);
						gs.getInventory().removeItem(ItemType.MUD, 5);
						gs.getInventory().removeItem(ItemType.PLANK, 25);
						gs.getInventory().removeItem(ItemType.OIL, 5);
						gs.getInventory().removeItem(ItemType.ARTIFACT, 1);
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						gs.setCompletionType(4);
						gs.setCompleted(true);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (gs.getInventory().getItemCount(ItemType.VINE) >= 3
								&& gs.getInventory()
										.getItemCount(ItemType.SAIL) >= 1
								&& gs.getInventory().getItemCount(
										ItemType.STICK) >= 10
								&& gs.getInventory().getItemCount(
										ItemType.METAL) >= 10
								&& gs.getInventory().getItemCount(ItemType.MUD) >= 5
								&& gs.getInventory().getItemCount(
										ItemType.PLANK) >= 25
								&& gs.getInventory().getItemCount(ItemType.OIL) >= 5
								&& gs.getInventory().getItemCount(
										ItemType.ARTIFACT) >= 1
								&& gs.getInventory().getItemCount(
										ItemType.FLIGHT) >= 1

						&& tile.id == TileId.SNOW);
					}

				}));

		/** Tile-Specific. These could appear around the player. **/

		// These are all hard-coded for now!
		this.tileActions.add(new Action("Pick Grass", "picking some grass", 6,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						if (tile.id == TileId.GRASS) {
							SoundManager.playSound("pick_flower");
							gs.getInventory().addItem(ItemType.GRASS);
							ts.setTileID(tile.x, tile.y, TileId.DIRT);
							agent.decFood(5);
						}
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (tile.id == TileId.GRASS);
					}

				}));

		this.tileActions.add(new Action("Drink Seawater", "drinking seawater",
				3, new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						agent.decWater(10);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (tile.id == TileId.WATER);
					}

				}));

		this.tileActions.add(new Action("Drink Pondwater",
				"drinking pondwater", 3, new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {

						agent.incWater(20);
						if (tile.attrHealth > 0) {
							tile.attrHealth -= 2;
							if (tile.attrHealth == 0) {
								ts.setTileID(tile.x, tile.y, TileId.DIRT);
							}
						}
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (tile.id == TileId.POND);
					}

				}));

		this.tileActions.add(new Action("Take Dirt", "digging up dirt", 3,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						SoundManager.playSound("digging");
						gs.getInventory().addItem(ItemType.MUD);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (tile.id == TileId.DIRT);
					}

				}));

		this.inventoryActions.add(new Action("Burn Corpse", "burning a corpse",
				15, new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						gs.getInventory().removeItem(ItemType.CORPSE);
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						gs.getInventory().addItem(ItemType.MEAT);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (gs.getInventory().getItemCount(ItemType.CORPSE) > 0 && tile
								.hasSprite(SpriteType.FIRE));
					}
				}));

		this.tileActions.add(new Action("Start Fire", "starting a fire", 10,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						gs.getInventory().removeItem(ItemType.FIRESTICK);
						gs.getInventory().addItem(ItemType.STICK);
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {

						if (tile.hasSprite(SpriteType.CORPSE))
							gs.getInventory().addItem(ItemType.MEAT);
						if (tile.hasSprite(SpriteType.HUT))
							gs.getInventory().addItem(ItemType.MUD);

						tile.clearAllSprites();
						tile.addSprite(SpriteType.FIRE);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						if (gs.getInventory().getItemCount(ItemType.FIRESTICK) == 0) {
							return false;
						}
						return (tile.id != TileId.SNOW
								&& tile.id != TileId.WATER && tile.id != TileId.WALL);
					}
				}));

		this.tileActions.add(new Action("Salvage Parts", "salvaging parts", 30,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						if (tile.hasSprite(SpriteType.WRECKAGE)) {
							gs.getInventory().addItem(ItemType.METAL);
							tile.getSpriteData(SpriteType.WRECKAGE).health -= 5;
						}
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (tile.hasSprite(SpriteType.WRECKAGE));
					}
				}));

		this.tileActions.add(new Action("Take Corpse", "taking a corpse", 5,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						if (tile.hasSprite(SpriteType.CORPSE)) {
							gs.getInventory().addItem(ItemType.CORPSE);
							tile.getSpriteData(SpriteType.CORPSE).health = 0;
							tile.addSprite(SpriteType.SKELETON);
						}
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (tile.hasSprite(SpriteType.CORPSE));
					}

				}));

		this.tileActions.add(new Action("Get Rock", "getting a rock", 3,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						if (tile.attrHealth > 0) {
							SoundManager.playSound("digging");
							tile.attrHealth -= 5;
							if (tile.attrHealth <= 0) {
								ts.setTileID(tile.x, tile.y, TileId.DIRT);
							}
							gs.getInventory().addItem(ItemType.ROCK);
						}
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (tile.id == TileId.ROCK);
					}

				}));

		this.tileActions.add(new Action("Find Berries", "finding berries", 5,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						if (tile.hasSprite(SpriteType.SHRUB)) {
							SoundManager.playSound("pick_flower");
							gs.getInventory().addItem(ItemType.BERRIES);

							tile.getSpriteData(SpriteType.SHRUB).health -= 5;
							if (tile.getSpriteData(SpriteType.SHRUB).health == 0) {
								spawnTileObject(TileId.GRASS, SpriteType.SHRUB,
										1, false, ts);
							}
						}
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (tile.hasSprite(SpriteType.SHRUB));
					}

				}));

		this.tileActions.add(new Action("Chop Tree", "chopping a tree", 30,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {

						if (tile.hasSprite(SpriteType.TREE)) {
							spawnTileObject(TileId.GRASS, SpriteType.TREE, 1,
									true, ts);
							tile.getSpriteData(SpriteType.TREE).health = 0;
							gs.getInventory().addItem(ItemType.PLANK, 5);
						}
						if (tile.hasSprite(SpriteType.PINE_TREE)) {
							spawnTileObject(TileId.ROCK, SpriteType.PINE_TREE,
									1, true, ts);
							tile.getSpriteData(SpriteType.PINE_TREE).health = 0;
							gs.getInventory().addItem(ItemType.PLANK, 5);
						}
						if (tile.hasSprite(SpriteType.PALM_TREE)) {
							spawnTileObject(TileId.DIRT, SpriteType.PALM_TREE,
									1, true, ts);
							tile.getSpriteData(SpriteType.PALM_TREE).health = 0;
							gs.getInventory().addItem(ItemType.PLANK, 5);
						}
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {

						return ((tile.hasSprite(SpriteType.PALM_TREE)
								|| tile.hasSprite(SpriteType.PINE_TREE) || tile
								.hasSprite(SpriteType.TREE)) && gs
								.getInventory().getItemCount(ItemType.AXE) >= 1);
					}

				}));

		this.tileActions.add(new Action("Find Artifacts", "finding artifacts",
				60, new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						if (tile.hasSprite(SpriteType.ALIEN_ARTIFACT)) {
							gs.getInventory().addItem(ItemType.ARTIFACT);
							tile.getSpriteData(SpriteType.ALIEN_ARTIFACT).health = 0;
						}
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (tile.hasSprite(SpriteType.ALIEN_ARTIFACT));
					}

				}));
		this.tileActions.add(new Action("Get Water", "getting water", 3,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						gs.getInventory().removeItem(ItemType.LEAF);
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						gs.getInventory().addItem(ItemType.WATER);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (gs.getInventory().getItemCount(ItemType.LEAF) >= 1 && (tile.id == TileId.POND || tile.id == TileId.WATER));
					}

				}));

		this.tileActions.add(new Action("Get Oil", "getting oil", 3,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						gs.getInventory().removeItem(ItemType.LEAF);
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						gs.getInventory().addItem(ItemType.OIL);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (tile.id == TileId.TARPIT && gs.getInventory()
								.getItemCount(ItemType.LEAF) >= 1);
					}

				}));

		this.tileActions.add(new Action("Take Stick", "taking a stick", 3,
				new IActionable() {

					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {

					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						if (tile.hasSprite(SpriteType.TREE)
								|| tile.hasSprite(SpriteType.PINE_TREE)
								|| tile.hasSprite(SpriteType.PALM_TREE)) {
							SoundManager.playSound("stick_crack");
							if (Math.random() < 0.9) {
								gs.getInventory().addItem(ItemType.STICK);
							} else {
								gs.getInventory().addItem(ItemType.LEAF);
							}

							int h = 0;
							if (tile.hasSprite(SpriteType.TREE))
								h = tile.getSpriteData(SpriteType.TREE).health--;
							if (tile.hasSprite(SpriteType.PINE_TREE))
								h = tile.getSpriteData(SpriteType.PINE_TREE).health--;
							if (tile.hasSprite(SpriteType.PALM_TREE))
								h = tile.getSpriteData(SpriteType.PALM_TREE).health--;

							if (h == 0) {
								if (tile.hasSprite(SpriteType.TREE))
									spawnTileObject(TileId.GRASS,
											SpriteType.TREE, 1, true, ts);
								if (tile.hasSprite(SpriteType.PINE_TREE))
									spawnTileObject(TileId.ROCK,
											SpriteType.PINE_TREE, 1, true, ts);
								if (tile.hasSprite(SpriteType.PALM_TREE))
									spawnTileObject(TileId.DIRT,
											SpriteType.PALM_TREE, 1, true, ts);
							}
						}
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (tile.hasSprite(SpriteType.PALM_TREE)
								|| tile.hasSprite(SpriteType.PINE_TREE) || tile
								.hasSprite(SpriteType.TREE));
					}

				}));

		this.tileActions.add(new Action("Take Web", "taking some webs", 5,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {

					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						gs.getInventory().addItem(ItemType.WEB);
						tile.getSpriteData(SpriteType.WEBBED_TREE).type = SpriteType.TREE;
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return tile.hasSprite(SpriteType.WEBBED_TREE);
					}

				}));

		this.tileActions.add(new Action("Take Vine", "taking a vine", 10,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {

					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						if (tile.hasSprite(SpriteType.PALM_TREE)) {
							gs.getInventory().addItem(ItemType.VINE);
							int h = (tile.getSpriteData(SpriteType.PALM_TREE).health -= 5);
							if (h == 0) {
								spawnTileObject(TileId.DIRT,
										SpriteType.PALM_TREE, 1, true, ts);
							}
						}
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return tile.hasSprite(SpriteType.PALM_TREE);
					}

				}));

		this.tileActions.add(new Action("Catch Fish", "catching a fish", 10,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {

					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						gs.getInventory().addItem(ItemType.FISH);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return tile.id == TileId.WATER
								&& gs.getInventory().getItemCount(
										ItemType.SPEAR) >= 1;
					}

				}));

		this.tileActions.add(new Action("Sacrifice Another",
				"sacrificing someone", 120, new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						gs.getInventory().removeItem(ItemType.CORPSE);
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {

						SoundManager.playSound("chant");
						gs.getInventory().addItem(ItemType.FLIGHT);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (gs.getInventory().getItemCount(ItemType.CORPSE) >= 1 && tile
								.hasSprite(SpriteType.ALTAR));
					}

				}));

		this.tileActions.add(new Action("Sacrifice Yourself",
				"sacrificing themselves", 120, new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						gs.getInventory().addItem(ItemType.FLIGHT);
						agent.setState(AgentState.DEAD);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (tile.hasSprite(SpriteType.ALTAR));
					}

				}));

		this.tileActions.add(new Action("Sleep", "sleeping", 0,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						agent.setState(AgentState.SLEEPING);

					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return tile.hasSprite(SpriteType.HUT);
					}

				}));

		this.tileActions.add(new Action("Explore Cave", "Exploring Cave", 50,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {

					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						if (tile.hasSprite(SpriteType.CAVE)) {
							Random r = new Random();
							if (r.nextDouble() < 0.7) {
								gs.getInventory().addItem(ItemType.SAIL);
								gs.getInventory().addItem(ItemType.OIL);
								gs.getInventory().addItem(ItemType.WEB);
								gs.getInventory().addItem(ItemType.AXE);
							} else {
								try {
									monsterManager.spawnMassiveMonster(tile.x,
											tile.y - 1);
								} catch (SlickException e) {
								}

							}
							tile.getSpriteData(SpriteType.CAVE).health = 0;
						}
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return tile.hasSprite(SpriteType.CAVE);
					}

				}));

		this.inventoryActions.add(new Action("Demo", "running a demo", 3,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						gs.getInventory().addItem(ItemType.VINE, 3);
						gs.getInventory().addItem(ItemType.SAIL, 1);
						gs.getInventory().addItem(ItemType.STICK, 10);
						gs.getInventory().addItem(ItemType.METAL, 10);
						gs.getInventory().addItem(ItemType.MUD, 5);
						gs.getInventory().addItem(ItemType.PLANK, 25);
						gs.getInventory().addItem(ItemType.OIL, 5);
						gs.getInventory().addItem(ItemType.ARTIFACT, 1);
						gs.getInventory().addItem(ItemType.FLIGHT, 1);
						gs.getInventory().addItem(ItemType.CLOTH, 10);
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return true;
					}

				}));

	}

	public ArrayList<Action> getValidActions(GameSession gs, TileSystem ts,
			Tile tile, Agent agent) {
		ArrayList<Action> validActions = new ArrayList<Action>();
		for (Action tileAction : tileActions) {
			if (tileAction.getActionable().canPerform(gs, agent, ts, tile)) {
				validActions.add(tileAction);
			}
		}
		for (Action inventoryAction : inventoryActions) {
			if (inventoryAction.getActionable().canPerform(gs, agent, ts, tile)) {
				validActions.add(inventoryAction);
			}
		}
		return validActions;
	}

	private void spawnTileObject(TileId tileType, SpriteType spriteType,
			int treeCount, boolean preferGroupings, TileSystem ts) {
		Random randomGenerator = new Random();
		while (true) {
			int x = randomGenerator.nextInt(ts.getSize() - 2) + 1;
			int y = randomGenerator.nextInt(ts.getSize() - 2) + 1;
			Tile tile = ts.getTile(x, y);
			if (tile.id == tileType && tile.numSprites() == 0
					&& tile.variant == 0) {
				float surroundTree = 1;
				if (ts.getTile(x + 1, y).hasSprite(spriteType))
					surroundTree++;
				if (ts.getTile(x - 1, y).hasSprite(spriteType))
					surroundTree++;
				if (ts.getTile(x, y + 1).hasSprite(spriteType))
					surroundTree++;
				if (ts.getTile(x, y - 1).hasSprite(spriteType))
					surroundTree++;
				float num = (float) randomGenerator.nextInt(100);
				if (preferGroupings)
					num /= surroundTree;
				else
					num /= 1.25;
				num *= surroundTree;

				if (num > 50) {
					treeCount -= 1;
					tile.addSprite(spriteType);
				}
			}

			if (treeCount == 0)
				return;
		}

	}
}
