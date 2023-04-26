package cu.sitrans.asktravel.service;

import cu.sitrans.asktravel.models.User;
import cu.sitrans.asktravel.payload.request.LoginRequest;
import cu.sitrans.asktravel.payload.request.PasswordRequest;
import cu.sitrans.asktravel.payload.request.SignupRequest;
import cu.sitrans.asktravel.payload.response.JwtResponse;
import cu.sitrans.asktravel.payload.response.TokenRefreshResponse;
import org.springframework.security.authentication.BadCredentialsException;

import java.io.IOException;


public interface AuthService {
    JwtResponse login(LoginRequest loginRequest) throws IOException;
    JwtResponse register(SignupRequest signupRequest);

    void recoveryPassword(PasswordRequest email) throws IOException;
    boolean verify (String verificationCode);

    boolean verifyPassword(String verificationCode);

    TokenRefreshResponse refreshToken (String requestRefreshToken);
}
