package app.application.usecases;

import app.domain.model.auth.AuthCredentials;
import app.domain.model.auth.TokenResponse;
import app.domain.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginUseCase {
    @Autowired
    private AuthenticationService authenticationService;

    public TokenResponse login(AuthCredentials credentials) throws Exception {
        return authenticationService.authenticate(credentials);
    }
}
