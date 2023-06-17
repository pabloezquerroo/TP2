package simulator.model;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;

public class MassLossingBody extends Body{
	protected double lFactor, lFrequency;
	private double cont;
	
	public MassLossingBody(String id, double m, Vector2D v,Vector2D p, double lossFactor, double lossFrequency ) {
		super(id,m,v,p);
		lFactor = lossFactor;
		lFrequency = lossFrequency;
		cont = 0.0;
	}
	
	public MassLossingBody(String id, double m, JSONArray v, JSONArray p, double lossFactor, double lossFrequency) {
		super(id,m,v,p);
		lFactor = lossFactor;
		lFrequency = lossFrequency;
		cont = 0.0;
	}
	
	@Override
	public JSONObject getState() {
		JSONObject item = new JSONObject();
		item.put("id", id);
		item.put("m", m);
		item.put("p", p.asJSONArray());
		item.put("v", v.asJSONArray());
		item.put("f", f.asJSONArray());
		item.put("factor", lFactor);
		item.put("freq", lFrequency);
		return item;
	}
	
	
	@Override
	public void move(double t) {
		super.move(t);
		cont += t;
		if(cont>=lFrequency) {
			cont=0.0;
			m = m*(1-lFactor);
		}
	}
}