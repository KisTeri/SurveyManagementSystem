package kiseleva.dev.SurveyManagementSystem.dtos.surveyDTO;

import kiseleva.dev.SurveyManagementSystem.dtos.questionDTO.AddQuestionRequestDTO;
import lombok.Data;
import java.time.Instant;
import java.util.List;

@Data
public class AddSurveyRequestDTO {

    public String title;

    public String description;

    public Instant createdAt;

    public List<AddQuestionRequestDTO> questions;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public List<AddQuestionRequestDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<AddQuestionRequestDTO> questions) {
        this.questions = questions;
    }
}
