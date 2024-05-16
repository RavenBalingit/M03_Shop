package views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import model.*;
import exception.*;

public class LoginView extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    JDialog insertWindow;

    private JButton btnNewButton;
    private JTextField textField;
    private JTextField textField_1;
    private JFrame frame;
    JLabel lblNewLabel_1;

    private int loginAttempts = 0;
    private static final int MAX_LOGIN_ATTEMPTS = 3;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginView frame = new LoginView();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public LoginView() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(400, 100, 745, 520);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setTitle("Login");
        setSize(414, 298);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Numero Empleado");
        lblNewLabel.setBounds(6, 79, 115, 16);
        getContentPane().add(lblNewLabel);

        textField = new JTextField();
        textField.setBounds(188, 74, 132, 26);
        getContentPane().add(textField);
        textField.setColumns(10);

        lblNewLabel_1 = new JLabel("Password");
        lblNewLabel_1.setBounds(6, 119, 59, 16);
        getContentPane().add(lblNewLabel_1);

        textField_1 = new JTextField();
        textField_1.setBounds(188, 114, 132, 26);
        textField_1.setColumns(10);
        getContentPane().add(textField_1);

        btnNewButton = new JButton("Acceder");
        btnNewButton.setBounds(314, 235, 94, 29);
        btnNewButton.addActionListener(this);
        getContentPane().add(btnNewButton);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnNewButton) {
            Employee employee = new Employee(null);
            try {
                if (employee.login(Integer.valueOf(textField.getText()), textField_1.getText())) {
                    System.out.println("funciona");
                    this.setVisible(false);
                    ShopView miTienda = new ShopView();
                    miTienda.setVisible(true);
                } else {
                    loginAttempts++;
                    if (loginAttempts >= MAX_LOGIN_ATTEMPTS) {
                        throw new LimitLoginException("Se han excedido los intentos de inicio de sesión.");
                    } else {
                        System.out.println("no funciona");
                        JOptionPane.showMessageDialog(insertWindow,
                                "ERROR: HA INTRODUCIDO MAL LOS DATOS DE LA PERSONA, POR FAVOR VUELVA A INTRODUCIRLOS",
                                "Error de Inserción", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (LimitLoginException ex) {
                JOptionPane.showMessageDialog(insertWindow, ex.getMessage(), "Error de Inserción",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        }
    }
}
