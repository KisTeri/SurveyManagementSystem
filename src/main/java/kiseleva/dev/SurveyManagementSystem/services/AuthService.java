package kiseleva.dev.SurveyManagementSystem.services;

import jakarta.transaction.Transactional;
import kiseleva.dev.SurveyManagementSystem.dtos.userDTO.AuthResponseDTO;
import kiseleva.dev.SurveyManagementSystem.dtos.userDTO.UserLoginRequestDTO;
import kiseleva.dev.SurveyManagementSystem.dtos.userDTO.UserRegisterRequestDTO;
import kiseleva.dev.SurveyManagementSystem.entities.UserEntity;
import kiseleva.dev.SurveyManagementSystem.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class AuthService {
    private final UserRepo userRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    @Autowired
    public AuthService(UserRepo userRepository, PasswordEncoder encoder, AuthenticationManager authManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    @Transactional
    public AuthResponseDTO register(UserRegisterRequestDTO dto) {
        if (userRepository.existsByUsername(dto.getUsername()))
            throw new IllegalArgumentException("Такое имя пользователя уже существует");
        if (userRepository.existsByEmail(dto.getEmail()))
            throw new IllegalArgumentException("Такая электронная почта уже существует");

        UserEntity user = new UserEntity();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setRoles(Set.of("ROLE_USER"));
        userRepository.save(user);

        AuthResponseDTO tokens = jwtService.issueTokensFor(user, false);
        return tokens;
    }

    @Transactional
    public AuthResponseDTO login(UserLoginRequestDTO dto) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );
        UserEntity user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
        return jwtService.issueTokensFor(user, true);
    }

}
