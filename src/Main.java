import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class Main {
	public static void main(String[] args) {
		String         name = "definitely not lost";
		BufferedReader reader;
		
		try {
			reader = new BufferedReader(new FileReader("names.txt"));
			
			String            line;
			ArrayList<String> lines = new ArrayList<String>();
			
			while((line = reader.readLine()) != null)
				lines.add(line);
			
			name = lines.get(new Random().nextInt(lines.size()));
		} catch (Exception e) {}
		
		Game game = new Game(name);
		game.draw();
	}
}
