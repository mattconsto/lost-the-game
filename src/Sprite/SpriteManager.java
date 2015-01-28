package Sprite;

import org.newdawn.slick.geom.Point;

import java.util.HashMap;

/**
 * Assuming 32x32 tiles,
 * Created by andy on 23/01/15.
 */
public class SpriteManager {
	
	//Static stuff
	private static HashMap<SpriteType, Sprite> hashMap = new HashMap<SpriteType, Sprite>() {{
        put(SpriteType.RELIGIOUS_ARTIFACT, new Sprite(new Point(0,0), true, false, 1f));
        put(SpriteType.SKELETON, new Sprite(new Point(32,0), true, true, 0.6f));
        put(SpriteType.CORPSE, new Sprite(new Point(64,0), true, true, 0.6f));
        put(SpriteType.PINE_TREE, new Sprite(new Point(96,0), true, false, 1.4f));
        put(SpriteType.TREE, new Sprite(new Point(128,0), true, false, 1.3f));
        put(SpriteType.PALM_TREE, new Sprite(new Point(32*5,0), true, false, 1.3f));
        put(SpriteType.HUT, new Sprite(new Point(32*6,0), true, false, 1.2f));
        put(SpriteType.ALIEN_ARTIFACT, new Sprite(new Point(32*7,0), true, false, 1));
        put(SpriteType.FIRE, new Sprite(new Point(32*8,0), true, true, 1.2f));
        put(SpriteType.SHRUB, new Sprite(new Point(32*9,0), true, false, 1));
        put(SpriteType.HUTONFIRE, new Sprite(new Point(32*10,0), true, false, 1.2f));
        put(SpriteType.WEB, new Sprite(new Point(32*11,0), true, false, 1.3f));
        put(SpriteType.WEBBED_TREE, new Sprite(new Point(32*12,0), true, false, 1.3f));
        put(SpriteType.ALTAR, new Sprite(new Point(32*13,0), true, false, 1.2f));
        put(SpriteType.WRECKAGE, new Sprite(new Point(32*14,0), true, true, 1.2f));
        put(SpriteType.CAVE, new Sprite(new Point(32*15,0), true, false, 1.2f));
    }};

    // Returns the location of the sprite in the tex map
    public static Point getTexCoord(SpriteType type) {
        if (hashMap.containsKey(type)) {
            return hashMap.get(type).getTexCoord();
        } else {
        	return null;
        }
    }
    
    // Returns the location of the sprite in the tex map
    public static Sprite getSprite(SpriteType type) {
        if (hashMap.containsKey(type)) {
            return hashMap.get(type);
        } else {
        	return null;
        }
    }
}
