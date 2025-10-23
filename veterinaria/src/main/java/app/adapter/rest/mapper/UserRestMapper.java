package app.adapter.rest.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.adapter.in.builder.UserBuilder;
import app.adapter.rest.request.CreateUserRequest;
import app.adapter.rest.response.UserResponse;
import app.domain.model.User;

@Component
public class UserRestMapper {

    @Autowired
    private UserBuilder userBuilder;

    public User toDomain(CreateUserRequest req) throws Exception {
        return userBuilder.build(
            req.getName(),
            req.getDocument(),
            req.getAge(),
            req.getUserName(),
            req.getPassword()
        );
    }

    public UserResponse toResponse(User user) {
        UserResponse res = new UserResponse();
        res.setId(user.getId());
        res.setName(user.getName());
        res.setDocument(user.getDocument());
        res.setAge(user.getAge());
        res.setUserName(user.getUserName());
        res.setRole(user.getRole() != null ? String.valueOf(user.getRole()) : null);
        return res;
    }
}
