package kiseleva.dev.SurveyManagementSystem.services;

import kiseleva.dev.SurveyManagementSystem.dtos.answerDTO.AddAnswerRequestDTO;
import kiseleva.dev.SurveyManagementSystem.dtos.answerDTO.GetAnswerResponseDTO;

import java.util.List;

public interface AnswerService {
    GetAnswerResponseDTO createAnswer(AddAnswerRequestDTO dto);
    List<GetAnswerResponseDTO> getAnswersByQuestion(Long id);
}
