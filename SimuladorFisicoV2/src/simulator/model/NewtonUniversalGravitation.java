package simulator.model;

import java.util.List;
import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws {
	private double G;
		
	public NewtonUniversalGravitation(double g) {
		G = g;
	}
	
	@Override
	public void apply(List<Body> bs) {
		for(int i = 0; i< bs.size()-1;i++) {
			if(bs.get(i).getMass()!=0) {
			for(int j = i+1; j< bs.size();j++) {
					if (bs.get(j).getMass()!=0) {
						bs.get(i).addForce(force(bs.get(i),bs.get(j)));
						bs.get(j).addForce(force(bs.get(j),bs.get(i)));
					}else {
						bs.get(j).resetVelocity();
					}
				}
			}else {
				bs.get(i).resetVelocity();
			}
		}
	}
	
	private Vector2D force(Body a, Body b) {
		Vector2D d;
		double f;
		double dist=a.getPosition().distanceTo(b.getPosition());
		double divisor= dist * dist;
		f = divisor>0 ? G*a.getMass()*b.getMass()/divisor: 0.0;
		d = b.getPosition().minus(a.getPosition()).direction();		
		return d.scale(f);
	}
	
	public String toString() {
		return "Newton’s Universal Gravitation with G= -"+G;
	}
	

	
}
