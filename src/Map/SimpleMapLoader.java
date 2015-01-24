package Map;

import TileSystem.Tile;

import java.io.*;

/**
 * Created by andy on 24/01/15.
 */
public class SimpleMapLoader {
    private static final String filePath = "map.txt";

    public TileSystem.Tile[][] loadMap() throws FileNotFoundException {
        TileSystem.Tile[][] tiles = null;
        BufferedReader reader = null;
        try {
            File file = new File(filePath);
            reader = new BufferedReader(new FileReader(file));

            // First line is the size (Assuming it's a square)
            int size = Integer.parseInt(reader.readLine());
            if (size < 1) {
                throw new Exception("Map has invalid grid size!");
            }

            tiles = new TileSystem.Tile[size][size];

            // Iterate through the file to set the map up.
            for (int x = 0; x < size; x++) {
                String line = reader.readLine();
                for (int y = 0; y < line.length(); y++) {
                    createTile(line.charAt(y), x, y);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                //ohgodwhathashappened?
            }
        }
        return tiles;
    }

    private TileSystem.Tile createTile(char mapChar, int x, int y) {
        switch(mapChar) {
            case 'M':
                return null;//new TileSystem.Tile(TileSystem.TileId.WATER,x,y,2);
            default:
                return null;//new TileSystem.Tile(TileSystem.TileId.DIRT,x,y,2);
        }
    }

}
