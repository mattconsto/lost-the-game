package deserted.engine;

import deserted.engine.graphics.Graphics;

import java.util.HashMap;

/**
 * Created by Ollie on 01/02/2015.
 */
public abstract class StateBasedGame {

    private HashMap<Integer, GameState> states;
    private GameState curState, nextState;
    private GameContainer gc;

    public StateBasedGame(){
        states = new HashMap<>();
    }

    public abstract void initStatesList(GameContainer gc);

    public final void addState(GameState gs){
        states.put(gs.getId(), gs);
    }

    public final void enterState(int stateId){
        if(states.containsKey(stateId))
            nextState = states.get(stateId);
        else {
            System.err.println("ERROR: Game does not contain the required state.");
            System.exit(-1);
        }
    }

    public final GameState getState(int stateId){
        return states.get(stateId);
    }

    public final void update(float delta){
        if(nextState != null){
            curState = nextState;
            nextState = null;
        }
        curState.update(gc, this, delta);
    }

    public final void render(Graphics g){
        curState.render(gc, this, g);
    }

    public final void setGameContainer(GameContainer gc){
        this.gc = gc;
    }

}
