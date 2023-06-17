package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;

public class MassEqualStates implements StateComparator{
	
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
		
		JSONObject o1= new JSONObject();
		JSONObject o2= new JSONObject();
		int i =0;
		while(i<arrayS1.length() && salida) {
			o1=arrayS1.getJSONObject(i);
			o2=arrayS2.getJSONObject(i);
			if(!o1.getString("id").equals(o2.getString("id")))salida=false;
			if(!o1.getString("m").equals(o2.getString("m"))) salida=false;
			i++;
		}
		
		return salida;
		
	}

}