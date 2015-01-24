package Player;
import java.util.Vector;

import org.lwjgl.util.vector.Vector2f;

import TileSystem.TileSystem;
import TileSystem.Tile;
import TileSystem.TileSystem.TileId;

public class PathFinder
	{
  	public int distances[][];
  	private int size;
  	private TileSystem ts;
  	
  	public PathFinder(TileSystem tsIn)
  	{
  		ts = tsIn;
  		size = ts.size;
  		distances = new int[size][size];
  		
  		for(int x = 0; x < size; x++){
              for(int y = 0; y < size; y++){
              	distances[x][y] = 9999;
              }
  		}
  		
  	}
  
  	public Vector<Tile> findPath(Vector2f start, Vector2f end)
  	{
  		Tile startTile = ts.getTileFromWorld(start.x,start.y);
  		Tile endTile = ts.getTileFromWorld(end.x, end.y);
 
  		if (startTile == null)
  			throw new IllegalArgumentException("Start tile is nothing !!!");
  		
  		if (endTile == null)
  			throw new IllegalArgumentException("End tile is nothing !!!");
  		
  		if (startTile.x == endTile.x && startTile.y == endTile.y) return new Vector<Tile>();
  		
  		setDistances(startTile, 0);
  		
  		Vector<Tile> tiles = getPath(startTile, endTile);
  		
  		return tiles;
  	}
  	
  	private Vector<Tile> getPath(Tile startTile, Tile endTile)
  	{
  		Vector<Tile> tiles = new Vector<Tile>();
  		
  		Tile currentTile = endTile;
  		do
  		{
  			tiles.add(currentTile);
  		    currentTile = getNextTile(currentTile);
  		   // System.out.println(currentTile.x + ", " + currentTile.y + " - " + distances[currentTile.x][currentTile.y]);
  		    if (currentTile == startTile) return tiles;
  		}while (true);
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
  				 min=dist;
  				minTile = tile;
  			}
  		}
  		if (currentTile.y >0)
  		{
  			Tile tile = ts.getTile(currentTile.x, currentTile.y-1);
  			int dist = distances[tile.x][tile.y];
  			if (dist < min)
  			{
  				 min=dist;
  				minTile = tile;
  			}
  		}
  		if (currentTile.x >0 && currentTile.y >0)
  		{
  			Tile tile = ts.getTile(currentTile.x-1, currentTile.y-1);
  			int dist = distances[tile.x][tile.y];
  			if (dist < min)
  			{
  				 min=dist;
  				minTile = tile;
  			}
  		}
  		if (currentTile.x < size-2)
  		{
  			Tile tile = ts.getTile(currentTile.x+1, currentTile.y);
  			int dist = distances[tile.x][tile.y];
  			if (dist < min)
  			{
  				 min=dist;
  				minTile = tile;
  			}
  		}
  		if (currentTile.y < size-2)
  		{
  			Tile tile = ts.getTile(currentTile.x, currentTile.y+1);
  			int dist = distances[tile.x][tile.y];
  			if (dist < min)
  			{
  				 min=dist;
  				minTile = tile;
  			}
  		}
  		if (currentTile.x < size-2 && currentTile.y < size-2)
  		{
  			Tile tile = ts.getTile(currentTile.x+1, currentTile.y+1);
  			int dist = distances[tile.x][tile.y];
  			if (dist < min)
  			{
  				 min=dist;
  				minTile = tile;
  			}
  		}
  		if (currentTile.x >0 && currentTile.y < size-2)
  		{
  			Tile tile = ts.getTile(currentTile.x-1, currentTile.y+1);
  			int dist = distances[tile.x][tile.y];
  			if (dist < min)
  			{
  				 min=dist;
  				minTile = tile;
  			}
  		}
  		if (currentTile.x < size-2 && currentTile.y >0)
  		{
  			Tile tile = ts.getTile(currentTile.x+1, currentTile.y-1);
  			int dist = distances[tile.x][tile.y];
  			if (dist < min)
  			{
  				 min=dist;
  				minTile = tile;
  			}
  		}
  		return minTile;
  	}
  	
  	private void setDistances(Tile startTile, int distance)
  	{
  		distances[startTile.x][startTile.y] = distance;
  		if (startTile.x >0)
  		{
  			Tile currentDest = ts.getTile(startTile.x-1, startTile.y);
  			int moveValue = getTileMoveAbility(currentDest);
  			int currentValue = distance + moveValue;
  			if (currentValue < distances[currentDest.x][currentDest.y])
  				setDistances(currentDest, currentValue);
  		}
  		if (startTile.y >0)
  		{
  			Tile currentDest = ts.getTile(startTile.x, startTile.y-1);
  			int moveValue = getTileMoveAbility(currentDest);
  			int currentValue = distance + moveValue;
  			if (currentValue < distances[currentDest.x][currentDest.y])
  				setDistances(currentDest, currentValue);
  		}
  		if (startTile.x >0 && startTile.y >0)
  		{
  			Tile currentDest = ts.getTile(startTile.x-1, startTile.y-1);
  			int moveValue = getTileMoveAbility(currentDest);
  			int currentValue = distance + moveValue;
  			if (currentValue < distances[currentDest.x][currentDest.y])
  				setDistances(currentDest, currentValue);
  		}
  		if (startTile.x < size-2)
  		{
  			Tile currentDest = ts.getTile(startTile.x+1, startTile.y);
  			int moveValue = getTileMoveAbility(currentDest);
  			int currentValue = distance + moveValue;
  			if (currentValue < distances[currentDest.x][currentDest.y])
  				setDistances(currentDest, currentValue);
  		}
  		if (startTile.y < size-2)
  		{
  			Tile currentDest = ts.getTile(startTile.x, startTile.y+1);
  			int moveValue = getTileMoveAbility(currentDest);
  			int currentValue = distance + moveValue;
  			if (currentValue < distances[currentDest.x][currentDest.y])
  				setDistances(currentDest, currentValue);
  		}
  		if (startTile.x < size-2 && startTile.y < size-2)
  		{
  			Tile currentDest = ts.getTile(startTile.x+1, startTile.y+1);
  			int moveValue = getTileMoveAbility(currentDest);
  			int currentValue = distance + moveValue;
  			if (currentValue < distances[currentDest.x][currentDest.y])
  				setDistances(currentDest, currentValue);
  		}
  		
  		if (startTile.x >0  && startTile.y < size-2)
  		{
  			Tile currentDest = ts.getTile(startTile.x-1, startTile.y+1);
  			int moveValue = getTileMoveAbility(currentDest);
  			int currentValue = distance + moveValue;
  			if (currentValue < distances[currentDest.x][currentDest.y])
  				setDistances(currentDest, currentValue);
  		}
  		
  		if (startTile.x < size-2 && startTile.y >0)
  		{
  			Tile currentDest = ts.getTile(startTile.x+1, startTile.y-1);
  			int moveValue = getTileMoveAbility(currentDest);
  			int currentValue = distance + moveValue;
  			if (currentValue < distances[currentDest.x][currentDest.y])
  				setDistances(currentDest, currentValue);
  		}
  	}
  	
  	private int getTileMoveAbility(Tile tileIn)
  	{
  		//This is how much is added to the distance variable per tile move bigger = slower
  		//negative = er no cannot do it
  		if (tileIn.id == TileId.WATER) return 9999;
  		if (tileIn.id == TileId.GRASS) return 99;
  		if (tileIn.id == TileId.DIRT) return 99;
			return 9999;
  	}
  	
	}

