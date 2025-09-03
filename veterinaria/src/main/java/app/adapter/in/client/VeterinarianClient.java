package app.adapter.in.client;

import java.util.Scanner;

import app.adapter.in.builder.UserBuilder;
import app.application.usecases.VeterinarianUseCase;
import app.domain.model.Pet;
import app.domain.model.User;

public class VeterinarianClient {

	private static final String MENU = "Ingrese una opcion \n" + "1. para crear dueño de mascota.\n"
			+ "2. para crear mascota.\n" + "3. para crear orden \n" + "4. para registrar historia \n"
			+ "5. para anular orden\n" + "6. para consultar ordenes. \n" + "7. para cerrar sesion.";

	private VeterinarianUseCase veterinarianUseCase;
	private UserBuilder userBuilder;
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

			return true;
		}
		case "4": {

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
			return true;
		}
		}
	}

	private Pet readPetData() {
		// TODO Auto-generated method stub
		return null;
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
