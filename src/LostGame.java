import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class LostGame extends StateBasedGame {
	
	public static final int STATE_PLAY = 1;
	
	protected String name;
	
	public LostGame(String name) {
		super(name);
		this.name = name;
	}
	
	public void draw() {
		System.out.println(name);
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		this.addState(new Play());
        this.enterState(STATE_PLAY);
	}
	
	public static void main(String[] args) {
		AppGameContainer appgc;
		String name = "definitely not lost";
		BufferedReader reader;

		try {
			reader = new BufferedReader(new FileReader("names.txt"));

			String line;
			ArrayList<String> lines = new ArrayList<String>();

			while ((line = reader.readLine()) != null)
				lines.add(line);

			name = lines.get(new Random().nextInt(lines.size()));
		} catch (Exception e) {
		}

		try {
			appgc = new AppGameContainer(new LostGame(name));
			appgc.setDisplayMode(800, 600, false);
			appgc.setTargetFrameRate(200000);
			appgc.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
