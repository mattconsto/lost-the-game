import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
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
	List<PlayerUI> players;
	Agent selectedAgent;
	Image stickFigure;

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		ts = new TileSystem(100);
		gs = new GameSession();
		players = new ArrayList<PlayerUI>();
		for (int i = 0; i < gs.getAgents().size(); i++) {
			players.add(new PlayerUI(ts));
		}
		selectedAgent = gs.getAgents().get(0);

		stickFigure = new Image("icons/stickperson.png");
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {

		Input input = container.getInput();
		// Test code
		ts.render(g);
		for (PlayerUI player : players) {
			player.render(g);
		}

		int footer_height = 50;
		int header_height = 40;
		int footer_y = container.getHeight() - footer_height;

		Rectangle headerRect = new Rectangle(0, 0, container.getWidth(),
				header_height);
		Rectangle footerRect = new Rectangle(0, footer_y, container.getWidth(),
				footer_height);

		// Header
		g.setColor(Color.lightGray);
		g.fillRoundRect(0, 0, container.getWidth(), header_height, 5);
		g.setColor(Color.gray);
		g.drawRoundRect(0, 0, container.getWidth(), header_height, 5);

		int footer_pad = 9;
		// Footer
		g.setColor(Color.gray);
		g.drawRoundRect(0, footer_y, container.getWidth(), footer_height, 5);
		g.setColor(Color.lightGray);
		g.fillRoundRect(0, footer_y, container.getWidth(), footer_height, 5);
		g.setColor(Color.black);
		g.drawString("" + gs.getDate().toString("dd/MM/yyyy HH:mm"), 5,
				footer_y + footer_pad);

		// Draw agents
		int agent_zone_x = 500;
		List<Agent> agents = gs.getAgents();
		List<Rectangle> agentZones = new ArrayList<Rectangle>();
		for (int i = 0; i < agents.size(); i++) {
			stickFigure.draw(agent_zone_x + (i * 30), footer_y + footer_pad);
			Rectangle rect = new Rectangle(agent_zone_x + (i * 30), footer_y
					+ footer_pad, stickFigure.getWidth(),
					stickFigure.getHeight());
			agentZones.add(rect);
		}

		if (input.isMousePressed(0)) {
			int mouseX = input.getMouseX();
			int mouseY = input.getMouseY();

			if (headerRect.contains(mouseX, mouseY)
					|| footerRect.contains(mouseX, mouseY)) {

				// Check the UI elements
				for (int i = 0; i < agentZones.size(); i++) {
					Rectangle agentZone = agentZones.get(i);
					if (agentZone
							.contains(input.getMouseX(), input.getMouseY())) {
						selectedAgent = agents.get(i);
						System.out.println(selectedAgent.getName());
					}
				}
			} else {
				if (selectedAgent != null) {
					Vector2f pos = ts.screenToWorldPos(mouseX, mouseY);
					players.get(agents.indexOf(selectedAgent)).moveto(pos.x,
							pos.y);
				}
			}
		}

		if (selectedAgent != null) {
			// Outline that agent
			int x_pos = agent_zone_x + (agents.indexOf(selectedAgent) * 30);
			int outline_margin = 2;
			g.drawRect(x_pos - outline_margin, footer_y + footer_pad
					- outline_margin, stickFigure.getWidth()
					+ (2 * outline_margin), stickFigure.getHeight()
					+ (2 * outline_margin));
		}

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		float seconds = (float) (delta / 1000.0);

		updateCamera(container, seconds);
		for (PlayerUI player : players) {
			player.update(seconds);
		}
		gs.update(seconds);
	}

	private void updateCamera(GameContainer container, float delta) {
		Input input = container.getInput();
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();

		int dWheel = Mouse.getDWheel();
		if (dWheel < 0)
			ts.setZoom(ts.zoomLevel + dWheel * delta * 0.06f);
		else if (dWheel > 0) {
			ts.setZoom(ts.zoomLevel + dWheel * delta * 0.06f);
		}
		if (ts.zoomLevel >= 2)
			ts.zoomLevel = 2;
		if (ts.zoomLevel <= 0.5f)
			ts.zoomLevel = 0.5f;
		
		if(/*mouseX < 50 ||*/ input.isKeyDown(Input.KEY_LEFT))
			ts.getCamera().move(-160*delta, 0);
		
		if(/*mouseY < 50 ||*/ input.isKeyDown(Input.KEY_UP))
			ts.getCamera().move(0, -160*delta);
		
		if(/*mouseX > container.getWidth()-50 ||*/ input.isKeyDown(Input.KEY_RIGHT))
			ts.getCamera().move(160*delta, 0);
		
		if(/*mouseY > container.getHeight()-50 ||*/ input.isKeyDown(Input.KEY_DOWN))
			ts.getCamera().move(0, 160*delta);
		
		//if(ts.getCamera().x < );
}
	@Override
	public int getID() {
		return LostGame.STATE_PLAY;
	}

}
