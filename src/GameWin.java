import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import Model.AgentState;
import Model.GameSession;
import Player.PlayerUI;


public class GameWin extends BasicGameState implements GameState {
	public static final int STATE_WIN = 3;
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	Play play;
	public void setPlayState(Play playIn)
	{
		play = playIn;
	}
	
	float scroller = 0;
	
	@Override
	public void render(GameContainer container, StateBasedGame state, Graphics g)
			throws SlickException {
		g.setColor(Color.gray);
		g.fillRect(0, 0, container.getWidth(), container.getHeight());
		g.setColor(Color.white);
		play.renderWorld(g);
		
		float offset=0;
		for (PlayerUI player : play.players)
		{
			if (player.agent.getState()== AgentState.DEAD)
			{
				g.drawString(player.agent.getName() + " died of exposure after only " + Math.floor(player.agent.getExpiredTime()) + " hours.", 10, scroller+offset);
			}
			else
			{
				g.drawString(player.agent.getName() + " survived the whole horrific ordeal - " + Math.floor(player.agent.getExpiredTime()) + " hours.", 10, scroller+offset);
			}
			offset-=30;
		}
		GameSession gs = GameSession.getInstance();
		
		if (gs.getCompletionType() == 1)
			g.drawString("Game Over You Win (You could do alot better that a raft)", container.getWidth()/2, container.getHeight()/2);
		else if(gs.getCompletionType() == 2)
			g.drawString("Game Over You Win (You could do better than a boat)", container.getWidth()/2, container.getHeight()/2);
		else if(gs.getCompletionType() == 3)
			g.drawString("Game Over You Win (You could do a bit better than a plane)", container.getWidth()/2, container.getHeight()/2);
		else if(gs.getCompletionType() == 4)
			g.drawString("Game Over You Win (You can't do much better than a SPACE CRAFT!)", container.getWidth()/2, container.getHeight()/2);
		
		//g.drawString("Game Over You Win (You could do better)", container.getWidth()/2, container.getHeight()/2);
		g.drawString("press Escape to exit or Enter to start again", container.getWidth()/2, container.getHeight()/2 + 30);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		if (arg0.getInput().isKeyDown(Input.KEY_ESCAPE)) {
			arg0.exit();
		}
		if (arg0.getInput().isKeyDown(Input.KEY_ENTER)) {
			arg1.enterState(Play.STATE_PLAY);
			arg1.getState(1).init(arg0, arg1);
		}
		
		scroller += ((float)arg2)/100;
	}

	@Override
	public int getID() {
		return STATE_WIN;
	}
}
