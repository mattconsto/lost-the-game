package Model;

import Player.PlayerUI;
import TileSystem.TileSystem;

public class Action {
	private IActionable actionable;
	private String name;
	private int duration;
	
	public Action(String name, int duration, IActionable actionable) {
		this.setActionable(actionable);
		this.name = name;
		this.duration = duration;
	}
		
//	public void perform(GameSession gs, Agent ag, TileSystem ts, PlayerUI pui) {
//		this.getActionable().beforeAction(gs, ag, ts, ts.getTileFromWorld(pui.location.x, pui.location.y));
//	}

	public String getName() {
		return this.name;
	}

	public IActionable getActionable() {
		return actionable;
	}

	public void setActionable(IActionable actionable) {
		this.actionable = actionable;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
}