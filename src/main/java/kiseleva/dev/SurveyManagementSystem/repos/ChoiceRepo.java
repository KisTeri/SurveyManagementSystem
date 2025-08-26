package kiseleva.dev.SurveyManagementSystem.repos;

import kiseleva.dev.SurveyManagementSystem.entities.ChoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChoiceRepo extends JpaRepository<ChoiceEntity, Long> {
}
