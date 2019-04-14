import java.util.ArrayList;
import processing.core.*;

public class Flock {
	private PApplet a;
	private ArrayList<Boid> nuee;

	public Flock(int n,PApplet p){
		this.a=p;
		this.nuee=new ArrayList<Boid>();
		for(int i=0;i<n;i++){
			this.nuee.add(new Boid(this,this.a,600,400));
		}
	}

	public void addBoid(int x,int y){
		Boid b=new Boid(this,this.a,x,y);
		this.nuee.add(b);
	}

	public void run(){
		for(Boid b: this.nuee){
			b.run();
		}
	}
	
	public ArrayList<Boid> neighbors(Boid b, double neighborDist){
		ArrayList<Boid> res=new ArrayList<Boid>();
		for(Boid b1: this.nuee){
			if((b1.distance(b)<neighborDist)&&(b.distance(b1)>0)){
				res.add(b1);
			}
		}
		return res;
	}
	
	
	
}




	
