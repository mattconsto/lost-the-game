package deserted.model;

import java.util.ArrayList;

import org.joda.time.LocalDateTime;

public class GameSession {
	private static GameSession instance;

	//private boolean walking = false;
	// Play time in seconds
	private double gameTimer;
	// Game time in minutes
	private double timeSurvived;
	// When we 'crashed'
	private LocalDateTime crashDate;
	private boolean completed;
	private int completionType;
	private Inventory inventory;

	private ArrayList<Agent> agents;

	private GameSession() {
		this.setCompleted(false);
		this.setCompletionType(0);
		this.setInventory(new Inventory());
		this.gameTimer = 0;
		this.timeSurvived = 0;
		this.agents = new ArrayList<Agent>();
		int year = (int) (2010 + (Math.round(Math.random() * 5) - 10));
		int month = (int) Math.ceil(Math.random() * 12);
		int day = (int) Math.ceil(Math.random() * 27);
		int hour = (int) (Math.random() * 24);
		int minute = (int) (Math.random() * 60);
		this.crashDate = new LocalDateTime(year, month, day, hour, minute);

		for (int i = 0; i < GameConfig.NUMBER_AGENTS; i++) {
			getAgents().add(new Agent());
		}
	}

	public static GameSession getInstance() {
		if (instance == null) {
			instance = new GameSession();
		}
		return instance;
	}

	public void update(float delta) {
		this.gameTimer += delta;
		this.timeSurvived = gameTimer * GameConfig.MINS_PER_SEC;
		// Update agent stats
		for (Agent agent : agents) {
			agent.update(delta);
		}
	}

	public double getTimeSurvived() {
		return this.timeSurvived;
	}

	public LocalDateTime getDate() {
		return this.crashDate.plusMinutes((int) Math.floor(this.timeSurvived));
	}

	public ArrayList<Agent> getAgents() {
		return agents;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public int getCompletionType() {
		return completionType;
	}

	public void setCompletionType(int completionType) {
		this.completionType = completionType;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
}
