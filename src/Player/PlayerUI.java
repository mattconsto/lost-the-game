package Player;
import java.util.Enumeration;
import java.util.Vector;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.lwjgl.util.vector.Vector2f;

import Model.Agent;
import TileSystem.TileSystem;
import TileSystem.Tile;


public class PlayerUI {

	TileSystem ts;
	PathFinder p;
	public Agent agent;
	
	public Vector2f location = new Vector2f(34,29);
	Vector2f destination = new Vector2f(34,29);
	Vector<Tile> destinations = new Vector<Tile>();
	public boolean atDestination = true;
	
	float playerWalkSpeedMS = 1.4f;		//average walk speed 1.4m per second
	float tileSizeM = 50.0f;			//Tile is 100m across
	float gameSpeed = 3600/30;			//Game is 30s is one hour 3600s is 30s => 120s per 1s
	Image playerImage = null;
	
	
	public PlayerUI(Agent agentIn, TileSystem tsIn) throws SlickException
	{
		agent = agentIn;
		ts = tsIn;
		p = new PathFinder(ts);
		
		playerImage = new Image("player/walking1.png");
	}
	
	public void moveto(float destinationX, float destinationY){
		atDestination = false;
		p = new PathFinder(ts);
		destination = new Vector2f(destinationX, destinationY);
		destinations = p.findPath(location, destination);
	}
	
	int imageWidth = 80;
	int imageHeight = 100;
	float animationFrame = 0;
	float angle = 0;
	
	public int getPlayerImageLocation()
	{
		if (animationFrame > 5) animationFrame = 0;
		if (atDestination) animationFrame = 0;
		
		return (int)animationFrame*imageWidth;
	}
	
	public void render(Graphics g){
		Vector2f screenLocation = ts.worldToScreenPos(location.x, location.y);
		g.setColor(new Color(255,0,0));
		//g.fillOval(screenLocation.x-5,screenLocation.y-5, 10, 10);
		
		Image realPlayer = playerImage.getSubImage(getPlayerImageLocation(),0,
													getPlayerImageLocation()+imageWidth,imageHeight);
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
		
		realPlayer.draw(screenLocation.x-30,screenLocation.y-30,
				screenLocation.x+40,screenLocation.y+40,0,0,imageWidth, imageHeight);
		
		g.drawOval(screenLocation.x-20,screenLocation.y-20,
				40,40);
		
		g.setColor(new Color(0,0,255));
		Vector2f lastPoint = ts.worldToScreenPos(location.x, location.y);
		for(int i =destinations.size()-1; i>=0 ; i--)
		{
			Tile dest = destinations.get(i);
			Vector2f pos = new Vector2f(dest.x+0.5f, dest.y+0.5f);
            Vector2f destPos = ts.worldToScreenPos(pos.x, pos.y);
            g.drawLine(destPos.x, destPos.y, lastPoint.x, lastPoint.y);
            lastPoint = destPos;
		}
	}
	
	public void update(float deltaTime) {
		if (atDestination) return;
		
		animationFrame += deltaTime*5;
		//Some basic movement code - a bit elaborate tbh
		float deltaTimeS = (float)deltaTime;
		float distanceTravelled = (deltaTimeS * gameSpeed * playerWalkSpeedMS)/ tileSizeM ;
		
		Vector2f currentDestination = destination;
		Tile destTile = null;
		if (destinations.size() > 1)
		{
		    destTile = destinations.get(destinations.size()-1);
			currentDestination = new Vector2f(destTile.x+0.5f, destTile.y+0.5f);
		}
		
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
			else
			{
				destinations.clear();
				//If the last step then just set the location and alert listeners
				location = destination;
				atDestination = true;
				firePlayerReachedDestinationEvent();
			}
		
		}
		
		
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
        if (_listeners != null && !_listeners.isEmpty())
        {
            Enumeration<PlayerReachedDestinationEvent> e = _listeners.elements();
            while (e.hasMoreElements())
            {
            	PlayerReachedDestinationEvent ev = e.nextElement();
                ev.reachedDestination(this, destination.x, destination.y);;
            }
        }
    }
     



  
}
