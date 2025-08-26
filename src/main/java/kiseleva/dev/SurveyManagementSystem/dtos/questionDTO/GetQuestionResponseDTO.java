package kiseleva.dev.SurveyManagementSystem.dtos.questionDTO;

import kiseleva.dev.SurveyManagementSystem.dtos.choiceDTO.GetChoiceResponseDTO;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class GetQuestionResponseDTO {
    public Long id;

    public String text;

    public Integer position;

    public String questionType;

    public List<GetChoiceResponseDTO> choices;
}
