package deserted.tilesystem;

import java.io.IOException;
import java.util.List;

import deserted.engine.math.Vector2f;
import deserted.map.LocalMapLoader;
import deserted.model.AgentState;
import deserted.model.GameSession;
import deserted.model.ItemType;
import deserted.player.PlayerUI;
import deserted.sprite.Sprite;
import deserted.sprite.SpriteManager;
import deserted.sprite.SpriteType;

public class TileSystem {
	
	public Camera camera;
	public int tileRes = 32;
	public int size;
	float resTimesScale = 32;
	
	private Image tileMap;
	private Image spriteMap;
	
	//private Renderer r;
	
	//private final Color semi = new Color(0, 0, 0, 0.3f);

	public enum TileId{
		GRASS,
		DIRT,
		WATER,
		SNOW,
		ROCK,
		WALL,
		POND,
		TARPIT,
		WRECKAGE
	}

	private Tile tiles[][];
	
	public TileSystem(Vector2f windowSize) {
		LocalMapLoader loader = new LocalMapLoader();
		//PerlinMapGenerator loader = new PerlinMapGenerator();

		camera = new Camera(20, 20, tileRes, windowSize);
		resTimesScale = tileRes * camera.zoom;
		
		setTileMap("dg_edging132.gif");
		setSpriteMap("itemsprites.gif");

		try {
			tiles = loader.load("2");
		} catch (IOException e) {
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
        		finalX = (x*resTimesScale)-offsets.x;
        		finalY = (y*resTimesScale)-offsets.y;
        		if(isOnScreen(x, y)){
            		Point src = TileImage.getTexCoord(tiles[x][y].id, tiles[x][y].touching, tiles[x][y].variant);
            		g.drawImage(tileMap, finalX, finalY, finalX+resTimesScale, finalY+resTimesScale, src.getX(), src.getY(), src.getX()+tileRes, src.getY()+tileRes);
            	}
            }
        }
	}
	
	public void renderGroundSprites(Graphics g, int row){
		float finalX, finalY, scale, scaleOffset;
		Sprite sprite;
		SpriteType type;
		
		Vector2f offsets = camera.getOffsets();
        for(int x = 0; x < size; x++){
        	type = tiles[x][row].getSpriteToDraw();
        	sprite = SpriteManager.getSprite(type);
        	if(sprite != null && sprite.isOnGround()){
	        	scale = sprite.getScale();
	        	scaleOffset = (scale - 1)*resTimesScale*0.5f;
	    		finalX = (x*resTimesScale)-offsets.x-scaleOffset;
	    		finalY = (row*resTimesScale)-offsets.y-scaleOffset;
	    		if(isOnScreen(x, row)){
            		Point src = sprite.getTexCoord(tiles[x][row].getSpriteData(type).timeOffset);
            		if(src != null)
            			g.drawImage(spriteMap, finalX, finalY, finalX+resTimesScale+scaleOffset*2, finalY+resTimesScale+scaleOffset, src.getX(), src.getY(), src.getX()+tileRes, src.getY()+tileRes);
	        	}
        	}
        }
	}
	
	public void render3DSprites(Graphics g, int row){
		float finalX, finalY, scale, scaleOffset;
		Sprite sprite;
		SpriteType type;
		
		Vector2f offsets = camera.getOffsets();
        for(int x = 0; x < size; x++){
        	type = tiles[x][row].getSpriteToDraw();
        	sprite = SpriteManager.getSprite(type);
        	if(sprite != null && !sprite.isOnGround()){
	        	scale = sprite.getScale();
	        	scaleOffset = (scale - 1)*resTimesScale*0.5f;
	    		finalX = (x*resTimesScale)-offsets.x-scaleOffset;
	    		finalY = (row*resTimesScale)-offsets.y-scaleOffset*2;
	    		if(isOnScreen(x, row)){
	    			Point src = sprite.getTexCoord(tiles[x][row].getSpriteData(type).timeOffset);
            		if(src != null)
            			g.drawImage(spriteMap, finalX, finalY, finalX+resTimesScale+scaleOffset*2, finalY+resTimesScale+scaleOffset*2, src.getX(), src.getY(), src.getX()+tileRes, src.getY()+tileRes);
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
            	if (tiles[x][y].vis ==0)
            	{
            		g.setColor(Color.black);
            		g.fillRect(finalX, finalY, resTimesScale, resTimesScale);
            	}
            	else if (tiles[x][y].vis <100)
            	{
            		g.setColor(new Color(0, 0, 0,1.0f-((float)tiles[x][y].vis)/100));
            		g.fillRect(finalX, finalY, resTimesScale, resTimesScale);       
            	}
            }
		}
	}
	
	public void update(List<PlayerUI> players, GameSession gs, float delta){
		for(int x = 0; x < size; x++){
            for(int y = 0; y < size; y++){
            	if(tiles[x][y].vis > 30)
            		tiles[x][y].vis = 30;
            	tiles[x][y].update();
            }
		}
		
		for(int x = 0; x < size; x++){
            for(int y = 0; y < size; y++){
            	if(tiles[x][y].hasSprite(SpriteType.FIRE)){
            		clearFowArea((float)x, (float)y,8);
            	}
            }
		}
		
		for(PlayerUI p : players){
			if (p.agent.getState() != AgentState.DEAD)
			{
				float xp = p.location.x-0.5f;
				float yp = p.location.y-0.5f;
				if(gs.getInventory().getItemCount(ItemType.FIRESTICK) > 0){
					clearFowArea(xp,yp,8);
				}else{
					clearFowArea(xp,yp,3);
				}
			}
		}
		Sprite.elapsedTime += delta;
		Tile.deltaPassed += delta;
	}
	
	private void clearFowArea(float xp, float yp, int fowClearArea)
	{
		//Hi, hope everyone likes this, if not just disable new Fog of war here
		boolean progressiveFow= true;
		int area = 30;
		int fowScaler = 25;
		for(int x = (int)xp - area; x < xp + area; x++){
			for(int y = (int)yp - area; y < yp + area; y++){
				if(x > 0 && y > 0 && x < size && y < size)
				{
					if (progressiveFow)	{
						float playerDist = dist(xp, yp, x, y);
						int vis = 100-((int)(((playerDist)-fowClearArea)*fowScaler));
						if (vis <0) vis =0;
						if (vis>100) vis = 100;
						if (vis>tiles[x][y].vis) tiles[x][y].vis =vis;
					}
					else{
						float playerDist = dist((int)(xp+0.5f), (int)(yp+0.5f), x, y);
						if (playerDist <= fowClearArea+1) {
							
							if (tiles[x][y].vis < 100) tiles[x][y].vis =100;
						}
					}
				}
			}
		}
	}
	
	private float dist(float x, float y, float x2, float y2){
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
			tileMap = new Image("images/tiles/"+fileName);
			tileMap.setFilter(Image.FILTER_NEAREST);
		} catch (SlickException e) {
			System.out.println("Error: Cannot load image " + fileName);
			e.printStackTrace();
		}
	}
	
	public void setSpriteMap(String fileName){
		try {
			spriteMap = new Image("images/tiles/"+fileName);
			spriteMap.setFilter(Image.FILTER_NEAREST);
		} catch (SlickException e) {
			System.out.println("Error: Cannot load image " + fileName);
			e.printStackTrace();
		}
	}

}
