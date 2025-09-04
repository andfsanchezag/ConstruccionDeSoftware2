package app.infrastructure.persistence.mapper;


import app.domain.model.User;
import app.domain.model.emuns.Role;
import app.infrastructure.persistence.entities.UserEntity;

public class UserMapper {

    public static UserEntity toEntity(User user) {
        if (user == null) return null;
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setDocument(user.getDocument());
        entity.setAge(user.getAge());
        entity.setRole(user.getRole().name());
        entity.setUserName(user.getUserName());
        entity.setPassword(user.getPassword());
        return entity;
    }

    public static User toDomain(UserEntity entity) {
        if (entity == null) return null;
        User user = new User();
        user.setId(entity.getId());
        user.setName(entity.getName());
        user.setDocument(entity.getDocument());
        user.setAge(entity.getAge());
        user.setRole(Role.valueOf(entity.getRole()));
        user.setUserName(entity.getUserName());
        user.setPassword(entity.getPassword());
        return user;
    }
}
