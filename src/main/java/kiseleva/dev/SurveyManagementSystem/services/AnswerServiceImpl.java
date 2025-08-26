package kiseleva.dev.SurveyManagementSystem.services;

import jakarta.transaction.Transactional;
import kiseleva.dev.SurveyManagementSystem.dtos.answerDTO.AddAnswerRequestDTO;
import kiseleva.dev.SurveyManagementSystem.dtos.answerDTO.GetAnswerResponseDTO;
import kiseleva.dev.SurveyManagementSystem.entities.AnswerEntity;
import kiseleva.dev.SurveyManagementSystem.entities.ChoiceEntity;
import kiseleva.dev.SurveyManagementSystem.entities.QuestionEntity;
import kiseleva.dev.SurveyManagementSystem.repos.AnswerRepo;
import kiseleva.dev.SurveyManagementSystem.repos.ChoiceRepo;
import kiseleva.dev.SurveyManagementSystem.repos.QuestionRepo;
import kiseleva.dev.SurveyManagementSystem.repos.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService{
    private final AnswerRepo answerRepository;
    private final ChoiceRepo choiceRepository;
    private final QuestionRepo questionRepository;
    private final UserRepo userRepository;
    private final IMapper IMapper;
    private static final Logger log = (Logger) LoggerFactory.getLogger(AnswerServiceImpl.class);

    @Autowired
    public AnswerServiceImpl(AnswerRepo answerRepository, ChoiceRepo choiceRepository, QuestionRepo questionRepository, UserRepo userRepository, IMapper iMapper) {
        this.answerRepository = answerRepository;
        this.choiceRepository = choiceRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        IMapper = iMapper;
    }

    @Transactional
    public GetAnswerResponseDTO createAnswer(AddAnswerRequestDTO dto) {
        log.info("Вызван метод createAnswer");

        QuestionEntity question = questionRepository.findById(dto.getQuestionId())
                .orElseThrow(() -> new IllegalArgumentException("Вопрос не найден: " + dto.getQuestionId()));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        List<ChoiceEntity> selectedChoices = null;
        if (dto.getSelectedChoices() != null && !dto.getSelectedChoices().isEmpty()) {
            selectedChoices = choiceRepository.findAllById(dto.getSelectedChoices());

            boolean invalidChoice = selectedChoices.stream()
                    .anyMatch(choice -> !choice.getQuestion().getId().equals(question.getId()));

            if (invalidChoice) {
                throw new IllegalArgumentException("Некоторые варианты ответа не принадлежат вопросу " + question.getId());
            }
        }

        AnswerEntity answer = IMapper.toAnswerEntity(dto);
        answer.setAnswer(dto.getAnswer());
        answer.setCreatedAt(Instant.now());
        answer.setQuestion(question);
        answer.setSelectedChoices(selectedChoices);
        answer.setUser(user);

        AnswerEntity newAnswer = answerRepository.save(answer);

        return IMapper.toAnswerResponseDTO(newAnswer);
    }

    public List<GetAnswerResponseDTO> getAnswersByQuestion(Long id) {
        log.info("Вызван метод getAnswersByQuestion");
        return answerRepository.findByQuestionId(id).stream()
                .map(IMapper::toAnswerResponseDTO)
                .toList();
    }
}
