import processing.core.PApplet;
import processing.core.PConstants;

public class Plastique extends Dechet {

	public Plastique(int x, int y, PApplet a,Flock f) {
		super(x,y,20,a,f);
	}
	
	public void render() {
		float r = (float) 2.0;
		// Déchet Plastique de couleur jaune 
		if (this.dur > 0) {
			float theta = this.vite.heading() + PConstants.HALF_PI;
			this.g.pushMatrix();
			this.g.translate(this.posi.x, this.posi.y);
			this.g.rotate(theta);
			this.g.beginShape();
			this.g.stroke(255, 255, 0);
			this.g.fill(255, 255, 0);
			this.g.curveVertex(0, 3 * r);
			this.g.curveVertex(3* r, r);
			this.g.curveVertex(3 * r, 0);
			this.g.curveVertex(r, -2 * r);
			this.g.curveVertex(0, -3 * r);
			this.g.curveVertex(-3 * r, -r);
			this.g.curveVertex(-r, 0);
			this.g.curveVertex(-3* r, r);
			this.g.curveVertex(0, 3 * r);
			this.g.endShape();
			this.g.popMatrix();
		}
	}
	
}
