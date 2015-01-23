package TileSystem;

import org.lwjgl.util.vector.Vector2f;

public class TileSystem {
	
	private Camera camera;
	private float zoomLevel = 1;
	
	public enum TileId{
		GRASS,
		STONE,
		SAND,
		OCEAN,
		DIRT,
	}
	
	public class Tile{
		int id;
		byte visibility = 0; //0 = unseen, 1 = seen, 2 = visible
	}
	
	private Tile tiles[][];
	
	public TileSystem(int width, int height){
		tiles = new Tile[width][height];
		camera = new Camera(50, 50);
	}
	
	public void setTile(int x, int y, Tile tile){
		tiles[x][y] = tile;
	}
	
	public Tile getTile(int x, int y){
		return tiles[x][y];
	}
	
	public Tile getTileFromScreen(int x, int y){
		return null;
	}
	
	public Vector2f screenToWorldPos(int scX, int scY){
		return new Vector2f(camera.getX()+scX, camera.getY()+scY);
	}
	
	public void setZoom(float zoomLevel){
		this.zoomLevel = zoomLevel;
	}
	
	public void renderGroundTiles(){
		
	}

}
