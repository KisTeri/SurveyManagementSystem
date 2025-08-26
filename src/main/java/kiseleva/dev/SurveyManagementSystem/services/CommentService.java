package kiseleva.dev.SurveyManagementSystem.services;

import kiseleva.dev.SurveyManagementSystem.dtos.commentDTO.AddCommentRequestDTO;
import kiseleva.dev.SurveyManagementSystem.dtos.commentDTO.GetCommentResponseDTO;
import java.util.List;

public interface CommentService {
    List<GetCommentResponseDTO> getAllComments(Long Id);
    GetCommentResponseDTO addComment(Long Id, AddCommentRequestDTO comment);
    void deleteComment(Long surveyId, Long commentId);
}
