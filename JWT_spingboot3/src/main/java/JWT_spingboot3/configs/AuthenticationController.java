package JWT_spingboot3.configs;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import JWT_spingboot3.entity.User;
import JWT_spingboot3.models.LoginResponse;
import JWT_spingboot3.models.LoginUserModel;
import JWT_spingboot3.models.RegisterUserModel;
import JWT_spingboot3.services.AuthenticationService;
import JWT_spingboot3.services.JwtService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    @Transactional
    public ResponseEntity<User> register(@RequestBody RegisterUserModel registerUser) {
        User registeredUser = authenticationService.signup(registerUser);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping(path = "/login")
    @Transactional
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserModel loginUser) {
        User authenticatedUser = authenticationService.authenticate(loginUser);

        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse res = new LoginResponse();
        res.setToken(jwtToken);
        res.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(res);
    }
}