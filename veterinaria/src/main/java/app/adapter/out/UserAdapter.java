package app.adapter.out;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.model.User;
import app.domain.ports.UserPort;
import app.infrastructure.persistence.entities.UserEntity;
import app.infrastructure.persistence.mapper.UserMapper;
import app.infrastructure.persistence.repository.UserRepository;

@Service
public class UserAdapter implements UserPort {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User findByDocument(User user) throws Exception {
		UserEntity userEntity = userRepository.findByDocument(user.getDocument());
		return UserMapper.toDomain(userEntity);
	}

	@Override
	public User findByUserName(User user) throws Exception {
		UserEntity userEntity = userRepository.findByUserName(user.getUserName());
		return UserMapper.toDomain(userEntity);
	}

	@Override
	public void save(User user) throws Exception {
		userRepository.save(UserMapper.toEntity(user));
	}

}
