package Sprite;

import TileSystem.TileSystem;
import org.newdawn.slick.geom.Point;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by andy on 24/01/15.
 */
public class GroundSprite {

    //returns the location of the tile in grid
    public static Point getSprite(TileSystem.TileId tileId, int variant) {
        if (variant == -1) {
            variant = 4;
        }
        if (tileToLocation.containsKey(tileId)) {
            return tileToLocation.get(tileId).get(variant);
        }
        return new Point(1*32,31*32);
    }

    /* Variants for Tile Maps:
        To Water:
        0 - ┌
        1 - ┬
        2 - ┐
        3 - ├      (aka)
        4 - ┼       012
        5 - ┤       345
        6 - └       678
        7 - ┴
        8 - ┘
        To Dirt:
        9 - ┌
        10- ┬
        11- ┐
        12- ├      (aka)
        13- ┼       09 10 11
        14- ┤       12 13 14
        15- └       15 16 17
        16- ┴
        17- ┘
    */

    public static HashMap<TileSystem.TileId,ArrayList<Point>> tileToLocation = new HashMap<TileSystem.TileId,ArrayList<Point>>(){{
        put(TileSystem.TileId.GRASS, new ArrayList<Point>() {{
            // To Grass
            add(new Point(0 ,0 ));// ┌
            add(new Point(1 ,0 ));// ┬
            add(new Point(3 ,0 ));// ┐
            add(new Point(0 ,1 ));// ├
            add(new Point(15,1 ));// ┼
            add(new Point(3 ,1 ));// ┤
            add(new Point(0 ,3 ));// └
            add(new Point(1 ,1 ));// ┴
            add(new Point(1 ,1 ));// ┘
            // To Dirt
            add(new Point(0+4 ,0+4 ));// ┌
            add(new Point(1+4 ,0+4 ));// ┬
            add(new Point(3+4 ,0+4 ));// ┐
            add(new Point(0+4 ,1+4 ));// ├
            add(new Point(15+4,1+4 ));// ┼
            add(new Point(3+4 ,1+4 ));// ┤
            add(new Point(0+4 ,3+4 ));// └
            add(new Point(1+4 ,1+4 ));// ┴
            add(new Point(1+4 ,1+4 ));// ┘

        }});
    }};

}
