package Sprite;

import TileSystem.TileAttr;
import org.newdawn.slick.geom.Point;

import java.util.HashMap;

/**
 * Assuming 32x32 tiles,
 * Created by andy on 23/01/15.
 */
public class Sprite {

    private static HashMap<TileAttr,Point> hashMap = new HashMap<TileAttr,Point>() {{
        put(TileAttr.RELIGIOUS_ARTIFACT, new Point(0,0));
        put(TileAttr.SKELETON, new Point(32,0));
        put(TileAttr.CORPSE, new Point(32*2,0));
        put(TileAttr.PINE_TREE, new Point(32*3,0));
        put(TileAttr.TREE, new Point(32*4,0));
        put(TileAttr.PALM_TREE, new Point(32*5,0));
        put(TileAttr.HUT, new Point(32*6,0));
        put(TileAttr.ALIEN_ARTIFACT, new Point(32*7,0));
        put(TileAttr.FIRE, new Point(32*8,0));
        put(TileAttr.SHRUB, new Point(32*9,0));
        put(TileAttr.HUTONFIRE, new Point(32*10,0));
        put(TileAttr.WEB, new Point(32*11,0));
        put(TileAttr.WEBBED_TREE, new Point(32*12,0));
        put(TileAttr.ALTAR, new Point(32*13,0));
        put(TileAttr.WRECKAGE, new Point(32*14,0));
        put(TileAttr.CAVE, new Point(32*15,0));
    }};

    // Returns the location of the tile in the sprite file
    public static Point getSprite(TileAttr tileAttr) {
        if (hashMap.containsKey(tileAttr)) {
            return hashMap.get(tileAttr);
        } else {
        	return null;
        }
    }

}
