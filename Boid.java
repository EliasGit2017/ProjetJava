import processing.core.*;
import java.util.ArrayList;

public class Boid {
	private Flock group;
	private PVector pos;
	private PVector vit;
	private float vmax;
	private float fmax;
	private PApplet graphicalContext;

	public Boid(Flock nu,PApplet a,int x,int y){
		this.graphicalContext=a;
		pos=new PVector(x,y);
		vmax=2;
		fmax=(float)0.05;
		int v1=(int)Math.random()*2-1;
		int v2=(int)Math.random()*2-1;
		vit=new PVector(v1,v2);
		this.group=nu;
	}

	public Boid(Flock nu,PApplet a,int x, int y, float v,float f){
		this.group=nu;
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
		PVector fcoh=this.cohesion();
		PVector fsep=this.separate();
		PVector falign=this.align();
		
		fcoh.mult(1.0f);
		fsep.mult(1.5f);
		falign.mult(1.0f);
		
		PVector acc=new PVector(0,0);
		acc.add(fsep);
		acc.add(fcoh);
		acc.add(falign);
		
		this.vit.add(acc);
		this.vit.limit(vmax);
		this.pos.add(this.vit);
		
	}

	public void run() {
		this.updatePosition();
		this.borders();
		this.render();
	}

	public void render() {
		// Draw a triangle rotated in the direction of velocity
		float r = (float) 2.0;
		// this.vit est la vitesse du Boid
		float theta = this.vit.heading() + PConstants.PI /2;
		this.graphicalContext.fill(300, 100);
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

	public double distance(Boid b2){
		return this.pos.dist(b2.pos);
	}
	
	public PVector align(){
		PVector faligne=new PVector(0,0);
		ArrayList<Boid> res=this.group.neighbors(this, 25.0);
		int nb=res.size();
		if(nb==0) return faligne;
		else{
			for(Boid b1: res){
				faligne.add(b1.vit);
			}
			faligne.div(res.size());
			if(faligne.mag()>0){
				faligne.normalize();
				faligne.mult(this.vmax);
				faligne.sub(this.vit);
				faligne.limit(this.fmax);
			}
		}
		return faligne;
	}
	
	public PVector separate(){
		PVector fsep=new PVector(0,0);
		ArrayList<Boid> tab=this.group.neighbors(this,20.0);
		if (tab.size()==0) return fsep;
		else{
			for(Boid b1 : tab){
				PVector sum=PVector.sub(this.pos,b1.pos);
				sum.normalize();
				fsep.add(sum.div((float)this.distance(b1)));
			}
		}
		fsep.div(tab.size());
		if(fsep.mag()>0){
			fsep.normalize();
			fsep.mult(this.vmax);
			fsep.sub(this.vit);
			fsep.limit(this.fmax);
		}
		return fsep;
	}
	
	public PVector steer(PVector target, boolean slowDown) {
	    PVector desired = PVector.sub(target, this.pos);
	    if (desired.mag() <= 0) {
		return new PVector(0, 0);
	    }
	    
	    desired.normalize();
	    if (slowDown && desired.mag() < 100.0) {
		desired.mult((float) (this.vmax * (desired.mag() / 100.0)));
	    } else {
		desired.mult(this.vmax);
	    }
	    
	    PVector steeringVector = PVector.sub(desired, this.vit);
	    steeringVector.limit((float) this.fmax);
	    return steeringVector;
	}
	
	public PVector cohesion(){
		PVector fcoh=new PVector(0,0);
		ArrayList<Boid> tab=this.group.neighbors(this,25.0);
		
		if(tab.size()==0)return fcoh;
		else{
			for(Boid b1: tab){
				fcoh.add(b1.vit);
			}
			fcoh.div(tab.size());
			if(fcoh.mag()>0){
				fcoh.normalize();
				fcoh.mult(this.vmax);
				fcoh.sub(this.vit);
				fcoh.limit(this.fmax);
			}
		}
		return fcoh;
	}

}
