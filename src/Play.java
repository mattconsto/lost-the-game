import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import TileSystem.TileSystem;
import Model.GameSession;
import Player.PlayerUI;

public class Play extends BasicGameState implements GameState {
	
	TileSystem ts;
	GameSession gs;
	PlayerUI player;

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		ts = new TileSystem(100);
		player = new PlayerUI(ts);
		gs = new GameSession();
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		
		
		//Test code
		ts.render(g);
		player.render(g);
		
		int footer_height = 40;
		int header_height = 40;
		int footer_y = container.getHeight()-footer_height;
		// Header
		g.setColor(Color.lightGray);
		g.fillRoundRect(0, 0, container.getWidth(), header_height, 5);
		g.setColor(Color.gray);
		g.drawRoundRect(0, 0, container.getWidth(), header_height, 5);
		
		// Footer
		g.setColor(Color.gray);
		g.drawRoundRect(0, footer_y, container.getWidth(), container.getHeight(), 5);
		g.setColor(Color.lightGray);
		g.fillRoundRect(0, footer_y, container.getWidth(), container.getHeight(), 5);
		g.setColor(Color.black);
		g.drawString(""+gs.getDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)), 5,footer_y+5);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		delta = delta/1000;
		
		updateCameraPosition(container, delta);
		player.update(delta);
		gs.update(delta);
	}

	private void updateCameraPosition(GameContainer container, int delta) {
		Input input = container.getInput();
		int mouseX = container.getInput().getMouseX();
		int mouseY = container.getInput().getMouseY();
		
		if(mouseX < 50 || input.isKeyDown(Input.KEY_LEFT))
			ts.getCamera().move(-100*delta, 0);
		
		if(mouseY < 50 || input.isKeyDown(Input.KEY_UP))
			ts.getCamera().move(0, -100*delta);
		
		if(mouseX > container.getWidth()-50 || input.isKeyDown(Input.KEY_RIGHT))
			ts.getCamera().move(100*delta, 0);
		
		if(mouseY > container.getHeight()-50 || input.isKeyDown(Input.KEY_DOWN))
			ts.getCamera().move(0, 100*delta);
		
		//if(ts.getCamera().x < (ts.getSize()*ts.zoomLevel));
	}

	@Override
	public int getID() {
		return LostGame.STATE_PLAY;
	}

}
