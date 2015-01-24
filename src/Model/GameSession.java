package Model;
import java.util.ArrayList;



public class GameSession {
	// 30 seconds = 1 hour
	// 1 second = 2 minutes
	private static final int MINS_PER_SEC = 2;
	
	// Play time in seconds
	private double gameTimer;
	// Game time in minutes
	private double timeSurvived;

	private ArrayList<Item> items;
	private ArrayList<Agent> agents;
	
	public GameSession() {
		this.gameTimer = 0;
		this.timeSurvived = 0;
		this.items = new ArrayList<Item>();
		this.agents = new ArrayList<Agent>();
	}
	
	public void update(int delta) {
		this.gameTimer += (delta/1000.0);
		this.timeSurvived = Math.floor(gameTimer*MINS_PER_SEC);
	}

	public double getTimeSurvived() {
		return this.timeSurvived;
	}
}
