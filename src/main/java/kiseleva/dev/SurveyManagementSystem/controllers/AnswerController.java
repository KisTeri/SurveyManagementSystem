package kiseleva.dev.SurveyManagementSystem.controllers;

import kiseleva.dev.SurveyManagementSystem.dtos.answerDTO.AddAnswerRequestDTO;
import kiseleva.dev.SurveyManagementSystem.dtos.answerDTO.GetAnswerResponseDTO;
import kiseleva.dev.SurveyManagementSystem.services.AnswerServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/answers")
public class AnswerController {

    private final AnswerServiceImpl answerServiceImpl;

    public AnswerController(AnswerServiceImpl answerServiceImpl) {
        this.answerServiceImpl = answerServiceImpl;
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping
    public ResponseEntity<GetAnswerResponseDTO> createAnswer(@RequestBody AddAnswerRequestDTO dto) {
        return ResponseEntity.ok(answerServiceImpl.createAnswer(dto));
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/question/{id}")
    public ResponseEntity<List<GetAnswerResponseDTO>> getAnswersByQuestion(@PathVariable Long id) {
        return ResponseEntity.ok(answerServiceImpl.getAnswersByQuestion(id));
    }
}
