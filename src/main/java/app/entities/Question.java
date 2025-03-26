package app.entities;

import app.dtos.QuestionDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String questionText;
    private DifficultyType difficultyType;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Answer> answers;

    public Question(Question question) {
    }

    public Question(QuestionDTO questionDTO) {
        this.id = questionDTO.getId();
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
