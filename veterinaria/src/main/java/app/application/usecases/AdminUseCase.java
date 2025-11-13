package app.application.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.model.User;
import app.domain.model.emuns.Role;
import app.domain.services.CreateUser;
@Service
public class AdminUseCase {
	@Autowired
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
