package deserted.model.action;

import deserted.model.Agent;

public interface IAction {
	public void onStart(Agent crafter);
	public void onComplete(Agent crafter);
	public boolean canPerform(Agent crafter);
}
