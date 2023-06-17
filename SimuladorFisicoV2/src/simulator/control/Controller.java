package simulator.control;

import java.io.*;
import java.util.List;
import org.json.*;
import simulator.model.*;
import simulator.factories.*;


public class Controller {
	private PhysicsSimulator _sim;
	private Factory<Body> _bodiesFactory;
	private Factory<ForceLaws> _force;

	public Controller(PhysicsSimulator simulator, Factory<Body> bodyFactory, Factory<ForceLaws> force) {
		_sim=simulator;
		_bodiesFactory= bodyFactory;
		_force = force;
		
	}

	public void loadBodies(InputStream in) {
		JSONObject jsonInput = new JSONObject(new JSONTokener(in));
		JSONArray bodies = jsonInput.getJSONArray("bodies");
		for(int i = 0; i< bodies.length();i++) {
			try {
				_sim.addBody(_bodiesFactory.createInstance(bodies.getJSONObject(i)));				
			}catch(IllegalArgumentException e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	public void run(int n, OutputStream out, InputStream expOut, StateComparator cmp) throws NotEqualStatesException{ 
		JSONObject expOutJO = null;
		if(expOut != null) {
			expOutJO = new JSONObject(new JSONTokener(expOut));
		}
		if(out == null) {
			out = new OutputStream() {
				@Override
				public void write(int b) throws IOException{}
				
			};
		}
		PrintStream p = new PrintStream(out);
		p.println("{");
		p.println("\"states\": [");
		
		JSONObject currState = null;
		JSONObject expState = null;
		currState = _sim.getState();
		p.println(currState);
		if(expOutJO != null) {
			expState = expOutJO.getJSONArray("states").getJSONObject(0);
			if(!cmp.equal(expState, currState)) {
				throw new NotEqualStatesException(expState, currState, 0);
			}
			
		}
		
		for(int i = 1; i< n;i++) {
			_sim.advance();
			currState=_sim.getState();
			p.print(",");
			p.println(currState);

			if(expOutJO != null) {
				expState = expOutJO.getJSONArray("states").getJSONObject(i);
				if(!cmp.equal(expState, currState)) {
					throw new NotEqualStatesException(expState, currState, i);
				}
			}
		}
		 		
		p.println("]");
		p.println("}");
	}
	
	public void run(int n) {
		for(int i = 0; i< n;i++) {
			_sim.advance();
		}
	}
	
	public void reset() { 
		_sim.reset();
	}
	
	public void setDeltaTime(double dt) {
		_sim.setDeltaTime(dt);
	}
	
	public void addObserver(SimulatorObserver o) {
		_sim.addObserver(o);
	}
	
	public List<JSONObject> getForceLawsInfo(){
		return _force.getInfo();
		
	}
	public void setForceLaws(JSONObject info) {
		_sim.setForceLawsLaws(_force.createInstance(info));
	}
	
}
