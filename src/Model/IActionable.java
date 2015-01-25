package Model;

import TileSystem.Tile;
import TileSystem.TileSystem;

public interface IActionable {
	void beforeAction(GameSession gs, Agent agent, TileSystem ts, Tile tile);
	void afterAction(GameSession gs, Agent agent, TileSystem ts, Tile tile);
	boolean canPerform(GameSession gs, Agent agent, TileSystem ts, Tile tile);
}
