package app.entities;
import lombok.*;
import jakarta.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String text;
    private boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;
}
