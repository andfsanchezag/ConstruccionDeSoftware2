package app.adapter.in.client;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import app.adapter.in.builder.ClinicalOrderBuilder;
import app.adapter.in.builder.ClinicalRecordBuilder;
import app.adapter.in.builder.PetBuilder;
import app.adapter.in.builder.UserBuilder;
import app.application.usecases.VeterinarianUseCase;
import app.domain.model.ClinicalOrder;
import app.domain.model.ClinicalRecord;
import app.domain.model.Pet;
import app.domain.model.User;

@Controller
public class VeterinarianClient {

	private static final String MENU = "Ingrese una opcion \n" + "1. para crear dueño de mascota.\n"
			+ "2. para crear mascota.\n" + "3. para crear orden \n" + "4. para registrar historia \n"
			+ "5. para anular orden\n" + "6. para consultar ordenes. \n" + "7. para cerrar sesion.";

	@Autowired
	private VeterinarianUseCase veterinarianUseCase;
	@Autowired
	private UserBuilder userBuilder;
	@Autowired
	private PetBuilder petBuilder;
	@Autowired
	private ClinicalOrderBuilder clinicalOrderBuilder;
	@Autowired
	private ClinicalRecordBuilder clinicalRecordBuilder;
	private static Scanner reader = new Scanner(System.in);

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
			User user = readOwnerData();
			veterinarianUseCase.CreateOwner(user);
			return true;
		}
		case "2": {
			Pet pet = readPetData();
			veterinarianUseCase.CreatePet(pet);
			return true;
		}
		case "3": {
			ClinicalOrder clinicalOrder = readOrderData();
			veterinarianUseCase.createOrder(clinicalOrder);
			return true;
		}
		case "4": {
			ClinicalRecord clinicalRecord = readClinicalRecordData();
			veterinarianUseCase.createClinicalRecord(clinicalRecord);
			return true;
		}
		case "5": {

			return true;
		}
		case "6": {

			return true;
		}
		case "7": {

			return false;
		}
		default: {
			System.out.println("Ingrese una opcion valida");
			return true;
		}
		}
	}

	private ClinicalRecord readClinicalRecordData() throws Exception{
		System.out.println("ingrese la cedula del veterinario que la genera");
		String veterinarian = reader.nextLine();
		System.out.println("ingrese el id de la mascota");
		String petId = reader.nextLine();
		System.out.println("ingrese el motivo de la consulta");
		String motive = reader.nextLine();
		System.out.println("ingrese el diagnostico");
		String diagnosis = reader.nextLine();
		System.out.println("la medicina asignada");
		String medicine = reader.nextLine();
		System.out.println("ingrese la dosis del medicamento");
		String doce = reader.nextLine();
		System.out.println("ingrese el nombre del procedimiento");
		String procedureName = reader.nextLine();
		System.out.println("ingrese la cedula del veterinario que la genera");
		String procedureDetails = reader.nextLine();
		System.out.println("ingrese el id de la orden");
		String orderId = reader.nextLine();
		System.out.println("ingrese las alergias");
		String allergies = reader.nextLine();
		System.out.println("ingrese sintomas");
		String symptoms = reader.nextLine();
		System.out.println("ingrese las vacunas");
		String vaccination = reader.nextLine();
		
		return clinicalRecordBuilder.create(veterinarian, petId, orderId);
	}

	private ClinicalOrder readOrderData() throws Exception {
		System.out.println("ingrese la cedula del veterinario que la genera");
		String veterinarian = reader.nextLine();
		System.out.println("ingrese el id de la mascota");
		String id = reader.nextLine();
		System.out.println("ingrese el nombre de la medicina");
		String medicine = reader.nextLine();
		System.out.println("ingrese la dosis");
		String doce = reader.nextLine();
		return clinicalOrderBuilder.builder(veterinarian, id, medicine, doce);

	}

	private Pet readPetData() throws Exception {
		System.out.println("ingrese la cedula del dueño");
		String document = reader.nextLine();
		System.out.println("ingrese el nombre de la mascota");
		String name = reader.nextLine();
		System.out.println("ingrese la edad de la mascota");
		String age = reader.nextLine();
		System.out.println("ingrese el peso");
		String weigth = reader.nextLine();
		System.out.println("ingrese la especie");
		String spices = reader.nextLine();
		System.out.println("ingrese las caracteristicas");
		String features = reader.nextLine();
		System.out.println("ingrese la raza");
		String breed = reader.nextLine();

		return petBuilder.builder(document, name, age, weigth, spices, features, breed);
	}

	private User readOwnerData() throws Exception {
		System.out.println("ingrese el nombre del dueño");
		String name = reader.nextLine();
		System.out.println("ingrese la cedula del dueño");
		String document = reader.nextLine();
		System.out.println("ingrese la edad del dueño");
		String age = reader.nextLine();
		return userBuilder.build(name, document, age, "N/A", "N/A");
	}
}
