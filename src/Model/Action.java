package Model;

import Player.PlayerUI;
import TileSystem.TileSystem;

public class Action {
	private IActionable actionable;
	private String name;
	
	public Action(String name, IActionable actionable) {
		this.setActionable(actionable);
		this.name = name;
	}
		
	public void perform(GameSession gs, Agent ag, TileSystem ts, PlayerUI pui) {
		this.getActionable().performAction(gs, ag, ts, ts.getTileFromWorld(pui.location.x, pui.location.y));
	}

	public String getName() {
		return this.name;
	}

	public IActionable getActionable() {
		return actionable;
	}

	public void setActionable(IActionable actionable) {
		this.actionable = actionable;
	}
	
}