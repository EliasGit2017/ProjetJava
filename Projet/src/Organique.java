import processing.core.*;

public class Organique extends Dechet {

	
	public Organique(int x,int y, PApplet a,Flock f) {
		super(x,y,2,a,f);
	}
	
	public void render() {
		float r=3;
		// Dechet organique de couleur verte 
		if (this.dur > 0) {
			float theta = this.vite.heading() + PConstants.HALF_PI;
			this.g.pushMatrix();
			this.g.translate(this.posi.x, this.posi.y);
			this.g.rotate(theta);
			this.g.beginShape();
			this.g.stroke(85, 107, 47);
			this.g.fill(85, 107, 47);
			this.g.curveVertex(-2*r, 2*r);
			this.g.curveVertex(0, 3*r);
			this.g.curveVertex(2*r, 2*r);
			this.g.curveVertex(3*r, 0);
			this.g.curveVertex(2*r, -2*r);
			this.g.curveVertex(0, -3*r);
			this.g.curveVertex(-2*r, -2 * r);
			this.g.curveVertex(-3*r, 0);
			this.g.curveVertex(-2*r, 2*r);
			this.g.endShape();
			this.g.popMatrix();
		}
	}
	
	
}