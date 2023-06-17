
package simulator.factories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;

public class BasicBodyBuilder extends Builder<Body>{

	public BasicBodyBuilder() {
		super("basic", "Default Body");		
	}

	@Override
	protected Body createTheInstance(JSONObject data){
		try{
			if (data.has("id")&&data.has("m")&&data.has("v")&&data.has("p")) {
				String id= data.getString("id");
				double m= data.getDouble("m");
				Vector2D v= new Vector2D(data.getJSONArray("v").getDouble(0), data.getJSONArray("v").getDouble(1));
				Vector2D p= new Vector2D(data.getJSONArray("p").getDouble(0), data.getJSONArray("p").getDouble(1));
				return new Body(id, m, v, p);
			}else {
				throw new IllegalArgumentException();
			}
		}catch(JSONException je){
			throw new IllegalArgumentException();
		}
		
	}
	
	@Override
	protected JSONObject createData() {
		JSONObject o = new JSONObject();
		o.put("id", "null");
		o.put("p", "null");
		o.put("v", "null");
		o.put("m", "null");
		return o;
	}

}
