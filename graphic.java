import processing.core.*;

public void run() {
    this.updatePosition();
    this.borders();
    this.render();
}

public void render() {
    // Draw a triangle rotated in the direction of velocity
    float r = (float) 2.0;
    // this.velocity est la vitesse du Boid
    float theta = this.vel.heading() + PConstants.PI / 2;
    this.graphicalContext.fill(200, 100);
    this.graphicalContext.stroke(255);
    this.graphicalContext.pushMatrix();
    this.graphicalContext.translate(this.pos.x, this.pos.y);
    this.graphicalContext.rotate(theta);
    this.graphicalContext.beginShape(PConstants.TRIANGLES);
    this.graphicalContext.vertex(0, -r * 2);
    this.graphicalContext.vertex(-r, r * 2);
    this.graphicalContext.vertex(r, r * 2);
    this.graphicalContext.endShape();
    this.graphicalContext.popMatrix();
}

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
