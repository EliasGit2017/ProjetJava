public PVector steer(PVector target, boolean slowDown) {
    PVector desired = PVector.sub(target, this.pos);
    if (desired.mag() <= 0) {
	return new PVector(0, 0);
    }
    
    desired.normalize();
    if (slowDown && desired.mag() < 100.0) {
	desired.mult((float) (this.maxSpeed * (desired.mag() / 100.0)));
    } else {
	desired.mult(this.maxSpeed);
    }
    
    PVector steeringVector = PVector.sub(desired, this.velocity);
    steeringVector.limit((float) this.maxForce);
    return steeringVector;
}
