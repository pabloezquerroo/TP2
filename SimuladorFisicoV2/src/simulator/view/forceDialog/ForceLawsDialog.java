package simulator.view.forceDialog;


import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.json.JSONObject;

public class ForceLawsDialog extends JDialog{ 

	private int _status;
	private List<JSONObject> _forceLawsInfo;
	private JComboBox<String> _lawsComboBox;
	private int _selectedLawsIndex;
	private JTable _table;
	private LawsTableModel tableModel;
	
	public ForceLawsDialog(Frame parent, List<JSONObject> forceLawsInfo) {
		super(parent,true);
		_forceLawsInfo = forceLawsInfo;
		initGUI();
	}
	
	private void initGUI() {
		_status = 0;
		setTitle("Force Laws Selection");
		//JFrame frame = new JFrame();
		//frame.setVisible(true);
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		
		JLabel txt = new JLabel("Select a force law and provide values for the parametes in the Value Column (default values are used for parametes with no value)");
		
		txt.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(txt);
		
		mainPanel.add(Box.createRigidArea(new Dimension(0,20)));
		
		tableModel = new LawsTableModel();
		_table = new JTable(tableModel);
		
		JScrollPane x = new JScrollPane(_table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mainPanel.add(x);
		
		mainPanel.add(Box.createRigidArea(new Dimension(0,20)));
		
		JPanel boxPanel = new JPanel();
		boxPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(boxPanel);
		
		_lawsComboBox = new JComboBox<>();
		
		for(JSONObject fl: _forceLawsInfo) {
			_lawsComboBox.addItem(fl.getString("desc"));
		}
		
		_lawsComboBox.setSelectedIndex(0);
		_selectedLawsIndex = 0;
		JSONObject data = _forceLawsInfo.get(_selectedLawsIndex).getJSONObject("data");
		tableModel.updateTable(data);
		
		_lawsComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				_selectedLawsIndex = _lawsComboBox.getSelectedIndex();
				JSONObject data = _forceLawsInfo.get(_selectedLawsIndex).getJSONObject("data");
				tableModel.updateTable(data);
				
			}
			
		});
		
		boxPanel.add(new JLabel("Force Law:  "));
		boxPanel.add(_lawsComboBox);
		
		mainPanel.add(Box.createRigidArea(new Dimension(0,20)));
		
		JPanel botones = new JPanel();
		botones.setLayout(new BoxLayout(botones,BoxLayout.X_AXIS));
		botones.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(botones);
		
		JButton OK = new JButton("OK");
		JButton Cancel = new JButton("Cancel");

		OK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//a�adir las acciones de confirmar
				_status = 1;
				setVisible(false);
			}
		});
		Cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				_status = 0;
				setVisible(false);

			}
		});
		botones.add(Cancel);
		botones.add(OK);
		this.pack();
		this.setVisible(true);
		
	}

	public int open() {
		// TODO Auto-generated method stub
		return _status;
	}

	public JSONObject getSelectedLaws() {
		// TODO Auto-generated method stub
		JSONObject item = new JSONObject();
		item.put("type",_forceLawsInfo.get(_selectedLawsIndex).get("type"));
		item.put("data",tableModel.forceToJSON());
		return item;
	}
}
