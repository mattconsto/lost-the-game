package deserted;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

import deserted.engine.GameContainer;
import deserted.engine.StateBasedGame;
import deserted.sound.SoundManager;

public class Game extends StateBasedGame {

	@Override
	public void initStatesList(GameContainer gc){

		GameIntro gi = new GameIntro();
		Play play = new Play();
		GameOver go = new GameOver();
		GameWin gw = new GameWin();
		go.setPlayState(play);
		gw.setPlayState(play);

		this.addState(gi);
		this.addState(play);
		this.addState(go);
		this.addState(gw);
		this.enterState(GameIntro.STATE_INTRO);

		SoundManager.getInstance(true);
	}

	public static void main(String[] args) {
		GameContainer gc;

		new JFrame();
		GraphicsDevice gd = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice();

		//gc = new GameContainer(new Game(), (int) gd.getDisplayMode().getWidth(), (int) gd.getDisplayMode().getHeight(), true);
		gc = new GameContainer(new Game(), 800, 600, false);
		
		
		gc.setVSync(true);
		gc.setTargetFrameRate(gd.getDisplayMode().getRefreshRate());
		gc.start();
	}
}
