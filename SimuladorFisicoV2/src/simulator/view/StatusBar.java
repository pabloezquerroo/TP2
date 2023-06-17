package simulator.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.List;

import javax.print.attribute.standard.OrientationRequested;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class StatusBar extends JPanel implements SimulatorObserver {
	// ...
	private JLabel _currTime;
	private JLabel _currLaws;
	private JLabel _numOfBodies;
	
	StatusBar(Controller ctrl) {
		_currTime = new JLabel();
		_currLaws = new JLabel();
		_numOfBodies = new JLabel();
		initGUI();
		ctrl.addObserver(this);
	}
	private void initGUI() {
		this.setLayout(new GridLayout(0,3));
		//this.setLayout( new FlowLayout());
		this.setBorder( BorderFactory.createBevelBorder( 1 ));
		
		// TODO complete the code to build the tool bar
		add(_currTime);
		add(_numOfBodies);
		add(_currLaws);

	}
	// other private/protected methods
	// ...
	// SimulatorObserver methods
	// ...
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		_numOfBodies.setText("Bodies:  "+ bodies.size());
		_currTime.setText("Time:  "+time);
		_currLaws.setText(fLawsDesc);
			/*}
		});*/
	}
	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		_numOfBodies.setText("Bodies:  "+ bodies.size());
		_currTime.setText("Time:  "+time);
		_currLaws.setText(fLawsDesc);
		
	}
	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub
		_numOfBodies.setText("Bodies:  "+ bodies.size());
		
	}
	@Override
	public void onAdvance(List<Body> bodies, double time) {
		// TODO Auto-generated method stub
		_numOfBodies.setText("Bodies:  "+ bodies.size());
		_currTime.setText("Time:  "+time);
	}
	
	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		// TODO Auto-generated method stub
		_currLaws.setText(fLawsDesc);		
		System.out.println(fLawsDesc);
	}
}

