package deserted.engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

import java.util.HashMap;

/**
 * Created by Ollie on 30/01/2015.
 */
public class Input extends GLFWKeyCallback {

    private HashMap<Integer, Boolean> keys;

    public Input(){
        keys = new HashMap<>();
    }

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        switch(action){
            case GLFW.GLFW_PRESS:
                keys.put(key, true);
                break;
            case GLFW.GLFW_RELEASE:
                keys.put(key, false);
                break;
        }
    }

    public boolean isKeyDown(int key){
        if(keys.containsKey(key))
            return keys.get(key);
        keys.put(key, false);
        return false;
    }

    public boolean isKeyUp(int key){
        if(keys.containsKey(key))
            return !keys.get(key);
        keys.put(key, false);
        return true;
    }

}
