package kiseleva.dev.SurveyManagementSystem.repos;

import kiseleva.dev.SurveyManagementSystem.entities.CommentEntity;
import kiseleva.dev.SurveyManagementSystem.entities.SurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepo extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findBySurveyId(Long surveyId);
}
