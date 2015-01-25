import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import Model.Action;
import Model.ActionManager;
import Model.Agent;
import Model.AgentState;
import Model.GameSession;
import Model.Item;
import Model.ItemFactory;
import Model.ItemType;
import Player.MonsterManager;
import Player.MonsterUI;
import Player.PlayerReachedDestinationEvent;
import Player.PlayerUI;
import TileSystem.Tile;
import TileSystem.TileAttr;
import TileSystem.TileSystem;
import TileSystem.TileSystem.TileId;

public class Play extends BasicGameState implements GameState,
		PlayerReachedDestinationEvent {

	public static final int STATE_PLAY = 1;

	TileSystem ts;
	GameSession gs;
	List<PlayerUI> players;
	Agent selectedAgent;
	List<Item> selectedItems;
	Image stickFigure;
	Map<ItemType, Image> itemImages;
	ActionManager actionManager;
	MonsterManager monsterManager;

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		ts = new TileSystem(new Point(container.getWidth(),
				container.getHeight()));
		ItemFactory.init();
		new Music("sounds/heart.ogg").loop();
		gs = GameSession.getInstance();
		players = new ArrayList<PlayerUI>();
		for (int i = 0; i < gs.getAgents().size(); i++) {
			PlayerUI pui = new PlayerUI(gs.getAgents().get(i), ts);
			pui.addReachedDestinationEvent(this);
			players.add(pui);
		}
		selectedAgent = gs.getAgents().get(0);
		selectedItems = new ArrayList<Item>();
		actionManager = new ActionManager();

		stickFigure = new Image("icons/stickperson.png");

		itemImages = new HashMap<ItemType, Image>();
		for (ItemType type : ItemType.values()) {
			Item item = ItemFactory.createItem(type);
			System.out.println(item);
			Image image = new Image("icons/" + item.getImageName() + ".png");
			itemImages.put(type, image);

		}

		monsterManager = new MonsterManager(ts, players);

		ts.getCamera().x = players.get(0).location.x;
		ts.getCamera().y = players.get(0).location.y;

		RandomTileObject(TileId.GRASS, TileAttr.TREE, 700, true);
		RandomTileObject(TileId.DIRT, TileAttr.PALM_TREE, 200, true);
		RandomTileObject(TileId.ROCK, TileAttr.PINE_TREE, 100, true);
		RandomTileObject(TileId.SNOW, TileAttr.ALIEN_ARTIFACT, 5, false);
		RandomTileObject(TileId.WATER, TileAttr.BOAT, 2, false);
		RandomTileObject(TileId.ROCK, TileAttr.CAVE, 2, false);
		RandomTileObject(TileId.GRASS, TileAttr.POND, 2, false);
		
		container.setShowFPS(false);
	}

	private void RandomTileObject(TileId tileType, TileAttr tileAtt, int treeCount, boolean preferGroupings)
	{
		Random randomGenerator = new Random();
		while(true)
		{
			int x = randomGenerator.nextInt(ts.getSize()-2)+1;
			int y = randomGenerator.nextInt(ts.getSize()-2)+1;
			Tile tile = ts.getTile(x, y);
			if (tile.id== tileType && tile.attr == TileAttr.NONE)
			{
				float surroundTree = 1;
				if (ts.getTile(x+1, y).attr==tileAtt) surroundTree++;
				if (ts.getTile(x-1, y).attr==tileAtt) surroundTree++;
				if (ts.getTile(x, y+1).attr==tileAtt) surroundTree++;
				if (ts.getTile(x, y-1).attr==tileAtt) surroundTree++;
				float num = (float)randomGenerator.nextInt(100) ;
				if (preferGroupings)
					num /= surroundTree; 
				else
					num /=1.25;
					num *= surroundTree; 
				
				if (num > 50)
				{
					treeCount-=1;
					tile.attr= tileAtt;
				}
			}
			
			if (treeCount ==0) return;
		}
		
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {

		Input input = container.getInput();
		
		ts.renderTiles(g);
		for(int y = 0; y < ts.size; y++){
			for (PlayerUI player : players) {
				if(player.location.y >= y-0.2f && player.location.y < y+0.8f)
					player.render(g, ts.camera.zoom);
			}
			ts.renderSprites(g, y);

			monsterManager.render(g, ts.camera.zoom, y);
		}
		
		for (PlayerUI player : players) {
			player.renderOverlay(g, ts.camera.zoom);
		}
		

		ts.renderFog(g);

		// Header vars
		int header_height = 50;
		int header_pad = 3;
		int h_y = header_pad;
		int h_h = header_height - (2 * header_pad);

		// Footer vars
		int footer_height = 60;
		int footer_y = container.getHeight() - footer_height;
		int footer_pad = 9;
		// Use f_h, f_y for the actual footer height / y pos
		int f_y = footer_y + footer_pad;
		int f_h = footer_height - (2 * footer_pad);

		// Action Bar vars
		int action_bar_height = 35;
		int action_bar_y = footer_y - action_bar_height;
		int action_bar_pad = 3;
		int a_y = action_bar_y + action_bar_pad;
		int a_h = action_bar_height - (2 * action_bar_pad);

		// Agent vars
		int agent_bar_y = header_height;
		int agent_bar_height = action_bar_y - header_height;
		int agent_bar_pad = 3;
		int ag_y = agent_bar_y + agent_bar_pad;
		int ag_h = agent_bar_height + (2 * agent_bar_pad);
		int agent_bar_width = 250;
		int ag_x = container.getWidth() - agent_bar_width - 2;

		Rectangle headerRect = new Rectangle(0, 0, container.getWidth(),
				header_height);
		Rectangle footerRect = new Rectangle(0, footer_y, container.getWidth(),
				footer_height);
		Rectangle actionRect = new Rectangle(0, action_bar_y,
				container.getWidth(), action_bar_height);
		Rectangle agentRect = new Rectangle(ag_x, ag_y, agent_bar_width,
				agent_bar_height);

		// Header
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, container.getWidth(), header_height);
		g.setColor(Color.gray);
		g.drawRect(0, 0, container.getWidth(), header_height);
		g.setColor(Color.black);
		g.drawString("" + gs.getDate().toString("dd/MM/yyyy HH:mm"), 5, h_y
				+ header_pad);
		g.drawString("" + Math.round(gs.getTimeSurvived() / 60)
				+ " hour(s) since incident", 5, h_y + header_pad + 18);

		// Footer
		g.setColor(Color.gray);
		g.drawRect(0, footer_y, container.getWidth(), footer_height);
		g.setColor(Color.lightGray);
		g.fillRect(0, footer_y, container.getWidth(), footer_height);

		// Action bar
		g.setColor(Color.gray);
		g.drawRect(0, action_bar_y, container.getWidth(), action_bar_height);
		g.setColor(Color.lightGray);
		g.fillRect(0, action_bar_y, container.getWidth(), action_bar_height);

		// Draw agents
		int agent_zone_x = 500;
		List<Agent> agents = gs.getAgents();
		List<Rectangle> agentZones = new ArrayList<Rectangle>();
		for (int i = 0; i < agents.size(); i++) {

			int y = ag_y + (i * 50);
			int pad = 7;
			Agent agent = agents.get(i);

			g.setColor(Color.gray);
			g.drawRect(ag_x, y, agent_bar_width, 48);
			g.setColor(Color.lightGray);
			g.fillRect(ag_x, y, agent_bar_width, 48);

			if (selectedAgent == agent) {
				g.setColor(Color.red);
				g.drawRect(ag_x, y, agent_bar_width, 48);
			} else {

			}

			g.setColor(Color.black);
			g.drawString(agent.getName(), ag_x + pad, y + pad);

			if (agent.getState() != AgentState.DEAD) {
				// Draw fills first
				// health
				g.setColor(Color.green);
				g.fillRect(ag_x + pad, y + 18 + pad,
						(agent.getHealth() * 80) / 100, 16);
				// thirst
				g.setColor(Color.blue);
				g.fillRect(ag_x + pad + 100, y + pad,
						(agent.getWater() * 80) / 100, 16);
				// hunger
				g.setColor(Color.red);
				g.fillRect(ag_x + pad + 100, y + 18 + pad,
						(agent.getFood() * 80) / 100, 16);

				// Draw outlines
				g.setColor(Color.black);
				g.drawRect(ag_x + pad, y + 18 + pad, 80, 16);
				g.drawRect(ag_x + pad + 100, y + pad, 80, 16);
				g.drawRect(ag_x + pad + 100, y + 18 + pad, 80, 16);
			} else {
				g.setColor(Color.red);
				g.drawString("DEAD", ag_x + pad + 100, y + pad);
			}

			if (selectedAgent != agent) {
				int t_w = g.getFont().getWidth("Play");
				int t_h = g.getFont().getHeight("Play");
				int b_w = t_w + 6;
				int b_h = a_h;
				int t_y = (a_h - t_h) / 2;
				int t_x = (b_w - t_w) / 2;

				int button_offset_x = 10;
				int button_offset_y = (50 - b_h) / 2;

				g.setColor(Color.white);
				g.fillRect(ag_x + agent_bar_width - t_w - button_offset_x - 1,
						y + button_offset_y - 1, b_w, b_h);

				g.setColor(Color.darkGray);
				g.fillRect(ag_x + agent_bar_width - t_w - button_offset_x + 1,
						y + button_offset_y + 1, b_w, b_h);
				g.setColor(Color.lightGray);
				g.fillRect(ag_x + agent_bar_width - t_w - button_offset_x, y
						+ button_offset_y, b_w, b_h);
				g.setColor(Color.black);
				g.drawString("Play", ag_x + agent_bar_width - t_w
						- button_offset_x + t_x, y + t_y + button_offset_y);
			} else {
				stickFigure.draw(ag_x + agent_bar_width - 32, y + 9, 32, 32);
			}
			Rectangle rect = new Rectangle(ag_x + pad, y + pad,
					agent_bar_width, 48);
			agentZones.add(rect);

		}

		// Draw inventory
		int inventory_zone_x = 10;
		List<ItemType> items = gs.getItems();
		List<Rectangle> inventoryZones = new ArrayList<Rectangle>();
		g.setColor(Color.black);
		for (int i = 0; i < ItemType.values().length; i++) {

			int x = inventory_zone_x + (i * f_h) + (i * 6);
			if (i < items.size()) {
				itemImages.get(items.get(i)).draw(x, f_y, f_h, f_h);

				Rectangle rect = new Rectangle(x, f_y, f_h, f_h);
				inventoryZones.add(rect);
				int count = gs.getItemCount(items.get(i));
				if (count > 1) {
					int w = g.getFont().getWidth("" + count);
					int h = g.getFont().getHeight("" + count);
					g.setColor(Color.cyan);
					g.fillRect(x + f_h - w, f_y + f_h - h, w, h);
					g.setColor(Color.black);
					g.drawString("" + count, x + f_h - w, f_y + f_h - h);
				}
			} else {
				g.setColor(Color.black);
				g.fillRect(x, f_y, f_h, f_h);
			}
			g.setColor(Color.darkGray);
			g.drawRect(x - 1, f_y - 1, f_h + 2, f_h + 2);
		}

		ArrayList<Rectangle> actionZones = new ArrayList<Rectangle>();
		ArrayList<Action> validActions = new ArrayList<Action>();
		if (selectedAgent != null) {

			// Draw Action Bar (TM)
			PlayerUI playerUI = players.get(agents.indexOf(selectedAgent));
			Tile tile = ts.getTileFromWorld(playerUI.location.x,
					playerUI.location.y);
			int x = 5;
			validActions = actionManager.getValidActions(gs, ts, tile,
					selectedAgent);
			for (Action action : validActions) {
				String name = action.getName();

				int t_w = g.getFont().getWidth(name);
				int t_h = g.getFont().getHeight(name);
				int b_w = t_w + 6;
				int b_h = a_h;
				int t_y = (a_h - t_h) / 2;
				int t_x = (b_w - t_w) / 2;

				g.setColor(Color.darkGray);
				g.drawRect(x, a_y, b_w, b_h);
				g.setColor(Color.lightGray);
				g.fillRect(x, a_y, b_w, b_h);
				g.setColor(Color.black);
				g.drawString(name, x + t_x, a_y + t_y);

				Rectangle zone = new Rectangle(x, a_y, b_w, b_h);
				actionZones.add(zone);
				x += (b_w + 2);
			}
		}

		if (input.isMousePressed(0)) {
			int mouseX = input.getMouseX();
			int mouseY = input.getMouseY();

			if (headerRect.contains(mouseX, mouseY)
					|| footerRect.contains(mouseX, mouseY)
					|| actionRect.contains(mouseX, mouseY)
					|| agentRect.contains(mouseX, mouseY)) {

				// Check the UI elements
				// Player selection
				for (int i = 0; i < agentZones.size(); i++) {
					Rectangle agentZone = agentZones.get(i);
					if (agentZone.contains(mouseX, mouseY)) {
						selectedAgent = agents.get(i);
					}
				}

				// Dead agents can't interact with inventory etc.
				if (selectedAgent.getState() != AgentState.DEAD) {
//					for (int i = 0; i < inventoryZones.size(); i++) {
//						Rectangle inventoryZone = inventoryZones.get(i);
//						if (inventoryZone.contains(mouseX, mouseY)) {
//							if (selectedItems.contains(items.get(i))) {
//								selectedItems.remove(items.get(i));
//							} else {
//								selectedItems.add(items.get(i));
//							}
//						}
//					}

					for (int i = 0; i < actionZones.size(); i++) {
						Rectangle actionZone = actionZones.get(i);
						if (actionZone.contains(mouseX, mouseY)) {
							Action action = validActions.get(i);
							int player_index = gs.getAgents().indexOf(
									selectedAgent);
							PlayerUI player = players.get(player_index);
							selectedAgent.startAction(action);

							Tile tile = ts.getTileFromWorld(player.location.x, player.location.y);
							action.getActionable().beforeAction(gs, selectedAgent, ts, tile);
						}
					}
				}

			} else {
				//This code handles mouse selection of other players
				boolean playerSelectionHappens = false;
				Vector2f pos = ts.screenToWorldPos(mouseX, mouseY);
				for (int i = 0; i < players.size(); i++) {
					PlayerUI player = players.get(i);
					if (player.agent.getState() != AgentState.DEAD)
					{
						float difX = player.location.x - pos.x;
						float difY = player.location.y - pos.y;
						float len = (float)Math.sqrt((difX*difX)+(difY*difY));
						if (len < 0.5)
						{
							selectedAgent = player.agent;
							playerSelectionHappens = true;
						}
					}
				}
				if (!playerSelectionHappens)
				{
					//See if we are attacking a monster
					boolean monsterSelectionHappens = false;
					for (int i = 0; i < monsterManager.monsters.size(); i++) {
						MonsterUI monster = monsterManager.monsters.get(i);
						float difX = monster.location.x - pos.x;
						float difY = monster.location.y - pos.y;
						float len = (float)Math.sqrt((difX*difX)+(difY*difY));
						if (len < 0.5)
						{
							if (selectedAgent != null && selectedAgent.getState() != AgentState.DEAD) {
								monster.agent.decHealth(20);
								if (monster.agent.getHealth() <=0)
								{
									monster.agent.setState(AgentState.DEAD);
									gs.addItemByType(ItemType.MEAT);
								}								
								
								monsterSelectionHappens = true;
							}
						}
					}
					
					if (!monsterSelectionHappens) {
						if (selectedAgent != null && selectedAgent.getState() != AgentState.DEAD) {
							if(selectedAgent.hasAction()) { selectedAgent.stopAction(); }
							players.get(agents.indexOf(selectedAgent)).moveto(pos.x, 
									pos.y);
							ts.getCamera().x = players.get(agents
									.indexOf(selectedAgent)).location.x;
							ts.getCamera().y = players.get(agents
									.indexOf(selectedAgent)).location.y;
							ts.getCamera().isFollowing = true;
						}
					}
				}
			}
		}

		for (int i = 0; i < players.size(); i++) {
			PlayerUI player = players.get(i);
			Agent agent = agents.get(i);
			
			if(agent.hasAction() && agent.haveFinishedAction()) {
				if(agent.getState() != AgentState.DEAD) {
					Tile tile = ts.getTileFromWorld(player.location.x, player.location.y);
					agent.getAction().getActionable().afterAction(gs, selectedAgent, ts, tile);
					agent.stopAction();
				}
			}
			
			AgentState state = agent.getState();
			boolean atDestination = players.get(i).atDestination;

			if (state == AgentState.WALKING && atDestination) {
				agents.get(i).setState(AgentState.STANDING);
			}
			if (state != AgentState.WALKING && !atDestination) {
				agents.get(i).setState(AgentState.WALKING);
			}
			if(state == AgentState.DEAD) {
				Tile tile = ts.getTileFromWorld(player.location.x, player.location.y);
				if(!agent.hasPlacedCorpse() && tile.attr != TileAttr.CORPSE) {
					tile.attr = TileAttr.CORPSE;
					agent.setPlacedCorpse(true);
				}
			}
		}

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		boolean alive = false;
		List<Agent> agents = gs.getAgents();
		List<Rectangle> agentZones = new ArrayList<Rectangle>();
		for (int i = 0; i < agents.size(); i++) {
			if (agents.get(i).getState() != AgentState.DEAD) {
				alive = true;
			}
		}

		if (!alive)
			game.enterState(GameOver.STATE_OVER);

		float seconds = (float) (delta / 1000.0);
		updateCamera(container, seconds);
		for (PlayerUI player : players) {
			player.update(seconds);
		}
		monsterManager.update(seconds);
		ts.updateFog(players, gs);
		gs.update(seconds);

		if (container.getInput().isKeyDown(Input.KEY_ESCAPE)) {
			container.exit();
		}
	}

	private void updateCamera(GameContainer container, float delta) {
		Input input = container.getInput();
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();

		int dWheel = Mouse.getDWheel();
		if (dWheel < 0)
			ts.zoom(dWheel * delta * 0.06f);
		else if (dWheel > 0) {
			ts.zoom(dWheel * delta * 0.06f);
		}

		if (input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A)) {
			ts.getCamera().move(-20/ts.camera.zoom * delta, 0);
			ts.getCamera().isFollowing = false;
		}

		if (input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W)) {
			ts.getCamera().move(0, -20/ts.camera.zoom * delta);
			ts.getCamera().isFollowing = false;
		}

		if (input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D)) {
			ts.getCamera().move(20/ts.camera.zoom * delta, 0);
			ts.getCamera().isFollowing = false;
		}

		if (input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S)) {
			ts.getCamera().move(0, 20/ts.camera.zoom * delta);
			ts.getCamera().isFollowing = false;
		}

		if (ts.getCamera().x < 0)
			ts.getCamera().x = 0;

		if (ts.getCamera().y < 0)
			ts.getCamera().y = 0;

		if (ts.getCamera().x > ts.size)
			ts.getCamera().x = ts.size;

		if (ts.getCamera().y > ts.size)
			ts.getCamera().y = ts.size;

		if (ts.getCamera().isFollowing) {
			for (PlayerUI p : players) {
				if (p.agent == selectedAgent) {
					ts.getCamera().x = p.location.x;
					ts.getCamera().y = p.location.y;
					break;
				}
			}
		}
	}

	@Override
	public int getID() {
		return STATE_PLAY;
	}

	@Override
	public void reachedDestination(PlayerUI pui, float x, float y) {

		// Tile reachedTile = ts.getTileFromWorld(x, y);
		// if (reachedTile.id == TileId.GRASS) {
		// gs.addItem(new Grass());
		// }
	}

}
