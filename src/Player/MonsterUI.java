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
import TileSystem.TileSystem;
import TileSystem.Tile;
import TileSystem.TileSystem.TileId;


public class MonsterUI {

	TileSystem ts;
	public Agent agent;
	
	public Vector2f location = new Vector2f(34,29);
	//Vector2f destination = new Vector2f(34,29);
	Vector<Tile> destinations = new Vector<Tile>();
	public boolean atDestination = true;
	List<PlayerUI> players;
	
	float tileSizeM = 50.0f;			//Tile is 100m across
	float gameSpeed = 3600/30;			//Game is 30s is one hour 3600s is 30s => 120s per 1s
	Vector<Image> playerImages = null;
	
	
	public MonsterUI(Agent agentIn, TileSystem tsIn, List<PlayerUI> playersIn) throws SlickException
	{
		agent = agentIn;
		ts = tsIn;
		players = playersIn;
		
		
		Image playerImage = new Image("player/walking1.png");
		
		playerImages = new Vector<Image>();
		playerImages.add(playerImage.getSubImage(0*imageWidth,0,(0*imageWidth)+imageWidth,imageHeight));
		playerImages.add(playerImage.getSubImage(1*imageWidth,0,(1*imageWidth)+imageWidth,imageHeight));
		playerImages.add(playerImage.getSubImage(2*imageWidth,0,(2*imageWidth)+imageWidth,imageHeight));
		playerImages.add(playerImage.getSubImage(3*imageWidth,0,(3*imageWidth)+imageWidth,imageHeight));
		playerImages.add(playerImage.getSubImage(4*imageWidth,0,(4*imageWidth)+imageWidth,imageHeight));
		playerImages.add(playerImage.getSubImage(5*imageWidth,0,(5*imageWidth)+imageWidth,imageHeight));
		
		//Random Start location
		location = randomLocation();
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
	
	
	int imageWidth = 80;
	int imageHeight = 100;
	float animationFrame = 0;
	float angle = 0;
	
	
	public Image getPlayerImage()
	{
		if (animationFrame > 5) animationFrame = 0;
		if (atDestination) animationFrame = 0;
		 return playerImages.get((int)animationFrame);
	}
	
	public void render(Graphics g){
		Vector2f screenLocation = ts.worldToScreenPos(location.x, location.y);

		
		g.setColor(new Color(255,0,0));
		Image realPlayer = getPlayerImage();
		realPlayer.setCenterOfRotation(30, 30);

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
		
		/*realPlayer.draw(screenLocation.x-30,screenLocation.y-30,
				screenLocation.x+40,screenLocation.y+40,0,0,imageWidth, imageHeight);*/
		realPlayer.rotate(-angle);
		
		g.fillRect(screenLocation.x-20,screenLocation.y-20,
				40,40);
		
	}
	
	public void update(float deltaTime) {
			if (destinations.size() == 0)
			{
			return;
			}
		animationFrame += deltaTime*5;
		//Some basic movement code - a bit elaborate tbh
		 Tile destTile = destinations.get(destinations.size()-1);
		 Vector2f currentDestination = new Vector2f(destTile.x+0.5f, destTile.y+0.5f);
		
		//average walk speed 1.4m per second
		float playerWalkSpeedMS = 1.4f;
		if (ts.getTileFromWorld(location.x, location.y).id == TileId.WATER)
		{
			playerWalkSpeedMS = 0.3f;
		}
		
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
		Random randomGenerator = new Random();
		PathFinder p = new PathFinder(ts, location);
		
		//Step 1 - See if we have a local player
		for (PlayerUI player : players)
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
						return;
					}
				}
				
			}
		}
		
		
		
		while(true)
		{
			atDestination = false;
			
			Vector2f destinationTemp = new Vector2f(randomGenerator.nextInt(ts.getSize()), ((float)randomGenerator.nextInt(ts.getSize())));
		
				Tile destTile = ts.getTileFromWorld(destinationTemp.x, destinationTemp.y);
				if (destTile.id != TileId.WATER)
				{
					Vector<Tile> destinationsTemp = p.findPath(destinationTemp);
					if (hasNoWater(destinationsTemp))
					{
						destinations = destinationsTemp;	
						return;
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
