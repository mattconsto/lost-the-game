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
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		ts = new TileSystem(100, 100);
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		ts.renderGroundTiles();
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
