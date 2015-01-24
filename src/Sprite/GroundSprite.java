package Sprite;

import TileSystem.TileSystem;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Transform;

import java.security.InvalidParameterException;
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
        put(TileSystem.TileId.GRASS, new ArrayList<Point>() {{
            add(new Point(15,1 ));// ┼
        }});
        put(TileSystem.TileId.GRASSWATER, new ArrayList<Point>() {{
            add(new Point(0 ,0 ));// ┌
            add(new Point(1 ,0 ));// ┬
            add(new Point(3 ,0 ));// ┐
            add(new Point(0 ,1 ));// ├
            add(new Point(15,1 ));// ┼
            add(new Point(3 ,1 ));// ┤
            add(new Point(0 ,3 ));// └
            add(new Point(1 ,3 ));// ┴
            add(new Point(3 ,3 ));// ┘
        }});
        put(TileSystem.TileId.GRASSDIRT, new ArrayList<Point>() {{
            // To Grass
            add(new Point(1 ,1+4 ));// ┌
            add(new Point(1 ,0+4 ));// ┬
            add(new Point(3 ,0+4 ));// ┐
            add(new Point(0 ,1+4 ));// ├
            add(new Point(15,1+4 ));// ┼
            add(new Point(3 ,1+4 ));// ┤
            add(new Point(0 ,3+4 ));// └
            add(new Point(1 ,1+4 ));// ┴
            add(new Point(1 ,1+4 ));// ┘

        }});

        put(TileSystem.TileId.DIRTWATER, new ArrayList<Point>() {{
            // To Grass
            add(new Point(1 ,1+4 ));// ┌
            add(new Point(1 ,0+4 ));// ┬
            add(new Point(3 ,0+4 ));// ┐
            add(new Point(0 ,1+4 ));// ├
            add(new Point(15,1+4 ));// ┼
            add(new Point(3 ,1+4 ));// ┤
            add(new Point(0 ,3+4 ));// └
            add(new Point(1 ,1+4 ));// ┴
            add(new Point(1 ,1+4 ));// ┘

        }});
    }};

}
