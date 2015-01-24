package Model;

public abstract class Item {
	public String name;
	
	public void update(int delta) {
		// Override this to handle items that need time info.
	}
	
	public abstract String getImageName();
	public abstract String getItemName();
	public abstract ItemType getType();
}
