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

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Answer> answers;

    public Question(Question question) {
    }

    public Question(QuestionDTO questionDTO) {
    }

    public enum DifficultyType{
        EASY, MEDIUM, HARD
    }

}
