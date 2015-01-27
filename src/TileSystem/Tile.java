package TileSystem;

/**
* Created by andy on 24/01/15.
*/
public class Tile {
    public TileSystem.TileId id;

    // The Variant and Touching are purely aesthetic
    public TileSystem.TileId touching = null;
    public int variant = -1;
    
    public TileAttr attr;
    public int attrHealth;
    
    

    public int vis = 0; //0 = unseen, 1 = seen, 2 = visible
    public int x, y;

    public Tile(TileSystem.TileId id, int x, int y, int vis){
        this.id = id;
        this.vis = vis;
        this.x = x;
        this.y = y;
        this.attr = TileAttr.NONE;
        this.attrHealth = 10;
    }

    public Tile(TileSystem.TileId id, int x, int y, int vis, int variant){
        this.id = id;
        this.vis = vis;
        this.x = x;
        this.y = y;
        this.variant = variant;
        this.attr = TileAttr.NONE;
        this.attrHealth = 10;
    }
}
