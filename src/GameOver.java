import java.awt.Font;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import Model.GameSession;
import Player.PlayerUI;


public class GameOver extends BasicGameState implements GameState {
	public static final int STATE_OVER = 2;
	
	Image image;
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub
		image = new Image("lose-nograd.png");
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
		
		
		
		/*g.setColor(Color.gray);
		g.fillRect(0, 0, container.getWidth(), container.getHeight());
		g.setColor(Color.black);*/
		
		play.renderWorld(g);
		g.setColor(Color.white);
		
		image.draw(0, 0, container.getWidth(), container.getHeight());
		
		float offset=0;
		for (PlayerUI player : play.players)
		{
			g.drawString(player.agent.getName() + " died of exposure after only " + Math.floor(player.agent.getExpiredTime()) + " hours.", 10, scroller+offset);
			offset-=30;
		}
		
		g.drawString("press Escape to exit or Enter to start again", container.getWidth()/2-250, container.getHeight()/2 + 300);
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
		return STATE_OVER;
	}
}
