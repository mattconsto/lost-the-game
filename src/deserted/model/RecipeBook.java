package deserted.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import deserted.model.action.BaseAction;
import deserted.model.action.CraftAction;
import deserted.model.action.EatAction;
import deserted.model.action.HarvestAction;
import deserted.model.action.OtherAction;
import deserted.model.item.EdibleItem;
import deserted.model.item.ItemFactory;
import deserted.model.item.ItemStack;
import deserted.model.item.ItemType;
import deserted.sprite.SpriteType;
import deserted.tilesystem.Tile;
import deserted.tilesystem.TileSystem;
import deserted.tilesystem.TileSystem.TileId;

/*
 * Recipes are a sort of action that operate on items in the inventory: this is distinct 
 * from TileActions (where the user acts on a tile and get something) or EatActions 
 * (where the user consumes an item).
 */
public class RecipeBook {

	private ArrayList<BaseAction> actions;

	public void addAction(BaseAction action) {
		actions.add(action);
	}

	public RecipeBook() {
		actions = new ArrayList<BaseAction>();
		/**
		 * Eating actions: rely on an item being in the user's inventory.
		 */
		addAction(new EatAction("Eat Fish", "eating a fish", 10, ItemType.FISH));
		addAction(new EatAction("Eat Cake", "eating cake", 10, ItemType.SNACK));
		addAction(new EatAction("Eat Berries", "eating berries", 10,
				ItemType.BERRIES));
		addAction(new EatAction("Eat Corpsicle", "eating a corpsicle", 10,
				ItemType.MEAT));
		addAction(new EatAction("Eat Corpse", "eating a corpse", 60,
				ItemType.CORPSE));
		addAction(new EatAction("Drink Water", "drinking water", 3,
				ItemType.WATER) {
			@Override
			public void onStart(Agent consumer) {
				super.onStart(consumer);
				consumer.getInventory().addItem(ItemType.LEAF);
			}
		});

		addAction(new HarvestAction("Pick Grass", "picking some grass", 6,
				ItemType.GRASS, TileId.GRASS, TileId.DIRT, 1));
		addAction(new HarvestAction("Take Dirt", "digging up dirt", 3,
				ItemType.MUD, TileId.DIRT, TileId.DIRT));
		addAction(new HarvestAction("Salvage Parts", "salvaging parts", 30,
				ItemType.METAL, SpriteType.WRECKAGE, SpriteType.NONE, 2));
		addAction(new HarvestAction("Find Artifacts", "finding artifacts", 60,
				ItemType.ARTIFACT, SpriteType.ALIEN_ARTIFACT, SpriteType.NONE,
				1));
		addAction(new HarvestAction("Get Rock", "getting a rock", 3,
				ItemType.ROCK, TileId.ROCK, TileId.DIRT, 2));
		addAction(new HarvestAction("Find Berries", "finding berries", 5,
				ItemType.BERRIES, SpriteType.SHRUB, SpriteType.NONE, 2));

		SpriteType[] trees = new SpriteType[]{SpriteType.TREE, SpriteType.PALM_TREE, SpriteType.PINE_TREE};
		for(SpriteType tree: trees) {
			addAction(new HarvestAction("Take Stick", "taking a stick", 3, null,
					tree, SpriteType.NONE, 5) {
				@Override
				public void onComplete(Agent harvester) {
					if(Math.random() > 0.8) {
						setHarvestType(ItemType.LEAF);
					}
					else {
						setHarvestType(ItemType.STICK);
					}
					super.onComplete(harvester);
				}
			});
		}
		
		addAction(new HarvestAction("Take Web", "taking some webs", 5,
				ItemType.WEB, SpriteType.WEBBED_TREE, SpriteType.TREE, 1));
		addAction(new HarvestAction("Take Vine", "taking a vine", 10,
				ItemType.VINE, SpriteType.PALM_TREE, SpriteType.NONE, 2));
		addAction(new HarvestAction("Take Corpse", "taking a corpse", 5,
				ItemType.CORPSE, SpriteType.CORPSE, SpriteType.SKELETON, 1));

		addAction(new HarvestAction("Catch Fish", "catching a fish", 10,
				ItemType.FISH, TileId.WATER, TileId.WATER) {
			@Override
			public boolean canPerform(Agent harvester) {
				return super.canPerform(harvester)
						&& harvester.getInventory().hasItem(ItemType.SPEAR);
			}
		});

		addAction(new HarvestAction("Chop Tree", "chopping a tree", 30,
				ItemType.PLANK, SpriteType.TREE, SpriteType.NONE) {
			@Override
			public boolean canPerform(Agent harvester) {
				return super.canPerform(harvester)
						&& harvester.getInventory().hasItem(ItemType.AXE);
			}
		});

		addAction(new HarvestAction("Get Water", "getting water", 3,
				ItemType.WATER, TileId.POND, TileId.DIRT, 5) {

			@Override
			public void onStart(Agent harvester) {
				super.onStart(harvester);
				harvester.getInventory().removeItem(ItemType.LEAF);
			}

			@Override
			public boolean canPerform(Agent harvester) {
				return super.canPerform(harvester)
						&& harvester.getInventory().hasItem(ItemType.LEAF);
			}
		});

		addAction(new HarvestAction("Get Oil", "getting oil", 3, ItemType.OIL,
				TileId.TARPIT, TileId.DIRT, 5) {

			@Override
			public void onStart(Agent harvester) {
				super.onStart(harvester);
				harvester.getInventory().removeItem(ItemType.LEAF);
			}

			@Override
			public boolean canPerform(Agent harvester) {
				return super.canPerform(harvester)
						&& harvester.getInventory().hasItem(ItemType.LEAF);
			}
		});

		/**
		 * Other actions are those that don't perform eating, crafting or
		 * harvesting. Typically interactions with the tile that the user is on.
		 */

		addAction(new OtherAction("Drink Pondwater", "drinking pondwater", 3) {
			@Override
			public boolean canPerform(Agent agent) {
				return agent.getTile().id == TileId.POND;
			}

			@Override
			public void onComplete(Agent agent) {
				Tile tile = agent.getTile();
				tile.attrHealth -= 2;
				if (tile.attrHealth <= 0) {
					TileSystem.getInstance().setTileID(tile.x, tile.y,
							TileId.DIRT);
					tile.attrHealth = 10;
				}

				agent.consume((EdibleItem) ItemFactory
						.createItem(ItemType.WATER));
			}
		});

		addAction(new OtherAction("Drink Seawater", "drinking seawater", 3) {
			@Override
			public boolean canPerform(Agent agent) {
				return agent.getTile().id == TileId.WATER;
			}

			@Override
			public void onComplete(Agent agent) {

				agent.consume((EdibleItem) ItemFactory
						.createItem(ItemType.SEAWATER));
			}
		});

		addAction(new OtherAction("Sleep", "sleeping", 0) {
			@Override
			public void onStart(Agent agent) {
				agent.setState(AgentState.SLEEPING);
			}

			@Override
			public boolean canPerform(Agent agent) {
				return agent.getTile().hasSprite(SpriteType.HUT);
			}
		});

		addAction(new OtherAction("Sacrifice Yourself",
				"sacrificing themselves", 120) {
			@Override
			public void onComplete(Agent agent) {
				agent.getInventory().addItem(ItemType.FLIGHT);
				agent.setState(AgentState.DEAD);
			}

			@Override
			public boolean canPerform(Agent agent) {
				return agent.getTile().hasSprite(SpriteType.ALTAR);
			}
		});

		addAction(new OtherAction("Sacrifice Another", "sacrificing someone",
				120) {

			@Override
			public void onStart(Agent agent) {
				agent.getInventory().removeItem(ItemType.CORPSE);
			}

			@Override
			public void onComplete(Agent agent) {
				agent.getInventory().addItem(ItemType.FLIGHT);
			}

			@Override
			public boolean canPerform(Agent agent) {
				return (agent.getInventory().hasItem(ItemType.CORPSE) && agent
						.getTile().hasSprite(SpriteType.ALTAR));
			}
		});

		addAction(new OtherAction("Explore Cave", "Exploring Cave", 50) {

			@Override
			public void onStart(Agent agent) {
				agent.getInventory().removeItem(ItemType.CORPSE);
			}

			@Override
			public void onComplete(Agent agent) {
				if (agent.getTile().hasSprite(SpriteType.CAVE)) {
					Random r = new Random();
					if (r.nextDouble() < 0.7) {
						agent.getInventory().addItem(ItemType.SAIL);
						agent.getInventory().addItem(ItemType.OIL);
						agent.getInventory().addItem(ItemType.WEB);
						agent.getInventory().addItem(ItemType.AXE);
					} else {
						// try
						// {
						// TODO: Need ref to MonsterManager
						// monsterManager.spawnMassiveMonster(tile.x,
						// tile.y - 1);
						// } catch (SlickException e) {
						// }

					}
					agent.getTile().getSpriteData(SpriteType.CAVE).health = 0;
				}
			}

			@Override
			public boolean canPerform(Agent agent) {
				return agent.getTile().hasSprite(SpriteType.CAVE);
			}
		});

		/**
		 * Craft actions: rely on a few items being in the user's inventory, and
		 * possible them being in a certain place.
		 */

		addAction(new CraftAction("Make Axe", "making an axe", 15,
				ItemType.AXE, new ItemType[] { ItemType.STICK, ItemType.METAL,
						ItemType.VINE }));
		addAction(new CraftAction("Make Spear", "making a spear", 15,
				ItemType.SPEAR, new ItemType[] { ItemType.STICK, ItemType.ROCK,
						ItemType.VINE }));
		addAction(new CraftAction("Make Mud Brick", "making a mud brick", 10,
				ItemType.BRICK, new ItemType[] { ItemType.MUD, ItemType.GRASS }));
		addAction(new CraftAction("Make Sail", "making a sail", 20,
				ItemType.SAIL,
				new ItemType[] { ItemType.CLOTH, ItemType.CLOTH }));
		addAction(new CraftAction("Make Cloth", "making some cloth", 30,
				ItemType.CLOTH,
				new ItemStack[] { new ItemStack(ItemType.WEB, 5) }));
		addAction(new CraftAction("Make Torch", "making a torch", 20,
				ItemType.FIRESTICK, new ItemType[] { ItemType.STICK,
						ItemType.ROCK, ItemType.OIL, ItemType.CLOTH }));

		addAction(new CraftAction("Cook Corpse", "Cooking a corpse", 15,
				ItemType.MEAT, new ItemType[] { ItemType.CORPSE }) {
			@Override
			public boolean canPerform(Agent crafter) {
				return super.canPerform(crafter)
						&& crafter.getTile().hasSprite(SpriteType.FIRE);
			}
		});

		addAction(new CraftAction("Build Hut", "building a hut", 60,
				new ItemStack[] { new ItemStack(ItemType.BRICK, 5) }) {
			@Override
			public void onComplete(Agent crafter) {
				crafter.getTile().addSprite(SpriteType.HUT);
			}

			@Override
			public boolean canPerform(Agent crafter) {
				return super.canPerform(crafter)
						&& crafter.getTile().id != TileId.WATER;
			}
		});

		addAction(new CraftAction("Start Fire", "starting a fire", 10,
				new ItemType[] { ItemType.FIRESTICK }) {

			@Override
			public void onStart(Agent crafter) {
				crafter.getInventory().removeItem(ItemType.FIRESTICK);
				crafter.getInventory().addItem(ItemType.STICK);
			}

			@Override
			public void onComplete(Agent crafter) {
				Tile tile = crafter.getTile();
				if (tile.hasSprite(SpriteType.CORPSE)) {
					crafter.getInventory().addItem(ItemType.MEAT);
				}
				if (tile.hasSprite(SpriteType.HUT)) {
					crafter.getInventory().addItem(ItemType.MUD);
				}
				tile.clearAllSprites();
				tile.addSprite(SpriteType.FIRE);
			}

			@Override
			public boolean canPerform(Agent crafter) {
				return super.canPerform(crafter)
						&& crafter.getTile().id != TileId.SNOW
						&& crafter.getTile().id != TileId.WATER
						&& crafter.getTile().id != TileId.WALL;
			}
		});

		addAction(new CraftAction("Build Raft", "building a raft", 180,
				new ItemStack[] { new ItemStack(ItemType.VINE, 3),
						new ItemStack(ItemType.SAIL),
						new ItemStack(ItemType.STICK, 10) }) {
			@Override
			public void onComplete(Agent crafter) {
				GameSession.getInstance().setCompletionType(1);
				GameSession.getInstance().setCompleted(true);
			}

			@Override
			public boolean canPerform(Agent crafter) {
				return super.canPerform(crafter)
						&& crafter.getTile().id == TileId.WATER;
			}
		});

		addAction(new CraftAction("Build Boat", "building a boat", 240,
				new ItemStack[] { new ItemStack(ItemType.VINE, 3),
						new ItemStack(ItemType.SAIL),
						new ItemStack(ItemType.STICK, 10),
						new ItemStack(ItemType.METAL, 10),
						new ItemStack(ItemType.MUD, 5),
						new ItemStack(ItemType.PLANK, 25) }) {
			@Override
			public void onComplete(Agent crafter) {
				GameSession.getInstance().setCompletionType(2);
				GameSession.getInstance().setCompleted(true);
			}

			@Override
			public boolean canPerform(Agent crafter) {
				return super.canPerform(crafter)
						&& crafter.getTile().id == TileId.WATER;
			}
		});

		addAction(new CraftAction("Build Plane", "building a plane", 300,
				new ItemStack[] { new ItemStack(ItemType.VINE, 3),
						new ItemStack(ItemType.SAIL),
						new ItemStack(ItemType.STICK, 10),
						new ItemStack(ItemType.METAL, 10),
						new ItemStack(ItemType.MUD, 5),
						new ItemStack(ItemType.PLANK, 25),
						new ItemStack(ItemType.OIL, 5),
						new ItemStack(ItemType.FLIGHT) }) {

			@Override
			public void onComplete(Agent crafter) {
				GameSession.getInstance().setCompletionType(3);
				GameSession.getInstance().setCompleted(true);
			}

			@Override
			public boolean canPerform(Agent crafter) {
				return super.canPerform(crafter)
						&& crafter.getTile().id == TileId.SNOW;
			}
		});

		addAction(new CraftAction("Build Spacecraft", "building a spacecraft",
				360, new ItemStack[] { new ItemStack(ItemType.VINE, 3),
						new ItemStack(ItemType.SAIL),
						new ItemStack(ItemType.STICK, 10),
						new ItemStack(ItemType.METAL, 10),
						new ItemStack(ItemType.MUD, 5),
						new ItemStack(ItemType.PLANK, 25),
						new ItemStack(ItemType.OIL, 5),
						new ItemStack(ItemType.FLIGHT),
						new ItemStack(ItemType.ARTIFACT) }) {
			@Override
			public void onComplete(Agent crafter) {
				GameSession.getInstance().setCompletionType(4);
				GameSession.getInstance().setCompleted(true);
			}

			@Override
			public boolean canPerform(Agent crafter) {
				return super.canPerform(crafter)
						&& crafter.getTile().id == TileId.SNOW;
			}
		});
	}

	public List<BaseAction> getValidActions(Agent agent) {
		List<BaseAction> validActions = new ArrayList<BaseAction>();
		for (BaseAction action : actions) {
			if (action.canPerform(agent)) {
				validActions.add(action);
			}
		}
		return validActions;
	}
}
