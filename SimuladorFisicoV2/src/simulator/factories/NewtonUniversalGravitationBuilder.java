package simulator.factories;

import org.json.JSONException;
import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws>{

	public NewtonUniversalGravitationBuilder() {
		super("nlug", "Newton´s law of universal gravitation");		
	}

	@Override
	protected ForceLaws createTheInstance(JSONObject data) {
		try{
			double G= data.has("G")? data.getDouble("G"):6.67E-11;				
			return new NewtonUniversalGravitation(G);
		}catch(JSONException je) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	protected JSONObject createData() {
		JSONObject o = new JSONObject();
		o.put("G", " the gravitational constant (a number)");
	
		return o;
	}
	
}
