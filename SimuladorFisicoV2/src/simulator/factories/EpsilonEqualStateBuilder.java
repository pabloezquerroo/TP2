package simulator.factories;

import org.json.JSONException;
import org.json.JSONObject;

import simulator.control.EpsilonEqualStates;
import simulator.control.StateComparator;

public class EpsilonEqualStateBuilder extends Builder<StateComparator> {

	public EpsilonEqualStateBuilder() {
		super("epseq", "Epsilon-equal states comparator");
	}

	@Override
	protected StateComparator createTheInstance(JSONObject data){
		try{
			double eps= data.has("eps")?data.getDouble("eps"):0.0;
			return new EpsilonEqualStates(eps);
		}catch(JSONException je){
			throw new IllegalArgumentException();
		}
	}
	
	@Override
	protected JSONObject createData() {
		JSONObject o = new JSONObject();
		o.put("eps", "null");
		return o;
	} 
	
	
}
