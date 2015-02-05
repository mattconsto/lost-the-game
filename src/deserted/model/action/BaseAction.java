package deserted.model.action;


public abstract class BaseAction implements IAction {
	private String name;
	private int duration;
	private String description;
	
	public BaseAction(String name, String description, int duration) {
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

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
}