package Map;

import TileSystem.Tile;
import TileSystem.TileSystem;

import java.io.*;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import org.lwjgl.Sys;

/**
 * Created by andy on 24/01/15.
 */
public class PerlinMapGenerator {
    public Tile[][] loadMap() {
    	int mapWidth  = 200;
    	int mapHeight = 200;
    	
        Tile[][] tiles = new Tile[200][200];
        
        boolean[][] land   = new boolean[mapWidth][mapHeight];
        float[][]   height = new float[mapWidth][mapHeight];
        
        PerlinNoiseGenerator gen = new PerlinNoiseGenerator(new Random().nextInt(Integer.MAX_VALUE));
        
        // Generate a circle and create inlets
        for(int x = 0; x < land.length; x++) {
        	for(int y = 0; y < land[x].length; y++) {
        		land[x][y] = Math.sqrt(Math.pow(x - mapHeight/2, 2) + Math.pow(y - mapHeight/2, 2)) < mapHeight/2 - 5
        				  && gen.turbulence2(x, y, (float) 0.04) < 0.8
        				  && gen.turbulence2(x, y, (float) 0.005) < 0.4;
        		
        		height[x][y] = (float) ((gen.turbulence2(x, y, (float) 0.05) + (new Random().nextFloat() - 0.5)) / 2);
        	}
        }

        for(int x = 0; x < 200; x++) {
        	for(int y = 0; y < 200; y++) {
        		if(land[x][y]) {
        			if(height[x][y] > -0.7) {
        				tiles[x][y] = new Tile(TileSystem.TileId.GRASS,x,y,0);
        			} else {
        				tiles[x][y] = new Tile(TileSystem.TileId.DIRT,x,y,0);
        			}
        		} else {
        			tiles[x][y] = new Tile(TileSystem.TileId.WATER,x,y,0);
        		}
        	}
        }
        
        return tiles;
    }
}