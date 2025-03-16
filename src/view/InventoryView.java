package view;

import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import model.Product;

public class InventoryView extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JButton returnButton;
    private JTable table;

    public InventoryView(ArrayList<Product> inventory) {
        setTitle("Inventory");
        setBounds(100, 100, 600, 400); 
        getContentPane().setLayout(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Wholesaler Price");
        model.addColumn("Public Price");
        model.addColumn("Stock");
        model.addColumn("Available");

        if (inventory != null) {
            for (Product product : inventory) {
                model.addRow(new Object[]{
                    product.getId(),
                    product.getName(),
                    product.getWholesalerPrice().toString(),
                    product.getPublicPrice().toString(),
                    product.getStock(),
                    product.getAvailable()
                });
            }
        }

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        returnButton = new JButton("Return");
        returnButton.addActionListener(this);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(returnButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e) {
        if (returnButton == e.getSource()) {
            this.dispose();
        }
    }
}