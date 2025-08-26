package kiseleva.dev.SurveyManagementSystem.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "survey_table")
public class SurveyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private Instant createdAt;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL)
    private List<QuestionEntity> questions;

    @OneToMany(mappedBy = "survey")
    private List<CommentEntity> comments;

    @OneToMany(mappedBy = "survey")
    private List<LikeEntity> likes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity createdBy;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public List<QuestionEntity> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionEntity> questions) {
        this.questions = questions;
    }

    public List<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
    }

    public List<LikeEntity> getLikes() {
        return likes;
    }

    public void setLikes(List<LikeEntity> likes) {
        this.likes = likes;
    }

    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
    }
}
