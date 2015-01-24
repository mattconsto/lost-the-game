package Player;
import java.util.Enumeration;
import java.util.Vector;

import org.newdawn.slick.Graphics;
import org.lwjgl.util.vector.Vector2f;

import TileSystem.TileSystem;


public class PlayerUI {

	TileSystem ts;
	Vector2f location = new Vector2f(50,50);
	Vector2f destination = new Vector2f(300,100);
	boolean atDestination = true;
	
	float playerWalkSpeedMS = 1.4f;		//average walk speed 1.4m per second
	float tileSizeM = 10.0f;			//Tile is 100m across
	float gameSpeed = 3600/30;			//Game is 30s is one hour 3600s is 30s => 120s per 1s
	
	public PlayerUI(TileSystem tsIn){
		ts = tsIn;
		moveto(400,200);
	}
	
	public void moveto(float destinationX, float destinationY){
		destination = new Vector2f(destinationX, destinationY);
		atDestination = false;
	}
	
	public void render(Graphics g){
		Vector2f screenLocation = ts.worldToScreenPos(location.x, location.y);
		g.fillOval(screenLocation.x,screenLocation.y, 10, 10);
	}
	
	public void update(int deltaTime) {
		if (atDestination) return;
		
		//Some basic movement code - a bit elaborate tbh
		float deltaTimeS = (float)deltaTime / 1000;
		float distanceTravelled = (deltaTimeS * gameSpeed * playerWalkSpeedMS)/ tileSizeM ;
				
		//Move the player
		Vector2f directionVec = new Vector2f(destination.x - location.x, destination.y-location.y);
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
			//If the last step then just set the location and alert listeners
			location = destination;
			atDestination = true;
			firePlayerReachedDestinationEvent();
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
        if (_listeners != null && _listeners.isEmpty())
        {
            Enumeration<PlayerReachedDestinationEvent> e = _listeners.elements();
            while (e.hasMoreElements())
            {
            	PlayerReachedDestinationEvent ev = e.nextElement();
                ev.reachedDestination(destination.x, destination.y);;
            }
        }
    }
     
}
