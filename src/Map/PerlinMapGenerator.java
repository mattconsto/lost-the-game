package map;

import TileSystem.Tile;
import TileSystem.TileSystem;

import java.util.Random;

import libs.PerlinNoiseGenerator;
import map.interfaces.MapGenerator;

/**
 * PerlinMapGenerator, generates a map using Perlin Noise.
 * 
 * @author Adny and Matthew
 */
public class PerlinMapGenerator implements MapGenerator {
    public Tile[][] generate(int size) {
    	
        Tile[][] tiles = new Tile[size][size];
        boolean[][] land   = new boolean[size][size];
        float[][]   height = new float[size][size];
        
        PerlinNoiseGenerator gen = new PerlinNoiseGenerator(new Random().nextInt(Integer.MAX_VALUE));
        
        // Generate a circle and create inlets
        for(int x = 0; x < land.length; x++) {
        	for(int y = 0; y < land[x].length; y++) {
        		land[x][y] = Math.sqrt(Math.pow(x - size/2, 2) + Math.pow(y - size/2, 2)) < size/2 - 5
        				  && gen.turbulence2(x, y, (float) 0.04) < 0.8
        				  && gen.turbulence2(x, y, (float) 0.005) < 0.4;
        		
        		height[x][y] = (float) ((gen.turbulence2(x, y, (float) 0.05) + (new Random().nextFloat() - 0.5)) / 2);
        	}
        }

        // Ewwww
        for(int x = 0; x < 200; x++) {
        	for(int y = 0; y < 200; y++) {
        		if(land[x][y]) {
        			if(height[x][y] > -3) {
        				tiles[x][y] = new Tile(TileSystem.TileId.SNOW,x,y,0);
        			} else if(height[x][y] > -2) {
        				tiles[x][y] = new Tile(TileSystem.TileId.ROCK,x,y,0);
        			} else if(height[x][y] > -0.7) {
        				tiles[x][y] = new Tile(TileSystem.TileId.GRASS,x,y,0);
        			} else {
        				tiles[x][y] = new Tile(TileSystem.TileId.DIRT,x,y,0);
        			}
        		} else {
        			tiles[x][y] = new Tile(TileSystem.TileId.WATER,x,y,0);
        		}
        	}
        }
        
        System.out.format("PerlinMapGenerator\tMap of size %d generated.\n", size);
        
        return tiles;
    }
}