package Sprite;

import TileSystem.TileSystem;
import org.newdawn.slick.Image;

import java.util.HashMap;

/**
 * Assuming 32x32 tiles
 * Created by andy on 23/01/15.
 */
public class Sprite {

    private class SpriteRef {
        int xLoc;
        int yLoc;
        String image;
        public SpriteRef(int xLoc, int yLoc, String image) {
            this.xLoc = xLoc;
            this.yLoc = yLoc;
            this.image = image;
        }
    }

    public Image getSpriteByTileIdAndVariant(TileSystem.TileId tileId, int variant) {
        return null;
    }

    /* Variants for Tile Maps:
        1 - ┌
        2 - ┬
        3 - ┐
        4 - ├      (aka)
        5 - ┼       123
        6 - ┤       456
        7 - └       789
        8 - ┴
        9 - ┘
    */

    public HashMap<TileSystem.TileId,SpriteRef> tiletoLocation = new HashMap<TileSystem.TileId, SpriteRef>(){{
        put(TileSystem.TileId.DIRT,new SpriteRef(1,1,"dg_edging132.gif"));
//        put(TileSystem.TileId.DIRT,new SpriteRef(1,1,"dg_armor32.gif"));
//        put(TileSystem.TileId.DIRT,new SpriteRef(1,1,"dg_armor32.gif"));
    }};


    }
}
