package simulator.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.*;

import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;
import simulator.view.forceDialog.ForceLawsDialog;

public class ControlPanel extends JPanel implements SimulatorObserver{
	JFileChooser fileChooser = new JFileChooser("resources");
	private Controller _ctrl;
	private boolean _stopped;
	private JButton _open;
	private JButton _physics;
	private JButton _run;
	private JButton _stop;
	private JButton _exit;
	private JSpinner _pasos;
	private JTextField _DeltaTime;
	private JSONObject Force;
	private ForceLawsDialog _changeForceLawsDialog = null;
	
	ControlPanel(Controller ctrl) {
	_ctrl = ctrl;
	_stopped = true;
	initGUI();
	_ctrl.addObserver(this);
	}
	private void initGUI() {
	// TODO build the tool bar by adding buttons, etc.
		JToolBar toolBar = new JToolBar();
		this.setLayout(new BorderLayout());
		
		//ABRIR
		_open = new JButton( new ImageIcon("resources/icons/open.png"));
		_open.setToolTipText("Open a file");
		_open.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
                int v = fileChooser.showOpenDialog(null);
                if (v==JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    System.out.println("loading " +file.getName());
                    DataInputStream aux;
                    try {
                        aux = new DataInputStream(new FileInputStream(file));
                        _ctrl.reset();
                        _ctrl.loadBodies(aux);
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    } 
                }
                else System.out.println("load cancelled by user");
            
            }
		});
		toolBar.add(_open);
		toolBar.addSeparator();
		
		//CAMBIAR LEY
		_physics = new JButton(new ImageIcon("resources/icons/physics.png"));
		_physics.setToolTipText("Change laws");
		_physics.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cambioFuerza();
				_changeForceLawsDialog = null;
			}
		});
		toolBar.add(_physics);
		toolBar.addSeparator();
		//JToolBar startstopPanel = new JToolBar();
		
		//RUN
		_run = new JButton(new ImageIcon("resources/icons/run.png"));
		_run.setToolTipText("Run");
		_run.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_stopped = false;
				_open.setEnabled(false);
				_physics.setEnabled(false);
				_exit.setEnabled(false);
				_run.setEnabled(false);
				_pasos.setEnabled(false);
				_DeltaTime.setEnabled(false);
				_ctrl.setDeltaTime(Double.parseDouble(_DeltaTime.getText())); 
				run_sim((Integer)_pasos.getValue()); 
			}
		});
		toolBar.add(_run);
		
		//STOP
		_stop = new JButton(new ImageIcon("resources/icons/stop.png"));
		_stop.setToolTipText("Stop");
		_stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_stopped = true;
			}
		});
		toolBar.add(_stop);
		toolBar.addSeparator();

		//STEPS
		_pasos = new JSpinner(new SpinnerNumberModel(10000,1,1000000,1));
		_pasos.setPreferredSize(new Dimension(80,40));
		_pasos.setMaximumSize(new Dimension(80,40));
		JLabel pasosT = new JLabel("Steps: ");
		toolBar.add(pasosT);
		toolBar.add("Steps:",_pasos);
		toolBar.addSeparator();
		
		//DT
		JLabel DLt = new JLabel("Delta-Time: ");
		_DeltaTime = new JTextField("2500", 5);
		_DeltaTime.setEditable(true);
		_DeltaTime.setPreferredSize(new Dimension(80,40));
		_DeltaTime.setMaximumSize(new Dimension(80,40));
		toolBar.add(DLt);
		toolBar.add("Delta-Time:",_DeltaTime);

		//EXIT
		_exit = new JButton(new ImageIcon("resources/icons/exit.png"));
		_exit.setToolTipText("Exit");
		_exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {confirmacionSalida();	}
		});
		
		toolBar.add(Box.createHorizontalGlue());
		toolBar.add(_exit);
		add(toolBar);
	}
	// other private/protected methods
	// ...
	private void confirmacionSalida() {
		int n=JOptionPane.showOptionDialog(new JFrame(), "�Seguro que deseas salir?", "Salir", JOptionPane.YES_NO_OPTION , JOptionPane.QUESTION_MESSAGE, null, null, null);
		if (n==0){System.exit(0);	}
	}
	
	private void cambioFuerza() {
		if(_changeForceLawsDialog == null) {
			_changeForceLawsDialog = new ForceLawsDialog((Frame)SwingUtilities.getWindowAncestor(this), _ctrl.getForceLawsInfo());
		}
		int status = _changeForceLawsDialog.open();
		if(status == 1) {
			try {
				JSONObject obj = _changeForceLawsDialog.getSelectedLaws();
				_ctrl.setForceLaws(obj);
				System.out.println(obj);
			} catch(Exception e) {
				JOptionPane.showMessageDialog(this.getParent(), "Something went wrong: " + e.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE );
			}		
		}
		
	}
	
	private void run_sim(int n) {

		if ( n>0 && !_stopped ) {
			try {
				_ctrl.run(1);
			} catch (Exception e) {
			// TODO show the error in a dialog box
			// TODO enable all buttons
				_stopped = true;
				return;
			}
			SwingUtilities.invokeLater( new Runnable() {
				@Override
				public void run() {
					run_sim(n-1);
				}
			});
		} else {
			_stopped = true;
			// TODO enable all buttons
			_open.setEnabled(true);
			_physics.setEnabled(true);
			_exit.setEnabled(true);
			_run.setEnabled(true);
			_pasos.setEnabled(true);
			_DeltaTime.setEnabled(true);
		}
	}
	
	// SimulatorObserver methods
	// ...
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		_stopped = true;
		
	}
	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onAdvance(List<Body> bodies, double time) {
		// TODO Auto-generated method stub
		
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