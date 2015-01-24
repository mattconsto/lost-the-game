package TileSystem;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

import Sprite.GroundSprite;

public class TileSystem {
	
	private Camera camera;
	public float zoomLevel = 1;
	private int tileRes = 32;
	private int size;
	
	private Image tileMap;
	
	private final Color semi = new Color(0, 0, 0, 0.5f);
	
	public enum TileId{
		GRASS,
		GRASSWATER,
		GRASSDIRT,
		STONE,
		SAND,
		SANDWATER,
		OCEAN,
		DIRT,
	}
	
	public class Tile{
		TileId id;
		int variant = -1;
		int vis = 0; //0 = unseen, 1 = seen, 2 = visible
		
		public Tile(TileId id, int vis){
			this.id = id;
			this.vis = vis;
		}
		
		public Tile(TileId id, int vis, int variant){
			this.id = id;
			this.vis = vis;
			this.variant = variant;
		}
	}
	
	private Tile tiles[][];
	
	public TileSystem(int size){
		this.size = size;
		tiles = new Tile[size][size];
		camera = new Camera(0, 0);
		
		setTileMap("dg_edging132.gif");
		
		for(int x = 0; x < size; x++){
            for(int y = 0; y < size; y++){
            	tiles[x][y] = new Tile(TileId.GRASS, 1);
            }
		}
		tiles[5][5] = new Tile(TileId.GRASS, 0);
		tiles[6][5] = new Tile(TileId.GRASS, 2);
		tiles[7][5] = new Tile(TileId.GRASS, 2);
		tiles[8][5] = new Tile(TileId.GRASS, 2);
		tiles[9][5] = new Tile(TileId.GRASS, 2);
		tiles[10][5] = new Tile(TileId.GRASS, 2);
		tiles[7][6] = new Tile(TileId.GRASS, 2);
		tiles[8][6] = new Tile(TileId.GRASS, 2);
		tiles[9][6] = new Tile(TileId.GRASS, 2);
		tiles[10][6] = new Tile(TileId.GRASS, 2);
		
		//Change variants
		//GroundSprite.setVariants(tiles);
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
	
	public int getSize(){
		return size;
	}
	
	public Vector2f screenToWorldPos(int scX, int scY){
		return new Vector2f(camera.x+scX, camera.y+scY);
	}
	
	public Vector2f worldToScreenPos(float worldX, float worldY){
		return new Vector2f(worldX - camera.x, worldY - camera.y);
	}
	
	public void setZoom(float zoomLevel){
		this.zoomLevel = zoomLevel;
	}
	
	public void render(Graphics g){
		renderTiles(g);
		renderFog(g);
	}
	
	private void renderTiles(Graphics g){
		float resTimesScale = tileRes * zoomLevel;
		float finalX, finalY;
		int xOffset = 0, yOffset = 1;
		
		for(int x = 0; x < size; x++){
            for(int y = 0; y < size; y++){
            	finalX = (x*resTimesScale)+xOffset;
            	finalY = (y*resTimesScale)+yOffset;
            	Point src = GroundSprite.getSprite(tiles[x][y].id, tiles[x][y].variant);
            	g.drawImage(tileMap, finalX, finalY, finalX+resTimesScale, finalY+resTimesScale, src.getX(), src.getY(), src.getX()+tileRes, src.getY()+tileRes);
            }
        }
	}
	
	private void renderFog(Graphics g){
		float resTimesScale = tileRes * zoomLevel;
		float finalX, finalY;
		int xOffset = 0, yOffset = 1;
		
		for(int x = 0; x < size; x++){
            for(int y = 0; y < size; y++){
            	finalX = (x*resTimesScale)+xOffset;
            	finalY = (y*resTimesScale)+yOffset;
            	switch(tiles[x][y].vis){
	            	case 0:
	            		g.setColor(Color.black);
	            		g.fillRect(finalX, finalY, resTimesScale, resTimesScale);
	            		break;
	            	case 1:
	            		g.setColor(semi);
	            		g.fillRect(finalX, finalY, resTimesScale, resTimesScale);
	            		break;
            	}
            }
		}
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
			tileMap.setFilter(Image.FILTER_NEAREST);
		} catch (SlickException e) {
			System.out.println("Error: Cannot load image " + fileName);
			e.printStackTrace();
		}
	}

}
