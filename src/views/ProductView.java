package views;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import model.*;
import views.*;
import main.*;

public class ProductView extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	JPanel buttonPane;
	JButton okButton;
	JButton cancelButton;
	private Shop shop;

	/**
	 * Launch the application.
	 */

	int cases = 0;

	/**
	 * Create the dialog.
	 */
	public ProductView(Shop shopInstance) {
		this.shop = shopInstance;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		{
			buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				okButton.addActionListener(this);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(this);
				getRootPane().setDefaultButton(cancelButton);
			}
		}

	}

	public void productName() {

		JLabel lblNewLabel = new JLabel("Nombre Producto");
		lblNewLabel.setBounds(50, 45, 110, 16);
		contentPanel.add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(172, 40, 130, 26);
		contentPanel.add(textField);
		textField.setColumns(10);

	}

	// Methods for the display
	public void productStock() {
		{
			JLabel lblNewLabel_1 = new JLabel("Stock Producto");
			lblNewLabel_1.setBounds(50, 90, 110, 16);
			contentPanel.add(lblNewLabel_1);
		}
		{
			textField_1 = new JTextField();
			textField_1.setColumns(10);
			textField_1.setBounds(172, 85, 130, 26);
			contentPanel.add(textField_1);
		}

	}

	public void productPrice() {
		{
			JLabel lblNewLabel_1 = new JLabel("Precio Producto");
			lblNewLabel_1.setBounds(50, 132, 110, 16);
			contentPanel.add(lblNewLabel_1);
		}

		{
			textField_2 = new JTextField();
			textField_2.setColumns(10);
			textField_2.setBounds(172, 127, 130, 26);
			contentPanel.add(textField_2);
		}

	}

	// Methods to CALL the displays
	public void addProduct() {
		productName();
		productStock();
		productPrice();
		cases = 1;

	}

	public void addStock() {
		productName();
		productStock();
		cases = 2;
	}

	public void deleteProduct() {
		productName();
		cases = 3;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == okButton) {
			switch (cases) {
			case 1:
				System.out.println("uno");

				String productName = textField.getText();
				int productStock = Integer.parseInt(textField_1.getText());
				double productPrice = Double.parseDouble(textField_2.getText());
				Product newProduct = new Product(productName, productPrice, true, productStock);

				if (!shop.alreadyExists(productName)) {
					shop.addProduct(newProduct);
					System.out.println("Producto agregado correctamente.");
					JOptionPane.showMessageDialog(null, "Producto " + productName + " añadido correctamente");
				} else {
					JOptionPane.showMessageDialog(null, "Ya existe el producto " + productName);
				}
				break;

			case 2:
				System.out.println("dos");
				String productNameToAdd = textField.getText();
				int stockToAdd = Integer.parseInt(textField_1.getText());
				Product productToAddStock = shop.findProduct(productNameToAdd);

				if (productToAddStock != null) {
					productToAddStock.setStock(productToAddStock.getStock() + stockToAdd);
					JOptionPane.showMessageDialog(null, "El stock del producto " + productNameToAdd + " ha sido actualizado a "
							+ productToAddStock.getStock());
					
					System.out.println("El stock del producto " + productNameToAdd + " ha sido actualizado a "
							+ productToAddStock.getStock());
				} else {
					JOptionPane.showMessageDialog(null,
							"No se ha encontrado el producto con nombre " + productNameToAdd);
				}

				break;

			case 3:
				System.out.println("tres");
				String productNameToDelete = textField.getText();
				Product deletedProduct = shop.findProduct(productNameToDelete);

				if (deletedProduct != null) {
					shop.deleteProduct(deletedProduct);
					System.out.println("Producto eliminado correctamente.");
					JOptionPane.showMessageDialog(null,"Producto eliminado correctamente.");
				} else {
					JOptionPane.showMessageDialog(null, "No se ha encontrado el producto con nombre " + productNameToDelete);
				}
				break;

			}
		}
		else if (e.getSource() == cancelButton) {
			System.out.println("wa");
			this.setVisible(false);
		}

	}

}
