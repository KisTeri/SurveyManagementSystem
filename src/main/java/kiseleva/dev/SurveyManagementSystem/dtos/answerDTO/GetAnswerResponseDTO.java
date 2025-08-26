package kiseleva.dev.SurveyManagementSystem.dtos.answerDTO;

import lombok.Data;
import java.time.Instant;
import java.util.List;

@Data
public class GetAnswerResponseDTO {
    private Long id;

    private String answer;

    private Instant createdAt;

    private List<Long> selectedChoices;

    private Long questionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
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
