package deserted;

import org.lwjgl.glfw.GLFW;

import deserted.engine.GameContainer;
import deserted.engine.GameState;
import deserted.engine.StateBasedGame;
import deserted.engine.graphics.Graphics;
import deserted.engine.graphics.Texture;

public class GameIntro extends GameState {
	public static final int STATE_INTRO = 0;
	
	Texture logo;
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) {

		 logo = new Texture("images/backgrounds/title.png");
		 logo.setFilter(Texture.FILTER_NEAREST);
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame state, Graphics g) {
		//g.fillRect(0, 0, container.getWidth(), container.getHeight());
		//g.setColor(Color.white);


		//logo.draw(0, 0, container.getWidth(), container.getHeight(),0,0,logo.getWidth(), logo.getHeight());

		//g.drawString("Press Enter to continue", container.getWidth() / 2 - 130, container.getHeight() - 150);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, float delta) {
		if (gc.getInput().isKeyDown(GLFW.GLFW_KEY_ENTER)) {
			sbg.enterState(Play.STATE_PLAY);
			sbg.getState(1).init(gc, sbg);
		}
	}

	@Override
	public int getId() {
		return STATE_INTRO;
	}
}
