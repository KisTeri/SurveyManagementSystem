package kiseleva.dev.SurveyManagementSystem.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "answer_table")
public class AnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String answer;

    private Instant createdAt;

    @ManyToMany
    @JoinTable(
            name = "answer_choices_table",
            joinColumns = @JoinColumn(name = "answer_id"),
            inverseJoinColumns = @JoinColumn(name = "choice_id")
    )
    private List<ChoiceEntity> selectedChoices;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionEntity question;

    @ManyToOne
    private UserEntity user;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public List<ChoiceEntity> getSelectedChoices() {
        return selectedChoices;
    }

    public void setSelectedChoices(List<ChoiceEntity> selectedChoices) {
        this.selectedChoices = selectedChoices;
    }

    public QuestionEntity getQuestion() {
        return question;
    }

    public void setQuestion(QuestionEntity question) {
        this.question = question;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
