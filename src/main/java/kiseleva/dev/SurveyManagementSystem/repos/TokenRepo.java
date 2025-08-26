package kiseleva.dev.SurveyManagementSystem.repos;

import kiseleva.dev.SurveyManagementSystem.entities.TokenEntity;
import kiseleva.dev.SurveyManagementSystem.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepo extends JpaRepository<TokenEntity, Long> {
    List<TokenEntity> findAllByUser(UserEntity user);
    Optional<TokenEntity> findByToken(String token);
}
