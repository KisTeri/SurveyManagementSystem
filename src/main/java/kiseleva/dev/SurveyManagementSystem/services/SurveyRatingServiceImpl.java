package kiseleva.dev.SurveyManagementSystem.services;


import kiseleva.dev.SurveyManagementSystem.dtos.surveyDTO.GetSurveyForRatingDTO;
import kiseleva.dev.SurveyManagementSystem.repos.SurveyRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyRatingServiceImpl {
    private final SurveyRepo surveyRepository;
    private final IMapper IMapper;
    private static final Logger log = (Logger) LoggerFactory.getLogger(SurveyRatingServiceImpl.class);

    @Autowired
    public SurveyRatingServiceImpl(SurveyRepo surveyRepository, IMapper IMapper) {
        this.surveyRepository = surveyRepository;
        this.IMapper = IMapper;
    }

    @Cacheable("topLikes")
    public List<GetSurveyForRatingDTO> getTopByLikes() {
        log.info("Вызван метод getTopByLikes()");
        return surveyRepository.findTopByLikes()
                .stream()
                .map(IMapper::toSurveyForRatingDTO)
                .toList();
    }

    @Cacheable("topComments")
    public List<GetSurveyForRatingDTO> getTopByComments() {
        log.info("Вызван метод getTopByComments()");
        return surveyRepository.findTopByComments()
                .stream()
                .map(IMapper::toSurveyForRatingDTO)
                .toList();
    }

    @CacheEvict(value = {"topLikes", "topComments"}, allEntries = true)
    public void invalidateRatingCache() {
    }
}
