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
		contentPane.add(btnContarCaja);
		btnContarCaja.addActionListener(this);

		btnAadirProducto = new JButton("2. Añadir Producto");
		btnAadirProducto.setBounds(6, 93, 190, 29);
		contentPane.add(btnAadirProducto);
		btnAadirProducto.addActionListener(this);

		btnAadirStock = new JButton("3. Añadir Stock");
		btnAadirStock.setBounds(6, 134, 190, 29);
		contentPane.add(btnAadirStock);
		btnAadirStock.addActionListener(this);

		btnEliminarProducto = new JButton("9. Eliminar producto");
		btnEliminarProducto.setBounds(6, 175, 190, 29);
		contentPane.add(btnEliminarProducto);
		btnEliminarProducto.addActionListener(this);

		addKeyListener(this);
		setFocusable(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnContarCaja) {  
			System.out.println("wan");
			openCashView();
			

		}
		else if (e.getSource() == btnAadirProducto) {
			System.out.println("tu");
			ProductView productView = new ProductView(shop); 
			productView.addProduct();
			productView.setVisible(true);



		}
		else if (e.getSource() == btnAadirStock) {
			System.out.println("tri");
			ProductView productView = new ProductView(shop); 
			productView.addStock();
			productView.setVisible(true);


		}
		else if (e.getSource() == btnEliminarProducto) {
			System.out.println("four");
			ProductView productView = new ProductView(shop); 
			productView.deleteProduct();
			productView.setVisible(true);


		}
	}
	
	public void openCashView() {
		CashView cashView = new CashView(shop);
		cashView.setVisible(true);

	}
	


	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	

	
}
