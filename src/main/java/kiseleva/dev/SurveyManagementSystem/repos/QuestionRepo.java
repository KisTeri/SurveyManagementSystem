package kiseleva.dev.SurveyManagementSystem.repos;

import kiseleva.dev.SurveyManagementSystem.entities.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepo extends JpaRepository<QuestionEntity, Long> {
}
