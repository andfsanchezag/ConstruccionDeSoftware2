package app.adapter.out;

import org.springframework.stereotype.Service;

import app.domain.model.User;
import app.domain.ports.UserPort;

@Service
public class UserAdapter implements UserPort {

	@Override
	public User findByDocument(User user) throws Exception {
	
		return null;
	}

	@Override
	public User findByUserName(User user) throws Exception {
		return null;
	}

	@Override
	public void save(User user) throws Exception {
		System.out.println("se ha creado el usuario");
	}

}
