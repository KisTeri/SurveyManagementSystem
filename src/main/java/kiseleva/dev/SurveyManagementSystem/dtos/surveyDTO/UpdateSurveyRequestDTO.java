package kiseleva.dev.SurveyManagementSystem.dtos.surveyDTO;

import kiseleva.dev.SurveyManagementSystem.dtos.questionDTO.GetQuestionResponseDTO;
import kiseleva.dev.SurveyManagementSystem.entities.QuestionEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateSurveyRequestDTO {

    public String title;

    public   String description;

    public List<GetQuestionResponseDTO> questions;
}
