package kiseleva.dev.SurveyManagementSystem.services;

import jakarta.transaction.Transactional;
import kiseleva.dev.SurveyManagementSystem.dtos.commentDTO.AddCommentRequestDTO;
import kiseleva.dev.SurveyManagementSystem.dtos.commentDTO.GetCommentResponseDTO;
import kiseleva.dev.SurveyManagementSystem.entities.CommentEntity;
import kiseleva.dev.SurveyManagementSystem.entities.SurveyEntity;
import kiseleva.dev.SurveyManagementSystem.entities.UserEntity;
import kiseleva.dev.SurveyManagementSystem.repos.CommentRepo;
import kiseleva.dev.SurveyManagementSystem.repos.SurveyRepo;
import kiseleva.dev.SurveyManagementSystem.repos.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepo commentRepository;
    private final SurveyRepo surveyRepository;
    private final SurveyRatingServiceImpl surveyRatingServiceImpl;
    private final UserRepo userRepository;
    private final IMapper IMapper;
    private static final Logger log = (Logger) LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    public CommentServiceImpl(CommentRepo commentRepository, SurveyRepo surveyRepository, SurveyRatingServiceImpl surveyRatingServiceImpl, UserRepo userRepository, IMapper iMapper) {
        this.commentRepository = commentRepository;
        this.surveyRepository = surveyRepository;
        this.surveyRatingServiceImpl = surveyRatingServiceImpl;
        this.userRepository = userRepository;
        IMapper = iMapper;
    }

    public List<GetCommentResponseDTO> getAllComments(Long surveyId){
        log.info("Вызван метод getAllComments()");
        var comments = commentRepository.findBySurveyId(surveyId).stream().toList();
        return IMapper.toCommentResponseDTOList(comments);
    }

    @Transactional
    public GetCommentResponseDTO addComment(Long surveyId, AddCommentRequestDTO commentDTO){
        log.info("Вызван метод addComment()");
        SurveyEntity survey = surveyRepository.findById(surveyId)
                .orElseThrow(()->new IllegalArgumentException("Опрос не найден"));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        CommentEntity comment = new CommentEntity();
        comment.setSurvey(survey);
        comment.setUser(user);
        comment.setText(commentDTO.getText());
        comment.setCreatedAt(Instant.now());

        var newComment = commentRepository.save(comment);

        surveyRatingServiceImpl.invalidateRatingCache();
        return IMapper.toCommentResponseDTO(newComment);
    }

    public void deleteComment(Long surveyId, Long commentId){
        log.info("Вызван метод deleteComment()");
        SurveyEntity survey = surveyRepository.findById(surveyId)
                .orElseThrow(()->new IllegalArgumentException("Опрос не найден"));

        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Комментарий не найден"));

        if (!comment.getSurvey().equals(survey)) {
            throw new IllegalArgumentException("Комментарий не относится к данному опросу");
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        var currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        if(!comment.getUser().getId().equals(currentUser.getId())){
            throw new AccessDeniedException("Вы не можете удалить чужой комментарий");
        }

        commentRepository.delete(comment);
        log.info("Комментарий id={} успешно удалён пользователем {}", commentId, username);
    }
















}
