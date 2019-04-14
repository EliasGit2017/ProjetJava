import processing.core.*;

public class BoidGUI extends PApplet {

	private Flock f;

	public void setup() {
		this.f = new Flock(50, this);
	}

	public void settings() {
		size(1145, 1068);
	}

	public void draw() {
		PImage img=loadImage("carte_france_satellite.jpg");
		this.background(img);
		this.f.run();
	}

	public void mousePressed() {
		this.f.addBoid(mouseX, mouseY);
	}

	public static void main(String[] args) {
		String[] a = { "MAIN" };
		PApplet.runSketch(a, new BoidGUI());
	}
}