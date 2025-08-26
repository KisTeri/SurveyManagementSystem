package kiseleva.dev.SurveyManagementSystem.controllers;

import kiseleva.dev.SurveyManagementSystem.dtos.userDTO.AuthResponseDTO;
import kiseleva.dev.SurveyManagementSystem.dtos.userDTO.RefreshRequestDTO;
import kiseleva.dev.SurveyManagementSystem.dtos.userDTO.UserLoginRequestDTO;
import kiseleva.dev.SurveyManagementSystem.dtos.userDTO.UserRegisterRequestDTO;
import kiseleva.dev.SurveyManagementSystem.services.AuthService;
import kiseleva.dev.SurveyManagementSystem.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody UserRegisterRequestDTO req) {
        return ResponseEntity.ok(authService.register(req));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody UserLoginRequestDTO req) {
        return ResponseEntity.ok(authService.login(req));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refresh(@RequestBody RefreshRequestDTO req) {
        return ResponseEntity.ok(jwtService.refresh(req.getRefreshToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody RefreshRequestDTO req) {
        jwtService.logout(req.getRefreshToken());
        return ResponseEntity.noContent().build();
    }
}
