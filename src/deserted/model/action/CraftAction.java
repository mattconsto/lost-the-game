package deserted.model.action;

import deserted.model.Agent;
import deserted.model.Inventory;
import deserted.model.item.ItemStack;
import deserted.model.item.ItemType;

public class CraftAction extends BaseAction implements IAction {
	private ItemType product;
	private ItemStack[] components;

	private static ItemStack[] typesToStacks(ItemType[] itemTypes) {

		ItemStack[] _stacks = new ItemStack[itemTypes.length];
		for (int i = 0; i < itemTypes.length; i++) {
			_stacks[i] = new ItemStack(itemTypes[i], 1);
		}
		return _stacks;
	}

	public CraftAction(String name, String desc, int duration,
			ItemType product, ItemStack[] stacks) {
		super(name, desc, duration);
		this.setProduct(product);
		this.setStacks(stacks);
	}

	public CraftAction(String name, String desc, int duration,
			ItemType product, ItemType[] itemTypes) {
		this(name, desc, duration, product, typesToStacks(itemTypes));
	}

	public CraftAction(String name, String desc, int duration,
			ItemType[] itemTypes) {
		this(name, desc, duration, null, typesToStacks(itemTypes));
	}

	public CraftAction(String name, String desc, int duration,
			ItemStack[] stacks) {
		this(name, desc, duration, null, stacks);
	}

	public void onStart(Agent crafter) {
		for (ItemStack stack : getStacks()) {
			crafter.getInventory().removeItem(stack.getItemType());
		}
	}

	public void onComplete(Agent crafter) {
		if (product != null) {
			crafter.getInventory().addItem(getProduct());
		}
	}

	public boolean canPerform(Agent crafter) {
		Inventory inventory = crafter.getInventory();
		for (ItemStack stack : this.getStacks()) {
			if (inventory.getItemCount(stack.getItemType()) < stack
					.getQuantity()) {
				return false;
			}
		}
		return true;
	}

	public ItemStack[] getStacks() {
		return components;
	}

	public void setStacks(ItemStack[] components) {
		this.components = components;
	}

	public ItemType getProduct() {
		return product;
	}

	public void setProduct(ItemType product) {
		this.product = product;
	}

}
