package kiseleva.dev.SurveyManagementSystem.dtos.choiceDTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class GetChoiceResponseDTO {
    public Long id;
    public String text;

}
