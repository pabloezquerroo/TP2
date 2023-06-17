package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {
	List<Builder<T>> _builders;
	List<JSONObject> _factoryElements;
	
	public BuilderBasedFactory(List<Builder<T>> builders) {
		 _builders = new ArrayList<>(builders);
		 _factoryElements= new ArrayList<JSONObject>();
		 for (int i=0; i<_builders.size(); i++) {
			 _factoryElements.add(builders.get(i).getBuilderInfo()); 
		 }
	}

	@Override
	public T createInstance(JSONObject info) {
		if (info==null)throw new IllegalArgumentException("Invalid value for crateInstance: null");
		int i=0;
		boolean salida=false;
		while (i<_builders.size() && !salida) {
			if(_builders.get(i).createInstance(info)!=null) salida=true;
			i++;
		 }
		if (!salida)throw new IllegalArgumentException();
		return _builders.get(i-1).createInstance(info);
	}

	@Override
	public List<JSONObject> getInfo() {
		return _factoryElements;
	}

}
