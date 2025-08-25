package app.application.usecases;

import app.domain.model.User;
import app.domain.model.emuns.Role;
import app.domain.services.CreateUser;

public class AdminUseCase {

	private CreateUser createUser;

	public void createVeterinarian(User user) throws Exception {
		user.setRole(Role.VETERINARIAN);
		createUser.create(user);
	}
	
	public void createSeller(User user) throws Exception {
		user.setRole(Role.SELLER);
		createUser.create(user);
	}

}
