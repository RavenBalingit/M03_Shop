package views;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.Employee;
import exception.LimitLoginException;

public class LoginView extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    JDialog insertWindow;

    private JButton btnNewButton;
    private JTextField textField;
    private JPasswordField passwordField;
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

        passwordField = new JPasswordField();
        passwordField.setBounds(188, 114, 132, 26);
        getContentPane().add(passwordField);

        btnNewButton = new JButton("Acceder");
        btnNewButton.setBounds(314, 235, 94, 29);
        btnNewButton.addActionListener(this);
        getContentPane().add(btnNewButton);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnNewButton) {
            Employee employee = new Employee(null);
            try {
                int employeeNumber = Integer.parseInt(textField.getText());
                String password = new String(passwordField.getPassword());
                
                if (employee.login(employeeNumber, password)) {
                    System.out.println("funciona");
                    this.setVisible(false);
                    ShopView miTienda = new ShopView();
                    miTienda.setVisible(true);
                } else {
                    handleFailedLogin("ERROR: HA INTRODUCIDO MAL LOS DATOS DE LA PERSONA, POR FAVOR VUELVA A INTRODUCIRLOS");
                }
            } catch (NumberFormatException ex) {
                handleFailedLogin("ERROR: El número de empleado debe ser un número válido.");
            }
        }
    }

    private void handleFailedLogin(String errorMessage) {
        loginAttempts++;
        JOptionPane.showMessageDialog(insertWindow, errorMessage, "Error de Inserción", JOptionPane.ERROR_MESSAGE);
        if (loginAttempts >= MAX_LOGIN_ATTEMPTS) {
            JOptionPane.showMessageDialog(insertWindow,
                    "Se han excedido los intentos de inicio de sesión.",
                    "Error de Inserción", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
}
