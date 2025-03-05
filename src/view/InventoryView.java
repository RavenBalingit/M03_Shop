package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InventoryView extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton returnButton;
	
	public InventoryView() {
		setTitle("Inventory");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		returnButton = new JButton("Return");
		returnButton.setBounds(327, 237, 117, 29);
		returnButton.addActionListener(this);
		getContentPane().add(returnButton);
		
		
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if(returnButton == e.getSource()) {
			this.dispose();
		}
	}
}
