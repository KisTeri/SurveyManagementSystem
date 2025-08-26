package kiseleva.dev.SurveyManagementSystem.services;

import kiseleva.dev.SurveyManagementSystem.JwtUtil;
import kiseleva.dev.SurveyManagementSystem.TokenValidationException;
import kiseleva.dev.SurveyManagementSystem.dtos.userDTO.AuthResponseDTO;
import kiseleva.dev.SurveyManagementSystem.entities.TokenEntity;
import kiseleva.dev.SurveyManagementSystem.entities.TokenType;
import kiseleva.dev.SurveyManagementSystem.entities.UserEntity;
import kiseleva.dev.SurveyManagementSystem.repos.TokenRepo;
import kiseleva.dev.SurveyManagementSystem.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class JwtService {
    private final JwtUtil jwtUtil;
    private final TokenRepo tokenRepository;
    private final UserRepo userRepository;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    public JwtService(JwtUtil jwtUtil, TokenRepo tokenRepository, UserRepo userRepository, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.jwtUtil = jwtUtil;
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    public AuthResponseDTO issueTokensFor(UserEntity user, boolean revokeAll) {
        UserDetails ud = userDetailsServiceImpl.loadUserByUsername(user.getUsername());

        if (revokeAll) {
            revokeAllValidTokens(user);
        }

        String access = jwtUtil.generateAccessToken(ud);
        String refresh = jwtUtil.generateRefreshToken(ud);

        saveToken(user, access, TokenType.ACCESS);
        saveToken(user, refresh, TokenType.REFRESH);

        return new AuthResponseDTO(access, refresh);
    }

    public AuthResponseDTO refresh(String refreshToken) {

        if (!jwtUtil.isValid(refreshToken, TokenType.REFRESH)) throw new TokenValidationException("Invalid refresh token");
        String username = jwtUtil.extractUsername(refreshToken);

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new TokenValidationException("User not found"));

        TokenEntity stored = tokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new TokenValidationException("Refresh token not found"));

        if (stored.isExpired() || stored.isRevoked() || stored.getTokenType() != TokenType.REFRESH) {
            throw new TokenValidationException("Refresh token is not active");
        }

        stored.setRevoked(true);
        stored.setExpired(true);
        tokenRepository.save(stored);

        return issueTokensFor(user, false);
    }

    public void revokeAllValidTokens(UserEntity user) {
        List<TokenEntity> valid = tokenRepository.findAllByUser(user);
        valid.forEach(t -> { t.setExpired(true); t.setRevoked(true); });
        tokenRepository.saveAll(valid);
    }

    public void logout(String refreshToken) {
        if (!jwtUtil.isValid(refreshToken, TokenType.REFRESH)) {
            throw new TokenValidationException("Invalid refresh token");
        }
        tokenRepository.findByToken(refreshToken).ifPresent(t -> {
            t.setExpired(true);
            t.setRevoked(true);
            tokenRepository.save(t);
        });
    }

    private void saveToken(UserEntity user, String tokenValue, TokenType type) {
        TokenEntity token = new TokenEntity();
        token.setToken(tokenValue);
        token.setUser(user);
        token.setRevoked(false);
        token.setExpired(false);
        token.setTokenType(type);
        tokenRepository.save(token);
    }

    public boolean isTokenActiveInDb(String token) {
        return tokenRepository.findByToken(token)
                .filter(t -> !t.isExpired() && !t.isRevoked())
                .isPresent();
    }
}
