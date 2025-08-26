package kiseleva.dev.SurveyManagementSystem.services;

import jakarta.transaction.Transactional;
import kiseleva.dev.SurveyManagementSystem.dtos.likeDTO.GetLikeResponseDTO;
import kiseleva.dev.SurveyManagementSystem.entities.LikeEntity;
import kiseleva.dev.SurveyManagementSystem.entities.SurveyEntity;
import kiseleva.dev.SurveyManagementSystem.repos.LikeRepo;
import kiseleva.dev.SurveyManagementSystem.repos.SurveyRepo;
import kiseleva.dev.SurveyManagementSystem.repos.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class LikeServiceImpl implements LikeService {
    private final LikeRepo likeRepository;
    private final SurveyRepo surveyRepository;
    private final SurveyRatingServiceImpl surveyRatingServiceImpl;
    private final UserRepo userRepository;
    private final IMapper IMapper;
    private static final Logger log = (Logger) LoggerFactory.getLogger(LikeServiceImpl.class);

    @Autowired
    public LikeServiceImpl(LikeRepo likeRepository, SurveyRepo surveyRepository, SurveyRatingServiceImpl surveyRatingServiceImpl, UserRepo userRepository, IMapper IMapper) {
        this.likeRepository = likeRepository;
        this.surveyRepository = surveyRepository;
        this.surveyRatingServiceImpl = surveyRatingServiceImpl;
        this.userRepository = userRepository;
        this.IMapper = IMapper;
    }

    @Transactional
    public GetLikeResponseDTO likeSurvey(Long surveyId){
        log.info("Вызван метод likeSurvey() по id опроса: id={}", surveyId);
        var survey = surveyRepository.findById(surveyId)
                .orElseThrow(()->new IllegalArgumentException("Опрос не найден"));

        var principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        if (likeRepository.existsBySurveyAndUser(survey, user)){
            throw new IllegalArgumentException("Like уже поставлен!");
        }
        var like = new LikeEntity();
        like.setCreatedAt(Instant.now());
        like.setSurvey(survey);
        like.setUser(user);

        var newLike = likeRepository.save(like);

        surveyRatingServiceImpl.invalidateRatingCache();

        return IMapper.toLikeResponseDTO(newLike);
    }


    public void unlikeSurvey(Long surveyId){
        log.info("Вызван метод unlikeSurvey() по id опроса: id={}", surveyId);
        SurveyEntity survey = surveyRepository.findById(surveyId)
                .orElseThrow(()->new IllegalArgumentException("Опрос не найден"));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userRepository.findByUsername(username)
                        .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        LikeEntity like = likeRepository.findBySurveyAndUser(survey, user)
                .orElseThrow(() -> new IllegalArgumentException("Лайк не найден"));

        likeRepository.delete(like);
        log.info("Лайк пользователя {} на опрос id={} успешно удалён", username, surveyId);
    }
}
