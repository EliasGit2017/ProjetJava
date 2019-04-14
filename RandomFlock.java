import processing.core.*;
import java.util.ArrayList;

public class RandomFlock {
	private PApplet a;
	private ArrayList<RandomBoid> nuee;
	
	public RandomFlock(int n,PApplet p){
		this.a=p;
		this.nuee=new ArrayList<RandomBoid>();
		for(int i=0;i<n;i++){
			this.nuee.add(new RandomBoid(this.a,600,400));
		}
	}
	
	public void addBoid(int x,int y){
		RandomBoid b=new RandomBoid(this.a,x,y);
		this.nuee.add(b);
	}
	
	public void run(){
		for(RandomBoid b: this.nuee){
			b.run();
		}
	}
	
}
