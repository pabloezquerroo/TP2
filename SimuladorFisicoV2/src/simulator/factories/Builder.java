package simulator.factories;

import java.util.Collection;

import org.json.JSONObject;

public abstract class Builder<T> {

	String _typeTag;
	String _desc;
	
	public Builder(JSONObject jsonObject) {
		this._typeTag=jsonObject.getString("type");
		this._typeTag=jsonObject.getString("desc");
	}
	
	public Builder(String type, String desc) {
		this._typeTag=type;
		this._desc=desc;
	}
	
	protected abstract T createTheInstance(JSONObject jsonObject);
	
	public T createInstance(JSONObject info) throws IllegalArgumentException{
		T b = null;
		if (_typeTag != null) {
			try {
				if(_typeTag.equals(info.getString("type"))) {
					b= createTheInstance(info.getJSONObject("data"));
				}				
			}catch(IllegalArgumentException ie){
				System.err.println(ie.getMessage());
			}
		}
		return b;
	}
	
	public JSONObject getBuilderInfo() {
		JSONObject info = new JSONObject();
		info.put("type", _typeTag);
		info.put("data", createData());
		info.put("desc", _desc);
		return info;
		
	}

	protected JSONObject createData() {
		return new JSONObject();
	}
}
