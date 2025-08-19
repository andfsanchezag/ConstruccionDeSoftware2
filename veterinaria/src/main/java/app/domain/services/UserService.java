package app.domain.services;

import app.domain.model.User;
import app.domain.ports.UserPort;

public class UserService {

	private UserPort userPort;

	public void createUser(User user) throws Exception {
		// validar que solo exista una persona con la cedula
		if (userPort.findByDocument(user) != null) {
			throw new Exception("ya existe una persona registrada con esa cedula");
		}

		// validar que solo exista un usuario con ese userName
		if (userPort.findByUserName(user) != null) {
			throw new Exception("ya existe una persona registrada con ese nombre de usuario");
		}
		userPort.save(user);
	}

}
