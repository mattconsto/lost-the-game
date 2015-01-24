import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import Model.Agent;
import Model.GameSession;
import Player.PlayerUI;
import TileSystem.TileSystem;

public class Play extends BasicGameState implements GameState {
	
	TileSystem ts;
	GameSession gs;
	PlayerUI player;
	Agent selectedAgent;

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		ts = new TileSystem(100);
		player = new PlayerUI(ts);
		gs = new GameSession();
		selectedAgent = gs.getAgents().get(0);
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
		g.drawString(""+gs.getDate().toString("dd/MM/yyyy HH:mm"), 5,footer_y+5);
	
		if(selectedAgent != null) {
			g.drawString(selectedAgent.getName(), 5, 5);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		float seconds = (float)(delta/1000.0);
		
		updateCamera(container, seconds);
		player.update(seconds);
		gs.update(seconds);
	}

	private void updateCamera(GameContainer container, float delta) {
		Input input = container.getInput();
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		
		int dWheel = Mouse.getDWheel();
		if(dWheel < 0)
			ts.zoomLevel += dWheel * delta * 0.06f;
		else if(dWheel > 0){
			ts.zoomLevel += dWheel * delta * 0.06f;
		}
		if(ts.zoomLevel >= 2)
			ts.zoomLevel = 2;
		if(ts.zoomLevel <= 0.5f)
			ts.zoomLevel = 0.5f;
		
		if(mouseX < 50 || input.isKeyDown(Input.KEY_LEFT))
			ts.getCamera().move(-160*delta, 0);
		
		if(mouseY < 50 || input.isKeyDown(Input.KEY_UP))
			ts.getCamera().move(0, -160*delta);
		
		if(mouseX > container.getWidth()-50 || input.isKeyDown(Input.KEY_RIGHT))
			ts.getCamera().move(160*delta, 0);
		
		if(mouseY > container.getHeight()-50 || input.isKeyDown(Input.KEY_DOWN))
			ts.getCamera().move(0, 160*delta);
		
		//if(ts.getCamera().x < );
	}

	@Override
	public int getID() {
		return LostGame.STATE_PLAY;
	}

}
