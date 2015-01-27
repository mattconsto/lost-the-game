package TileSystem;

import java.util.ArrayList;

import Sprite.Sprite;
import Sprite.SpriteManager;

/**
* Created by andy on 24/01/15.
*/
public class Tile {
    public TileSystem.TileId id;

    // The Variant and Touching are purely aesthetic
    public TileSystem.TileId touching = null;
    public int variant = -1;
    
    public SpriteType attr;
    public int attrHealth;
    
    private ArrayList<Sprite> sprites;

    public int vis = 0; //0 = unseen, 1 = seen, 2 = visible
    public int x, y;

    public Tile(TileSystem.TileId id, int x, int y, int vis){
        this.id = id;
        this.vis = vis;
        this.x = x;
        this.y = y;
        this.attr = SpriteType.NONE;
        this.attrHealth = 10;
        
        sprites = new ArrayList<>();
    }

    public Tile(TileSystem.TileId id, int x, int y, int vis, int variant){
        this.id = id;
        this.vis = vis;
        this.x = x;
        this.y = y;
        this.variant = variant;
        this.attr = SpriteType.NONE;
        this.attrHealth = 10;
        
        sprites = new ArrayList<>();
    }
    
    public void addSprite(SpriteType type){
    	sprites.add(SpriteManager.getSprite(type));
    }
    
    public void removeSprite(SpriteType type){
    	sprites.remove(SpriteManager.getSprite(type));
    }
}
