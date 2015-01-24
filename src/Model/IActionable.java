package Model;

import java.util.List;

import Player.PlayerUI;
import TileSystem.TileSystem;
import TileSystem.TileSystem.TileId;

public interface IActionable {
	void performAction(GameSession gs, Agent agent, TileSystem ts, PlayerUI pui);
	boolean canPerform(GameSession gs, Agent agent, TileSystem ts, TileId tile,
			List<Item> selectedItems);
}
