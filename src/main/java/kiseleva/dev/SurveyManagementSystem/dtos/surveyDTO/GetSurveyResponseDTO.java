package kiseleva.dev.SurveyManagementSystem.dtos.surveyDTO;

import kiseleva.dev.SurveyManagementSystem.dtos.commentDTO.GetCommentResponseDTO;
import kiseleva.dev.SurveyManagementSystem.dtos.likeDTO.GetLikeResponseDTO;
import kiseleva.dev.SurveyManagementSystem.dtos.questionDTO.GetQuestionResponseDTO;
import kiseleva.dev.SurveyManagementSystem.dtos.userDTO.GetUserDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Data
@Getter
@Setter
public class GetSurveyResponseDTO {

    public Long id;

    public String title;

    public String description;

    public Instant createdAt;

    public List<GetQuestionResponseDTO> questions;

    public List<GetCommentResponseDTO> comments;

    public List<GetLikeResponseDTO> likes;

    private GetUserDTO createdBy;

}
