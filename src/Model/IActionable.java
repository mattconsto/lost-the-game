package Model;

import Player.MonsterManager;
import TileSystem.Tile;
import TileSystem.TileSystem;

public interface IActionable {
	void beforeAction(GameSession gs, Agent agent, TileSystem ts, Tile tile);
	void afterAction(GameSession gs, Agent agent, TileSystem ts, Tile tile, MonsterManager monsterManager);
	boolean canPerform(GameSession gs, Agent agent, TileSystem ts, Tile tile);
}
