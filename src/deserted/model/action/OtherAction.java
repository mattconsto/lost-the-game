package deserted.model.action;

import deserted.model.Agent;

public class OtherAction extends BaseAction implements IAction {

	public OtherAction(String name, String description, int duration) {
		super(name, description, duration);
	}

	@Override
	public void onStart(Agent crafter) {
	}

	@Override
	public void onComplete(Agent crafter) {
	}

	@Override
	public boolean canPerform(Agent crafter) {
		return true;
	}

}
