package kiseleva.dev.SurveyManagementSystem.controllers;

import kiseleva.dev.SurveyManagementSystem.dtos.commentDTO.AddCommentRequestDTO;
import kiseleva.dev.SurveyManagementSystem.dtos.likeDTO.GetLikeResponseDTO;
import kiseleva.dev.SurveyManagementSystem.dtos.surveyDTO.AddSurveyRequestDTO;
import kiseleva.dev.SurveyManagementSystem.dtos.commentDTO.GetCommentResponseDTO;
import kiseleva.dev.SurveyManagementSystem.dtos.surveyDTO.GetSurveyResponseDTO;
import kiseleva.dev.SurveyManagementSystem.services.CommentServiceImpl;
import kiseleva.dev.SurveyManagementSystem.services.LikeServiceImpl;
import kiseleva.dev.SurveyManagementSystem.services.SurveyServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/surveys")
public class SurveyController {
    private final SurveyServiceImpl surveyServiceImpl;
    private final LikeServiceImpl likeServiceImpl;
    private final CommentServiceImpl commentServiceImpl;

    public SurveyController(SurveyServiceImpl surveyServiceImpl, LikeServiceImpl likeServiceImpl, CommentServiceImpl commentServiceImpl) {
        this.surveyServiceImpl = surveyServiceImpl;
        this.likeServiceImpl = likeServiceImpl;
        this.commentServiceImpl = commentServiceImpl;
    }

    @PreAuthorize("permitAll()")
    @GetMapping
    public ResponseEntity<List<GetSurveyResponseDTO>> getAllSurveys(){
        return ResponseEntity.ok(surveyServiceImpl.getAllSurveys());
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public ResponseEntity<GetSurveyResponseDTO> getSurveyById(@PathVariable Long id){
        return ResponseEntity.ok(surveyServiceImpl.getSurveyById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<GetSurveyResponseDTO> createSurvey(@RequestBody AddSurveyRequestDTO survey){
        return ResponseEntity.ok(surveyServiceImpl.createSurvey(survey));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{surveyId}")
    public ResponseEntity<Void> deleteSurvey(@PathVariable("surveyId") Long id)
    {
        surveyServiceImpl.deleteSurvey(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<GetSurveyResponseDTO> updateSurvey(
            @PathVariable Long id,
            @RequestBody AddSurveyRequestDTO dto) {
        GetSurveyResponseDTO updatedDto = surveyServiceImpl.updateSurvey(id, dto);
        return ResponseEntity.ok(updatedDto);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/{id}/like")
    public ResponseEntity<GetLikeResponseDTO> likeSurvey(@PathVariable("id") Long surveyId){
        return ResponseEntity.ok(likeServiceImpl.likeSurvey(surveyId));
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/{id}/unlike")
    public ResponseEntity<Void> unlikeSurvey(@PathVariable Long id){
        likeServiceImpl.unlikeSurvey(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}/comments")
    public ResponseEntity<List<GetCommentResponseDTO>> getComments(@PathVariable Long id) {
        return ResponseEntity.ok(commentServiceImpl.getAllComments(id));
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/{id}/comment")
    public ResponseEntity<GetCommentResponseDTO> addComment(@PathVariable Long id,
                                                            @RequestBody AddCommentRequestDTO comment){
        return ResponseEntity.ok(commentServiceImpl.addComment(id, comment));
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/{surveyId}/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long surveyId, @PathVariable Long id){
        commentServiceImpl.deleteComment(surveyId, id);
        return ResponseEntity.noContent().build();
    }
}
