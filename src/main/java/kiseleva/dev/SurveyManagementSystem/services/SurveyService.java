package kiseleva.dev.SurveyManagementSystem.services;

import kiseleva.dev.SurveyManagementSystem.dtos.surveyDTO.AddSurveyRequestDTO;
import kiseleva.dev.SurveyManagementSystem.dtos.surveyDTO.GetSurveyResponseDTO;

import java.util.List;

public interface SurveyService {
    List<GetSurveyResponseDTO> getAllSurveys();
    GetSurveyResponseDTO getSurveyById(Long id);
    GetSurveyResponseDTO createSurvey(AddSurveyRequestDTO survey);
    void deleteSurvey(Long id);
}
