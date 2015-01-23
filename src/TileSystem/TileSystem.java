package TileSystem;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class TileSystem {
	
	private Camera camera;
	private float zoomLevel = 1;
	private int tileRes = 32;
	
	private Image tileMap;
	
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
		camera = new Camera(0, 0);
		
		setTileMap("dg_grounds32.gif");
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
		return new Vector2f(camera.x+scX, camera.y+scY);
	}
	
	public void setZoom(float zoomLevel){
		this.zoomLevel = zoomLevel;
	}
	
	public void renderGroundTiles(){
		tileMap.draw(0, 0, 0.8f);
	}
	
	public Camera getCamera(){
		return camera;
	}
	
	public void setTileRes(int res){
		tileRes = res;
	}
	
	public void setTileMap(String fileName){
		try {
			tileMap = new Image("tiles/"+fileName);
		} catch (SlickException e) {
			System.out.println("Error: Cannot load image " + fileName);
			e.printStackTrace();
		}
	}

}
