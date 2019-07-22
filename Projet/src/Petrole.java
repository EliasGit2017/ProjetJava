import java.util.ArrayList;
import processing.core.*;

public class Petrole extends Polluants {
	
	public Petrole(int x,int y,PApplet a,Flock f) {
		super(x,y,1000000,a,f);
	}
	
	

	@Override
	public void render() {
		//Tâche de pétrole
		if (this.dur > 0) {
			float theta = this.vite.heading() + PConstants.HALF_PI;
			this.g.pushMatrix();
			this.g.translate(this.posi.x, this.posi.y);
			this.g.rotate(theta);
			this.g.beginShape();
			this.g.stroke(0);
			this.g.fill(0);
			for(int angle=0;angle<360;angle+=5) {
				float val=(float)Math.cos(Math.toRadians(angle))*(float)60.0;
				for(int a=0;a<360;a+=75) {
					float xoff=(float)Math.cos(Math.toRadians(a))*val;
					float yoff=(float)Math.sin(Math.toRadians(a))*val;
					this.g.ellipse(200+xoff, 200+yoff, val, val);
				}
			}
			this.g.endShape();
			this.g.popMatrix();
		}
	}
	
	public double distancepet(Petrole b2){
		return this.posi.dist(b2.posi);
	}
	
	public PVector cohesion(){
		PVector fcoh=new PVector(0,0);
		ArrayList<Petrole> tab=this.f.neighborspet(this,500.0);
		
		if(tab.size()==0)return fcoh;
		else{
			for(Petrole b1: tab){
				fcoh.add(b1.vite);
			}
			fcoh.div(tab.size());
			if(fcoh.mag()>0){
				fcoh.normalize();
				fcoh.mult(this.vmax);
				fcoh.sub(this.vite);
				fcoh.limit(this.fmax);
			}
		}
		return fcoh;
	}
	
	public PVector align(){
		PVector faligne=new PVector(0,0);
		ArrayList<Petrole> res=this.f.neighborspet(this, 25.0);
		int nb=res.size();
		if(nb==0) return faligne;
		else{
			for(Petrole b1: res){
				faligne.add(b1.vite);
			}
			faligne.div(res.size());
			if(faligne.mag()>0){
				faligne.normalize();
				faligne.mult(this.vmax);
				faligne.sub(this.vite);
				faligne.limit(this.fmax);
			}
		}
		return faligne;
	}
	
	public PVector separate(){
		PVector fsep=new PVector(0,0);
		ArrayList<Petrole> tab=this.f.neighborspet(this,500.0);
		if (tab.size()==0) return fsep;
		else{
			for(Petrole b1 : tab){
				PVector sum=PVector.sub(this.posi,b1.posi);
				sum.normalize();
				fsep.add(sum.div((float)this.distancepet(b1)));
			}
		}
		fsep.div(tab.size());
		if(fsep.mag()>0){
			fsep.normalize();
			fsep.mult(this.vmax);
			fsep.sub(this.vite);
			fsep.limit(this.fmax);
		}
		return fsep;
	}
		

	@Override
	public void updatePosition() {
		// TODO Auto-generated method stub
		PVector fcoh=this.cohesion();
		PVector fsep=this.separate();
		PVector falign=this.align();
		fcoh.mult(0.5f);
		fcoh.limit(fmax);
		fsep.mult(0.5f);
		fsep.limit(fmax);
		falign.mult(0.5f);
		falign.limit(fmax);
		PVector acc=new PVector(0,0);
		acc.add(fcoh);
		acc.add(fsep);
		acc.add(falign);
		this.vite.add(acc);
		this.vite.limit(1);
		this.posi.add(this.vite);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.updatePosition();
		this.borders();
		this.render();
	}

}
