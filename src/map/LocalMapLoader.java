package map;

import TileSystem.Tile;
import TileSystem.TileSystem;

import java.io.*;

import map.interfaces.MapLoader;

/**
 * Loads a map from the local computer.
 * 
 * @author Andy and Matthew
 */
public class LocalMapLoader implements MapLoader {
	public Tile[][] load(String mapName) throws IOException {
		Tile[][]       tiles  = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(String.format("/maps/%s.map", mapName))));

		// First line is the size (Assuming it's a square)
		int size = Integer.parseInt(reader.readLine());
		if (size < 1)
			throw new IOException("Invalid Map!");

		tiles = new Tile[size][size];

		// Iterate through the file to set the map up.
		for (int x = 0; x < size; x++) {
			String line = reader.readLine();
			for (int y = 0; y < line.length(); y++) {
				tiles[x][y] = createTile(line.charAt(y), x, y);
			}
		}
		
        System.out.format("LocalMapLoader\tMap of size %d loaded.\n", size);
		
		return tiles;
	}

	private Tile createTile(char mapChar, int x, int y) {
		switch(mapChar) {
			case '@':
				return new Tile(TileSystem.TileId.WATER,x,y,0);
			case '+':
			return new Tile(TileSystem.TileId.DIRT,x,y,0);
			case '\'':
				return new Tile(TileSystem.TileId.GRASS,x,y,0);
			case ':':
				return new Tile(TileSystem.TileId.ROCK,x,y,0);
			case ' ':
				return new Tile(TileSystem.TileId.SNOW,x,y,0);
			default:
				return new Tile(TileSystem.TileId.DIRT,x,y,0);
		}
	}

}
