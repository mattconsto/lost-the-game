package Model;

import java.util.Random;

public class Agent {
	private int hunger;
	private int thirst;
	private int health;
	private String name;
	
	public Agent(int hunger, int thirst, int health) {
		this.setHunger(hunger);
		this.setThirst(thirst);
		this.setHealth(health);
		this.generateName();
	}
	
	private void generateName() {
		String[] names = {"Bill", "Joe", "Jane", "Andy", "Ollie", "Mike", "Jonah", "Sam", "Joanne", "Suzy", "Lucy", "Mary", "Betsy"};
		this.setName(names[new Random().nextInt(names.length)]);
	}

	public Agent() {
		this.setHunger(100);
		this.setThirst(100);
		this.setHealth(100);
		this.generateName();
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
