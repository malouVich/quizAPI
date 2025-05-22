package app.entities;

import app.dtos.QuestionDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonProperty("question")
    private String questionText;

    @Enumerated(EnumType.STRING)
    private DifficultyType difficultyType;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Answer> answers = new ArrayList<>();

    public Question(Question question) {
    }

    public Question(QuestionDTO questionDTO) {
        this.questionText = questionDTO.getQuestionText();
        this.difficultyType = questionDTO.getDifficultyType();
    }

    public enum DifficultyType{
        EASY, MEDIUM, HARD
    }

    // Bi-directional relationship
    public void addAnswer(Answer answer) {
        if ( answer != null) {
            this.answers.add(answer);
            answer.setQuestion(this);
        }
    }
}