package simulator.factories;

import org.json.JSONException;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MassLossingBody;


public class MassLosingBodyBuilder extends Builder<Body> {
	public MassLosingBodyBuilder() {
		super("mlb", "Default MassLosingBody");		
	}

	@Override
	protected Body createTheInstance(JSONObject data){
		try{
			if (data.has("id")&&data.has("m")&&data.has("v")&&data.has("p")&&data.has("factor")&&data.has("freq")) {				
				String id= data.has("id")?data.getString("id"):"b1";
				double m= data.has("m")?data.getDouble("m"):5.97e24;
				Vector2D v= data.has("v")?new Vector2D(data.getJSONArray("v").getDouble(0), data.getJSONArray("v").getDouble(1)):new Vector2D(0.05e04, 0.0e00);
				Vector2D p= data.has("p")?new Vector2D(data.getJSONArray("p").getDouble(0), data.getJSONArray("p").getDouble(1)):new Vector2D(0.00e00, 0.0e00);
				double factor= data.has("factor")?data.getDouble("factor"):1e-3;
				double freq= data.has("freq")?data.getDouble("freq"):1e3;
				return new MassLossingBody(id, m, v, p, factor, freq);
			}else {
				throw new IllegalArgumentException();
			}
		}catch(JSONException je) {
			throw new IllegalArgumentException();
		}
	}
	
	@Override
	protected JSONObject createData() {
		JSONObject o = new JSONObject();
		o.put("id", "null");
		o.put("v", "null");
		o.put("p", "null");
		o.put("m", "null");
		o.put("freq", "null");
		o.put("factor", "null");
		
		return o;
	}
}
