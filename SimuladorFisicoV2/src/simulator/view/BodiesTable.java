package simulator.view;

import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import simulator.control.Controller;

public class BodiesTable extends JPanel{
	private BodiesTableModel _BTM;
	private JTable _t;
	
	BodiesTable (Controller ctrl){
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black, 2),
				"Bodies",
				TitledBorder.LEFT, TitledBorder.TOP));
		//TODO complete
		_BTM = new BodiesTableModel(ctrl);
		_t = new JTable(_BTM);
		add(new JScrollPane(_t));
		//JScrollPane scroll = new JScrollPanel(new JPanel(this));
		//JPanel panel = new JPanel(new JScrollPane(new BodiesTableModel(ctrl))); //no entiendo
		
	}
	
}

