import processing.core.*;

public abstract class Dechet extends Polluants {
	
	
	public Dechet(int x,int y,int dur,PApplet a,Flock f) {
		super(x,y,dur,a,f);
	}

	@Override
	public abstract void render() ;
	
	//Mise à jour de la position du déchet sans prise en compte des forces de groupe. Les déchets sont supposés dériver librement
	@Override
	public void updatePosition() {
		PVector acc=new PVector(0,0);
		this.vite.add(acc);
		this.vite.limit(vmax);
		this.posi.add(this.vite);

	}

	@Override
	public void run() {
		this.updatePosition();
		this.borders();
		this.render();
	}

}
