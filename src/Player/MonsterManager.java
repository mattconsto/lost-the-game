package Player;

import java.util.List;
import java.util.Random;
import java.util.Vector;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Graphics;

import Model.Agent;
import TileSystem.TileSystem;

public class MonsterManager {

	Vector<MonsterUI> monsters = new Vector<MonsterUI>();
	Vector<Agent> monsterAgents;
	List<PlayerUI> players;
	TileSystem ts;
	
	public MonsterManager(TileSystem tsIn, List<PlayerUI> playersIn) throws SlickException
	{
		monsterAgents = new Vector<Agent>();
		monsterAgents.add(new Agent());
		ts = tsIn;
		players = playersIn;
	}
	
	public void render(Graphics g)
	{
		for (MonsterUI monster : monsters)
		{
			monster.render(g);
		}
		
	}
	
	public void update(float delta) throws SlickException
	{
		if (monsters.size() < 2)
		{
			Random randomGenerator = new Random();
			Agent monsterAgent = monsterAgents.get(randomGenerator.nextInt(monsterAgents.size()));
			monsters.addElement(new MonsterUI(monsterAgent, ts, players));
		}
		
		for (MonsterUI monster : monsters)
		{
			monster.update(delta);
		}
	}
	
}
