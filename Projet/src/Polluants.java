import processing.core.*; 

public abstract class Polluants {
	protected PVector posi;
	protected PVector vite;
	protected float vmax;
	protected float fmax;
	protected PApplet g;
	protected int dur;
	protected Flock f;
	
	public Polluants(int x,int y,int dur,PApplet a,Flock f) {
		posi=new PVector(x,y);
		this.vmax=1;
		vite=new PVector((float)Math.random()*2-1,(float)Math.random()*2-1);
		this.fmax=(float)0.05;
		this.dur=dur;
		this.g=a;
		this.f=f;
	}
	
	public PVector randomForce(){
		int f1=(int)Math.random()*20-10;
		int f2=(int)Math.random()*20-10;
		return new PVector(f1,f2);
	}
	//Getter retournant la position d'un polluant
	public PVector getPos() {
		return posi;
	}
	
	public abstract void render();
	
	public abstract void updatePosition();
	
	public abstract void run();
	
	public void borders() {
		float r = (float) 2.0;
		if (this.posi.x < -r) {
			this.posi.x = g.width + r;
		}

		if (this.posi.y < -r) {
			this.posi.y = g.height + r;
		}

		if (this.posi.x > g.width + r) {
			this.posi.x = -r;
		}

		if (this.posi.y > g.height + r) {
			this.posi.y = -r;
		}
	}
	//Getter retournant la duree de vie d'un polluant
	public int getDur() {
		return this.dur;
	}
	//Méthode permettant de retirer des points de vie à un polluant
	public void removeDur(int n) {
		if (this.dur <= n) {
			this.dur = 0;
		} else
			this.dur -= n;
	}
		
	
}
