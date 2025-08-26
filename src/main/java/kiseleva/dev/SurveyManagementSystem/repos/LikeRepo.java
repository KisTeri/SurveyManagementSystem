package kiseleva.dev.SurveyManagementSystem.repos;

import kiseleva.dev.SurveyManagementSystem.entities.LikeEntity;
import kiseleva.dev.SurveyManagementSystem.entities.SurveyEntity;
import kiseleva.dev.SurveyManagementSystem.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepo extends JpaRepository<LikeEntity, Long> {
    boolean existsBySurveyAndUser(SurveyEntity survey, UserEntity user);
    Optional<LikeEntity> findBySurveyAndUser(SurveyEntity survey, UserEntity user);
}
