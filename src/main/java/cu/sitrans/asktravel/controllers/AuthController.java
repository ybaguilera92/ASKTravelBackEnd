package cu.sitrans.asktravel.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import cu.sitrans.asktravel.payload.request.LoginRequest;
import cu.sitrans.asktravel.payload.request.PasswordRequest;
import cu.sitrans.asktravel.payload.request.SignupRequest;
import cu.sitrans.asktravel.payload.request.TokenRefreshRequest;
import cu.sitrans.asktravel.payload.response.JwtResponse;
import cu.sitrans.asktravel.payload.response.MessageResponse;;
import cu.sitrans.asktravel.payload.response.ResponseHandler;
import cu.sitrans.asktravel.repositories.UserRepository;
import cu.sitrans.asktravel.service.AuthService;
import cu.sitrans.asktravel.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws IOException {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest, HttpServletRequest httpServletRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        return ResponseEntity.ok(authService.register(signUpRequest));
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUser(@Param("code") String code) {
        return ResponseEntity.ok(authService.verify(code));
    }
    @GetMapping("/verifyPassword")
    public ResponseEntity<?> recoverPassword(@Param("code") String code) {
        //System.out.println(code);
        return ResponseEntity.ok(authService.verifyPassword(code));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        return ResponseEntity.ok(authService.refreshToken(requestRefreshToken));
    }
    @PostMapping("/recoveryPassword")
    public ResponseEntity<?> recoveryPassword(@RequestBody PasswordRequest email, HttpServletRequest httpServletRequest) throws IOException{
        //System.out.println(email);
       /* String cadena="";
        int longitud=email.length();
        for(int i = 0; i < longitud; i ++){
            if(email.charAt(i)==':')
            {
                cadena=email.substring(i+2,longitud-2);
//                cadena=cadena.replace("\"","");
//                cadena=cadena.replace(" ","");
                break;
            }
        }*/
//        for(int i = 0; i < cadena.length(); i ++){
//            System.out.println(cadena.charAt(i));
//        }
      //  System.out.println(cadena);
        if (!userRepository.existsByEmail(email.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email no exist!"));
        }
        authService.recoveryPassword(email);
        return ResponseHandler.generateResponse("Se ha enviado la nueva contraseña a su correo electrónico", HttpStatus.OK,"" );
    }
}

