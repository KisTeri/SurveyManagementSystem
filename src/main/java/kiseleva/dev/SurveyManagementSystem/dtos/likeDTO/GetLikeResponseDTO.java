package kiseleva.dev.SurveyManagementSystem.dtos.likeDTO;

import kiseleva.dev.SurveyManagementSystem.dtos.userDTO.GetUserDTO;
import lombok.Data;

import java.time.Instant;

@Data
public class GetLikeResponseDTO {
    private Long id;

    private Instant createdAt;

    private Long surveyId;

    private GetUserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public GetUserDTO getUser() {
        return user;
    }

    public void setUser(GetUserDTO user) {
        this.user = user;
    }
}
