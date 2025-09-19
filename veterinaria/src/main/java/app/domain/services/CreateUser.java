package app.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.application.exceptions.BusinessException;
import app.domain.model.User;
import app.domain.model.emuns.Role;
import app.domain.ports.UserPort;

@Service
public class CreateUser {

	@Autowired
	private UserPort userPort;

	public void create(User user) throws Exception {
		if (userPort.findByDocument(user) != null) {
			throw new BusinessException("ya existe una persona registrada con esa cedula");
		}

		if (!user.getRole().equals(Role.OWNER) && userPort.findByUserName(user) != null) {
			throw new BusinessException("ya existe una persona registrada con ese nombre de usuario");
		}
		userPort.save(user);
	}

}
