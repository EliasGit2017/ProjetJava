import processing.core.PVector;

public interface Mouvement {
	
	public void updatePosition();
	
	public void run();
	
	public PVector separate();
	
	public PVector align();
	
	public PVector cohesion();
	
	public void render();
	

}
