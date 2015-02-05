package deserted;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import deserted.sound.SoundManager;

public class Game extends StateBasedGame {

	protected String name;

	public Game(String name) {
		super(name);
		this.name = name;
	}

	public void draw() {
		System.out.println(name);
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {

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
		AppGameContainer appgc;

		new JFrame();

		try {
			GraphicsDevice gd = GraphicsEnvironment
					.getLocalGraphicsEnvironment().getDefaultScreenDevice();

			appgc = new AppGameContainer(new Game("Deserted"));
			appgc.setVSync(true);
			appgc.setDisplayMode(1024, 768, false);
			appgc.setTargetFrameRate(gd.getDisplayMode().getRefreshRate());
			appgc.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
