package kiseleva.dev.SurveyManagementSystem.repos;

import kiseleva.dev.SurveyManagementSystem.dtos.surveyDTO.GetSurveyResponseDTO;
import kiseleva.dev.SurveyManagementSystem.entities.SurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SurveyRepo extends JpaRepository<SurveyEntity, Long> {
    Optional<GetSurveyResponseDTO> findByTitle(String title);
    boolean existsByTitle(String title);

    @Query("SELECT s FROM SurveyEntity s LEFT JOIN s.likes l GROUP BY s.id ORDER BY COUNT(l) DESC")
    List<SurveyEntity> findTopByLikes();

    @Query("SELECT s FROM SurveyEntity s LEFT JOIN s.comments c GROUP BY s.id ORDER BY COUNT(c) DESC")
    List<SurveyEntity> findTopByComments();
}
