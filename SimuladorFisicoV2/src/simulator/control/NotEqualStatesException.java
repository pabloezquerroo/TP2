package simulator.control;

import org.json.JSONObject;

public class NotEqualStatesException extends Exception {

	public NotEqualStatesException() {
	}
	
	public NotEqualStatesException(JSONObject jo0,JSONObject jo1, int n) {
		super("\nEn la ejecucion " + n + " se ha detectado que el estado \n" + jo0.toString() + " y \n" + jo1.toString() + "\n son distintos.");
	}

}
