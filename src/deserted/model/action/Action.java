package deserted.model.action;


public class Action {
	private IActionable actionable;
	private String name;
	private int duration;
	private String description;
	
	public Action(String name, String description, int duration, IActionable actionable) {
		this.setActionable(actionable);
		this.name = name;
		this.duration = duration;
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
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