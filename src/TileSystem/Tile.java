package TileSystem;

import java.util.ArrayList;
import java.util.Iterator;

import Sprite.SpriteType;

/**
* Created by andy on 24/01/15.
*/
public class Tile {
    public TileSystem.TileId id;

    // The Variant and Touching are purely aesthetic
    public TileSystem.TileId touching = null;
    public int variant = -1;
    public int attrHealth;
    
    public static float deltaPassed = 0;
    public static int spriteSwitchInterval = 1;
    
    public class SpriteData{
    	public SpriteType type;
    	public int health = 10;
    	public float timeOffset = 0;
    	
    	public SpriteData(SpriteType type, float timeOffset){
    		this.type = type;
    		this.timeOffset = timeOffset;
    	}
    }
    
    private ArrayList<SpriteData> sprites;

    public int vis = 0; //0 = unseen, 1 = seen, 2 = visible
    public int x, y;

    public Tile(TileSystem.TileId id, int x, int y, int vis){
        this.id = id;
        this.vis = vis;
        this.x = x;
        this.y = y;
        this.attrHealth = 10;
        
        sprites = new ArrayList<>();
    }

    public Tile(TileSystem.TileId id, int x, int y, int vis, int variant){
        this.id = id;
        this.vis = vis;
        this.x = x;
        this.y = y;
        this.variant = variant;
        this.attrHealth = 10;
        
        sprites = new ArrayList<>();
    }
    
    public void addSprite(SpriteType type){
    	sprites.add(new SpriteData(type, (float)Math.random()*100));
    }

    public void update(){
    	ArrayList<SpriteData> r = new ArrayList<>();
    	for(SpriteData d : sprites){
    		if(d.health <= 0)
    			r.add(d);
    	}
    	for(SpriteData d : r){
    		if(d.health <= 0)
    			sprites.remove(d);
    	}
    }
    
    public SpriteType getSpriteToDraw(){
    	if(sprites.size() > 0){
    		return sprites.get((int)(deltaPassed/spriteSwitchInterval)%sprites.size()).type;
    	}
    	return null;
    }
    
    public SpriteData getSpriteData(SpriteType type){
    	for(SpriteData d : sprites){
    		if(d.type == type)
    			return d;
    	}
    	return null;
    }
    
    public int numSprites(){
    	return sprites.size();
    }
    
    public boolean hasSprite(SpriteType type){
    	for(SpriteData d : sprites){
    		if(d.type == type && d.health != 0)
    			return true;
    	}
    	return false;
    }
    
    public void removeSprite(SpriteType type){
    	Iterator<SpriteData> i = sprites.iterator();
    	SpriteData d;
    	while(i.hasNext()){
    		d = i.next();
    		if(d.type == type){
    			sprites.remove(d);
    			return;
    		}
    	}
    }
    
    public void clearAllSprites(){
    	sprites.clear();
    }
}
