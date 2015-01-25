package Sprite;

import TileSystem.TileSystem;
import TileSystem.TileAttr;
import org.newdawn.slick.geom.Point;

import java.security.InvalidParameterException;
import java.util.HashMap;

/**
 * Assuming 32x32 tiles
 * Created by andy on 23/01/15.
 */
public class Sprite {

    private static HashMap<TileAttr,Point> hashMap = new HashMap<TileAttr,Point>() {{
        put(TileAttr.RELIGIOUS_ARTIFACT,new Point(0,0));

    }};

    //returns the location of the tile in grid
    public static Point getSprite(TileAttr tileAttr) {
        if (hashMap.containsKey(tileAttr)) {
            return hashMap.get(tileAttr);
        } else {
        	return null;
            //throw new InvalidParameterException("Missing Sprite for TileID");
        }
    }




}
