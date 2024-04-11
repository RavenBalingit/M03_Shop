package main;

import model.Amount;
import model.Client;
import model.Employee;
import model.Product;
import model.Sale;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Shop {
	private Amount cash = new Amount(100);
	private ArrayList<Product> inventory;
	private int numberProducts;
	private ArrayList<Sale> sales = new ArrayList<Sale>();

	private int Stock = 0;
	private int saleNumber = 0;
	final static double TAX_RATE = 1.04;

	public Shop() {
		inventory = new ArrayList<Product>();
	}

	public static void main(String[] args) {
		Shop shop = new Shop();
		shop.login();

		shop.loadInventory();

		Scanner scanner = new Scanner(System.in);
		int opcion = 0;
		boolean exit = false;
		
		
		
		
		
		
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
			System.out.println("8) Ver valor total de ventas");
			System.out.println("9) Eliminar un producto");
			System.out.println("10) Salir del programa");
			System.out.println("11) Quitar producto");
			System.out.print("Seleccione una opción: ");
			opcion = scanner.nextInt();

			switch (opcion) {
			case 1:
				shop.showCash();
				break;

			case 2:
				shop.addProduct();
				break;

			case 3:
				shop.addStock();
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
				shop.showTotalSale();
				break;

			case 9:
				shop.removeProduct();
				break;

			case 11:
				shop.loadFile();
				break;

			case 10:
				exit = true;
				break;
			}

		} while (!exit);
		System.out.println("Saliendo de miTienda.com ...");
	}

	/**
	 * load initial inventory to shop
	 */
	public void loadInventory() {
		addProduct(new Product("Manzana", 10.00, true, 10));
		addProduct(new Product("Pera", 20.00, true, 20));
		addProduct(new Product("Hamburguesa", 30.00, true, 30));
		addProduct(new Product("Fresa", 5.00, true, 20));
	}

	/**
	 * show current total cash
	 */
	private void showCash() {
		System.out.println("Dinero actual: " + cash);
	}

	/**
	 * add a new product to inventory getting data from console
	 */
	public void addProduct() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Nombre del producto: ");
		String name = scanner.next();
		Product product = findProduct(name);

		if (product == null) {
			System.out.print("Precio mayorista: ");
			double wholesalerPrice = scanner.nextDouble();
			System.out.print("Stock: ");
			int stock = scanner.nextInt();

			addProduct(new Product(name, wholesalerPrice, true, stock));
		} else {
			System.out.println("Ya existe el producto " + name);
		}
	}

	/**
	 * add stock for a specific product
	 */
	public void addStock() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Seleccione un nombre de producto: ");
		String name = scanner.next();
		Product product = findProduct(name);

		if (product != null) {
			// ask for stock
			System.out.print("Seleccione la cantidad a añadir: ");
			Stock = scanner.nextInt();
			// update stock product
			product.setStock(product.getStock() + Stock);
			System.out.println("El stock del producto " + name + " ha sido actualizado a " + product.getStock());

		} else {
			System.out.println("No se ha encontrado el producto con nombre " + name);
		}
	}

	/**
	 * set a product as expired
	 */
	private void setExpired() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Seleccione un nombre de producto: ");
		String name = scanner.next();
		Product product = findProduct(name);
		if (product != null) {
			product.expire();
			System.out.println("El stock del producto " + product.getName() + " ha sido actualizado a "
					+ product.getPublicPrice());

		}
	}

	/**
	 * show all inventory
	 */
	public void showInventory() {
		System.out.println("Contenido actual de la tienda:");
		for (Product product : inventory) {
			if (product != null) {
				System.out.println(product);
			}
		}
	}

	/**
	 * make a sale of products to a client
	 */
	public void sale() {
	    ArrayList<Product> products = new ArrayList<Product>();
	    Scanner sc = new Scanner(System.in);

	    // Crear un nuevo objeto Client
	    Client client = new Client();
	    System.out.println("Realizar venta, escribir nombre cliente");
	    // Utilizar una variable diferente para el nombre del cliente
	    String clientName = sc.nextLine();
	    client.setName(clientName); // Asignar el nombre al objeto Client

	    double totalAmount = 0.0;
	    String name = "";
	    while (!name.equals("0")) {
	        System.out.println("Introduce el nombre del producto, escribir 0 para terminar:");
	        name = sc.nextLine();

	        if (name.equals("0")) {
	            break;
	        }
	        Product product = findProduct(name);
	        boolean productAvailable = false;

	        if (product != null && product.isAvailable()) {
	            productAvailable = true;
	            totalAmount += product.getPublicPrice().getValue();
	            product.setStock(product.getStock() - 1);
	            if (product.getStock() == 0) {
	                product.setAvailable(false);
	            }
	            products.add(product);
	            System.out.println("Producto añadido con éxito");
	        } else {
	            System.out.println("Producto no encontrado o sin stock");
	        }
	    }

	    LocalDateTime date = LocalDateTime.now();
	    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	    String salesDate = date.format(format);
	    System.out.println("\nHora de venta: " + salesDate);

	    // Aplicar impuesto al total
	    totalAmount *= TAX_RATE;

	    // Verificar si el cliente puede pagar el total
	    if (client.pay(new Amount(totalAmount))) {
	        // Actualizar el efectivo en caja si el cliente pudo pagar
	        cash.setValue(cash.getValue() + totalAmount);
	        Sale sale = new Sale(client, products, new Amount(totalAmount), date);
	        sales.add(sale);
	        System.out.println("Venta realizada con éxito, total: " + totalAmount);
	    } else {
	        // Manejar el caso en que el cliente no tiene suficiente balance
	        System.out.println("El cliente no tiene suficiente balance para completar la compra.");
	    }
	}


	/**
	 * show all sales
	 */

	public void showSales() {
		System.out.println("Lista de ventas:");
		for (Sale sale : sales) {
			if (sale != null) {
				System.out.println(sale);
			}
		}

		Scanner scanner = new Scanner(System.in);
		System.out.println("¿Desea exportar todas las ventas a un archivo? (Y/N)");
		String respuesta = scanner.next().toLowerCase();
		if (respuesta.equals("y")) {
			exportSalesToFile();
		} else {
			System.out.println("No se pudo seleccionar correctamente la opción");
		}
	}

	private void exportSalesToFile() {
		LocalDate date = LocalDate.now();
		String fileName = "src/files/sales_yyyy-mm-dd.txt";

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
			int saleNumber = 1;
			for (Sale sale : sales) {
				StringBuilder sb = new StringBuilder();
				sb.append(saleNumber).append(";Client=").append(sale.getClient()).append(";");
				sb.append("Date=").append(sale.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))
						.append("; ");
				sb.append(saleNumber).append(";Products=");
				for (Product product : sale.getProducts()) {
					sb.append(product.getName()).append(",").append(product.getPublicPrice()).append(";");
				}
				sb.append(" ");
				sb.append(saleNumber).append(";Amount=").append(sale.getAmount()).append(";");
				writer.write(sb.toString());
				writer.newLine();
				saleNumber++;
				System.out.println("Exportación realizada con éxito");
			}
		} catch (IOException e) {
			System.out.println("Ocurrió un error al escribir en el archivo: " + e.getMessage());
		}
	}

	/*
	 * add a product to inventory
	 * 
	 * @param product
	 */
	public void addProduct(Product product) {
		inventory.add(product);
		numberProducts++;
	}

	// show the total amount of all the sales

	public void showTotalSale() {
		double totalAmount = 0;
		for (Sale sale : sales) {
			if (sale != null) {
				totalAmount += sale.getAmount().getValue();
			}
		}
		System.out.println("El valor total de todas las ventas es: " + totalAmount);
	}

	private void removeProduct() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Nombre del producto: ");
		String name = scanner.next();
		Product product = findProduct(name);

		if (product != null) {
			inventory.remove(product);
			System.out.println("Producto " + name + " ha sido eliminado del inventario");

		} else {
			System.out.println("No se ha encontrado el producto con nombre " + name);
		}

	}

	/**
	 * find product by name
	 * 
	 * @param product name
	 */
	public Product findProduct(String name) {
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i) != null && inventory.get(i).getName().equalsIgnoreCase(name)) {
				return inventory.get(i);
			}
		}
		return null;

	}

	public void loadFile() {
		String fileName = "src/files/inputInventory";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = reader.readLine()) != null) {
				// Separar
				String[] data = line.split(";");

				// Darle nombres a los datos recogidos
				String productName = data[0].split(":")[1];
				double wholesalerPrice = Double.parseDouble(data[1].split(":")[1]);
				int stock = Integer.parseInt(data[2].split(":")[1]);

				// Ponerlo en Product.java
				addProduct(new Product(productName, wholesalerPrice, true, stock));
			}
			reader.close();
			System.out.println("El inventario ha sido actualizado.");
		} catch (IOException message) {
			System.out.println("Hubo un error al cargar el inventario: " + message.getMessage());
		}
	}

	
	private void login() {
	    Scanner scanner = new Scanner(System.in);
	    boolean loginSuccessful = false;
	    while (!loginSuccessful) {
	        System.out.print("Ingrese número de empleado: ");
	        int employeeNumber = scanner.nextInt(); // Considerar agregar scanner.nextLine() si se encuentra un error con la entrada
	        System.out.print("Ingrese contraseña: ");
	        String password = scanner.next();
	        Employee employee = new Employee();
	        loginSuccessful = employee.login(employeeNumber, password);
	        if (!loginSuccessful) {
	            System.out.println("Datos incorrectos, intente nuevamente.");
	        } else {
	            System.out.println("Inicio de sesión exitoso.");
	        }
	    }
	}

}