package deserted.engine.math;

/**
 * Created by Ollie on 01/02/2015.
 */
public class Vector2f {

    public float x, y;

    public Vector2f(){
        this(0,0);
    }

    public Vector2f(float x, float y){
        this.x = x;
        this.y = y;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

}
