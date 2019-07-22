import processing.core.*;

public class Affichage extends PApplet {
	
	public Flock f;	
	
	//On démarre avec un banc de 150 poissons
	public void setup() {
		this.f = new Flock(150, this);
	}
	//Taille de l'écran (adaptée à la résolution de l'image)
	public void settings() {
		size(1100, 823);
	}
	//Dessin contenant les informations principales décrivant l'environnement 
		public void draw() {
		PImage img=loadImage("fond3.jpg");
		this.background(img);
		this.f.run();
		textSize(15);
		fill(256,256,256);
		text("nombre de morts "+ Flock.getnbMorts(), 10, 50);
		text("nombre de vivants (pour l'instant ...)"+this.f.nbpoissons(),10,65);
		text("petrole "+this.f.getpet(),10,80);
		text("organique "+this.f.getorga(),10,95);
		text("plastique "+this.f.getplastique(),10,110);
		if(this.f.getplastique()>50||this.f.getorga()>50||this.f.getpet()>3) {
			textSize(20);
			fill(256,0,0);
			text("warning!!! environnement trop toxique",500, 80);
		}
		
	}
	//Méthode permettant d'ajouter des poissons à la positions du curseur de la souris (clic gauche) ou de tuer la moitié de la population (clic droit)
	public void mousePressed() {
		if(mouseButton==LEFT)this.f.addBoid(mouseX, mouseY);
		if(mouseButton==RIGHT) this.f.mourrir();
		else return ;
	}
	//Méthode permettant d'ajouter des déchets dans l'environnement
	public void keyPressed() {
		if(keyPressed) {
			if(key=='o' || key=='O') this.f.addOrganique(200, 700);
			if(key=='p' || key=='P') this.f.addPetrole(200, 200);
			if(key=='s' || key=='S') this.f.addPlastique(1000, 700);
		}
		else return;
	}
	

	public static void main(String[] args) {
		String[] a = { "MAIN" };
		PApplet.runSketch(a, new Affichage());
	}
}