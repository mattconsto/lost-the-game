package Player;

import java.util.List;
import java.util.Random;
import java.util.Vector;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Graphics;

import Model.Agent;
import Model.AgentState;
import TileSystem.TileSystem;

public class MonsterManager {

	public Vector<MonsterUI> monsters = new Vector<MonsterUI>();
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
	
	public void render(Graphics g, float scale, int row)
	{
		for (MonsterUI monster : monsters)
		{
			if((int)monster.location.y == row)
				monster.render(g, scale);
		}
		
	}
	
	public void update(float delta) throws SlickException
	{
		if (monsters.size() < 5)
		{
			Random randomGenerator = new Random();
			Agent monsterAgent = monsterAgents.get(randomGenerator.nextInt(monsterAgents.size()));
			monsters.addElement(new MonsterUI(monsterAgent, ts, players, randomGenerator.nextInt(100) > 50));
		}
		
		for (MonsterUI monster : new Vector<MonsterUI>(monsters))
		{
			monster.update(delta);
			if (monster.agent.getState() == AgentState.DEAD)
				monsters.remove(monster);
		}
	}
	
}
