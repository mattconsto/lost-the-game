package Model;

public class Agent {
	private int hunger;
	private int thirst;
	private int health;
	
	public Agent(int hunger, int thirst, int health) {
		this.setHunger(hunger);
		this.setThirst(thirst);
		this.setHealth(health);
	}
	
	public Agent() {
		this.setHunger(100);
		this.setThirst(100);
		this.setHealth(100);
	}

	public int getHunger() {
		return hunger;
	}

	public void setHunger(int hunger) {
		this.hunger = hunger;
	}

	public int getThirst() {
		return thirst;
	}

	public void setThirst(int thirst) {
		this.thirst = thirst;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
}
