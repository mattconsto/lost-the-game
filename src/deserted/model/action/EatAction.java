package deserted.model.action;

import deserted.model.Agent;
import deserted.model.GameSession;
import deserted.model.item.EdibleItem;
import deserted.model.item.ItemFactory;
import deserted.model.item.ItemType;

public class EatAction extends BaseAction implements IAction {

	private EdibleItem edibleItem;
	private ItemType edibleItemType;

	public EatAction(String name, String desc, int duration, ItemType edibleItemType) {
		super(name, desc, duration);
		this.setEdibleItemType(edibleItemType);
		this.edibleItem = (EdibleItem)ItemFactory.createItem(edibleItemType);
	}
	
	@Override
	public void onStart(Agent crafter) {
		crafter.getInventory().removeItem(edibleItemType);
	}
	
	@Override
	public void onComplete(Agent crafter) {
		crafter.consume(edibleItem);
	}

	@Override
	public boolean canPerform(Agent crafter) {
		return crafter.getInventory().hasItem(edibleItemType);
	}

	public ItemType getEdibleItemType() {
		return edibleItemType;
	}

	public void setEdibleItemType(ItemType edibleItemType) {
		this.edibleItemType = edibleItemType;
	}
	
}
