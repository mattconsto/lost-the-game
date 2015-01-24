package Player;
import java.util.Enumeration;
import java.util.Vector;

import org.newdawn.slick.Graphics;
import org.lwjgl.util.vector.Vector2f;

import TileSystem.TileSystem;
import TileSystem.TileSystem.Tile;
import TileSystem.TileSystem.TileId;


public class PlayerUI {

	TileSystem ts;
	Vector2f location = new Vector2f(50,50);
	Vector2f destination = new Vector2f(300,100);
	Vector2f ultimateDestination = new Vector2f(300,100);
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
		int tileType = ts.getTile(0,0).variant;
	}
	
	public void render(Graphics g){
		Vector2f screenLocation = ts.worldToScreenPos(location.x, location.y);
		g.fillOval(screenLocation.x,screenLocation.y, 10, 10);
	}
	
	public void update(float deltaTime) {
		if (atDestination) return;
		
		//Some basic movement code - a bit elaborate tbh
		float deltaTimeS = (float)deltaTime;
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
     



    public class PathFinder
	{
    	private int distances[][];
    	private int size;
    	
    	public PathFinder(TileSystem ts)
    	{
    		size = ts.size;
    		distances = new int[size][size];
    		
    		for(int x = 0; x < size; x++){
                for(int y = 0; y < size; y++){
                	distances[x][y] = 9999;
                }
    		}
    		
    	}
    
    	public Vector<Tile> FindPath(Vector2f start, Vector2f end)
    	{
    		Tile startTile = ts.getTileFromWorld(start.x,start.y);
    		Tile endTile = ts.getTileFromWorld(end.x, end.y);
    		
    		if (startTile.x == endTile.x && startTile.y == endTile.y) return null;
    		
    		SetDistances(startTile, 0);
    		
    		Vector<Tile> tiles = GetPath(startTile, endTile);
    		
    		return tiles;
    	}
    	
    	private Vector<Tile> GetPath(Tile startTile, Tile endTile)
    	{
    		Vector<Tile> tiles = new Vector<Tile>();
    		
    		Tile currentTile = endTile;
    		do
    		{
    			tiles.add(currentTile);
    		    currentTile = getNextTile(currentTile);
    		}while (currentTile != startTile);
    		
    		return tiles;
    	}

    	private Tile getNextTile(Tile currentTile)
    	{
    		int min = 9999;
    		Tile minTile = null;
    		if (currentTile.x >0)
    		{
    			Tile tile = ts.getTile(currentTile.x-1, currentTile.y);
    			int dist = distances[tile.x][tile.y];
    			if (dist < min)
    			{
    				dist = min;
    				minTile = tile;
    			}
    		}
    		if (currentTile.y >0)
    		{
    			Tile tile = ts.getTile(currentTile.x, currentTile.y-1);
    			int dist = distances[tile.x][tile.y];
    			if (dist < min)
    			{
    				dist = min;
    				minTile = tile;
    			}
    		}
    		if (currentTile.x < size)
    		{
    			Tile tile = ts.getTile(currentTile.x+1, currentTile.y);
    			int dist = distances[tile.x][tile.y];
    			if (dist < min)
    			{
    				dist = min;
    				minTile = tile;
    			}
    		}
    		if (currentTile.y < size)
    		{
    			Tile tile = ts.getTile(currentTile.x, currentTile.y+1);
    			int dist = distances[tile.x][tile.y];
    			if (dist < min)
    			{
    				dist = min;
    				minTile = tile;
    			}
    		}
    		return minTile;
    	}
    	
    	private void SetDistances(Tile startTile, int distance)
    	{
    		distances[startTile.x][startTile.y] = distance;
    		if (startTile.x >0)
    		{
    			Tile currentDest = ts.getTile(startTile.x-1, startTile.y);
    			int moveValue = getTileMoveAbility(currentDest);
    			int currentValue = distance + moveValue;
    			if (currentValue < distances[currentDest.x][currentDest.y])
    				SetDistances(currentDest, currentValue);
    		}
    		if (startTile.y >0)
    		{
    			Tile currentDest = ts.getTile(startTile.x, startTile.y-1);
    			int moveValue = getTileMoveAbility(currentDest);
    			int currentValue = distance + moveValue;
    			if (currentValue < distances[currentDest.x][currentDest.y])
    				SetDistances(currentDest, currentValue);
    		}
    		if (startTile.x < size)
    		{
    			Tile currentDest = ts.getTile(startTile.x+1, startTile.y);
    			int moveValue = getTileMoveAbility(currentDest);
    			int currentValue = distance + moveValue;
    			if (currentValue < distances[currentDest.x][currentDest.y])
    				SetDistances(currentDest, currentValue);
    		}
    		if (startTile.y < size)
    		{
    			Tile currentDest = ts.getTile(startTile.x, startTile.y+1);
    			int moveValue = getTileMoveAbility(currentDest);
    			int currentValue = distance + moveValue;
    			if (currentValue < distances[currentDest.x][currentDest.y])
    				SetDistances(currentDest, currentValue);
    		}
    	}
    	
    	private int getTileMoveAbility(Tile tileIn)
    	{
    		//This is how much is added to the distance variable per tile move bigger = slower
    		//negative = er no cannot do it
    		if (tileIn.variant == 0) return -999999;
    		if (tileIn.variant == 1) return 1;
    		if (tileIn.variant == 2) return 2;
    	
    	}
    	
	}

}
