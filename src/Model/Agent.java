package Model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Agent {
	private float food;
	private float water;
	private float health;
	private String name;
	private boolean walking;
	private boolean drowning;
	
	
	private static int namePos = 0;
	private static boolean shuffled = false;
	
	private String[] names = {"Bill", "Joe", "Jane", "Andy", "Ollie", "Mike", "Jonah", "Sam", "Joanne", "Suzy", "Lucy", "Mary", "Betsy"};
	
	
	public Agent(float food, float water, float health) {
		this();
		this.setFood(food);
		this.setWater(water);
		this.setHealth(health);
	}
	
	private void generateName() {
		if(!shuffled) {
			List<String> nameList = Arrays.asList(names);
			Collections.shuffle(nameList);
			names = nameList.toArray(new String[nameList.size()]);
			shuffled= true;
		}
		this.setName(names[namePos++ % names.length]);
	}

	public Agent() {
		this.setFood(100);
		this.setWater(100);
		this.setHealth(100);
		this.generateName();
		this.setDrowning(false);
	}
	
	public float getFood() {
		return food;
	}

	public void setFood(float food) {
		this.food = food;
	}

	public float getWater() {
		return water;
	}

	public void setWater(float water) {
		this.water = water;
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getWalking() {
		return this.walking;
	}
	
	public void setWalking(boolean walking) {
		this.walking = walking;
	}

	public void decWater(float w) {
		this.water -= w;
		if(this.water < 0) this.water = 0;
	}
	
	public void incWater(float w) {
		this.water += w;
		if(this.water > 100) { this.water = 100; }
	}
	
	public void decFood(float f) {
		this.food -= f;
		if(this.food < 0) this.food = 0;
	}
	
	public void incFood(float f) {
		this.food += f;
		if(this.food > 100) { this.food = 100; }
	}
	public void decHealth(float h) {
		this.health -= h;
		if(this.health < 0) this.health = 0;
	}
	
	public void incHealth(float h) {
		this.health += h;
		if(this.health > 100) { this.health = 100; }
	}

	public boolean isDrowning() {
		return drowning;
	}

	public void setDrowning(boolean drowning) {
		this.drowning = drowning;
	}
}
