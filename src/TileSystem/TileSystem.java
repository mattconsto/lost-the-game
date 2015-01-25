package TileSystem;

import Map.PerlinMapGenerator;
import Map.SimpleMapLoader;
import Model.AgentState;

import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

import Player.PlayerUI;
import Sprite.GroundSprite;

import java.io.FileNotFoundException;

public class TileSystem {
	
	public Camera camera;
	public int tileRes = 32;
	public int size;
	float resTimesScale = 32;
	
	private Image tileMap;
	
	private final Color semi = new Color(0, 0, 0, 0.3f);

	public enum TileId{
		GRASS,
		DIRT,
		WATER,
		SNOW,
		ROCK,
		WALL
	}

	private Tile tiles[][];
	
	public TileSystem(Point windowSize){
		SimpleMapLoader loader = new SimpleMapLoader();
		//PerlinMapGenerator loader = new PerlinMapGenerator();

		camera = new Camera(34, 29, tileRes, windowSize);
		resTimesScale = tileRes * camera.zoom;
		
		setTileMap("dg_edging132.gif");

		try {
			tiles = loader.loadMap();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.size = tiles[0].length;
		VariantChooser variantChooser = new VariantChooser(size,tiles);
		variantChooser.setVariants();

		for(int x = 0; x < size; x++){
			for(int y = 0; y < size; y++){
				getTile(x, y).x = x;
				getTile(x, y).y = y;
			}
		}
	}
	
	public void setTile(int x, int y, Tile tile){
		tiles[x][y] = tile;
	}

	public void setTileID(int x, int y, TileSystem.TileId id) {
		tiles[x][y].id = id;
		VariantChooser variantChooser = new VariantChooser(size, tiles);
		variantChooser.setVariantAround(x,y);
	}
	
	public Tile getTile(int x, int y){
		return tiles[x][y];
	}
	
	public Tile getTileFromScreen(int x, int y){
		Vector2f world = screenToWorldPos(x, y);
		return getTileFromWorld(world.getX(), world.getY());
	}
	
	public int getSize(){
		return size;
	}
	
	public Tile getTileFromWorld(float x, float y){
		if(x > size || x < 0 || y > size || y < 0)
			return null;
		return tiles[(int)x][(int)y];
	}
	
	public Vector2f screenToWorldPos(int scX, int scY){
		return camera.screenToWorldPos(scX, scY);
	}
	
	public Vector2f worldToScreenPos(float worldX, float worldY){
		return camera.worldToScreenPos(worldX, worldY);
	}
	
	public void zoom(float zoomDelta){
		camera.zoom(zoomDelta);
		resTimesScale = tileRes * camera.zoom;
	}
	
	
	
	public void renderTiles(Graphics g){
		float finalX, finalY;
		
		Vector2f offsets = camera.getOffsets();
		for(int x = 0; x < size; x++){
            for(int y = 0; y < size; y++){
            	if (tiles[x][y].vis != 0)
            	{
            		finalX = (x*resTimesScale)-offsets.x;
            		finalY = (y*resTimesScale)-offsets.y;
            		if(isOnScreen(x, y)){
	            		Point src = GroundSprite.getSprite(tiles[x][y].id, tiles[x][y].touching, tiles[x][y].variant, TileAttr.NONE);
	            		g.drawImage(tileMap, finalX, finalY, finalX+resTimesScale, finalY+resTimesScale, src.getX(), src.getY(), src.getX()+tileRes, src.getY()+tileRes);
            		}
            	}
            }
        }
	}
	
	private boolean isOnScreen(float x, float y){
		Vector2f sc = camera.worldToScreenPos(x, y);
		if(sc.x < -resTimesScale)
			return false;
		if(sc.x > (camera.windowSize.getX()+resTimesScale))
			return false;
		if(sc.y < -resTimesScale)
			return false;
		if(sc.y > (camera.windowSize.getY()+resTimesScale))
			return false;
		return true;
	}
	
	public void renderFog(Graphics g){
		float finalX, finalY;
		
		Vector2f offsets = camera.getOffsets();
		for(int x = 0; x < size; x++){
            for(int y = 0; y < size; y++){
            	finalX = (x*resTimesScale)-offsets.x;
        		finalY = (y*resTimesScale)-offsets.y;
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
	
	public void updateFog(List<PlayerUI> players){
		for(int x = 0; x < size; x++){
            for(int y = 0; y < size; y++){
            	if(tiles[x][y].vis == 2)
            		tiles[x][y].vis = 1;
            }
		}
		
		for(PlayerUI p : players){
			if (p.agent.getState() != AgentState.DEAD)
			{
				int xp = (int)p.location.x;
				int yp = (int)p.location.y;
				for(int x = xp - 5; x < xp + 5; x++){
					for(int y = yp - 5; y < yp + 5; y++){
						if(x > 0 && y > 0 && x < size && y < size)
							if(dist(xp, yp, x, y) <= 4){
								tiles[x][y].vis = 2;
							}
					}
				}
			}
		}
	}
	
	private float dist(int x, int y, int x2, int y2){
		return (float) Math.sqrt((x2-x)*(x2-x)+(y2-y)*(y2-y));
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
