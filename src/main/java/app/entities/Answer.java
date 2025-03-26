package app.entities;
import app.dtos.AnswerDTO;
import lombok.*;
import jakarta.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String answerText;
    private Boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    public Answer(AnswerDTO answerDTO) {
        this.answerText = answerDTO.getAnswerText();
        this.isCorrect = answerDTO.getIsCorrect();
    }


}
