package app.adapter.in.client;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import app.adapter.in.builder.InvoiceBuilder;
import app.application.usecases.SellerUseCase;
import app.domain.model.Invoice;

@Controller
public class SellerClient {

	private static final String MENU = "Ingrese la opcion que desea:" + " \n 1. para crear factura"
			+ " \n 2. para ver ordenes de una mascota " + "\n 3. para cerrar sesion";
	private static Scanner reader = new Scanner(System.in);
	@Autowired
	private SellerUseCase sellerCaseUse;
	@Autowired
	private InvoiceBuilder invoiceBuilder;

	public void session() {
		boolean session = true;
		while (session) {
			session = menu();
		}
	}

	private boolean menu() {
		try {
			System.out.println(MENU);
			String option = reader.nextLine();
			return options(option);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return true;
		}
	}

	private boolean options(String option) throws Exception {
		switch (option) {
		case "1": {
			Invoice invoice = readInvoiceData();
			sellerCaseUse.CreateInvoice(invoice);
			return true;
		}
		case "2": {
			return true;
		}
		case "3": {
			return false;
		}
		default: {
			return true;
		}
		}
	}

	private Invoice readInvoiceData() throws Exception {

		System.out.println("ingrese el id de la mascota");
		String petId = reader.nextLine();
		System.out.println("ingrese la cedula del dueño");
		String ownerDocument = reader.nextLine();
		System.out.println("ingrese el nombre del producto");
		String productName = reader.nextLine();
		System.out.println("ingrese el precio");
		String amount = reader.nextLine();
		System.out.println("ingrese si en caso de ser un medicamento");
		String isMedicine= reader.nextLine();
		System.out.println("ingrese el id de la orden en caso de ser un medicamento");
		String orderId= reader.nextLine();
		
		return invoiceBuilder.build(petId, ownerDocument, amount, productName, isMedicine, orderId);
	}

}
