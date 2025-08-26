package kiseleva.dev.SurveyManagementSystem.dtos.questionDTO;

import kiseleva.dev.SurveyManagementSystem.dtos.choiceDTO.AddChoiceRequestDTO;
import lombok.Data;

import java.util.List;

@Data
public class AddQuestionRequestDTO {

    private Long id;

    private String text;

    private Integer position;

    private String questionType;

    private List<AddChoiceRequestDTO> choices;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public List<AddChoiceRequestDTO> getChoices() {
        return choices;
    }

    public void setChoices(List<AddChoiceRequestDTO> choices) {
        this.choices = choices;
    }
}
