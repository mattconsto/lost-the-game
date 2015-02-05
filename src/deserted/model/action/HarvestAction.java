package deserted.model.action;

import deserted.model.Agent;
import deserted.model.item.ItemType;
import deserted.sprite.SpriteType;
import deserted.tilesystem.Tile;
import deserted.tilesystem.TileSystem;
import deserted.tilesystem.TileSystem.TileId;

public class HarvestAction extends BaseAction implements IAction {

	private TileId beforeTileId;
	private TileId afterTileId;
	private SpriteType beforeSpriteType;
	private SpriteType afterSpriteType;
	private int harvests;
	private ItemType harvestType;

	public HarvestAction(String name, String desc, int duration,
			ItemType harvestType, TileId beforeTileId, TileId afterTileId,
			int harvests) {
		super(name, desc, duration);
		this.harvestType = harvestType;
		this.beforeTileId = beforeTileId;
		this.afterTileId = afterTileId;
		this.harvests = harvests;
	}

	public HarvestAction(String name, String desc, int duration,
			ItemType harvestType, SpriteType beforeSpriteType,
			SpriteType afterSpriteType, int harvests) {
		super(name, desc, duration);
		this.harvestType = harvestType;
		this.beforeSpriteType = beforeSpriteType;
		this.afterSpriteType = afterSpriteType;
		this.harvests = harvests;
	}

	public HarvestAction(String name, String desc, int duration,
			ItemType harvestType, TileId beforeTileId, TileId afterTileId) {
		this(name, desc, duration, harvestType, beforeTileId, afterTileId, 0);
	}

	public HarvestAction(String name, String desc, int duration,
			ItemType harvestType, SpriteType beforeSpriteType,
			SpriteType afterSpriteType) {
		this(name, desc, duration, harvestType, beforeSpriteType,
				afterSpriteType, 0);
	}

	@Override
	public void onStart(Agent crafter) {
	}

	@Override
	public void onComplete(Agent crafter) {
		// In some cases the agent immediately consumes the item.
		if (harvestType != null) {
			crafter.getInventory().addItem(harvestType);
		}
		// If harvests is 0, you can harvest as many times as you like.
		if (harvests > 0) {
			Tile tile = crafter.getTile();
			if (beforeSpriteType != null) {
				tile.getSpriteData(beforeSpriteType).health -= (10 / harvests);
				if (tile.getSpriteData(beforeSpriteType).health <= 0) {
					tile.removeSprite(beforeSpriteType);
					tile.addSprite(afterSpriteType);
					tile.getSpriteData(afterSpriteType).health = 10;
				}

			} else if (beforeTileId != null) {
				tile.attrHealth -= (10 / harvests);
				if (tile.attrHealth <= 0) {
					TileSystem.getInstance().setTileID(tile.x, tile.y,
							afterTileId);
					tile.attrHealth = 10;
				}
			}
		}
	}

	@Override
	public boolean canPerform(Agent crafter) {
		return crafter.getTile().id == beforeTileId
				|| crafter.getTile().hasSprite(beforeSpriteType);
	}

	public TileId getBeforeTileId() {
		return beforeTileId;
	}

	public void setBeforeTileId(TileId beforeTileId) {
		this.beforeTileId = beforeTileId;
	}

	public TileId getAfterTileId() {
		return afterTileId;
	}

	public void setAfterTileId(TileId afterTileId) {
		this.afterTileId = afterTileId;
	}

	public SpriteType getBeforeSpriteType() {
		return beforeSpriteType;
	}

	public void setBeforeSpriteType(SpriteType beforeSpriteType) {
		this.beforeSpriteType = beforeSpriteType;
	}

	public SpriteType getAfterSpriteType() {
		return afterSpriteType;
	}

	public void setAfterSpriteType(SpriteType afterSpriteType) {
		this.afterSpriteType = afterSpriteType;
	}

	public int getHarvests() {
		return harvests;
	}

	public void setHarvests(int harvests) {
		this.harvests = harvests;
	}

	public ItemType getHarvestType() {
		return harvestType;
	}

	public void setHarvestType(ItemType harvestType) {
		this.harvestType = harvestType;
	}

}
