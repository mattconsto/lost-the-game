package deserted.engine;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

import java.nio.ByteBuffer;
import java.util.HashMap;

/**
 * Created by Ollie on 30/01/2015.
 */
public class Input extends GLFWKeyCallback {

    private HashMap<Integer, Boolean> keys;
    
    private long window;
    private ByteBuffer xPos, yPos;

    public Input(long window){
        keys = new HashMap<>();
        xPos = BufferUtils.createByteBuffer(1);
        yPos = BufferUtils.createByteBuffer(1);
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
//        if(keys.containsKey(key))
//            return keys.get(key);
//        keys.put(key, false);
//        return false;
        
        if(GLFW.glfwGetKey(window, key) == GLFW.GLFW_PRESS || GLFW.glfwGetKey(window, key) == GLFW.GLFW_REPEAT){
        	return true;
        }else{
        	return false;
        }
    }
    
    public boolean isMouseButtonDown(int button){
    	if(GLFW.glfwGetMouseButton(window, button) == GLFW.GLFW_PRESS || GLFW.glfwGetMouseButton(window, button) == GLFW.GLFW_REPEAT){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    public int getMouseX(){
    	GLFW.glfwGetMonitorPos(window, xPos, yPos);
    	return xPos.get(0);
    }
    
    public int getMouseY(){
    	GLFW.glfwGetMonitorPos(window, xPos, yPos);
    	return yPos.get(0);
    }
}
