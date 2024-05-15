package main;

import model.*;
import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;

public class Shop {
	public static Amount cash = new Amount(100);
	private static ArrayList<Product> inventory = new ArrayList<Product>();
	private static ArrayList<Sale> sales = new ArrayList<Sale>();
	private int Stock = 0;
	final static double TAX_RATE = 1.04;

	public static void main(String[] args) {
		Shop shop = new Shop();

		shop.loadInventory();
		while(!shop.initSesion()) {
			continue;
		}
		
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
			System.out.println("8) Eliminar producto");
			System.out.println("9) Ver valor total de ventas");
			System.out.println("10) Salir programa");
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
				shop.confirmDeleteProduct(null);
				break;

			case 9:
				shop.showTotalSale();
				break;

			case 10:
				exit = true;
				break;
			}

		} while (!exit);
		System.out.println("Gracias por comprar en miTienda.com");
	}

	/**
	 * load initial inventory to shop
	 */
	public void loadInventory() {
		Path filePath = Paths.get("src//files", "inputInventory.txt");

		try

		{
			String content = Files.readString(filePath);
			// reading file from given path
			String[] list = content.split(";");
			for (int i = 0; i < list.length - 1; i += 3) {
				String[] name = list[i].split(":");
				String[] wholeaSalerPrice = list[i + 1].split(":");
				String[] stock = list[i + 2].split(":");
				 Product result  = findProduct(name[1]);
					if(alreadyExists(name[1]) == false) {						
						addProduct(new Product(name[1], Double.valueOf(wholeaSalerPrice[1]), true, Integer.valueOf(stock[1])));
					}else {
						System.out.println("Producto con mismo nombre");
					}
				

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * show current total cash
	 */
	public static void showCash() {
		System.out.println(String.valueOf(cash));
	}

	/**
	 * add a new product to inventory getting data from console
	 */
	public void addProduct() {

		Scanner scanner = new Scanner(System.in);
		System.out.print("Nombre: ");
		String name = scanner.nextLine();
		if(alreadyExists(name) == false) {
		System.out.print("Precio mayorista: ");
		double wholesalerPrice = scanner.nextDouble();
		System.out.print("Stock: ");
		int stock = scanner.nextInt();
		addProduct(new Product(name, wholesalerPrice, true, stock));
		}else {
			System.out.println("Nombre del producto ya existe");
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
		product.expire();
		if (product != null) {
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
		// ask for client name
		ArrayList<Product> products = new ArrayList<Product>();
		Scanner sc = new Scanner(System.in);
		System.out.println("Realizar venta, escribir nombre cliente");
		String nameClient = sc.nextLine();
		// sale product until input name is not 0
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
				// if no more stock, set as not available to sale
				if (product.getStock() == 0) {
					product.setAvailable(false);
				}
				products.add(product);
				System.out.println("Producto añadido con éxito");
			}

			if (!productAvailable) {
				System.out.println("Producto no encontrado o sin stock");
			}
		}
		LocalDateTime myDateObj = LocalDateTime.now();
		Client client = new Client (nameClient);
		Amount total = new Amount (totalAmount);
		total.setValue(total.getValue() * TAX_RATE);
		sales.add(new Sale(client, products, total, myDateObj));
		// show cost total
		
		cash.setValue(total.getValue() + cash.getValue());
		System.out.println("Venta realizada con éxito, total: " + total);
		if (!client.pay(total)) {
			System.out.println("El cliente debe: " + client.getBalance());
		}
	}

	/**
	 * show all sales
	 */
	private void showSales() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Lista de ventas:");
		double totalSaleAmount = 0;
		int counter = 0;
		for (Sale sale : sales) {
			if (sale != null) {
				System.out.println(sale);
				totalSaleAmount += sale.getAmount().getValue();
			}
		}

		System.out.println("Quieres guardarlo en un archivo? (Y/N)");
		String respuesta = scanner.next();
		if (respuesta.equals("Y")) {
			String content = "";

			DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			for (Sale sale : sales) {
				if (sale != null) {
					counter++;
					content += counter + ";Client=" + sale.getClient() + ";Date=" + sale.getTime().format(myFormatObj)
							+ ";\n" + counter + ";Products=";

					for (Product product : sale.getProducts()) {
						content += product.getName() + "," + product.getPublicPrice() + ";";
					}

					content += "\n" + counter + ";Amount=" + sale.getAmount() + ";\n";

				}
			}

			LocalDate date = LocalDate.now();
			try {
				try {
					File file = new File("src\\files\\" + counter + "_" + date + ".txt");
					if (file.createNewFile()) {
						System.out.println("File created: " + file.getPath());
					} else {
						System.out.println("File already exists.");
					}
				} catch (IOException e) {
					System.out.println("An error occurred.");
					e.printStackTrace();
				}
				PrintWriter out = new PrintWriter(counter + "_" + date + ".txt");
				out.println(content);
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}
	}

	/*
	 * add a product to inventory
	 * 
	 * @param product
	 */
	public void addProduct(Product product) {

		inventory.add(product);
	}

	/**
	 * check if inventory is full or not
	 */

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

	/**
	 * find product by name
	 * 
	 * @param product name
	 */
	public Product findProduct (String name) {

		Product product;

		for (Iterator iterator = inventory.iterator(); iterator.hasNext();) {
			product = (Product) iterator.next();

			if (product.getName().equalsIgnoreCase(name)) {
				return product;
			}
		}
		return null;
	}

	public void confirmDeleteProduct(String name) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Seleccione un nombre de producto:");
		String name2 = scanner.next();
		Product product = findProduct(name2);
		if (product != null) {
			System.out.println("Producto eliminado");
		} else {
			System.out.println("Prducto no encontrado");
		}

	}
	
	/*
	 * add a product to inventory
	 * 
	 * @param product
	 */
	public void deleteProduct(Product product) {
		inventory.remove(product);
	}
	
	public boolean alreadyExists(String nombre) {
		if (findProduct(nombre) != null) {
			return true;
		}else {
			return false;
		}
	}
	public boolean initSesion() {
		Scanner scanner = new Scanner (System.in);
		System.out.println("Introduzca el numero de empleado");
		int user = scanner.nextInt();
		System.out.println("Introduzca la contraseña de empleado");
		String password = scanner.next();
		Employee employee = new Employee(null); 
		if (employee.login(user, password)){
			return true;
		}
		else {
		System.out.println("Datos incorrectos");
		return false;
		}
	}
}