package app.entities;

import app.dtos.QuestionDTO;
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
    private String questionText;

    @Enumerated(EnumType.STRING)
    private DifficultyType difficultyType;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Answer> answers = new HashSet<>();

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
