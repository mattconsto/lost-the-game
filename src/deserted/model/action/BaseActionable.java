package deserted.model.action;

import deserted.model.Agent;
import deserted.model.GameSession;
import deserted.player.MonsterManager;
import deserted.tilesystem.Tile;
import deserted.tilesystem.TileSystem;

public class BaseActionable implements IActionable {

	@Override
	public void beforeAction(GameSession gs, Agent agent, TileSystem ts,
			Tile tile) {
	}

	@Override
	public void afterAction(GameSession gs, Agent agent, TileSystem ts,
			Tile tile, MonsterManager monsterManager) {
	}

	@Override
	public boolean canPerform(GameSession gs, Agent agent, TileSystem ts,
			Tile tile) {
		return true;
	}

}
