package map.interfaces;

import java.io.IOException;

import TileSystem.Tile;

/**
 * MapLoader Interface
 * 
 * @author Matthew
 */
public interface MapLoader {
	/**
	 * Loads a map from a location
	 * @param path Path to the location
	 * @return A map
	 * @throws IOException If there is a problem reading the map.
	 */
	public Tile[][] load(String path) throws IOException;
}