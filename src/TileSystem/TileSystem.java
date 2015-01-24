package TileSystem;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class TileSystem {
	
	private Camera camera;
	private float zoomLevel = 1;
	private int tileRes = 32;
	private int width, height;
	
	private Image tileMap;
	
	public enum TileId{
		GRASS,
		STONE,
		SAND,
		OCEAN,
		DIRT,
	}
	
	public class Tile{
		TileId id;
		int vis = 0; //0 = unseen, 1 = seen, 2 = visible
		
		public Tile(TileId id, int vis){
			this.id = id;
			this.vis = vis;
		}
	}
	
	private Tile tiles[][];
	
	public TileSystem(int width, int height){
		this.width = width; this.height = height;
		tiles = new Tile[width][height];
		camera = new Camera(0, 0);
		
		setTileMap("dg_grounds32.gif");
		
		for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
            	tiles[x][y] = new Tile(TileId.GRASS, 2);
            }
		}
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
	
	public void render(Graphics g){
		float resTimesScale = tileRes * zoomLevel;
		float finalX, finalY;
		int xOffset = 0, yOffset = 1;
		
		for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
            	finalX = (x*resTimesScale)+xOffset;
            	finalY = (y*resTimesScale)+yOffset;
            	switch(tiles[x][y].id){
	                case GRASS:
	                	if(tiles[x][y].vis == 0)
	                		g.fillRect(finalX, finalY, resTimesScale, resTimesScale);
	                	g.drawImage(tileMap, finalX, finalY, finalX+resTimesScale, finalY+resTimesScale, 0, tileRes, tileRes, tileRes*2);
	                	break;
//	                case STONE:
//	                    g.drawImage(tileMap, (x*tileRes*scale)+xOffset, (y*resolution*scale)+yOffset, (x*resolution*scale)+xOffset+resolution*scale, (y*resolution*scale)+yOffset+resolution*scale, 0, 0, resolution, resolution);
//	                    break;
//	                case DIRT:
//	                    g.drawImage(tileMap, (x*tileRes*scale)+xOffset, (y*resolution*scale)+yOffset, (x*resolution*scale)+xOffset+resolution*scale, (y*resolution*scale)+yOffset+resolution*scale, resolution, 0, resolution+resolution, resolution);
//	                    break;
//	                case OCEAN:
//	                    g.drawImage(tileMap, (x*tileRes*scale)+xOffset, (y*resolution*scale)+yOffset, (x*resolution*scale)+xOffset+resolution*scale, (y*resolution*scale)+yOffset+resolution*scale, resolution*2, 0, resolution+resolution*2, resolution);
//	                    break;
//	                case SAND:
//	                    g.drawImage(tileMap, (x*tileRes*scale)+xOffset, (y*resolution*scale)+yOffset, (x*resolution*scale)+xOffset+resolution*scale, (y*resolution*scale)+yOffset+resolution*scale, resolution*3, 0, resolution+resolution*3, resolution);
//	                    break;
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
