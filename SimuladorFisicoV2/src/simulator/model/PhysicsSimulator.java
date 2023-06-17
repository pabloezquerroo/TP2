package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


import simulator.misc.Vector2D;

public class PhysicsSimulator {
	private double _dt = 0.0, _T ;
	private ForceLaws _FL; 
	private List<Body> _bs;
	private List<SimulatorObserver> _obs;
	
	public PhysicsSimulator(double dt, ForceLaws FL) throws IllegalArgumentException {	
		_T=0.0;
		_bs = new ArrayList<Body>(); 
		_obs = new ArrayList<SimulatorObserver>();
		setDeltaTime(dt);
		setForceLawsLaws(FL);
	}
	
	public void advance() {
		for(int i = 0; i< _bs.size();i++) {
			_bs.get(i).resetForce();
		}
		_FL.apply(_bs);
		for(int i = 0; i<_bs.size();i++) {
			_bs.get(i).move(_dt);
		}
		_T += _dt;
		for(int i = 0; i< _obs.size();i++) {
			_obs.get(i).onAdvance(_bs, _T);
		}
	}
	
	
	public void addBody(Body b) throws IllegalArgumentException{
		/*
		 * for(int i = 0; i< _bs.size();i++) {
			if(b.getid() == _bs.get(i).getid()) throw new IllegalArgumentException("Cuerpo repetido");
		}
		*/
		if(_bs.contains(b)) {
			throw new IllegalArgumentException("Cuerpo repetido");
		}
		_bs.add(b);
		for(int i = 0; i< _obs.size();i++) {
			_obs.get(i).onBodyAdded(_bs, b);
			
		}
	}
	
	public JSONObject getState() {
		JSONArray array= new JSONArray();
		JSONObject item = new JSONObject();
		item.put("time", _T);
		for(int i=0;i<_bs.size(); i++) {
			array.put(_bs.get(i).getState());
		}
		item.put("bodies", array);
		return item;
	}
	
	public String toString() {
		return getState().toString();
	}

	public void reset() {
		_bs.clear();
		_T=0.0;
		for(int i = 0; i< _obs.size();i++) {
			_obs.get(i).onReset(_bs, _T, _dt, _FL.toString());
		}
	}
	
	public void setDeltaTime(double dt) {
		if(dt<=0.0)throw new IllegalArgumentException();
		this._dt = dt;
		for(int i = 0; i< _obs.size();i++) {
			_obs.get(i).onDeltaTimeChanged(_dt);
		}
	}
	
	public void setForceLawsLaws(ForceLaws forceLaws) {
		if(forceLaws == null)throw new IllegalArgumentException();
		this._FL = forceLaws;
		for(int i = 0; i< _obs.size();i++) {
			_obs.get(i).onForceLawsChanged(_FL.toString());
		}
	}
	
	public void addObserver(SimulatorObserver o) {
		if(!_obs.contains(o)) {
			this._obs.add(o);
			o.onRegister(_bs, _T, _dt, _FL.toString()); //el _FL toString() es por que el registrer recibe un String
				
		}
		
	}
	
}


























