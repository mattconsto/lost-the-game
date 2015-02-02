package deserted;

import org.lwjgl.glfw.GLFW;
import org.newdawn.slick.Color;

import deserted.engine.GameContainer;
import deserted.engine.GameState;
import deserted.engine.StateBasedGame;
import deserted.engine.graphics.Graphics;
import deserted.engine.graphics.Texture;
import deserted.player.PlayerUI;

public class GameOver extends GameState {
	public static final int STATE_OVER = 2;
	
	Texture image;
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) {
		image = new Texture("images/backgrounds/lose-nograd.png");
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
		/*g.setColor(Color.gray);
		g.fillRect(0, 0, container.getWidth(), container.getHeight());
		g.setColor(Color.black);*/
		
//		play.renderWorld(g);
//		g.setColor(Color.white);
//		
//		image.draw(0, 0, gc.getWidth(), gc.getHeight());
//		
//		float offset=0;
//		for (PlayerUI player : play.players)
//		{
//			g.drawString(player.agent.getName() + " died of exposure after only " + Math.floor(player.agent.getExpiredTime()) + " hours.", 10, scroller+offset);
//			offset-=30;
//		}
//		
//		g.drawString("press Escape to exit or Enter to start again", container.getWidth()/2-250, container.getHeight()/2 + 300);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, float delta) {
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
		return STATE_OVER;
	}
}
