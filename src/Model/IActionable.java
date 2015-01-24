package Model;

import Player.PlayerUI;
import TileSystem.TileSystem;

public interface IActionable {
	void performAction(GameSession gs, Agent agent, TileSystem ts, PlayerUI pui);
}
