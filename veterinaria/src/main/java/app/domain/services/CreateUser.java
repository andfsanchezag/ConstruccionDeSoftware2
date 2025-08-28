package app.domain.services;

import app.domain.model.User;
import app.domain.model.emuns.Role;
import app.domain.ports.UserPort;

public class CreateUser {

	private UserPort userPort;

	public void create(User user) throws Exception {
		if (userPort.findByDocument(user) != null) {
			throw new Exception("ya existe una persona registrada con esa cedula");
		}

		if (!user.getRole().equals(Role.OWNER) && userPort.findByUserName(user) != null) {
			throw new Exception("ya existe una persona registrada con ese nombre de usuario");
		}
		userPort.save(user);
	}

}
