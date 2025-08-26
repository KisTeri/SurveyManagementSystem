package kiseleva.dev.SurveyManagementSystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "question_table")
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private Integer position;

    private QuestionType questionType;

    @JsonIgnore
    @ManyToOne
    private SurveyEntity survey;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<ChoiceEntity> choices;

    public enum QuestionType{ text, single_choice, multiple_choice}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public SurveyEntity getSurvey() {
        return survey;
    }

    public void setSurvey(SurveyEntity survey) {
        this.survey = survey;
    }

    public List<ChoiceEntity> getChoices() {
        return choices;
    }

    public void setChoices(List<ChoiceEntity> choices) {
        this.choices = choices;
    }
}
