package kiseleva.dev.SurveyManagementSystem.dtos.commentDTO;

import kiseleva.dev.SurveyManagementSystem.dtos.surveyDTO.GetSurveyResponseDTO;
import lombok.Data;

import java.time.Instant;

@Data
public class AddCommentRequestDTO {
    public String text;

    public Instant createdAt;

    public GetSurveyResponseDTO survey;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public GetSurveyResponseDTO getSurvey() {
        return survey;
    }

    public void setSurvey(GetSurveyResponseDTO survey) {
        this.survey = survey;
    }
}
