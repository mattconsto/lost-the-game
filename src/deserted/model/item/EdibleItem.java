package deserted.model.item;

public class EdibleItem extends Item {
	private int water;
	private int food;
	private int health;
	
	public EdibleItem(String name, String imageName, int water, int food, int health) {
		super(name, imageName);
		this.water = water;
		this.food = food;
		this.health = health;
	}
	
	public EdibleItem(String name, String imageName, int water, int food) {
		this(name, imageName, water, food, 0);
	}
	
	public EdibleItem(String name, int water, int food, int health) {
		this(name, null, water, food, health);
	}
	
	public EdibleItem(String name, int water, int food) {
		this(name, water, food, 0);
	}

	public int getWater() {
		return water;
	}

	public void setWater(int water) {
		this.water = water;
	}

	public int getFood() {
		return food;
	}

	public void setFood(int food) {
		this.food = food;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	
	
}
