package deserted;

import org.lwjgl.glfw.GLFW;
import org.newdawn.slick.Color;

import deserted.engine.GameContainer;
import deserted.engine.GameState;
import deserted.engine.StateBasedGame;
import deserted.engine.graphics.Graphics;
import deserted.engine.graphics.Texture;
import deserted.model.AgentState;
import deserted.model.GameSession;
import deserted.player.PlayerUI;

public class GameWin extends GameState {
	public static final int STATE_WIN = 3;
	
	Texture image;
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg){
		image = new Texture("images/backgrounds/win-transparent.png");
		scroller = 0;
	}

	Play play;
	public void setPlayState(Play playIn)
	{
		play = playIn;
	}
	
	float scroller = 0;
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
//		g.setColor(Color.gray);
//		g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
//		g.setColor(Color.white);
//		play.renderWorld(g);
//
//		//image.draw(0, 0, gc.getWidth(), gc.getHeight());
//		
//		g.setColor(Color.white);
//		
//		float offset=0;
//		for (PlayerUI player : play.players)
//		{
//			if (player.agent.getState()== AgentState.DEAD)
//			{
//				g.drawString(player.agent.getName() + " died of exposure after only " + Math.floor(player.agent.getExpiredTime()) + " hours.", 10, scroller+offset);
//			}
//			else
//			{
//				g.drawString(player.agent.getName() + " survived the whole horrific ordeal - " + Math.floor(player.agent.getExpiredTime()) + " hours.", 10, scroller+offset);
//			}
//			offset-=30;
//		}
//		GameSession gs = GameSession.getInstance();
//		
//		if (gs.getCompletionType() == 1)
//			g.drawString("Game Over You Win (You could do alot better that a raft)", gc.getWidth()/2-250, gc.getHeight()/2 + 350);
//		else if(gs.getCompletionType() == 2)
//			g.drawString("Game Over You Win (You could do better than a boat)", gc.getWidth()/2-250, gc.getHeight()/2 + 350);
//		else if(gs.getCompletionType() == 3)
//			g.drawString("Game Over You Win (You could do a bit better than a plane)", gc.getWidth()/2-250, gc.getHeight()/2 + 350);
//		else if(gs.getCompletionType() == 4)
//			g.drawString("Game Over You Win (You can't do much better than a SPACE CRAFT!)", gc.getWidth()/2-250, c.getHeight()/2 + 350);
//		
//		//g.drawString("Game Over You Win (You could do better)", container.getWidth()/2, container.getHeight()/2);
//		g.drawString("press Escape to exit or Enter to start again", gc.getWidth()/2-250, gc.getHeight()/2 + 400);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, float delta){
		if (gc.getInput().isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
			gc.exit();
		}
		if (gc.getInput().isKeyDown(GLFW.GLFW_KEY_ENTER)) {
			sbg.enterState(Play.STATE_PLAY);
			sbg.getState(1).init(gc, sbg);
		}
		
		scroller += 0.1f*delta;
	}

	@Override
	public int getId() {
		return STATE_WIN;
	}
}
