package Player;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.lwjgl.util.vector.Vector2f;

import Model.Agent;
import Model.AgentState;
import Model.GameSession;
import TileSystem.TileAttr;
import TileSystem.TileSystem;
import TileSystem.Tile;
import TileSystem.TileSystem.TileId;


public class MonsterUI {

	TileSystem ts;
	public Agent agent;
	int monsterId;
	
	public Vector2f location = new Vector2f(34,29);
	//Vector2f destination = new Vector2f(34,29);
	Vector<Tile> destinations = new Vector<Tile>();
	public boolean atDestination = true;
	List<PlayerUI> players;
	
	float tileSizeM = 50.0f;			//Tile is 100m across
	float gameSpeed = 3600/30;			//Game is 30s is one hour 3600s is 30s => 120s per 1s
	Vector<Image> playerImages =	 null;
	//average walk speed 1.4m per second
	float playerWalkSpeedMS = 1.4f;
	float playerWalkSpeedMSSlow = 1.4f;
	float playerWalkSpeedMSFast = 1.4f;
	float playerDamage;
	
	float lastValue = 100.0f;
	double lastDecrementTime=0.0;
	boolean showHealth = false;
	
	int imageWidth = 32;
	int imageHeight = 32;
	public MonsterUI(Agent agentIn, TileSystem tsIn, List<PlayerUI> playersIn, int monsterIdIn) throws SlickException
	{
		agent = agentIn;
		ts = tsIn;
		players = playersIn;
		monsterId = monsterIdIn;
		
		
		
		
		if (monsterId ==0 || monsterId ==1)
		{
			imageWidth = 32;
			imageHeight = 32;
			Image playerImage = new Image("monster/spider.gif");
			playerImages = new Vector<Image>();
			playerImages.add(playerImage.getSubImage(0*imageWidth,0,(0*imageWidth)+imageWidth,imageHeight));
			playerImages.add(playerImage.getSubImage(1*imageWidth,0,(1*imageWidth)+imageWidth,imageHeight));
			playerImages.add(playerImage.getSubImage(2*imageWidth,0,(2*imageWidth)+imageWidth,imageHeight));
			playerWalkSpeedMSSlow = 0.2f;
			playerWalkSpeedMSFast = 2.0f;
			playerDamage = 10.0f;
		}
		if(monsterId == 2 || monsterId == 3)
		{
			imageWidth = 56;
			imageHeight = 71;
			Image playerImage = new Image("monster/Grue.png");
			playerImages = new Vector<Image>();
			playerImages.add(playerImage.getSubImage(0*imageWidth,0*imageHeight,1*imageWidth, 1*imageHeight));
			playerImages.add(playerImage.getSubImage(1*imageWidth,0*imageHeight,2*imageWidth, 1*imageHeight));
			playerImages.add(playerImage.getSubImage(0*imageWidth,1*imageHeight,1*imageWidth, 2*imageHeight));
			playerImages.add(playerImage.getSubImage(1*imageWidth,1*imageHeight,2*imageWidth, 2*imageHeight));
			playerWalkSpeedMSSlow = 0.2f;
			playerWalkSpeedMSFast = 2.0f;
			playerDamage = 100.0f;
		}
		
		//Random Start location
		location = randomLocation();
		randomMove();
	}
	
	public void ResetDestination()
	{
		randomMove();
	}
	
	private Vector2f randomLocation()
	{
		Random randomGenerator = new Random();
		while(true)
		{
			int x = randomGenerator.nextInt(ts.size);
			int y = randomGenerator.nextInt(ts.size);
			Tile tile = ts.getTile(x, y);
			if (tile.id == TileId.GRASS) return new Vector2f(x,y);
		}
	}
	
	

	float animationFrame = 0;
	float angle = 0;
	
	
	public Image getPlayerImage()
	{
		if (animationFrame >= playerImages.size()) animationFrame = 0;
		if (atDestination) animationFrame = 0;
		 return playerImages.get((int)animationFrame);
	}
	int direction=0;
	
	public void render(Graphics g, float scale){
		Vector2f screenLocation = ts.worldToScreenPos(location.x, location.y);

		if (monsterId == 0 || monsterId ==1)
		{
		g.setColor(new Color(255,0,0));
		Image realPlayer = getPlayerImage();
		realPlayer.setCenterOfRotation(16*scale, 16*scale);

	if (destinations.size()>1) 
		{
			Vector2f lookPointA = new Vector2f(destinations.get(destinations.size()-2).x,destinations.get(destinations.size()-2).y);
			Vector2f lookPointB = new Vector2f(destinations.get(destinations.size()-1).x,destinations.get(destinations.size()-1).y);
			float xdif = lookPointB.x - lookPointA.x;
			float ydif = lookPointB.y - lookPointA.y;
			if (xdif >0 && ydif==0) angle = 270;
			if (xdif <0 && ydif==0) angle = 90;
			if (xdif ==0 && ydif>0) angle = 0;
			if (xdif ==0 && ydif<0) angle = 180;
	
			if (xdif >0 && ydif>0) angle = 90+45+180;
			if (xdif <0 && ydif>0) angle = 270+45+90;
			if (xdif >0 && ydif<0) angle = 90+45+90;
			if (xdif <0 && ydif<0) angle = 270+45-180;
			
			
		}
		
		realPlayer.rotate(angle);
		
		realPlayer.draw(screenLocation.x-16*scale,screenLocation.y-16*scale,
				screenLocation.x+16*scale,screenLocation.y+16*scale,0,0,imageWidth, imageHeight);
		realPlayer.rotate(-angle);	
		}
		else
		{
			g.setColor(new Color(255,0,0));

			if (destinations.size()>1) 
				{
					Vector2f lookPointA = new Vector2f(destinations.get(destinations.size()-2).x,destinations.get(destinations.size()-2).y);
					Vector2f lookPointB = new Vector2f(destinations.get(destinations.size()-1).x,destinations.get(destinations.size()-1).y);
					float xdif = lookPointB.x - lookPointA.x;
					float ydif = lookPointB.y - lookPointA.y;
					if (xdif >0 && ydif==0) direction = 2;
					if (xdif <0 && ydif==0) direction = 0;
					if (xdif ==0 && ydif>0) direction = 1;
					if (xdif ==0 && ydif<0) direction = 3;
			
					if (xdif >0 && ydif>0) direction = 1;
					if (xdif <0 && ydif>0) direction = 1;
					if (xdif >0 && ydif<0) direction = 2;
					if (xdif <0 && ydif<0) direction = 0;
					
					
				}
				Image realPlayer = playerImages.get(direction);

				realPlayer.draw(screenLocation.x-15*scale,screenLocation.y-15*scale,
						screenLocation.x+15*scale,screenLocation.y+15*scale,0,0,imageWidth, imageHeight);

			
		}
	}
	
	
	public void renderOverlay(Graphics g, float scale){
		Vector2f screenLocation = ts.worldToScreenPos(location.x, location.y);

		if (showHealth)
		{
			float health = agent.getHealth();
			if (health < 20)
				g.setColor(new Color(255,0,0.0f,0.5f));
			else
				g.setColor(new Color(0,255,0.0f,0.5f));
			
			float progress = health/100.0f;
			g.fillRect(screenLocation.x-20*scale,  screenLocation.y+20*scale, 40*scale * progress, 10*scale);
			g.drawRect(screenLocation.x-20*scale,  screenLocation.y+20*scale, 40*scale, 10*scale);
		}
	}
	
	private void updateHealthDisplay()
	{
		float health = agent.getHealth();
		if (health < lastValue)
		{
			lastValue = health;
			showHealth = true;
			GameSession gs = GameSession.getInstance();
			lastDecrementTime = gs.getTimeSurvived();
		}
		if (showHealth)
		{
			GameSession gs = GameSession.getInstance();
			if ((gs.getTimeSurvived()- lastDecrementTime) > 5) 
				showHealth = false;
		}
	}
	
	private void updateWebsOnTrees()
	{
		Tile current = ts.getTileFromWorld(location.x,  location.y);
		if (current.attr == TileAttr.TREE) 
			current.attr = TileAttr.WEBBED_TREE;
	}
	
	public void update(float deltaTime) {
		updateHealthDisplay();
		updateWebsOnTrees();
		
		boolean mauledPlayer = false;
		//See if we are less than 1 square from any player and if so injure them !
		for (PlayerUI player : players)
		{
			if (player.agent.getState()  != AgentState.DEAD)
			{
				Vector2f playerLocation = player.location;
				float difX = location.x - playerLocation.x;
				float difY = location.y - playerLocation.y;
				float distToPlayer = (float)Math.sqrt((difX*difX)+(difY*difY));
				if (distToPlayer < 1)
				{
					player.agent.decHealth(playerDamage* deltaTime);
					mauledPlayer = true;
				}
			}
		}
		
			if (destinations.size() == 0)
			{
				if (!mauledPlayer)
				{	randomMove();}
			return;
			}
		animationFrame += deltaTime*5;
		//Some basic movement code - a bit elaborate tbh
		 Tile destTile = destinations.get(destinations.size()-1);
		 Vector2f currentDestination = new Vector2f(destTile.x+0.5f, destTile.y+0.5f);
		

	
			
		
		float deltaTimeS = (float)deltaTime;
		float distanceTravelled = (deltaTimeS * gameSpeed * playerWalkSpeedMS)/ tileSizeM ;
		
		//Move the player
		Vector2f directionVec = new Vector2f(currentDestination.x - location.x, currentDestination.y-location.y);
		float vecLength = (float)Math.sqrt((directionVec.x * directionVec.x) + (directionVec.y * directionVec.y));
		if (vecLength > distanceTravelled)
		{
			Vector2f directionVecUnit = new Vector2f(directionVec.x / vecLength, directionVec.y / vecLength);
			Vector2f directionInStep = new Vector2f(directionVecUnit.x * distanceTravelled, directionVecUnit.y * distanceTravelled);
		
			Vector2f nextLocation = new Vector2f(location.x + directionInStep.x, location.y+directionInStep.y);
			location = nextLocation;
		}
		else
		{
			location = currentDestination;
			if (destTile != null)
			{
				destinations.removeElement(destTile);
			}
			
			if (destinations.size() ==0)
			{
				//Right we need a new destination
				randomMove();
			}
		
		}
		
		

		

	}
	
	private void randomMove()
	{
		if (monsterId==1 || monsterId==3)
			randomMoveEvil();
		else
			randomMoveGood();
	}
	
	private void randomMoveEvil()
	{
		Random randomGenerator = new Random();
		PathFinder p = new PathFinder(ts, location);
		
		//Step 1 - See if we have a local player
		for (PlayerUI player : players)
		{
			if (player.agent.getState()  != AgentState.DEAD)
			{
			Vector2f playerLocation = player.location;
			float difX = location.x - playerLocation.x;
			float difY = location.y - playerLocation.y;
			float distToPlayer = (float)Math.sqrt((difX*difX)+(difY*difY));
			if (distToPlayer < 10)
			{
				Tile destTile = ts.getTileFromWorld(playerLocation.x, playerLocation.y);
				if (destTile.id != TileId.WATER)
				{
					Vector<Tile> destinationsTemp = p.findPath(playerLocation);
					if (hasNoWater(destinationsTemp))
					{
						destinations = destinationsTemp;
						playerWalkSpeedMS = playerWalkSpeedMSFast;
						return;
					}
				}
			}	
			}
		}
	
		
		while(true)
		{
			atDestination = false;
			float randX = location.x+randomGenerator.nextInt(30)-15;
			float randY = location.y+randomGenerator.nextInt(30)-15;
			Vector2f destinationTemp = new Vector2f(randX,randY);
			if (destinationTemp.x >=0 && destinationTemp.y >=0 && destinationTemp.x < ts.getSize() && destinationTemp.y < ts.getSize())
			{
				Tile destTile = ts.getTileFromWorld(destinationTemp.x, destinationTemp.y);
				if (destTile.id != TileId.WATER)
				{
					Vector<Tile> destinationsTemp = p.findPath(destinationTemp);
					if (hasNoWater(destinationsTemp))
					{
						destinations = destinationsTemp;	
						playerWalkSpeedMS = playerWalkSpeedMSSlow;
						return;
					}
				}
			}
		}
	}
	
	private void randomMoveGood()
	{
		Random randomGenerator = new Random();
		PathFinder p = new PathFinder(ts, location);
		
		boolean run = false;
		while(true)
		{
			atDestination = false;
			float randX = location.x+randomGenerator.nextInt(30)-15;
			float randY = location.y+randomGenerator.nextInt(30)-15;
			Vector2f destinationTemp = new Vector2f(randX,randY);
			if (destinationTemp.x >=0 && destinationTemp.y >=0 && destinationTemp.x < ts.getSize() && destinationTemp.y < ts.getSize())
			{
				boolean noGood = false;
				
				//Step 1 - See if we have a local player
				for (PlayerUI player : players)
				{
					if (player.agent.getState()  != AgentState.DEAD)
					{
						Vector2f playerLocation = player.location;
						float difX = destinationTemp.x - playerLocation.x;
						float difY = destinationTemp.y - playerLocation.y;
						float distToPlayer = (float)Math.sqrt((difX*difX)+(difY*difY));
						if (distToPlayer < 10)
						{
							noGood = true;
							run = true;
						}	
					}
				}
				if (!noGood)
				{
					Tile destTile = ts.getTileFromWorld(destinationTemp.x, destinationTemp.y);
					if (destTile.id != TileId.WATER)
					{
						Vector<Tile> destinationsTemp = p.findPath(destinationTemp);
						if (hasNoWater(destinationsTemp))
						{
							destinations = destinationsTemp;
							if (run)
							{
								playerWalkSpeedMS = playerWalkSpeedMSFast;
							}
							else
							{
								playerWalkSpeedMS = playerWalkSpeedMSSlow;
							}
							
							return;
						}
					}
				}
			}
		}
	}
	
	private boolean hasNoWater(Vector<Tile> tiles)
	{
		for (Tile tile : tiles)
		{
			if (tile.id== TileId.WATER) return false;
		}
		return true;
	}
	
    protected Vector<PlayerReachedDestinationEvent> _listeners;
     
    public void addReachedDestinationEvent(PlayerReachedDestinationEvent listener)
    {
        if (_listeners == null)
            _listeners = new Vector<PlayerReachedDestinationEvent>();
             
        _listeners.addElement(listener);
    }
    
    protected void firePlayerReachedDestinationEvent()
    {
       /* if (_listeners != null && !_listeners.isEmpty())
        {
            Enumeration<PlayerReachedDestinationEvent> e = _listeners.elements();
            while (e.hasMoreElements())
            {
            	PlayerReachedDestinationEvent ev = e.nextElement();
                ev.reachedDestination(this, destination.x, destination.y);;
            }
        }*/
    }
     



  
}
