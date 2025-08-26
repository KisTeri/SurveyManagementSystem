package kiseleva.dev.SurveyManagementSystem.services;

import kiseleva.dev.SurveyManagementSystem.dtos.likeDTO.GetLikeResponseDTO;

public interface LikeService {
    GetLikeResponseDTO likeSurvey(Long Id);
    void unlikeSurvey(Long Id);

}
