package Player;
import java.util.Enumeration;
import java.util.Vector;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.lwjgl.util.vector.Vector2f;

import Model.Agent;
import TileSystem.TileSystem;
import TileSystem.TileSystem.Tile;


public class PlayerUI {

	TileSystem ts;
	PathFinder p;
	public Agent agent;
	
	public Vector2f location = new Vector2f(50,50);
	Vector2f destination = new Vector2f(20,20);
	Vector<Tile> destinations = new Vector<Tile>();
	boolean atDestination = true;
	
	float playerWalkSpeedMS = 1.4f;		//average walk speed 1.4m per second
	float tileSizeM = 50.0f;			//Tile is 100m across
	float gameSpeed = 3600/30;			//Game is 30s is one hour 3600s is 30s => 120s per 1s
	
	public PlayerUI(Agent agentIn, TileSystem tsIn){
		agent = agentIn;
		ts = tsIn;
		p = new PathFinder(ts);
	}
	
	public void moveto(float destinationX, float destinationY){
		atDestination = false;
		p = new PathFinder(ts);
		destination = new Vector2f(destinationX, destinationY);
		destinations = p.findPath(location, destination);
	}
	
	public void render(Graphics g){
		Vector2f screenLocation = ts.worldToScreenPos(location.x, location.y);
		g.setColor(new Color(255,0,0));
		g.fillOval(screenLocation.x-5,screenLocation.y-5, 10, 10);
		

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
