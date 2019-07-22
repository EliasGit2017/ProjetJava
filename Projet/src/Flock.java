import java.util.ArrayList;
import processing.core.*;

public class Flock {
	public static int nbMorts=0;
	private int nbm;
	private PApplet a;
	public ArrayList<Boid> nuee;
	public ArrayList<Plastique> plastique;
	public ArrayList<Organique> organique;
	public ArrayList<Petrole> petrole;

	//Constructeur initialisant l'environnement à n poissons, 15 déchets plastiques, 15 déchets organiques et 1 flaque de pétrole  
	public Flock(int n,PApplet p){
		this.a=p;
		this.nuee=new ArrayList<Boid>();
		for(int i=0;i<n;i++){
			this.nuee.add(new Boid(this,this.a,600,400));
		}
		this.organique=new ArrayList<Organique>();
		for(int j=0;j<15;j++) {
			this.organique.add(new Organique(500,500,this.a,this));
		}
		this.plastique=new ArrayList<Plastique>();
		for(int j=0;j<15;j++) {
			this.plastique.add(new Plastique(500,500,this.a,this));
		}
		this.petrole = new ArrayList<Petrole>();
		this.petrole.add(new Petrole(800, 500, this.a, this));

	}
	//Getter static retournant le nombre de morts
	public static int getnbMorts() {
		return nbMorts;
	} 
	
	//Getter retournant le nombre de flaques de pétrole
	public int getpet() {
		return this.petrole.size();
	}
	//Getter retournant le nombre de déchets plastiques
	public int getplastique() {
		return this.plastique.size();
	}
	//Getter retournant le nombre de déchets organiques
	public int getorga() {
		return this.organique.size();
	}
	//Getter retournant le nombre de poissons en vie
	public int nbpoissons(){
		return nuee.size();
	}
	//Méthode permettant de tuer la moitié de la population
	public void mourrir() {
		for(int i=0;i<this.nuee.size()/2;i++) {
			this.nuee.remove(i);
			nbMorts++;
			nbm=nbMorts;
		}
	}
	//Méthode permettant d'ajouter un poisson à la position souhaitée
	public void addBoid(int x,int y){
		Boid b=new Boid(this,	this.a,x,y);
		this.nuee.add(b);
	}
	//Méthode permettant d'ajouter une flaque de pétrole à la position souhaitée
	public void addPetrole(int x,int y) {
		Petrole p=new Petrole(x,y,this.a,this);
		this.petrole.add(p);
	}
	//Méthode permettant d'ajouter un déchet plastique à la position souhaitée
	public void addPlastique(int x,int y) {
		Plastique pl=new Plastique(x,y,this.a,this);
		this.plastique.add(pl);
	}
	//Méthode permettant d'ajouter un déchet organique à la position souhaitée
	public void addOrganique(int x,int y) {
		Organique o=new Organique(x,y,this.a,this);
		this.organique.add(o);
	}
	//Run permettant de mettre en mouvement les objets de l'environnement en tenant compte des collisions
	public void run() {
		for (Boid b : this.nuee) {
			b.run();
		}
		for(Organique o: this.organique) {
			o.run();
		}
		for(Plastique o: this.plastique) {
			o.run();
		}
		for(Petrole o: this.petrole) {
			o.run();
		}
		this.collision();
	}
	
	//Méthode permettant de gérer les collisions
	public void collision() {
		int res=0;
		//Parcours des poissons en vie 
		for(Boid b:this.nuee) {
			//S'il y'a collision avec un déchet organique, le poisson perd 10 points de vie et le déchet organique est mangé
			for(Organique o: this.organique) {
				if (b.col(o)) {
					res+= 10;
					o.removeDur(2);
					break;
				}
			}
			//S'il y'a collision avec un déchet plastique, le poisson perd 25 points de vie et le déchet plastique perd 10 points de vie (si le poisson est très proche du déchet, il le mange une 2eme fois après un tour de boucle (sur boid))
			for(Plastique o: this.plastique) {
				if (b.col(o)) {
					res+= 15;
					o.removeDur(10);
					break;
				}
			}
			//S'il y'a collision avec une flaque de pétrole, le poisson perd 35 points de vie et deviens malade (ou meurt directement). En cas de maladie, il nage lentement, de manière désordonnée sans prendre en compte les forces qui lui sont appliquées. 
			for(Petrole o: this.petrole) {
				if(b.colPetrole(o)) {
					res+=35;
					break;
				}
			}
			b.removePointsVie(res);
			res=0;
		}
		//Retrait des déchets mangés par les poissons (le pétrole n'est pas retiré car on suppose qu'il est résistant et difficilement bio-dégradable)
		for(int j=0;j<this.nuee.size();j++) {
			if(this.nuee.get(j).getPointdeVie()==0) {
				this.nuee.remove(j);
				nbMorts++;
				nbm=nbMorts;
			}
		}
		for(int k=0;k<this.plastique.size();k++) {
			if(this.plastique.get(k).getDur()==0) {
				this.plastique.remove(k);
			}
		}
		for(int k=0;k<this.organique.size();k++) {
			if(this.organique.get(k).getDur()==0) {
				this.organique.remove(k);
			}
		}
	}
	
	//Méthode retournant les voisins d'un poisson (situés à une distance strictement inférieure à neighborDist)
	public ArrayList<Boid> neighbors(Boid b, double neighborDist){
		ArrayList<Boid> res=new ArrayList<Boid>();
		for(Boid b1: this.nuee){
			if((b1.distance(b)<neighborDist)&&(b.distance(b1)>0)){
				res.add(b1);
			}
		}
		return res;
	}
	//Méthode retournant les voisines d'une flaque de pétrole (situées à une distance strictement inférieure à neighborDist)
	public ArrayList<Petrole> neighborspet(Petrole b, double neighborDist){
		ArrayList<Petrole> res=new ArrayList<Petrole>();
		for(Petrole b1: this.petrole){
			if((b1.distancepet(b)<neighborDist)&&(b.distancepet(b1)>0)){
				res.add(b1);
			}
		}
		return res;
	}
	
		
}




	
