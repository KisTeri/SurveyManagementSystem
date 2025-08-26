package kiseleva.dev.SurveyManagementSystem.services;

import kiseleva.dev.SurveyManagementSystem.dtos.answerDTO.AddAnswerRequestDTO;
import kiseleva.dev.SurveyManagementSystem.dtos.answerDTO.GetAnswerResponseDTO;
import kiseleva.dev.SurveyManagementSystem.dtos.choiceDTO.AddChoiceRequestDTO;
import kiseleva.dev.SurveyManagementSystem.dtos.choiceDTO.GetChoiceResponseDTO;
import kiseleva.dev.SurveyManagementSystem.dtos.commentDTO.GetCommentResponseDTO;
import kiseleva.dev.SurveyManagementSystem.dtos.likeDTO.GetLikeResponseDTO;
import kiseleva.dev.SurveyManagementSystem.dtos.questionDTO.AddQuestionRequestDTO;
import kiseleva.dev.SurveyManagementSystem.dtos.questionDTO.GetQuestionResponseDTO;
import kiseleva.dev.SurveyManagementSystem.dtos.surveyDTO.AddSurveyRequestDTO;
import kiseleva.dev.SurveyManagementSystem.dtos.surveyDTO.GetSurveyForRatingDTO;
import kiseleva.dev.SurveyManagementSystem.dtos.surveyDTO.GetSurveyResponseDTO;
import kiseleva.dev.SurveyManagementSystem.dtos.userDTO.GetUserDTO;
import kiseleva.dev.SurveyManagementSystem.entities.*;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@org.mapstruct.Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IMapper {
    GetSurveyResponseDTO toSurveyDTO(SurveyEntity survey);
    List<GetSurveyResponseDTO> toSurveyDTOList(List<SurveyEntity> surveys);
    SurveyEntity toSurveyEntity(AddSurveyRequestDTO addSurveyDTO);
    GetSurveyForRatingDTO toSurveyForRatingDTO(SurveyEntity survey);

    @Mapping(source = "survey.id", target = "surveyId")
    @Mapping(source = "user", target = "user")
    GetLikeResponseDTO toLikeResponseDTO (LikeEntity like);


    List<GetCommentResponseDTO> toCommentResponseDTOList (List<CommentEntity> comments);
    GetCommentResponseDTO toCommentResponseDTO (CommentEntity comment);


    QuestionEntity toQuestionEntity(AddQuestionRequestDTO qDto);
    List<QuestionEntity> questionEntityListMap(List<AddQuestionRequestDTO> qdtos);
    GetQuestionResponseDTO toQuestionDTO(QuestionEntity question);


    ChoiceEntity toChoiceEntity(AddChoiceRequestDTO cDto);
    List<ChoiceEntity> toChoiceDTOList (List<AddChoiceRequestDTO> choices);
    //AddChoiceRequestDTO toChoiceRequestDTO(ChoiceEntity cEntity);
    @Mapping(source = "choice.id", target = "id")
    @Mapping(source = "choice.text", target = "text")
    GetChoiceResponseDTO toChoiceDTO(ChoiceEntity choice);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "question", ignore = true)
    @Mapping(target = "selectedChoices", ignore = true)
    AnswerEntity toAnswerEntity(AddAnswerRequestDTO dto);

    @Mapping(source = "question.id", target = "questionId")
    @Mapping(source = "selectedChoices", target = "selectedChoices", qualifiedByName = "mapChoiceIds")
    @Mapping(source = "answer", target = "answer")
    GetAnswerResponseDTO toAnswerResponseDTO(AnswerEntity answer);

    GetUserDTO toUserDTO (UserEntity user);

    @Mapping(target = "createdAt", ignore = true)
    void updateSurveyFromDto(AddSurveyRequestDTO dto, @MappingTarget SurveyEntity entity);

    void updateQuestionFromDto(AddQuestionRequestDTO dto, @MappingTarget QuestionEntity entity);

    void updateChoiceFromDto(AddChoiceRequestDTO dto, @MappingTarget ChoiceEntity entity);

    @Named("mapChoiceIds")
    default List<Long> mapChoiceIds(List<ChoiceEntity> choices) {
        if (choices == null) return null;
        return choices.stream().map(ChoiceEntity::getId).toList();
    }
}
