package simulator.model;
import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;

public class Body {
	protected Vector2D v, f, p;
	protected String id;
	protected double m;
	
	
	public Body(String id, double m, Vector2D v,Vector2D p) {
		this.id= id;
		this.m = m;
		this.v = v;
		this.p = p;
		this.f = new Vector2D();
	}
	
	public Body(String id, double m, JSONArray v, JSONArray p) {
		this.id= id;
		this.m = m;
		this.v = new Vector2D(v.getDouble(0), v.getDouble(1));
		this.p = new Vector2D(p.getDouble(0), p.getDouble(1));
		this.f = new Vector2D();
	}

	public String getid() {
		return id;
	}
	
	public Vector2D getVelocity() {
		return v;
	}
	
	public Vector2D getForce() {
		return f;
	}
	
	public Vector2D getPosition() {
		return p;
	}
	
	public double getMass() {
		return m;
	}
	
	public void addForce(Vector2D f) {
		this.f=this.f.plus(f);
	}
	
	void resetForce() {
		this.f = new Vector2D();
	}
	
	public void move(double t) {
		Vector2D a= new Vector2D();
		if(m != 0.0) {
			a = getForce().scale(1/m);
		}

		this.p=getPosition().plus(getVelocity().scale(t).plus(a.scale(t * t * 0.5)));
		this.v=getVelocity().plus(a.scale(t));
	}
	
	public JSONObject getState() {
		JSONObject item = new JSONObject();
		item.put("id", id);
		item.put("m", m);
		item.put("p", p.asJSONArray());
		item.put("v", v.asJSONArray());
		item.put("f", f.asJSONArray());
		return item;
	}
	
	public String toString() {
		return getState().toString();
	}

	public void resetVelocity() {
		this.v=new Vector2D();
	}
}

