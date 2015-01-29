package Graphics;

public class Vertex {
	
	public float x, y;
	public float u = 0, v = 0;
	public float r = 1, g = 1, b = 1, a = 1;
	
	public Vertex(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public Vertex(float x, float y, float r, float g, float b, float a){
		this(x, y);
		this.r = r; this.g = g; this.b = b; this.a = a;
	}
	
	public Vertex(float x, float y, float u, float v, float r, float g, float b, float a){
		this(x, y, r, g, b, a);
		this.u = u;
		this.v = v;
	}
	
	public float[] getData(){
		return new float[]{
				x, y, r, g, b, a
		};
	}

}
