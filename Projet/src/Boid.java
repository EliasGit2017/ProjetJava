import processing.core.*;
import java.util.ArrayList;

public class Boid implements Mouvement {
	private int pointsVie;
	private Flock group;
	private PVector pos;
	private PVector vit;
	private float vmax;
	private float fmax;
	public boolean vie;
	public boolean petrole;
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
		this.pointsVie=100;
		vie=true;
		petrole=false;
	}
	//Constructeur avec précision de la vitesse maximale et de la force maximale
	public Boid(Flock nu,PApplet a,int x, int y, float v,float f){
		this.group=nu;
		this.graphicalContext=a;
		pos=new PVector(x,y);
		vmax=v;
		fmax=f;
		int v1=(int)Math.random()*2-1;
		int v2=(int)Math.random()*2-1;
		vit=new PVector(v1,v2);
		this.pointsVie=100;
		vie=true;
		petrole=false;
	}
	//Getter pour l'attribut pointsdeVie
	public int getPointdeVie() {
		return this.pointsVie;
	}
	//Méthode permettant de tester la collision entre un poisson et un déchet
	public boolean col(Polluants p) {
		if(/*Math.sqrt((pos.x-p.getPos().x)*(pos.x-p.getPos().x)+(pos.y-p.getPos().y)*(pos.y-p.getPos().y))<15*/
				this.pos.dist(p.posi)<15) {
			return true;
		}
		else return false;
	}
	//Méthode permettant de tester la collision entre un poisson et une flaque de pétrole
	public boolean colPetrole(Petrole p) {
		if(/*Math.sqrt((pos.x-p.getPos().x)*(pos.x-p.getPos().x)+(pos.y-p.getPos().y)*(pos.y-p.getPos().y))<60*/
				this.pos.dist(p.posi)<60) {
			this.petrole=true;
			return true;
		}
		else return false;
	}
	//Méthode permettant de retirer des points de vie en cas d'intoxication
	public void removePointsVie(int n) {
		if(this.pointsVie<=n) {
			this.pointsVie=0;
			vie=false;
		}
		else this.pointsVie -= n;
	}
	//Méthode permettant de donner un vecteur force aléatoire 
	public PVector randomForce(){
		int f1=(int)Math.random()*20-10;
		int f2=(int)Math.random()*20-10;
		return new PVector(f1,f2);
	}

	//Méthode permettant de calculer la force de cohésion pour un poisson en fonction des poissons qui l'entourent
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
	//Méthode permettant de calculer la force de séparation pour un poisson en fonction des poissons qui l'entourent
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
	//Méthode permettant de calculer la force d'alignement pour un poisson en fonction de ses voisins
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
	//Méthode permettant la gestion du changement de direction 
//	public PVector steer(PVector target, boolean slowDown) {
//		PVector desired = PVector.sub(target, this.pos);
//		if (desired.mag() <= 0) {
//			return new PVector(0, 0);
//		}
//
//		desired.normalize();
//		if (slowDown && desired.mag() < 100.0) {
//			desired.mult((float) (this.vmax * (desired.mag() / 100.0)));
//		} else {
//			desired.mult(this.vmax);
//		}
//
//		PVector steeringVector = PVector.sub(desired, this.vit);
//		steeringVector.limit((float) this.fmax);
//		return steeringVector;
//	}
	//Méthode permettant la gestion de l'environnement de manière torique
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
	//Méthode permettant de calculer la distance entre 2 poissons
	public double distance(Boid b2){
		return this.pos.dist(b2.pos);
	}
	
	public void render() {
		// Dessine  un poisson de couleur rose (en cas de bonne santé) ou de couleur noire (en cas de maladie)
		float r = (float) 1.0;
		// this.velocity est la vitesse du poisson
		if (this.pointsVie > 0) {
			float theta = this.vit.heading() + PConstants.PI / 2;
			//this.graphicalContext.stroke(255,192,203);
			//this.graphicalContext.fill(255,192,203);
			this.graphicalContext.pushMatrix();
			this.graphicalContext.translate(this.pos.x, this.pos.y);
			this.graphicalContext.rotate(theta);
			this.graphicalContext.beginShape();
			if(this.petrole==true) {
				this.graphicalContext.stroke(255,192,203);
				this.graphicalContext.fill(0,0,0);
			}
			else {
				this.graphicalContext.stroke(255,192,203);
				this.graphicalContext.fill(255,192,203);
				}
			this.graphicalContext.vertex(0, r * 6);
			this.graphicalContext.vertex(r * 2, r * 4);
			this.graphicalContext.vertex(r * 3, r * 2);
			this.graphicalContext.vertex(r * 3, -2 * r);
			this.graphicalContext.vertex(r * 2, -4 * r);
			this.graphicalContext.vertex(r * 1, -5 * r);
			this.graphicalContext.curveVertex(0, -r * 6);
			this.graphicalContext.vertex(r * -1, -5 * r);
			this.graphicalContext.vertex(r * -2, -4 * r);
			this.graphicalContext.vertex(r * 2, -4 * r);
			this.graphicalContext.vertex(r * -2, -4 * r);
			this.graphicalContext.vertex(-3 * r, -2 * r);
			this.graphicalContext.vertex(-3 * r, -2 * r);
			this.graphicalContext.vertex(-2 * r, r * 4);
			this.graphicalContext.vertex(3 * r, r * 10);
			this.graphicalContext.vertex(-3 * r, r * 10);
			this.graphicalContext.vertex(0, r * 6);
			this.graphicalContext.endShape();
			this.graphicalContext.popMatrix();
		}
	}
	//Mise à jour de la position du poisson en fonction des forces qui lui sont appliquées
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
		if(this.petrole) {
			this.vit.limit((float)0.5);
		}else{
			this.vit.limit(vmax);
		}
		this.pos.add(this.vit);
		
	}
	//Run pour le mouvement du poisson 
	public void run() {
		this.updatePosition();
		this.borders();
		this.render();
	}

}
