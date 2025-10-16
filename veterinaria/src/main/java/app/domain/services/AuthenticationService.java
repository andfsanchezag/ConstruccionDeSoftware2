package app.domain.services;

import app.domain.model.User;
import app.domain.model.auth.AuthCredentials;
import app.domain.model.auth.TokenResponse;
import app.domain.ports.AuthenticationPort;
import app.domain.ports.UserPort;
import app.application.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    
    @Autowired
    private AuthenticationPort authenticationPort;
    
    @Autowired
    private UserPort userPort;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public TokenResponse authenticate(AuthCredentials credentials) {
        User user = this.getUserByUsername(credentials.getUsername());
        this.validatePassword(credentials.getPassword(), user.getPassword());
        return authenticationPort.authenticate(credentials, user.getRole());
    }

    private User getUserByUsername(String username) {
        User user = userPort.findByUsername(username);
        if (user == null) {
            throw new BusinessException("Usuario no encontrado");
        }
        return user;
    }

    private void validatePassword(String inputPassword, String storedPassword) {
        if (!passwordEncoder.matches(inputPassword, storedPassword)) {
            throw new BusinessException("Contraseña incorrecta");
        }
    }
}