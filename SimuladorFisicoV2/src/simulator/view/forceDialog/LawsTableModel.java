package simulator.view.forceDialog;

import simulator.misc.Vector2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.json.JSONObject;

public class LawsTableModel extends AbstractTableModel{
	private String[] _columns = {"Key","value","Description"};
	private List<LawsInfo> _rows;
	
	public LawsTableModel() {
		_rows = new ArrayList<LawsInfo>();
	}
	
	public void updateTable(JSONObject data) {
		_rows.clear();
		for(String key: data.keySet()) {
			LawsInfo li= new LawsInfo(key,"",data.getString(key));
			_rows.add(li);
		}
		fireTableStructureChanged();
	}
	
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return _rows.size();
	}
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return _columns.length;
	}
	@Override
	public String getColumnName(int column) {
		return _columns[column];
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		LawsInfo li = _rows.get(rowIndex);
		String s = "";
		switch(columnIndex) {
		case 0:
			s = li.getKey();
		break;
		case 1:
			s = li.getValue();
			break;
		case 2:
			s = li.getDescription();
			break;
		
		}
		
		return s;
	}
	
	@Override
	public void setValueAt(Object o, int rowIndex, int columnIndex) {
		LawsInfo li = _rows.get(rowIndex);
		li.setValue(o.toString());
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 1;
	}
	
	public JSONObject forceToJSON() {
		JSONObject datos = new JSONObject();
		for(int i =0;i<_rows.size();i++) {
			Object value = getValue(_rows.get(i).getValue());
			if(value != null) datos.put(_rows.get(i).getKey(), value);
			
		}
		return datos;
	}
	
	private Object getValue(String in) {
		String[] parts = in.split(",");
		if(parts.length == 1 && parts[0] != "") {
			return Double.parseDouble(parts[0]);
		}
		else if(parts.length == 2) {
			return (new Vector2D(Double.parseDouble(parts[0]),Double.parseDouble(parts[1])).asJSONArray());
		}
		else {
			return null;
		}
	}
}
