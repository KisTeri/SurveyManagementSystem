package kiseleva.dev.SurveyManagementSystem.repos;

import kiseleva.dev.SurveyManagementSystem.entities.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepo extends JpaRepository<AnswerEntity, Long> {
    List<AnswerEntity> findByQuestionId(Long questionId);
}
