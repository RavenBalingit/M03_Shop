package views;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import model.Employee;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.SwingConstants;
import model.*;
import views.*;
import main.*;

public class ShopView extends JFrame implements ActionListener, KeyListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JDialog countMoney;
	JFrame parentFrame;
	JButton btnContarCaja;
	JLabel lblNewLabel;
	JButton btnAadirProducto;
	JButton btnAadirStock;
	JButton btnEliminarProducto;
	Shop shop = new Shop();
	CashView cashView = new CashView(shop);
	ProductView productView = new ProductView(shop);

	/**
	 * Launch the application.
	 */
	/**
	 * Create the frame.
	 * @param frame 
	 */
	
	

	
	public ShopView() {	
		initializeComponents();
		shop.loadInventory();
	}
	
	public void visualize() {
		parentFrame.setVisible(false);
	}

	private void initializeComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblNewLabel = new JLabel("Seleccione una opción");
		lblNewLabel.setBounds(6, 6, 140, 16);
		contentPane.add(lblNewLabel);

		btnContarCaja = new JButton("1. Contar Caja");
		btnContarCaja.setBounds(6, 52, 190, 29);
		btnContarCaja.addActionListener(this);
		btnContarCaja.addKeyListener(this);
		btnContarCaja.setFocusable(false); 
		contentPane.add(btnContarCaja);

		btnAadirProducto = new JButton("2. Añadir Producto");
		btnAadirProducto.setBounds(6, 93, 190, 29);
		btnAadirProducto.addActionListener(this);
		btnAadirProducto.addKeyListener(this);
		btnAadirProducto.setFocusable(false); 
		contentPane.add(btnAadirProducto);

		btnAadirStock = new JButton("3. Añadir Stock");
		btnAadirStock.setBounds(6, 134, 190, 29);
		btnAadirStock.addActionListener(this);
		btnAadirStock.addKeyListener(this); 
		btnAadirStock.setFocusable(false);
		contentPane.add(btnAadirStock);

		btnEliminarProducto = new JButton("9. Eliminar producto");
		btnEliminarProducto.setBounds(6, 175, 190, 29);
		btnEliminarProducto.addActionListener(this);
		btnEliminarProducto.addKeyListener(this); 
		btnEliminarProducto.setFocusable(false); 
		contentPane.add(btnEliminarProducto);

		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false); // Ensure key events are captured
	}

	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnContarCaja) {
			openCashView();
		} else if (e.getSource() == btnAadirProducto) {
			addProduct();
		} else if (e.getSource() == btnAadirStock) {
			addStock();
		} else if (e.getSource() == btnEliminarProducto) {
			deleteProduct();
		}
	}
	
	
	
	
	public void openCashView() {
		cashView.setVisible(true);
	}

	public void addProduct() {
		productView.addProduct();
		productView.setVisible(true);
	}

	public void addStock() {
		productView.addStock();
		productView.setVisible(true);
	}

	public void deleteProduct() {
		productView.deleteProduct();
		productView.setVisible(true);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
			case KeyEvent.VK_1:
				openCashView();
				break;
			case KeyEvent.VK_2:
				addProduct();
				break;
			case KeyEvent.VK_3:
				addStock();
				break;
			case KeyEvent.VK_9:
				deleteProduct();
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
