package Sprite;

import TileSystem.TileSystem;
import TileSystem.TileAttr;
import org.newdawn.slick.geom.Point;

import java.security.InvalidParameterException;
import java.util.HashMap;

/**
 * Assuming 32x32 tiles,
 * Sprites are
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
