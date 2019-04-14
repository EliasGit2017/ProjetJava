import processing.core.*;
import java.util.*;

public class RandomBoid {
	private PVector pos;
	private PVector vit;
	private float vmax;
	private float fmax;
	private PApplet graphicalContext;
	
	public RandomBoid(PApplet a,int x,int y){
		this.graphicalContext=a;
		pos=new PVector(x,y);
		vmax=2;
		fmax=(float)0.05;
		int v1=(int)Math.random()*2-1;
		int v2=(int)Math.random()*2-1;
		vit=new PVector(v1,v2);
	}
	
	public RandomBoid(PApplet a,int x, int y, float v,float f){
		this.graphicalContext=a;
		pos=new PVector(x,y);
		vmax=v;
		fmax=f;
		int v1=(int)Math.random()*2-1;
		int v2=(int)Math.random()*2-1;
		vit=new PVector(v1,v2);
	}
	
	public PVector randomForce(){
		int f1=(int)Math.random()*20-10;
		int f2=(int)Math.random()*20-10;
		return new PVector(f1,f2);
	}
	
	public void updatePosition(){
		vit.add(this.randomForce());
		vit.limit(this.vmax);
		pos.add(vit);
	}
	
	public void run() {
	    this.updatePosition();
	    this.borders();
	    this.render();
	}

	public void render() {
	    // Draw a triangle rotated in the direction of velocity
	    float r = (float) 2.0;
	    // this.velocity est la vitesse du Boid
	    float theta = this.vit.heading() + PConstants.PI /2;
	    this.graphicalContext.fill(200, 100);
	    this.graphicalContext.stroke(255);
	    this.graphicalContext.pushMatrix();
	    this.graphicalContext.translate(this.pos.x, this.pos.y);
	    this.graphicalContext.rotate(theta);
	    this.graphicalContext.beginShape(PConstants.TRIANGLES);
	    this.graphicalContext.vertex(0, -r * 2);
	    this.graphicalContext.vertex(-r, r * 2);
	    this.graphicalContext.vertex(r, r * 2);
	    this.graphicalContext.endShape();
	    this.graphicalContext.popMatrix();
	}

	public void borders() {
	    float r = (float) 2.0;
	    if (this.pos.x < -r) {
		this.pos.x = graphicalContext.width + r;
	    }
	    
	    if (this.pos.y < -r) {
		this.pos.y = graphicalContext.height + r;
	    }
	    
	    if (this.pos.x > graphicalContext.width + r) {
		this.pos.x = -r;
	    }
	    
	    if (this.pos.y > graphicalContext.height + r) {
		this.pos.y = -r;
	    }
	}

}
