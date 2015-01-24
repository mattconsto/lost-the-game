import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import TileSystem.TileSystem;


public class Play extends BasicGameState implements GameState {
	
	TileSystem ts;

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		ts = new TileSystem(100, 100);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		ts.renderGroundTiles();
		
		// Header
		g.setColor(Color.gray);
		g.drawRoundRect(0, 0, container.getWidth(), 25, 5);
		g.setColor(Color.lightGray);
		g.fillRoundRect(0, 0, container.getWidth(), 25, 5);
		// Footer

		g.setColor(Color.gray);
		g.drawRoundRect(0, container.getHeight()-25, container.getWidth(), container.getHeight(), 5);
		g.setColor(Color.lightGray);
		g.fillRoundRect(0, container.getHeight()-25, container.getWidth(), container.getHeight(), 5);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {

	}

	@Override
	public int getID() {
		return LostGame.STATE_PLAY;
	}

}
