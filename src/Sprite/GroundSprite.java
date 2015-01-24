package Sprite;

import TileSystem.TileSystem;
import org.newdawn.slick.geom.Point;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by andy on 24/01/15.
 */
public class GroundSprite {
    /* TILES WE CAN'T HAVE
    * -------------------
    * 1-wide Grass against Dirt,
    * 1-wide Dirt against Water,
    * Grass with >1 corners and 0 sides of Dirt,
    * Dirt with >1 corners and 0 sides of Water,
    * Grass surrounded by > 2 sides of Dirt
    * Dirt surrounded by > 2 sides of Water
    * */



    //returns the location of the tile in grid
    public static Point getSprite(TileSystem.TileId tileId, int variant) {
        if (variant == -1) {
            variant = 0;
        }
        if (tileToLocation.containsKey(tileId)) {
            ArrayList<Point> variants = tileToLocation.get(tileId);
            if (variants.size() < variant - 1) {
                throw new InvalidParameterException("No variant for this tile type");
            }
            Point loc = variants.get(variant);
            return new Point(loc.getX()*32,loc.getY()*32);
        } else {
            throw new InvalidParameterException("Tile type sprite not implemented yet.");
        }
    }

    public static HashMap<TileSystem.TileId, ArrayList<Point>> tileToLocation = new HashMap<TileSystem.TileId, ArrayList<Point>>(){{
        // Water is the most important, so it only has 1 variation.
        put(TileSystem.TileId.WATER, new ArrayList<Point>() {{
            add(new Point(1,14));// ┼
        }});
        put(TileSystem.TileId.DIRT, new ArrayList<Point>() {{
            add(new Point(5,14));// ┼
            // Corner Adjacent Water
            add(new Point(4+1 ,4+1 ));// ┌
            add(new Point(4+2 ,4+1 ));// ┐
            add(new Point(4+1 ,4+2 ));// └
            add(new Point(4+2 ,4+2 ));// ┘
            // 1 Side adjacent Water
            add(new Point(4+1 ,4+0 ));// ┬
            add(new Point(4+0 ,4+1 ));// ├
            add(new Point(4+3 ,4+1 ));// ┤
            add(new Point(4+1 ,4+3 ));// ┴
            // 2 Side adjacent Water
            add(new Point(4+0 ,4+0 ));// ┌
            add(new Point(4+3 ,4+0 ));// ┐
            add(new Point(4+0 ,4+3 ));// └
            add(new Point(4+3 ,4+3 ));// ┘
        }});
        put(TileSystem.TileId.GRASS, new ArrayList<Point>() {{
            add(new Point(1,15));// ┼

            // Grass -> Water
            // Corner Adjacent Water
            add(new Point(1 ,1 ));// ┌
            add(new Point(2 ,1 ));// ┐
            add(new Point(1 ,2 ));// └
            add(new Point(2 ,2 ));// ┘
            // 1 Side adjacent Water
            add(new Point(1 ,0 ));// ┬
            add(new Point(0 ,1 ));// ├
            add(new Point(3 ,1 ));// ┤
            add(new Point(1 ,3 ));// ┴
            // 2 Side adjacent Water
            add(new Point(0 ,0 ));// ┌
            add(new Point(3 ,0 ));// ┐
            add(new Point(0 ,3 ));// └
            add(new Point(3 ,3 ));// ┘

            // Grass -> Dirt
            // Corner Adjacent Dirt
            add(new Point(1 ,4+1 ));// ┌
            add(new Point(2 ,4+1 ));// ┐
            add(new Point(1 ,4+2 ));// └
            add(new Point(2 ,4+2 ));// ┘
            // 1 Side Adjacent Dirt
            add(new Point(1 ,4+0 ));// ┬
            add(new Point(3 ,4+1 ));// ├
            add(new Point(0 ,4+1 ));// ┤
            add(new Point(1 ,4+3 ));// ┴
            // 2 Side Adjacent Dirt
            add(new Point(0 ,4+0 ));// ┌
            add(new Point(3 ,4+0 ));// ┐
            add(new Point(0 ,4+3 ));// └
            add(new Point(3 ,4+3 ));// ┘
            // Dirt on either side is Illegal
            // 3 Side Adjacent Dirt is Illegal

        }});
    }};

}
