package deserted.engine;

import deserted.engine.graphics.Graphics;

/**
 * Created by Ollie on 01/02/2015.
 */
public abstract class GameState {

    public abstract int getId();

    public abstract void init(GameContainer gc, StateBasedGame sbg);

    public abstract void update(GameContainer gc, StateBasedGame sbg, float delta);

    public abstract void render(GameContainer gc, StateBasedGame sbg, Graphics g);

}
