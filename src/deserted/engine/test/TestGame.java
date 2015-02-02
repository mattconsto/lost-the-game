package deserted.engine.test;

import deserted.engine.GameContainer;
import deserted.engine.StateBasedGame;

/**
 * Created by Ollie on 01/02/2015.
 */
public class TestGame extends StateBasedGame {

    @Override
    public void initStatesList(GameContainer gc) {
        gc.setTitle("Test Game");

        TestState state = new TestState();

        addState(state);
        enterState(TestState.ID);
    }
}
