package simulator.view.forceDialog;

public class LawsInfo {
	private String key;
	private String value;
	private String description;
	
	public LawsInfo(String k, String v, String d) {
		key = k;
		value = v;
		description = d;
	}

	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}
	
	public String getDescription() {
		return description;
	}
	
}
