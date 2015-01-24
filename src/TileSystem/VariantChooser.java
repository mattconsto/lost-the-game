package TileSystem;

import java.util.ArrayList;

/**
 * Created by andy on 24/01/15.
 */
public class VariantChooser {
    private int size;
    private Tile[][] tiles;

    // The Priority is the order that the system will prioritise the spread of a single tile.
    // (i.e. if water is highest priority, and there is a single tile of water in a grid of grass,
    // it will look like there are 9 tiles of water)
    private static final ArrayList<TileSystem.TileId> tilePriority  = new ArrayList<TileSystem.TileId>(){{
        // (Lower number means higher priority)
        // if you change this, you'll need to reorder GroundSprite.java
        add(TileSystem.TileId.WATER);
        add(TileSystem.TileId.DIRT);
        add(TileSystem.TileId.GRASS);
    }};

    public VariantChooser(int size, Tile[][] tiles) {
        this.size = size;
        this.tiles = tiles;
    }

    public void setVariants() {
        for(int x = 0; x < size; x++){
            for(int y = 0; y < size; y++){
                setVariant(x,y);
            }
        }
    }

    private boolean setVariant(int x, int y) {
        Tile tile = tiles[x][y];
        //Up Left
        TileSystem.TileId upLeft = getTileType(x-1,y-1);
        //Up
        TileSystem.TileId upMiddle = getTileType(x,y-1);
        //Up Right
        TileSystem.TileId upRight = getTileType(x+1,y-1);
        //Middle Left
        TileSystem.TileId middleLeft = getTileType(x-1,y);
        //Middle Right
        TileSystem.TileId middleRight = getTileType(x+1,y);
        //Down Left
        TileSystem.TileId downLeft = getTileType(x-1,y+1);
        //Down
        TileSystem.TileId downMiddle = getTileType(x,y+1);
        //Down Right
        TileSystem.TileId downRight = getTileType(x+1,y+1);

        // Try tiles in order of priority.
        for (TileSystem.TileId type : tilePriority) {
            // Check the sides for this type
            int sides = ((upMiddle == type)?1:0)+((middleLeft == type)?1:0)+((middleRight == type)?1:0)+((downMiddle == type)?1:0);

            // 0 Sides
            if (sides == 0) {
                // Check the corners for this type
                int corners = ((upLeft == type) ? 1 : 0) + ((upRight == type) ? 1 : 0) + ((downLeft == type) ? 1 : 0) + ((downRight == type) ? 1 : 0);

                if (corners == 1) {
                    if(downRight == type) {
                        tile.variant += 0;
                    }else if(downLeft == type) {
                        tile.variant += 1;
                    }else if(upRight == type) {
                        tile.variant += 2;
                    }else if(upLeft == type) {
                        tile.variant += 3;
                    }
                    return true;

                // ERROR Case: 2 or more corners with no sides.
                } else if(corners > 1) {
                    return blanken(tile);
                }

            // 1 Side
            } else if (sides == 1) {
                tile.variant = 4;
                if(upMiddle == type) {
                    tile.variant += 0;
                }else if(middleLeft == type) {
                    tile.variant += 1;
                }else if(middleRight == type) {
                    tile.variant += 2;
                }else if(downMiddle == type) {
                    tile.variant += 3;
                }
                return true;

            // 2 Sides
            } else if (sides == 2) {
                tile.variant = 8;
                // Error Case: 2 opposite sides
                if((upMiddle == type && downMiddle == type)||(middleLeft == type && middleRight == type)) {
                    return blanken(tile);
                }
                if(upMiddle == type && middleLeft == type) {
                    tile.variant += 0;
                } else if(middleRight == type && upMiddle == type){
                    tile.variant += 1;
                } else if(middleLeft == type && downMiddle == type) {
                    tile.variant += 2;
                } else if(downMiddle == type && middleRight == type) {
                    tile.variant += 3;
                }
                return true;

            // ERROR Case: >2 sides
            } else { // if (sides > 2) {
                return blanken(tile);
            }
        }
        return blanken(tile);
    }

    private boolean blanken(Tile tile) {
        tile.touching = null;
        tile.variant = 0;
        return true;
    }

    private TileSystem.TileId getTileType(int x,int y) {
        if (x > size-1 || x < 0 || y > size-1 || y < 0 ) {
            // if it's null, return water.
            return TileSystem.TileId.WATER;
        }
        return tiles[x][y].id;
    }
}
