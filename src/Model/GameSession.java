package Model;
//import java.time.LocalDateTime;
import java.util.ArrayList;



public class GameSession {
	// 30 seconds = 1 hour
	// 1 second = 2 minutes
	private static final int MINS_PER_SEC = 2;
	private static final int NUMBER_AGENTS = 2;
	
	// Play time in seconds
	private double gameTimer;
	// Game time in minutes
	private long timeSurvived;
	// When we 'crashed'
	//private LocalDateTime crashDate;

	private ArrayList<Item> items;
	private ArrayList<Agent> agents;
	
	public GameSession() {
		this.gameTimer = 0;
		this.timeSurvived = 0;
		this.items = new ArrayList<Item>();
		this.agents = new ArrayList<Agent>();
		int year = (int)(2010+(Math.round(Math.random()*5)-10));
		int month = (int)Math.ceil(Math.random()*12);
		int day = (int)Math.ceil(Math.random()*27);
		int hour = (int)(Math.random()*24);
		int minute = (int)(Math.random()*60);
		//this.crashDate = LocalDateTime.of(year, month, day, hour, minute);
		
		for(int i=0; i<NUMBER_AGENTS; i++) {
			getAgents().add(new Agent());
		}
		this.generateInventory();
		
		
	}
	
	public void update(int delta) {
		this.gameTimer += (delta/1000.0);
		this.timeSurvived = (long)Math.floor(gameTimer*MINS_PER_SEC);
	}

	public double getTimeSurvived() {
		return this.timeSurvived;
	}
	
//	public LocalDateTime getDate() {
//		return this.crashDate.plusMinutes(this.timeSurvived);
//	}

	public ArrayList<Agent> getAgents() {
		return agents;
	}

	public ArrayList<Item> getItems() {
		return items;
	}
	
	public void addItem(Item item) {
		items.add(item);
	}
	
	public void removeItem(Item item) {
		items.remove(item);
	}
	
	private void generateInventory() {
		ItemType[] itemTypes = {ItemType.PHONE, ItemType.CLOTH};
		for(ItemType itemType: itemTypes) {
			if(Math.random() > 0.8) {
				addItem(ItemFactory.createItem(itemType));
			}
		}
	}
}
