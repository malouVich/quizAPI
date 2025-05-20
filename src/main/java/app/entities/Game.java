package app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int currentQuestionIndex;
    private int score;
    private boolean active;

    @OneToMany
    private List<Question> questions;

    // Getters, setters, constructors
}

