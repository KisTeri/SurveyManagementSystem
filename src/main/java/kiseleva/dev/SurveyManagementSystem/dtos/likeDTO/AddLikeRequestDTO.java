package kiseleva.dev.SurveyManagementSystem.dtos.likeDTO;

import kiseleva.dev.SurveyManagementSystem.dtos.surveyDTO.GetSurveyResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class AddLikeRequestDTO {

    private Instant createdAt;

    private GetSurveyResponseDTO survey;
}
