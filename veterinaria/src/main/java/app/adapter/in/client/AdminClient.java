package app.adapter.in.client;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import app.adapter.in.builder.UserBuilder;
import app.application.usecases.AdminUseCase;
import app.domain.model.User;

@Controller
public class AdminClient {

	private static final String MENU = "Ingrese una de las opciones \n 1. para crear veterinario \n 2. para crear vendedor \n 3. para salir";
	private static Scanner reader = new Scanner(System.in);
	@Autowired
	private AdminUseCase adminUseCase;
	@Autowired
	private UserBuilder userBuilder;

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
			switch (option) {
			case "1": {
				User user = readInfoFromUser();
				adminUseCase.createVeterinarian(user);
				return true;
			}
			case "2": {
				User user = readInfoFromUser();
				adminUseCase.createSeller(user);
				return true;
			}
			case "3": {
				System.out.println("hasta luego \n cerrando sesion");
				return false;
			}
			default: {
				System.out.println("ingrese una opcion valida");
				return true;
			}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return true;
		}
	}
	
	private User readInfoFromUser() throws Exception{
		System.out.println("ingrese el nombre de la persona");
		String name = reader.nextLine();
		System.out.println("ingrese la cedula de la persona");
		String document = reader.nextLine();
		System.out.println("ingrese el nombre de de usuario");
		String userName = reader.nextLine();
		System.out.println("ingrese la contraseña");
		String password = reader.nextLine();
		System.out.println("ingrese la edad de la persona");
		String age = reader.nextLine();
		return userBuilder.build(name, document, age, userName, password);
		
	}

}
