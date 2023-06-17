package simulator.factories;

import org.json.JSONException;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws>{
	public MovingTowardsFixedPointBuilder() {
		super("mtfp", "Moving towards a fixed point");		
	}

	@Override
	protected ForceLaws createTheInstance(JSONObject data){
		try {
			Vector2D c = data.has("c")?new Vector2D(data.getJSONArray("c").getDouble(0), data.getJSONArray("c").getDouble(1)): new Vector2D();
            double g = data.has("g")?data.getDouble("g"):9.81;
            return new MovingTowardsFixedPoint (c, g);
		}catch(JSONException je) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	protected JSONObject createData() {
		JSONObject o = new JSONObject();
		o.put("c", "the point towards which bodies move (a json list of 2 numbers, e.g., [100.0,50.0])");
		o.put("g", "the length of the acceleration vector (a number)");
		return o;
	}
}
