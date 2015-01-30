package map.interfaces;

import TileSystem.Tile;

/**
 * MapGenerator Interface
 * 
 * @author Matthew
 */
public interface MapGenerator {
	/**
	 * Generate a Map of the size specified
	 * @param size The size of the map.
	 * @return A map.
	 */
	public Tile[][] generate(int size);
}