package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.*;
import main.*;
import views.*;

public class CashView extends JDialog implements ActionListener  {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	JButton okButton;
	private JTextField textField_1;
	private Shop shop;


	/**
	 * Launch the application.
	 */

	/**
	 * Create the dialog.
	 */
	public CashView(Shop shopInstance) {
        this.shop = shopInstance;
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        {
            JLabel lblNewLabel = new JLabel("Dinero total:");
            lblNewLabel.setBounds(175, 21, 79, 16);
            contentPanel.add(lblNewLabel);
        }
        {
            textField_1 = new JTextField();
            textField_1.setBounds(147, 49, 130, 26);
            contentPanel.add(textField_1);
            textField_1.setEditable(false);
            textField_1.setColumns(10);
            textField_1.setText(String.valueOf(Shop.cash));
        }

        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);

            {
                okButton = new JButton("OK");
                okButton.setActionCommand("OK");
                buttonPane.add(okButton);
                okButton.addActionListener(this);
				getRootPane().setDefaultButton(okButton);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == okButton) {
            this.setVisible(false);
        }
    }
}