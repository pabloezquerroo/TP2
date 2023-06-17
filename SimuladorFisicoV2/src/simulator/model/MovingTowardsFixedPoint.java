package simulator.model;

import java.util.List;
import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws{
	private double g;
	private Vector2D c;
	
	public MovingTowardsFixedPoint(Vector2D v, double g) {
		this.c = v;
		this.g = g;
	}	
	
	@Override
	public void apply(List<Body> bs) {
		Vector2D d;
		double f;
		for (int i =0;i<bs.size();i++) {
			f= g * bs.get(i).getMass();
			d= c.minus(bs.get(i).getPosition()).direction();
			bs.get(i).addForce(d.scale(f));
		}
	}
	
	public String toString() {
		return "Moving towards -"+ c +"with constant acceleration -"+g;
	}

}