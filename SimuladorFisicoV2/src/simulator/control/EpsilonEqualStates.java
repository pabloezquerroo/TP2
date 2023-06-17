package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;

public class EpsilonEqualStates implements StateComparator{
	protected double _eps;
	
	public EpsilonEqualStates(double eps) {
		this._eps= eps;
	}
	
	@Override
	public boolean equal(JSONObject s1, JSONObject s2) {
		boolean salida=true;
		JSONArray arrayS1 = new JSONArray();
		JSONArray arrayS2 = new JSONArray();
		arrayS1 = s1.getJSONArray("bodies");
		arrayS2 = s2.getJSONArray("bodies");
		
		if (s1.getDouble("time")!=s2.getDouble("time")) {
			return false;
		}
		if(arrayS1.length() != arrayS2.length()){
			return false;
		}
		
		int i=0;
		while(i<arrayS1.length()&&salida) {
			if(!arrayS1.getJSONObject(i).getString("id").equals(arrayS2.getJSONObject(i).getString("id")))salida=false;
			if(Math.abs(arrayS1.getJSONObject(i).getDouble("m")-arrayS2.getJSONObject(i).getDouble("m"))>_eps)salida=false;
			Vector2D aux1= new Vector2D(arrayS1.getJSONObject(i).getJSONArray("v").getDouble(0), arrayS1.getJSONObject(i).getJSONArray("v").getDouble(1));
			Vector2D aux2= new Vector2D(arrayS2.getJSONObject(i).getJSONArray("v").getDouble(0), arrayS2.getJSONObject(i).getJSONArray("v").getDouble(1));
			if(aux1.distanceTo(aux2)>_eps)salida=false;
			aux1= new Vector2D(arrayS1.getJSONObject(i).getJSONArray("p").getDouble(0), arrayS1.getJSONObject(i).getJSONArray("p").getDouble(1));
			aux2= new Vector2D(arrayS2.getJSONObject(i).getJSONArray("p").getDouble(0), arrayS2.getJSONObject(i).getJSONArray("p").getDouble(1));
			if(aux1.distanceTo(aux2)>_eps)salida=false;
			aux1= new Vector2D(arrayS1.getJSONObject(i).getJSONArray("f").getDouble(0), arrayS1.getJSONObject(i).getJSONArray("f").getDouble(1));
			aux2= new Vector2D(arrayS2.getJSONObject(i).getJSONArray("f").getDouble(0), arrayS2.getJSONObject(i).getJSONArray("f").getDouble(1));
			if(aux1.distanceTo(aux2)>_eps)salida=false;
			i++;
		}
	
		return salida;
	}

}
