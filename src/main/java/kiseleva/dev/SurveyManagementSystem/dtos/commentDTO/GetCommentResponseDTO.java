package kiseleva.dev.SurveyManagementSystem.dtos.commentDTO;

import kiseleva.dev.SurveyManagementSystem.dtos.userDTO.GetUserDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class GetCommentResponseDTO {
    public Long id;

    public String text;

    public Instant createdAt;

    public GetUserDTO user;
}
