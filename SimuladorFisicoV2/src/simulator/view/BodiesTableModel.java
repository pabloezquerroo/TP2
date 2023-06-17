package simulator.view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import simulator.control.Controller;
import simulator.model.*;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {

	private List<Body> _bodies;
	private String[] columnNames= {"Id", "Mass", "Position", "Velocity", "Force"};
	
	BodiesTableModel(Controller ctrl) {
		_bodies = new ArrayList<>();
		ctrl.addObserver(this);
	}
	@Override
	public int getRowCount() {
	// TODO complete
		if (this._bodies == null) return 0;
		else return this._bodies.size();
	}
	@Override
	public int getColumnCount() {
	// TODO complete
		return columnNames.length;
	}
	
	@Override
	public String getColumnName(int col) {
		if (this.columnNames==null) return "";
		else return this.columnNames[col];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
	// TODO complete
		Object salida = null;
		switch(columnIndex){//forma muy cutre
		case 0:
			salida = _bodies.get(rowIndex).getid();
			break;
		case 1:
			salida = _bodies.get(rowIndex).getMass();
			break;
		case 2:
			salida = _bodies.get(rowIndex).getPosition();
			break;
		case 3:
			salida = _bodies.get(rowIndex).getVelocity();
			break;
		case 4:
			salida = _bodies.get(rowIndex).getForce();
			break;
		}
		return salida;
	}
	// SimulatorObserver methods
	// ...
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		_bodies.clear();
		_bodies.addAll(bodies);
		fireTableStructureChanged();
		
	}
	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		_bodies.clear();
		_bodies.addAll(bodies);
		fireTableStructureChanged();
		
	}
	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub
		_bodies.add(b);
		fireTableStructureChanged();
	}
	@Override
	public void onAdvance(List<Body> bodies, double time) {
		// TODO Auto-generated method stub
		_bodies.clear();
		_bodies.addAll(bodies);
		fireTableStructureChanged();
		
	}
	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}
}

