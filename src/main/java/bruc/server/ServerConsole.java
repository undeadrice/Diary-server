package bruc.server;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServerConsole extends JFrame {

	private JScrollPane scrollPane;
	private JTextArea txt;
	private JTextField fld;
	private JPanel panel;

	public ServerConsole() {
		setSize(400, 300);
	}

	public void initGui() {
		addComponents();
		this.setTitle("Server");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}

	private void addComponents() {
		panel = new JPanel(new BorderLayout());

		txt = new JTextArea();
		fld = new JTextField();

		scrollPane = new JScrollPane(txt);
		scrollPane.setPreferredSize(new Dimension(380, 230));

		panel.add(scrollPane, BorderLayout.NORTH);
		panel.add(fld, BorderLayout.SOUTH);
		this.add(panel);
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public JTextArea getTxt() {
		return txt;
	}

	public JTextField getFld() {
		return fld;
	}

	public JPanel getPanel() {
		return panel;
	}

}
