package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;

public class MainWindow  extends JFrame {
	// ...
	Controller _ctrl;
	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		_ctrl = ctrl;
		initGUI();
	}
	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);
		
		// TODO complete this method to build the GUI

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		
		mainPanel.add(contentPanel);
		
		ControlPanel ctrlPanel = new ControlPanel(_ctrl);
		BodiesTable bodiesInfo = new BodiesTable(_ctrl);
		Viewer universeViewer = new Viewer(_ctrl);
		StatusBar statusBar = new StatusBar(_ctrl);
		
		mainPanel.add(ctrlPanel, BorderLayout.PAGE_START);
		mainPanel.add(statusBar, BorderLayout.PAGE_END);
		
		bodiesInfo.setPreferredSize(new Dimension(800, 300));
		contentPanel.add(bodiesInfo);
		
		universeViewer.setPreferredSize(new Dimension(800, 600));
		contentPanel.add(new JScrollPane(universeViewer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		
		mainPanel.add(contentPanel);
		mainPanel.setVisible(true);
		
		this.pack();
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setVisible(true);

	}

}