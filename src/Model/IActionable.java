package Model;

import TileSystem.Tile;
import TileSystem.TileSystem;

public interface IActionable {
	void performAction(GameSession gs, Agent agent, TileSystem ts, Tile tile);
	boolean canPerform(GameSession gs, Agent agent, TileSystem ts, Tile tile);
}
