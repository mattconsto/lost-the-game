package Sprite;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.geom.Point;

import TileSystem.TileAttr;
import TileSystem.TileSystem;

/**
 * Created by andy on 24/01/15.
 */
public class GroundSprite {
    /* TILES WE CAN'T HAVE
    * -------------------
    * 1-wide Grass against Dirt -> Substitute Dirt.
    * 1-wide Dirt against Water -> Substitute Water.
    * Grass with >1 corners and 0 sides of Dirt -> Substitute Dirt.
    * Dirt with >1 corners and 0 sides of Water -> Substitute Water.
    * Grass surrounded by > 2 sides of Dirt -> Substitute Dirt.
    * Dirt surrounded by > 2 sides of Water -> Substitute Water.
    * */

    //returns the location of the tile in grid
    public static Point getSprite(TileSystem.TileId tileId, TileSystem.TileId touching, int variant) {
        if (variant == -1) {
            variant = 0;
        }
        if (tileToLocation.containsKey(tileId)) {
            HashMap<TileSystem.TileId,ArrayList<Point>> allVariants = tileToLocation.get(tileId);
            ArrayList<Point> variants;

            if (allVariants.containsKey(touching)) {
                variants = allVariants.get(touching);
            } else {
                variants = allVariants.get(null);
            }

            Point loc;
            if (variants.size() < variant+1) {
                loc = variants.get(0);
            } else {
                loc = variants.get(variant);
            }
            return new Point(loc.getX()*32,loc.getY()*32);
        } else {
            throw new InvalidParameterException("Tile type sprite not implemented yet.");
        }
    }

    public static HashMap<TileSystem.TileId, HashMap<TileSystem.TileId, ArrayList<Point>>> tileToLocation = new HashMap<TileSystem.TileId,HashMap<TileSystem.TileId,  ArrayList<Point>>>(){{

        // Water is the highest priority, so it only has 1 variation.
        put(TileSystem.TileId.WATER, new HashMap<TileSystem.TileId,ArrayList<Point>>() {{
            put(null, new ArrayList<Point>()
            {{
                add(new Point(1,14));// ┼
            }});
        }});

        // Dirt
        put(TileSystem.TileId.DIRT, new HashMap<TileSystem.TileId,ArrayList<Point>>() {
            {
                // Dirt touching nothing
                put(null, new ArrayList<Point>() {{
                    add(new Point(5, 14));// ┼
                }});

                // Dirt touching Water
                put(TileSystem.TileId.WATER, new ArrayList<Point>() {{
                    // Corner Adjacent Water
                    add(new Point(4 + 1, 4 + 1));// ┌
                    add(new Point(4 + 2, 4 + 1));// ┐
                    add(new Point(4 + 1, 4 + 2));// └
                    add(new Point(4 + 2, 4 + 2));// ┘
                    // 1 Side adjacent Water
                    add(new Point(4 + 1, 4 + 0));// ┬
                    add(new Point(4 + 0, 4 + 1));// ├
                    add(new Point(4 + 3, 4 + 1));// ┤
                    add(new Point(4 + 1, 4 + 3));// ┴
                    // 2 Side adjacent Water
                    add(new Point(4 + 0, 4 + 0));// ┌
                    add(new Point(4 + 3, 4 + 0));// ┐
                    add(new Point(4 + 0, 4 + 3));// └
                    add(new Point(4 + 3, 4 + 3));// ┘
                }});
        }});
        // Grass
        put(TileSystem.TileId.GRASS, new HashMap<TileSystem.TileId,ArrayList<Point>>() {{
            // Grass only
            put(null, new ArrayList<Point>()
            {{
                add(new Point(1, 15));// ┼
            }});

            // Grass touching Water
            put(TileSystem.TileId.WATER, new ArrayList<Point>()
            {{
                // Corner Adjacent Water
                add(new Point(1, 1));// ┌
                add(new Point(2, 1));// ┐
                add(new Point(1, 2));// └
                add(new Point(2, 2));// ┘
                // 1 Side adjacent Water
                add(new Point(1, 0));// ┬
                add(new Point(0, 1));// ├
                add(new Point(3, 1));// ┤
                add(new Point(1, 3));// ┴
                // 2 Side adjacent Water
                add(new Point(0, 0));// ┌
                add(new Point(3, 0));// ┐
                add(new Point(0, 3));// └
                add(new Point(3, 3));// ┘
            }});

            // Grass touching Dirt
            put(TileSystem.TileId.DIRT, new ArrayList<Point>()
            {{
                // Corner Adjacent Dirt
                add(new Point(1, 4 + 1));// ┌
                add(new Point(2, 4 + 1));// ┐
                add(new Point(1, 4 + 2));// └
                add(new Point(2, 4 + 2));// ┘
                // 1 Side Adjacent Dirt
                add(new Point(1, 4 + 0));// ┬
                add(new Point(0, 4 + 1));// ├
                add(new Point(3, 4 + 1));// ┤
                add(new Point(1, 4 + 3));// ┴
                // 2 Side Adjacent Dirt
                add(new Point(0, 4 + 0));// ┌
                add(new Point(3, 4 + 0));// ┐
                add(new Point(0, 4 + 3));// └
                add(new Point(3, 4 + 3));// ┘
                // Dirt on either side is Illegal
                // 3 Side Adjacent Dirt is Illegal
            }});

            // Grass touching Dirt
            put(TileSystem.TileId.DIRT, new ArrayList<Point>()
            {{
                    // Corner Adjacent Dirt
                    add(new Point(1, 4 + 1));// ┌
                    add(new Point(2, 4 + 1));// ┐
                    add(new Point(1, 4 + 2));// └
                    add(new Point(2, 4 + 2));// ┘
                    // 1 Side Adjacent Dirt
                    add(new Point(1, 4 + 0));// ┬
                    add(new Point(0, 4 + 1));// ├
                    add(new Point(3, 4 + 1));// ┤
                    add(new Point(1, 4 + 3));// ┴
                    // 2 Side Adjacent Dirt
                    add(new Point(0, 4 + 0));// ┌
                    add(new Point(3, 4 + 0));// ┐
                    add(new Point(0, 4 + 3));// └
                    add(new Point(3, 4 + 3));// ┘
                    // Dirt on either side is Illegal
                    // 3 Side Adjacent Dirt is Illegal
                }});
        }});

        // Snow
        put(TileSystem.TileId.SNOW, new HashMap<TileSystem.TileId,ArrayList<Point>>() {{
            put(null, new ArrayList<Point>()
            {{
                add(new Point(8,0));// ┼
            }});
        }});

        // Rock
        put(TileSystem.TileId.ROCK, new HashMap<TileSystem.TileId,ArrayList<Point>>() {{
            put(null, new ArrayList<Point>()
            {{
                add(new Point(8,1));// ┼
            }});
        }});

        // Wall
        put(TileSystem.TileId.WALL, new HashMap<TileSystem.TileId,ArrayList<Point>>() {{
            put(null, new ArrayList<Point>()
            {{
                    add(new Point(8,4));// ┼
                }});
        }});

    }};

}
