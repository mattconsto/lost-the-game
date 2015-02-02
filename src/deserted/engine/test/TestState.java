package deserted.engine.test;

import deserted.engine.GameContainer;
import deserted.engine.GameState;
import deserted.engine.StateBasedGame;
import deserted.engine.graphics.Graphics;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.util.Random;

/**
 * Created by Ollie on 01/02/2015.
 */
public class TestState extends GameState {
    public static final int ID = 0;

    private Random r;

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) {
        r = new Random();
        gc.setTargetFrameRate(2);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, float delta) {
        if (gc.getInput().isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
            gc.exit();
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
        GL11.glClearColor(r.nextFloat(), r.nextFloat(), r.nextFloat(), 1);
    }
}
