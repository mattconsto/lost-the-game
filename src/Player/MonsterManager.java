package Player;

import java.util.List;
import java.util.Random;
import java.util.Vector;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Graphics;

import Model.Agent;
import Model.AgentState;
import TileSystem.TileSystem;

public class MonsterManager {

	public Vector<MonsterUI> monsters = new Vector<MonsterUI>();
	List<PlayerUI> players;
	TileSystem ts;
	
	public MonsterManager(TileSystem tsIn, List<PlayerUI> playersIn) throws SlickException
	{

		ts = tsIn;
		players = playersIn;
	}
	
	public void render(Graphics g, float scale, int row)
	{
		for (MonsterUI monster : monsters)
		{
			if(monster.location.y >= row-0.2f && monster.location.y < row+0.8f)
				monster.render(g, scale);
		}
		
	}
	
	public void renderOverlay(Graphics g, float scale)
	{
		for (MonsterUI monster : monsters)
		{
				monster.renderOverlay(g, scale);
		}
		
	}
	
	public void update(float delta) throws SlickException
	{
		if (monsters.size() < 20)
		{
			Random randomGenerator = new Random();
			monsters.addElement(new MonsterUI(new Agent(), ts, players, randomGenerator.nextInt(4) ));
		}
		
		for (MonsterUI monster : new Vector<MonsterUI>(monsters))
		{
			monster.update(delta);
			if (monster.agent.getState() == AgentState.DEAD)
				monsters.remove(monster);
		}
	}
	
	public void spawnMassiveMonster(int x, int y) throws SlickException
	{
		//Random randomGenerator = new Random();
		MonsterUI monster = new MonsterUI(new Agent(), ts, players, 2);
		monster.location = new Vector2f((float)x,(float)y);
		monsters.addElement(monster);
		
	}
	
}
