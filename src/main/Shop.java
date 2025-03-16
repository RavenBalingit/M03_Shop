package main;

import model.Amount;
import model.Client;
import model.Employee;
import model.Product;
import model.Sale;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import dao.DaoImplMongoDB;

public class Shop {
    private Amount cash = new Amount(100.00);
    private ArrayList<Product> inventory;
    private int numberProducts;
    private ArrayList<Sale> sales;
    private boolean errorMethot = true;
    final static double TAX_RATE = 1.04;
    private int countSales;
    
    private DaoImplMongoDB dao = new DaoImplMongoDB();
    
    public Shop() {
        inventory = new ArrayList<Product>();        
        sales = new ArrayList<Sale>();
        countSales = 0;
        dao.connect(); 
        readInventory(); 
    }

    public static void main(String[] args) {
        Shop shop = new Shop();

        shop.readInventory();
        
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;
        boolean exit = false;
        
        shop.initSession();

        do {
            System.out.println("\n");
            System.out.println("===========================");
            System.out.println("Menu principal miTienda.com");
            System.out.println("===========================");
            System.out.println("1) Contar caja");
            System.out.println("2) Añadir producto");
            System.out.println("3) Añadir stock");
            System.out.println("4) Marcar producto proxima caducidad");
            System.out.println("5) Ver inventario");
            System.out.println("6) Venta");
            System.out.println("7) Ver ventas");
            System.out.println("8) Ver Precio total de ventas");
            System.out.println("9) Eliminar producto");
            System.out.println("10) Salir programa");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
            case 1:
                shop.showCash();
                break;
            case 2:
                shop.addNewProduct(null, 0, new Amount(0));
                break;
            case 3:
                shop.addStock(null, 0);
                break;
            case 4:
                shop.setExpired();
                break;
            case 5:
                shop.showInventory();
                break;
            case 6:
                shop.sale();
                break;
            case 7:
                shop.showSales();
                break;
            case 8:
                shop.showAmountVentas();
                break;
            case 9:
                shop.deleteProduct(null);
                break;
            case 10:
                exit = true;
                shop.dao.disconnect(); // Cerrar conexión al salir
                break;
            default:
                System.out.println("This option not exist");
            }
            
        } while (!exit);
    }

    public void loadInventory() {
        addProduct(new Product("Manzana", new Amount(10.00), 10, true));
        addProduct(new Product("Pera", new Amount(20.00), 20, true));
        addProduct(new Product("Hamburguesa", new Amount(30.00), 30, true));
        addProduct(new Product("Fresa", new Amount(5.00), 20, true));
    }
    
    public void loadInventoryFile() {
        try {
            String dirPath = System.getProperty("user.dir") + File.separator + "files";
            File dir = new File(dirPath);

            if (!dir.exists()) {
                dir.mkdir();
            }

            File f = new File(dirPath + File.separator + "inputInventory.txt");

            if (f.exists()) {
                readInventory();
            } else {
                System.out.println("File created: " + f.getName());
                loadInventory();

                try (FileWriter myWriter = new FileWriter(f)) {
                    for (Product product : inventory) {
                        if (product != null) {
                            myWriter.write("Product:" + product.getName() + ";Wholesaler Price:"
                                    + product.getWholesalerPrice() + ";Stock:" + product.getStock() + ";\n");
                        }
                    }
                    System.out.println("File inventory finished");
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo de inventario.");
            e.printStackTrace();
        }
    }
    
    public void readInventory() {
        inventory = dao.getInventory();
        for (Product product : inventory) {
            System.out.println(product); // Para depuración
        }
    }
    
    public boolean writeInventory() {
        return dao.writeInventory(inventory);
    }
    
    private void updateFileSales() {
        int numberSale = 1;
        
        try {
            LocalDate dateSaleFile = LocalDate.now();
            File fileSales = new File("sales" + dateSaleFile + ".txt");
            if (fileSales.createNewFile()) {
                System.out.println("File created: " + fileSales.getName());
            } else {
                System.out.println("uploading file");
                fileSales.delete();    
            }
            
            FileWriter myWriter = new FileWriter("sales" + dateSaleFile + ".txt");
            for (Sale sale : sales) {
                if (sale != null) {
                    myWriter.write(numberSale + "; Cliente=" + sale.getClient() +
                            ";Date=" + sale.getDate() + ";\n");  
                    for (Product product : inventory) {
                        if (product != null) {
                            myWriter.write(numberSale + ";Products=" + product.getName() + ","
                                    + product.getPublicPrice() + ";");  
                        }
                    }    
                    myWriter.write("\n" + numberSale + ";Amount=" + sale.getAmount() + ";\n");
                    numberSale++;
                }
            }
            System.out.println("File finished");
            myWriter.close();
            
        } catch (IOException e) {
            System.out.println("Error: Archivo no encontrado");
            e.printStackTrace();
        }
    }
    
    public String showCash() {
        String cashValue = cash.toString();
        return cashValue;
    }

    public boolean addNewProduct(String name, int stock, Amount price) {
        Product product = findProduct(name);
        if (product != null) {
            errorMethot = false;
        } else {
            if (stock <= 0) {
                addProduct(new Product(name, price, stock, false));
                errorMethot = dao.addProduct(name, price, stock, false);
            } else {
                addProduct(new Product(name, price, stock, true));
                errorMethot = dao.addProduct(name, price, stock, true);
            }
        }
        return errorMethot;
    }

    public boolean addStock(String name, int stock) {
        Product product = findProduct(name);
        if (product != null) {
            if (product.getStock() <= 0) {
                product.setAvailable(true);
            }
            product.setStock(product.getStock() + stock);
            System.out.println("El stock del producto " + name + " ha sido actualizado a " + product.getStock());
            dao.updateProduct(product);
            errorMethot = true;
        } else {
            errorMethot = false;
        }
        return errorMethot;
    }

    private void setExpired() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.next();

        Product product = findProduct(name);

        if (product != null) {
            product.expire();
            System.out.println("El stock del producto " + name + " ha sido actualizado a " + product.getPublicPrice());
        }
    }

    public ArrayList<Product> showInventory() {
        System.out.println("Inventory size: " + inventory.size()); // Para depuración
        return inventory; // Devolvemos la lista cargada desde MongoDB
    }

    public void sale() {
        ArrayList<Product> products = new ArrayList<Product>();
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Realizar venta, escribir nombre cliente");
        String nameClient = sc.nextLine();
        Client client = new Client(nameClient);
        Amount totalAmount = new Amount(0);
        String name = "";
        while (!name.equals("0")) {
            for (int i = 0; i < inventory.size(); i++) {
                System.out.println(inventory.get(i));
            }
            System.out.println("Introduce el nombre del producto, escribir 0 para terminar:");
            name = sc.nextLine();
            
            if (name.equals("0")) {
                break;
            }
            Product product = findProduct(name);
            boolean productAvailable = false;

            if (product != null && product.getAvailable()) {
                productAvailable = true;
                double sum = totalAmount.getValue() + product.getPublicPrice().getValue();
                totalAmount.setValue(sum); 
                product.setStock(product.getStock() - 1);
                if (product.getStock() == 0) {
                    product.setAvailable(false);
                }
                System.out.println("Producto añadido con éxito");
                products.add(product);
            }

            if (!productAvailable) {
                System.out.println("Producto no encontrado o sin stock");
            }
        }
        
        double sum = totalAmount.getValue() * TAX_RATE;
        totalAmount.setValue(sum);
        cash.setValue(totalAmount.getValue() + cash.getValue());
        System.out.println("Venta realizada con éxito, total " + totalAmount + "\n");
        client.pay(totalAmount);

        LocalDateTime date = LocalDateTime.now();
        Sale sale = new Sale(client, products, totalAmount, date);
        sales.add(sale);
    }
    
    private void showSales() {
        Scanner sc = new Scanner(System.in);
        boolean check;
        if (sales != null) {
            System.out.println("Lista de ventas:");
            for (Sale sale : sales) {
                if (sale != null) {
                    System.out.println(sale.toString());
                }
            }
            do {
                check = false;
                System.out.println("Do you what to safe this file sales (Y/N)");
                String button = sc.next();
                if (button.equalsIgnoreCase("Y")) {
                    updateFileSales();
                    check = true;
                } else if (button.equalsIgnoreCase("N")) {
                    check = true;
                } else {
                    System.out.println("Error, please insert Y or N");
                }
            } while (check == false);
        } else {
            System.out.println("There are not sales");
        }        
    }

    public void showAmountVentas() {
        double total = 0;
        if (sales != null) {
            for (Sale sale : sales) {
                if (sale != null) {
                    total += sale.getAmount().getValue();
                }
            }
        }
        System.out.println("Amount sales = " + total + Amount.getCurrency());
    }

    public boolean deleteProduct(String name) {
        Product product = findProduct(name);
        
        if (product != null) {
            inventory.remove(product);
            System.out.println(product.getName() + " was deleted");
            dao.deleteProduct(name);
            errorMethot = true;
        } else {
            System.out.println("This product not exists");
            errorMethot = false;
        }
        return errorMethot;
    }
    
    public void addProduct(Product product) {
        inventory.add(product);
    }
    
    public Product findProduct(String name) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i) != null && inventory.get(i).getName().equalsIgnoreCase(name)) {
                return inventory.get(i);
            }
        }
        return null;
    }

    public void initSession() {
        Scanner sc = new Scanner(System.in);
        boolean logged;
        do {
            System.out.println("User: ");
            int user = sc.nextInt();
            System.out.println("Password: ");
            String password = sc.next();
            
            Employee employee = new Employee(dao);
            logged = employee.login(user, password);
            if (!logged) {
                System.out.println("Login failed. Try again.");
            }
        } while (!logged);
    }

    public DaoImplMongoDB getDao() {
        return dao;
    }
}