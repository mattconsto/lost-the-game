package deserted.engine;

import deserted.engine.Input;
import deserted.engine.graphics.Graphics;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * Created by Ollie on 30/01/2015.
 */
public class GameContainer {

    private GLFWErrorCallback errorCallback;
    private StateBasedGame game;

    private long window;
    private Input input;
    private Graphics g;

    private int width, height;
    private boolean fullscreen;
    private double frameInterval = 1/60;

    private boolean running = true;

    public GameContainer(StateBasedGame game, int width, int height, boolean fullscreen){
        this.game = game;
        this.width = width;
        this.height = height;
        this.fullscreen = fullscreen;
        init();
    }

    private void init() {
        game.setGameContainer(this);

        glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));

        if (glfwInit() != GL11.GL_TRUE)
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);

        window = glfwCreateWindow(width, height, "NO TITLE SET", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        //glfwSetKeyCallback(window, input);

        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        glfwSetWindowPos(
                window,
                (GLFWvidmode.width(vidmode) - width) / 2,
                (GLFWvidmode.height(vidmode) - height) / 2
        );

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);

        glfwShowWindow(window);

        GLContext.createFromCurrent();
       
        GL11.glViewport(0, 0, width, height);
        
        input = new Input(window);
        g = new Graphics();
    }

    public void updateAndClear(){
        glfwSwapBuffers(window);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glfwPollEvents();
    }

    public void setVisible(boolean val){
        if(val){
            glfwShowWindow(window);
        }else{
            glfwHideWindow(window);
        }
    }

    private boolean shouldClose(){
        return (glfwWindowShouldClose(window) == GL_TRUE);
    }

    public Input getInput(){
        return input;
    }

    public void setTitle(String title){
        glfwSetWindowTitle(window, title);
    }

    public void setTargetFrameRate(int target){
        this.frameInterval = 1000D/(double)target;
    }

    public void start(){
        long lastTime = System.currentTimeMillis();
        long temp = 0;
        long elapsedTime = 0;
        float delta;

        int frameCount = 0;
        float frameTime = 0;

        while(!shouldClose()){
            temp = System.currentTimeMillis();
            elapsedTime += temp - lastTime;
            delta = (float)(temp - lastTime)/1000;
            lastTime = temp;
            frameTime += delta;
            game.update(delta);

            while(elapsedTime >= frameInterval){
                render();
                elapsedTime -= frameInterval;
                frameCount++;
            }

            if(frameTime >= 2){
                System.out.println("FPS: " + frameCount / frameTime);
                frameTime = 0;
                frameCount = 0;
            }
        }
    }

    private void render(){
        game.render(g);
        updateAndClear();
    }
    
    public void setVSync(boolean enabled){
    	if(enabled){
    		glfwSwapInterval(1);
    	}else{
    		glfwSwapInterval(0);
    	}
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public void exit(){
        glfwDestroyWindow(window);
        input.release();
        glfwTerminate();
        errorCallback.release();
        running = false;
    }

}
