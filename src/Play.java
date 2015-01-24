import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import TileSystem.TileSystem;
import Player.PlayerUI;

public class Play extends BasicGameState implements GameState {
	
	TileSystem ts;
	PlayerUI player;

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		ts = new TileSystem(100);
		player = new PlayerUI(ts);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		
		
		//Test code
		ts.render(g);
		player.render(g);
		
		// Header
		g.setColor(Color.lightGray);
		g.fillRoundRect(0, 0, container.getWidth(), 25, 5);
		g.setColor(Color.gray);
		g.drawRoundRect(0, 0, container.getWidth(), 25, 5);
		
		// Footer
		g.setColor(Color.gray);
		g.drawRoundRect(0, container.getHeight()-25, container.getWidth(), container.getHeight(), 5);
		g.setColor(Color.lightGray);
		g.fillRoundRect(0, container.getHeight()-25, container.getWidth(), container.getHeight(), 5);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
		updateCameraPosition(container);
		player.update(delta);
	}

	private void updateCameraPosition(GameContainer container) {
		int mouseX = container.getInput().getMouseX();
		int mouseY = container.getInput().getMouseY();
	}

	@Override
	public int getID() {
		return LostGame.STATE_PLAY;
	}

}
