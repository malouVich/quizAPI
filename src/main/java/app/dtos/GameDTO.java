package app.dtos;

import java.util.List;

public class GameDTO {
    private Long id;
    private int currentQuestionIndex;
    private int score;
    private boolean active;
    private List<QuestionDTO> questions;
    private QuestionDTO currentQuestion;
}