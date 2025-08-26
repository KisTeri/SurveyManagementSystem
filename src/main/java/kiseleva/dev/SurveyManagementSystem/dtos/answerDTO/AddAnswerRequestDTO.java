package kiseleva.dev.SurveyManagementSystem.dtos.answerDTO;

import lombok.Data;
import java.util.List;

@Data
public class AddAnswerRequestDTO {

    private String answer;

    private List<Long> selectedChoices;

    private Long questionId;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<Long> getSelectedChoices() {
        return selectedChoices;
    }

    public void setSelectedChoices(List<Long> selectedChoices) {
        this.selectedChoices = selectedChoices;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
}
