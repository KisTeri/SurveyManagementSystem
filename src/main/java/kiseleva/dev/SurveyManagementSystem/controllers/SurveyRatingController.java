package kiseleva.dev.SurveyManagementSystem.controllers;


import kiseleva.dev.SurveyManagementSystem.dtos.surveyDTO.GetSurveyForRatingDTO;
import kiseleva.dev.SurveyManagementSystem.services.SurveyRatingServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rating")
public class SurveyRatingController {

    private final SurveyRatingServiceImpl surveyRatingServiceImpl;

    public SurveyRatingController(SurveyRatingServiceImpl surveyRatingServiceImpl) {
        this.surveyRatingServiceImpl = surveyRatingServiceImpl;
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN') or isAnonymous()")
    @GetMapping("/top-likes")
    public ResponseEntity<List<GetSurveyForRatingDTO>> getTopByLikes() {
        return ResponseEntity.ok(surveyRatingServiceImpl.getTopByLikes());
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN') or isAnonymous()")
    @GetMapping("/top-comments")
    public ResponseEntity<List<GetSurveyForRatingDTO>> getTopByComments() {
        return ResponseEntity.ok(surveyRatingServiceImpl.getTopByComments());
    }
}
