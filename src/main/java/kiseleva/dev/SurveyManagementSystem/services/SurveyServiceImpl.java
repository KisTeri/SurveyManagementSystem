package kiseleva.dev.SurveyManagementSystem.services;

import jakarta.transaction.Transactional;
import kiseleva.dev.SurveyManagementSystem.dtos.choiceDTO.AddChoiceRequestDTO;
import kiseleva.dev.SurveyManagementSystem.dtos.questionDTO.AddQuestionRequestDTO;
import kiseleva.dev.SurveyManagementSystem.dtos.surveyDTO.AddSurveyRequestDTO;
import kiseleva.dev.SurveyManagementSystem.dtos.surveyDTO.GetSurveyResponseDTO;
import kiseleva.dev.SurveyManagementSystem.dtos.surveyDTO.UpdateSurveyRequestDTO;
import kiseleva.dev.SurveyManagementSystem.entities.ChoiceEntity;
import kiseleva.dev.SurveyManagementSystem.entities.QuestionEntity;
import kiseleva.dev.SurveyManagementSystem.entities.SurveyEntity;
import kiseleva.dev.SurveyManagementSystem.entities.UserEntity;
import kiseleva.dev.SurveyManagementSystem.repos.SurveyRepo;
import kiseleva.dev.SurveyManagementSystem.repos.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SurveyServiceImpl implements SurveyService {

    private final SurveyRepo surveyRepository;
    private final UserRepo userRepository;
    private final IMapper IMapper;
    private static final Logger log = (Logger) LoggerFactory.getLogger(SurveyServiceImpl.class);

    @Autowired
    public SurveyServiceImpl(SurveyRepo surveyRepository, UserRepo userRepository, IMapper IMapper) {
        this.surveyRepository = surveyRepository;
        this.userRepository = userRepository;
        this.IMapper = IMapper;
    }

    public List<GetSurveyResponseDTO> getAllSurveys(){
        log.info("Вызван метод getAllSurveys()");
        var surveys = surveyRepository.findAll();
        return IMapper.toSurveyDTOList(surveys);
    }

    public GetSurveyResponseDTO getSurveyById(Long id){
        log.info("Вызван метод getSurveyById(): id={}", id);
        var survey = surveyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Опрос не найден"));
        return IMapper.toSurveyDTO(survey);
    }


    @Transactional
    public GetSurveyResponseDTO createSurvey(AddSurveyRequestDTO dto){
        log.info("Вызван метод create()");

        SurveyEntity survey = IMapper.toSurveyEntity(dto);

        if(survey.getId() != null){
            throw new IllegalArgumentException("Id должно быть пустым!");
        }
        if (surveyRepository.existsByTitle(dto.getTitle())) {
            throw new IllegalArgumentException("Опрос с таким названием уже существует: " + dto.getTitle());
        }

        var auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        UserEntity creator = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден: " + username));

        survey.setCreatedBy(creator);
        survey.setCreatedAt(Instant.now());

        if(survey.getQuestions() != null){
                survey.getQuestions().forEach(question ->{
                    question.setSurvey(survey);
                    if(question.getChoices() != null){
                        question.getChoices().forEach(choice -> choice.setQuestion(question));
                    }
                });
        }
        SurveyEntity newSurvey = surveyRepository.save(survey);
        return IMapper.toSurveyDTO(newSurvey);
    }


    public void deleteSurvey(Long id){
        log.info("Вызван метод deleteSurvey(): id={}", id);
        if(surveyRepository.findById(id).isEmpty()){
            throw new IllegalArgumentException("Опрос не найден по заданному id=%s"
                    .formatted(id));
        }
        surveyRepository.deleteById(id);
    }

    @Transactional
    public GetSurveyResponseDTO updateSurveyy(Long id, UpdateSurveyRequestDTO surveyDTO){
        log.info("Вызван метод updateSurvey(): id={}", id);
        SurveyEntity survey = surveyRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException
                        ("Опрос не найден по заданному id=%s"
                .formatted(id)));
        if (surveyDTO.title != null && !surveyDTO.title.isEmpty()
                && !surveyDTO.title.equals(survey.getTitle())){
            Optional<GetSurveyResponseDTO> surveyDTOOpt = surveyRepository.findByTitle(surveyDTO.title);
            if(surveyDTOOpt.isPresent()){
                throw new IllegalArgumentException("Заголовок уже занят");
            }
            survey.setTitle(surveyDTO.title);
        }
        survey.setDescription(surveyDTO.description);
        return IMapper.toSurveyDTO(survey);
    }

    @Transactional
    public GetSurveyResponseDTO updateSurvey(Long id, AddSurveyRequestDTO dto) {
        log.info("Вызван метод updateSurvey() для id={}", id);

        SurveyEntity existingSurvey = surveyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Опрос с id " + id + " не найден"));

        if (!existingSurvey.getTitle().equals(dto.getTitle())
                && surveyRepository.existsByTitle(dto.getTitle())) {
            throw new IllegalArgumentException("Опрос с таким названием уже существует: " + dto.getTitle());
        }

        IMapper.updateSurveyFromDto(dto, existingSurvey);

        Set<Long> dtoQuestionIds = dto.getQuestions() == null ? Set.of() :
                dto.getQuestions().stream()
                        .map(AddQuestionRequestDTO::getId)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet());

        existingSurvey.getQuestions().removeIf(q -> !dtoQuestionIds.contains(q.getId()));

        if (dto.getQuestions() != null) {
            for (AddQuestionRequestDTO qDto : dto.getQuestions()) {
                QuestionEntity questionEntity;

                if (qDto.getId() != null) {
                    questionEntity = existingSurvey.getQuestions().stream()
                            .filter(q -> q.getId().equals(qDto.getId()))
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("Вопрос с id " + qDto.getId() + " не найден"));

                    IMapper.updateQuestionFromDto(qDto, questionEntity);
                } else {
                    questionEntity = IMapper.toQuestionEntity(qDto);
                    questionEntity.setSurvey(existingSurvey);
                    existingSurvey.getQuestions().add(questionEntity);
                }
                questionEntity.setSurvey(existingSurvey);

                if (questionEntity.getChoices() == null) {
                    questionEntity.setChoices(new ArrayList<>());
                }

                Set<Long> dtoChoiceIds = qDto.getChoices() == null ? Set.of() :
                        qDto.getChoices().stream()
                                .map(AddChoiceRequestDTO::getId)
                                .filter(Objects::nonNull)
                                .collect(Collectors.toSet());

                questionEntity.getChoices().removeIf(c -> !dtoChoiceIds.contains(c.getId()));

                if (qDto.getChoices() != null) {
                    for (AddChoiceRequestDTO cDto : qDto.getChoices()) {
                        ChoiceEntity choiceEntity;

                        if (cDto.getId() != null) {
                            choiceEntity = questionEntity.getChoices().stream()
                                    .filter(c -> c.getId().equals(cDto.getId()))
                                    .findFirst()
                                    .orElseThrow(() -> new IllegalArgumentException("Вариант с id " + cDto.getId() + " не найден"));

                            IMapper.updateChoiceFromDto(cDto, choiceEntity);
                        } else {
                            choiceEntity = IMapper.toChoiceEntity(cDto);
                            choiceEntity.setQuestion(questionEntity);
                            questionEntity.getChoices().add(choiceEntity);
                        }
                        choiceEntity.setQuestion(questionEntity);
                    }
                }
            }
        }

        SurveyEntity updatedSurvey = surveyRepository.save(existingSurvey);
        return IMapper.toSurveyDTO(updatedSurvey);
    }

}
